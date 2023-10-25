using System.Collections;
using System.Collections.Generic;
using UnityEngine;



public class AdversarialObject : IPinButton
{
    
    public override void printUI(){
        if (gameObject.name == "Transparent_1"){
            // simulated raycats
            Ray ray = new Ray(gameObject.transform.position, Vector3.forward);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit, Mathf.Infinity))
            {
                var hitResult = hit.collider.GetComponent<VictimObject>();
                if (hitResult){
                    hitResult.printUI();
                }
            }   
            Logger.Instance.LogInfo("transparent1 triggered");
        }

        if (gameObject.name == "Transparent_2"){
            // simulated raycats
            Ray ray = new Ray(gameObject.transform.position, Vector3.forward);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit, Mathf.Infinity))
            {
                var hitResult = hit.collider.GetComponent<VictimObject>();
                if (hitResult){
                    hitResult.printUI();
                }
            }   
            Logger.Instance.LogInfo("transparent2 triggered");
        }

        if (gameObject.name == "Transparent_3"){
            // simulated raycats
            Ray ray = new Ray(gameObject.transform.position, Vector3.forward);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit, Mathf.Infinity))
            {
                var hitResult = hit.collider.GetComponent<VictimObject>();
                if (hitResult){
                    hitResult.printUI();
                }
            }   
            Logger.Instance.LogInfo("transparent3 triggered");
        }

        if (gameObject.name == "Transparent_4"){
            // simulated raycats
            Ray ray = new Ray(gameObject.transform.position, Vector3.forward);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit, Mathf.Infinity))
            {
                var hitResult = hit.collider.GetComponent<VictimObject>();
                if (hitResult){
                    hitResult.printUI();
                }
            }   
            Logger.Instance.LogInfo("transparent4 triggered");
        }

         if (gameObject.name == "Transparent_5"){

            Logger.Instance.LogInfo("blocked");
        }
    }

}
