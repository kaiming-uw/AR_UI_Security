using System.Collections;
using System.Collections.Generic;
using UnityEngine;



public abstract class IPinButton : MonoBehaviour {
    public abstract void printUI();
}


public class VictimObject : IPinButton
{

    public override void printUI(){
        Logger.Instance.LogInfo("Victim print UI triggered");
        Logger.Instance.LogInfo("User Entered: " + gameObject.name);

    }
}
