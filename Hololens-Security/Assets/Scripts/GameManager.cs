using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using Microsoft.MixedReality.Toolkit.UI;
using Microsoft.MixedReality.Toolkit.Input;
using Microsoft.MixedReality.Toolkit.Experimental.UI;
using UnityEngine.InputSystem.HID;
using static Unity.VisualScripting.Member;

public class GameManager : MonoBehaviour
{
    [SerializeField] TextMeshProUGUI text;
    [SerializeField] Transform mixedRealitySceneContent;
    [SerializeField] GameObject redCube;
    [SerializeField] GameObject blueCube;
    [SerializeField] GameObject invisibleCube;
    [SerializeField] GameObject blockCube;
    [SerializeField] Material originalRedMaterial;
    [SerializeField] Material originalBlueMaterial;
    GameObject localRedCube;
    GameObject localBlueCube;
    GameObject localInvisibleCube;
    GameObject localRedBlockCube;
    GameObject localBlueBlockCube;

    float number_of_rays = 10000;
    float totalAngle = 360;

    string prevText = "";

    public void recordTouch(GameObject obj)
    {
        text.text += "Touched: " + obj.name + "\n";
    }

    public void recordPoint(GameObject obj)
    {
        text.text += "Pointed at: " + obj.name + "\n";
    }

    public void recordGrab(GameObject obj)
    {
        text.text += "Grabbed: " + obj.name + "\n";
    }

    public void recordGaze(GameObject obj)
    {
        text.text += "Gazed at: " + obj.name + "\n";
    }

    public void shootTargettedRaycast()
    {
        if (localRedCube != null)
        {
            Vector3 fromPosition = Camera.main.transform.position;
            Vector3 toPosition = localRedCube.transform.position;
            Vector3 dir = toPosition - fromPosition;

            RaycastHit hit;
            if (Physics.Raycast(Camera.main.transform.position, dir, out hit, Mathf.Infinity))
            {
                text.text += "Raycast: " + hit.transform.name + "\n";
            }
        }
    }

    public void shootRandomRaycasts()
    {
        print("Shooting Raycast!");

        float delta = totalAngle / number_of_rays;

        var hashSet = new HashSet<string>();
        for (int i = 0; i < number_of_rays; i++)
        {
            var dir = Quaternion.Euler(i * delta, i * delta, i * delta) * new Vector3(1f, 1f, 1f);

            RaycastHit hit;
            if (Physics.Raycast(Camera.main.transform.position, dir, out hit, Mathf.Infinity))
            {
                hashSet.Add(hit.transform.name);
            }
        }

        string hitObjects = "";
        foreach (string s in hashSet)
        {
            hitObjects += (s + " ");
        }

        if (hitObjects != "")
            text.text += "Raycast: " + hitObjects + "\n";
    }

    public void spawnRedCube()
    {
        if (localRedCube == null)
        {
            localRedCube = Instantiate(redCube, mixedRealitySceneContent);
            localRedCube.transform.position = new Vector3(0f, 0f, 2f);
            localRedCube.transform.rotation = Quaternion.identity;

            localRedCube.GetComponent<ObjectManipulator>().OnManipulationStarted.AddListener((ManipulationEventData m) => recordGrab(localRedCube));
            localRedCube.GetComponent<EyeTrackingTarget>().OnLookAtStart.AddListener(() => recordGaze(localRedCube));
            localRedCube.GetComponent<TouchHandler>().OnTouchStarted.AddListener((HandTrackingInputEventData h) => recordGaze(localRedCube));

            text.text += "Spawning Red Cube! \n";
        }
    }

    public void spawnBlueCube()
    {
        if (localBlueCube == null)
        {
            localBlueCube = Instantiate(blueCube, mixedRealitySceneContent);
            localBlueCube.transform.position = new Vector3(0f, 0f, 2f);
            localBlueCube.transform.rotation = Quaternion.identity;

            localBlueCube.GetComponent<ObjectManipulator>().OnManipulationStarted.AddListener((ManipulationEventData m) => recordGrab(localBlueCube));
            localBlueCube.GetComponent<EyeTrackingTarget>().OnLookAtStart.AddListener(() => recordGaze(localBlueCube));
            localBlueCube.GetComponent<TouchHandler>().OnTouchStarted.AddListener((HandTrackingInputEventData h) => recordGaze(localBlueCube));

            text.text += "Spawning Blue Cube! \n";
        }
    }

    public void redZeroAlpha()
    {
        if (localRedCube != null) {
            MeshRenderer renderer = localRedCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                Color oldColor = renderer.material.color;
                renderer.material.color = new Color(oldColor.r, oldColor.g, oldColor.b, 0.0f);

                text.text += "Changed Red Cube's Alpha to 0! \n";
            }
        }
    }

    public void blueZeroAlpha()
    {
        if (localBlueCube != null)
        {
            MeshRenderer renderer = localBlueCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                Color oldColor = renderer.material.color;
                renderer.material.color = new Color(oldColor.r, oldColor.g, oldColor.b, 0.0f);

                text.text += "Changed Blue Cube's Alpha to 0! \n";
            }
        }
    }

    public void redNullMaterial()
    {
        if (localRedCube != null) {
            MeshRenderer renderer = localRedCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.material = null;
                text.text += "Changed Red Cube's Material to Null! \n";
            }
        }
    }

    public void blueNullMaterial()
    {
        if (localBlueCube != null)
        {
            MeshRenderer renderer = localBlueCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.material = null;
                text.text += "Changed Blue Cube's Material to Null! \n";
            }
        }
    }

    public void redDisableRenderer()
    {
        if (localRedCube != null)
        {
            MeshRenderer renderer = localRedCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.enabled = false;
                text.text += "Disabled Red Cube's Renderer! \n";
            }
        }
    }

    public void blueDisableRenderer()
    {
        if (localBlueCube != null)
        {
            MeshRenderer renderer = localBlueCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.enabled = false;
                text.text += "Disabled Blue Cube's Renderer! \n";
            }
        }
    }

    public void spawnRedBlockCube()
    {
        if (localRedCube != null && localRedBlockCube == null)
        {
            localRedBlockCube = Instantiate(blockCube, localRedCube.transform.position, localRedCube.transform.rotation);
        }
    }

    public void spawnBlueBlockCube()
    {
        if (localBlueCube != null && localBlueBlockCube == null)
        {
            localBlueBlockCube = Instantiate(blockCube, localBlueCube.transform.position, localBlueCube.transform.rotation);
        }
    }

    public void spawnInvisibleCube()
    {
        if (localInvisibleCube == null)
        {
            /*
            if (localRedCube != null)
            {
                localInvisibleCube = Instantiate(invisibleCube, localRedCube.transform.position, Quaternion.identity);
                text.text += "Invisible Cube Planted! \n";
            }
            else if (localBlueCube != null) {
                localInvisibleCube = Instantiate(invisibleCube, localBlueCube.transform.position, Quaternion.identity);
                text.text += "Invisible Cube Planted! \n";
            }
            */

            localInvisibleCube = Instantiate(invisibleCube, 
                                             new Vector3(Camera.main.transform.position.x, 
                                                         Camera.main.transform.position.y - (invisibleCube.transform.localScale.y / 2), 
                                                         Camera.main.transform.position.z), 
                                             Camera.main.transform.rotation, 
                                             Camera.main.transform);
        }
    }

    public void destroyInvisibleCube()
    {
        if (localInvisibleCube != null)
        {
            Destroy(localInvisibleCube);
            localInvisibleCube = null;
            text.text += "No More Invisible Cube! \n";
        }
    }

    public void interceptTextInput(MRTKTMPInputField inputText)
    {
        string s = inputText.text;
        if (!s.Equals(prevText))
        {
            if (s.Length > prevText.Length)
            {
                text.text += "Typed character " + s[s.Length - 1] + "\n";
            }
            else
            {
                text.text += "Removed character " + prevText[prevText.Length - 1] + "\n";
            }

            prevText = s;
        }
    }
    
    public void resetObjects()
    {
        if (localRedCube != null)
        {
            localRedCube.transform.position = new Vector3(0f, 0f, 2f);
            localRedCube.transform.rotation = Quaternion.identity;

            MeshRenderer renderer = localRedCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.enabled = true;
                renderer.material = originalRedMaterial;
            }
        }

        if (localBlueCube != null)
        {
            localBlueCube.transform.position = new Vector3(0f, 0f, 2f);
            localBlueCube.transform.rotation = Quaternion.identity;

            MeshRenderer renderer = localBlueCube.GetComponent<MeshRenderer>();
            if (renderer != null)
            {
                renderer.enabled = true;
                renderer.material = originalBlueMaterial;
            }
        }

        if (localInvisibleCube != null)
        {
            Destroy(localInvisibleCube);
            localInvisibleCube = null;
        }

        if (localRedBlockCube != null)
        {
            Destroy(localRedBlockCube);
            localRedBlockCube = null;
        }

        if (localBlueBlockCube != null)
        {
            Destroy(localBlueBlockCube);
            localBlueBlockCube = null;
        }

        text.text += "Reset All Objects! \n";
    }

    public void resetTextPanel()
    {
        text.text = "";
    }
}
