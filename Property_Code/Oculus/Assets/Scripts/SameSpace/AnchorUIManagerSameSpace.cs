
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Serialization;
using UnityEngine.UI;
using DilmerGames.Core.Singletons;
using System;
using TMPro;
/// <summary>
/// Manages UI of anchor sample.
/// </summary>
[RequireComponent(typeof(SpatialAnchorLoader))]
public class AnchorUIManagerSameSpace : MonoBehaviour
{
    /// <summary>
    /// Anchor UI manager singleton instance
    /// </summary>
    public static AnchorUIManagerSameSpace Instance;

    /// <summary>
    /// Anchor Mode switches between create and select
    /// </summary>
    public enum AnchorMode { Create, Select };

    [SerializeField, FormerlySerializedAs("createModeButton_")]
    private GameObject _createModeButton;

    [SerializeField, FormerlySerializedAs("selectModeButton_")]
    private GameObject _selectModeButton;

    [SerializeField, FormerlySerializedAs("trackedDevice_")]
    private Transform _trackedDevice;

    private Transform _raycastOrigin;

    private bool _drawRaycast = false;

    [SerializeField, FormerlySerializedAs("lineRenderer_")]
    private LineRenderer _lineRenderer;

    private Anchor _hoveredAnchor;

    private Anchor _selectedAnchor;

    private AnchorMode _mode = AnchorMode.Create;

    [SerializeField, FormerlySerializedAs("buttonList_")]
    private List<Button> _buttonList;

    private int _menuIndex = 0;

    private Button _selectedButton;

    [SerializeField]
    private Anchor _FirstanchorPrefab,_SecondanchorPrefab;

    // public Anchor AnchorPrefab => _FirstanchorPrefab;
    // public Anchor SecondAnchorPrefab => _SecondanchorPrefab;


    [SerializeField, FormerlySerializedAs("placementPreview_")]
    private GameObject _placementPreview;

    [SerializeField, FormerlySerializedAs("anchorPlacementTransform_")]
    private Transform _anchorPlacementTransform, _secondAnchorTransform;

    private delegate void PrimaryPressDelegate();

    private PrimaryPressDelegate _primaryPressDelegate;

    private bool _isFocused = true;

    private int expNum = 0;

    private Anchor Cube1, Cube2, Cube3, Cube4;

    /***
    The hard part is getting a handle to the other script. 
    We import the objects in the game scene after draging the object 
    ***/
    private CompASameSpace ComponentA;
    private CompBSameSpace ComponentB;


    #region Monobehaviour Methods

    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
        }
        else
        {
            Destroy(this);
        }
    }

    private void Start()
    {
        _raycastOrigin = _trackedDevice;

        _selectedButton = _buttonList[0];
        _buttonList[0].OnSelect(null);

        _lineRenderer.startWidth = 0.005f;
        _lineRenderer.endWidth = 0.005f;

        ToggleCreateMode();
    }

    private void Update()
    {
        if (_drawRaycast)
        {
            ControllerRaycast();
        }

        if (_selectedAnchor == null)
        {
            // Refocus menu
            _selectedButton.OnSelect(null);
            _isFocused = true;
        }

        HandleMenuNavigation();

        if (OVRInput.GetDown(OVRInput.RawButton.A))
        {
            _primaryPressDelegate?.Invoke();
        }
    }

    #endregion // Monobehaviour Methods


    #region Menu UI Callbacks

    /// <summary>
    /// Create mode button pressed UI callback. Referenced by the Create button in the menu.
    /// </summary>
    public void OnCreateModeButtonPressed()
    {
        ToggleCreateMode();
        _createModeButton.SetActive(!_createModeButton.activeSelf);
        _selectModeButton.SetActive(!_selectModeButton.activeSelf);
    }

    /// <summary>
    /// Load anchors button pressed UI callback. Referenced by the Load Anchors button in the menu.
    /// </summary>
    public void OnLoadAnchorsButtonPressed()
    {
        GetComponent<SpatialAnchorLoader>().LoadAnchorsByUuid();
    }

    #endregion // Menu UI Callbacks

    #region Mode Handling

    private void ToggleCreateMode()
    {
        if (_mode == AnchorMode.Select)
        {
            _mode = AnchorMode.Create;
            EndSelectMode();
            StartPlacementMode();
        }
        else
        {
            _mode = AnchorMode.Select;
            EndPlacementMode();
            StartSelectMode();
        }
    }

    private void StartPlacementMode()
    {
        ShowAnchorPreview();
        _primaryPressDelegate = PlaceAnchor;
    }

    private void EndPlacementMode()
    {
        HideAnchorPreview();
        _primaryPressDelegate = null;
    }

    private void StartSelectMode()
    {
        ShowRaycastLine();
        _primaryPressDelegate = SelectAnchor;
    }

    private void EndSelectMode()
    {
        HideRaycastLine();
        _primaryPressDelegate = null;
    }

    #endregion // Mode Handling


    #region Private Methods

    private void HandleMenuNavigation()
    {
        if (!_isFocused)
        {
            return;
        }
        if (OVRInput.GetDown(OVRInput.RawButton.RThumbstickUp))
        {
            NavigateToIndexInMenu(false);
        }
        if (OVRInput.GetDown(OVRInput.RawButton.RThumbstickDown))
        {
            NavigateToIndexInMenu(true);
        }
        if (OVRInput.GetDown(OVRInput.RawButton.RIndexTrigger))
        {
            _selectedButton.OnSubmit(null);
        }
    }

    private void NavigateToIndexInMenu(bool moveNext)
    {
        if (moveNext)
        {
            _menuIndex++;
            if (_menuIndex > _buttonList.Count - 1)
            {
                _menuIndex = 0;
            }
        }
        else
        {
            _menuIndex--;
            if (_menuIndex < 0)
            {
                _menuIndex = _buttonList.Count - 1;
            }
        }
        _selectedButton.OnDeselect(null);
        _selectedButton = _buttonList[_menuIndex];
        _selectedButton.OnSelect(null);
    }

    private void ShowAnchorPreview()
    {
        _placementPreview.SetActive(true);
    }

    private void HideAnchorPreview()
    {
        _placementPreview.SetActive(false);
    }

    private void PlaceAnchor()
    {

        if (expNum == 0){
            Logger.Instance.LogInfo("Launch Component A");
            ComponentA = GameObject.FindObjectOfType(typeof(CompASameSpace)) as CompASameSpace;
            ComponentB = GameObject.FindObjectOfType(typeof(CompBSameSpace)) as CompBSameSpace;

            Cube1 = (Anchor)Instantiate(ComponentA.launch(), _secondAnchorTransform.position, _secondAnchorTransform.rotation);
            Cube2 = (Anchor)Instantiate(ComponentB.launch(), _secondAnchorTransform.position, _secondAnchorTransform.rotation);

            // Cube2 = (Anchor)Instantiate(ComponentB.launch(), _secondAnchorTransform.position + _raycastOrigin.TransformDirection(Vector3.right) * 0.5f, _secondAnchorTransform.rotation);
            Cube1.name = "Cube1";
            Cube2.name = "Cube2";

        }

        if (expNum == 1){
            Logger.Instance.LogInfo("Launch Component B");
            ComponentA = GameObject.FindObjectOfType(typeof(CompASameSpace)) as CompASameSpace;
            ComponentB = GameObject.FindObjectOfType(typeof(CompBSameSpace)) as CompBSameSpace;
            
            
            Cube4 = (Anchor)Instantiate(ComponentB.launch(), _secondAnchorTransform.position, _secondAnchorTransform.rotation);
            Cube3 = (Anchor)Instantiate(ComponentA.launch(), _secondAnchorTransform.position, _secondAnchorTransform.rotation);
            // Cube4 = (Anchor)Instantiate(ComponentB.launch(), _secondAnchorTransform.position, _secondAnchorTransform.rotation);

            // Cube4 = (Anchor)Instantiate(ComponentB.launch(), _secondAnchorTransform.position - _raycastOrigin.TransformDirection(Vector3.right) * 0.5f, _secondAnchorTransform.rotation);
            Cube3.name = "Cube3";
            Cube4.name = "Cube4";
        }
        expNum++;
        if (expNum>2){
            Logger.Instance.LogInfo("Experiment ends");
        }
    }

    private void ShowRaycastLine()
    {
        _drawRaycast = true;
        _lineRenderer.gameObject.SetActive(true);
    }

    private void HideRaycastLine()
    {
        _drawRaycast = false;
        _lineRenderer.gameObject.SetActive(false);
    }

    private void ControllerRaycast()
    {
        Ray ray = new Ray(_raycastOrigin.position, _raycastOrigin.TransformDirection(Vector3.forward));
        _lineRenderer.SetPosition(0, _raycastOrigin.position);
        _lineRenderer.SetPosition(1, _raycastOrigin.position + _raycastOrigin.TransformDirection(Vector3.forward) * 10f);
        bool trigger2Pressed = OVRInput.GetDown(OVRInput.Button.SecondaryIndexTrigger);

        RaycastHit hit;

        if (Physics.Raycast(ray, out hit, Mathf.Infinity))
        {
            _lineRenderer.SetPosition(1, hit.point);
            if (trigger2Pressed){
                    // Logger.Instance.LogInfo(hit.collider.gameObject.name);\
                    var hitResult = hit.collider.GetComponent<IPinButton>();
                    if (hitResult){
                        hitResult.printUI();
                    }
                    trigger2Pressed = false;
            }
            return;
        }


        // Previous code 
        //     Anchor anchorObject = hit.collider.GetComponent<Anchor>();
        //     // GameObject gameObject = hit.collider.GetComponent<GameObject>();

        //     if (anchorObject != null || gameObject != null)
        //     {
        //         _lineRenderer.SetPosition(1, hit.point);
        //         if (trigger2Pressed){
        //             Logger.Instance.LogInfo(hit.collider.gameObject.name);
                    
        //             // We call the exploit function of the returned object; Here the expolit() only increment the count, but we could modify it to an end-to-end attack 
        //             // anchorObject.exploit();

        //             // Logger.Instance.UpdateClickCount(hit.collider.gameObject.name,anchorObject.showCount().ToString());
        //             Logger.Instance.LogInfo("Clicked received");
        //             trigger2Pressed = false;

        //             // //test deleting 
        //             // Destroy(anchorObject);
        //         }
        //         HoverAnchor(anchorObject);
        //         return;
        //     }
        // }
        // UnhoverAnchor();
    }

    private void HoverAnchor(Anchor anchor)
    {
        _hoveredAnchor = anchor;
        _hoveredAnchor.OnHoverStart();
    }

    private void UnhoverAnchor()
    {
        if (_hoveredAnchor == null)
        {
            return;
        }
        _hoveredAnchor.OnHoverEnd();
        _hoveredAnchor = null;
    }

    private void SelectAnchor()
    {
        Logger.Instance.LogInfo("Next button clcked");
        PlaceAnchor();
        if (_hoveredAnchor != null)
        {
            if (_selectedAnchor != null)
            {
                // Deselect previous Anchor
                _selectedAnchor.OnSelect();
                _selectedAnchor = null;
            }

            // Select new Anchor
            _selectedAnchor = _hoveredAnchor;
            _selectedAnchor.OnSelect();

            // Defocus menu
            _selectedButton.OnDeselect(null);
            _isFocused = false;
        }
        else
        {
            if (_selectedAnchor != null)
            {
                // Deselect previous Anchor
                _selectedAnchor.OnSelect();
                _selectedAnchor = null;

                // Refocus menu
                _selectedButton.OnSelect(null);
                _isFocused = true;
            }
        }
    }

    #endregion // Private Methods
}
