using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestRaycast : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }
    public float speed = 10.5f; 

    // Update is called once per frame
    void Update()
    {
         if ( Input.GetMouseButtonDown (0)){ 
        RaycastHit hit; 
        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition); 
        if ( Physics.Raycast (ray,out hit,100.0f)) {
            Debug.Log("You selected the " + hit.transform.name); // ensure you picked right object
        }
        }


         Vector3 pos = transform.position;

        if (Input.GetKey("w")) 
        {
            pos.y += speed * Time.deltaTime;
        }
        if (Input.GetKey("s")) 
        {
            pos.y -= speed * Time.deltaTime;
        }
        if (Input.GetKey("d")) 
        {
            pos.x += speed * Time.deltaTime;
        }
        if (Input.GetKey("a")) 
        {
            pos.x -= speed * Time.deltaTime;
        }
        if (Input.GetKey("q")) 
        {
            pos.z += speed * Time.deltaTime;
        }
        if (Input.GetKey("e")) 
        {
            pos.z -= speed * Time.deltaTime;
        }

        transform.position = pos;
    }
}
