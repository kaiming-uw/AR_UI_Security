using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CompBInvisibility : MonoBehaviour
{

    [SerializeField]
    public Anchor Cube2_Material, Cube3_1_Material,Cube3_2_Material,Cube3_3_Material,Cube3_4_Material;





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
        return Cube2_Material;
    }

    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    public Anchor next(int experimentNumber){
        switch (experimentNumber)
        {
        case 5:
            return Cube3_4_Material;
        case 4:
            // t = Resource.Load<Texture>("Assets/Materials/transparent.png");
            // Cube3_3_Material.material.mainTexture = t;
            return Cube3_3_Material;
        case 3:
            return Cube3_2_Material;
        case 2:
            return Cube3_1_Material;
        default:
            Logger.Instance.LogInfo("Waiting for valid experiment number");
            return null;
        }

    }



}
