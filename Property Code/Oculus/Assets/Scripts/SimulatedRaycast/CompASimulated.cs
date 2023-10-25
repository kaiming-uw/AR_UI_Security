using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CompASimulated : MonoBehaviour
{

    [SerializeField]
    public Anchor _FirstanchorPrefab;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public Anchor launch(){
        return _FirstanchorPrefab;
    }

        /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    public void next(int experimentNumber){

    }

}
