using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CompBSameSpace : MonoBehaviour
{

    [SerializeField]
    public Anchor _SecondanchorPrefab;


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    /**
     * Launches this component and creates associated anchorPrefab for this component to prepare for experiments 
     * @returns anchorPrefab created upon Component launch
     */
    public Anchor launch(){
        return _SecondanchorPrefab;
    }

    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    public void next(int experimentNumber){

    }
}
