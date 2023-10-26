// using System.Collections.Generic;
// using System.Linq;
// using TMPro;
// using UnityEngine;
// using UnityEngine.UI;

// public class SpatialAnchorsPlacement : MonoBehaviour
// {
//     [SerializeField]
//     private Transform anchorTransform;

//     [SerializeField]
//     private Button eraseAllAnchorsFromStorage;

//     [SerializeField]
//     private Button eraseAllAnchorsFromMemory;

//     [SerializeField]
//     private Button Next;

//     [SerializeField]
//     private Button Launch;

//     private Dictionary<ulong, GameObject> anchorsToBeSaved = new Dictionary<ulong, GameObject>();

//     // private int prevResolvedAnchorCount = 0;

//     private void Awake()
//     {
//         eraseAllAnchorsFromMemory.onClick.AddListener(() => DeleteAll());
//         eraseAllAnchorsFromStorage.onClick.AddListener(() => DeleteAll(true));
//         resolveAllAnchors.onClick.AddListener(() => ResolveAllAnchors());
//         Next.onClick.AddListener(() => Next(0));
//     }

//     private void Next(int expNum){

//     }

//     private void DeleteAll(bool fromStorage = false)
//     {
//         // var anchors = SpatialAnchorsManager.Instance.resolvedAnchors.Keys.ToList();

//         // Logger.Instance.LogInfo($"DeleteAll(fromStorage:{fromStorage}) anchors found:{anchors.Count}");

//         // foreach (var anchor in anchors)
//         // {
//         //     Logger.Instance.LogInfo($"Attempting to delete anchor:{anchor}");

//         //     if (fromStorage)
//         //         SpatialAnchorsManager.Instance.EraseAnchor(anchor);
//         //     else
//         //         SpatialAnchorsManager.Instance.DestroyAnchor(anchor);           

//         //     Logger.Instance.LogInfo($"Finished deleting anchor: {anchor}");
//         // }
//     }

//     // private void ResolveAllAnchors() => SpatialAnchorsManager.Instance.QueryAllLocalAnchors();

//     void Update()
//     {

//     }

//     private void CreateAnchor()
//     {

//     }

//     private void AdditionalFeatures(bool state = true)
//     {

//     }

//     private void SaveAllAnchors()
//     {

//     }
// }
