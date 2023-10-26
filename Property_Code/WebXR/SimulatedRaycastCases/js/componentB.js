import {makeCube, makeClickText, showToast} from './utils.js';

/**
 * The following class is developed according to the following interface
 * 
 * interface ARComponent:
 *      Methods
 *      launch():
 *          Zero argument function that launches a component on startup of the harness.
 *          returns a mesh object
 *      next(experimentNumber):
 *          Preps this component for the next experiment, and then carries out the required behavior 
 *          for this component for the next experiment.
 *          
 * The ARComponent interface is intended to allow for easy development of new components used in experiments
 * The workflow of calls to the launch() and next(experimentNumber) functions are as follows
 * 
 * (From Harness Perspective)
 *      Setup User Interface
 *      Component.launch() for each component in experiment
 *      foreach experiment i:
 *          Component.next(i) for each component in experiment
 */

export class ComponentB
{
    constructor (scene, anchor, reticle, cubeInfoAreas)
    {
        this.scene = scene;
        this.reticlePosition = new THREE.Vector3(reticle.position.x, reticle.position.y, reticle.position.z);
        this.anchor = anchor;
        this.cubeInfoAreas = cubeInfoAreas;
        this.cubeSize = 0.2;
        this.reticle = reticle;
        this.raycaster = new THREE.Raycaster();

    }

    /**
     * Launches this component and creates associated meshes for this component to prepare for experiments 
     * @returns Mesh created upon Component launch
     */
    launch(referenceCube)
    {   
        this.referenceCube = referenceCube;
        return true;
    }
    
    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber)
    {
        const startVector = new THREE.Vector3(0,5,0);
        const direction = new THREE.Vector3(0,0,0);
        console.log(this.anchor);
        console.log(this.referenceCube.position);
        this.referenceCube.geometry.computeBoundingBox();
        var center = new THREE.Vector3();
        this.referenceCube.geometry.boundingBox.getCenter(center);
        console.log(center);
        console.log(this.referenceCube.localToWorld(center));
        center = this.referenceCube.localToWorld(center);
        this.referenceCube.material.side = THREE.DoubleSide;
        switch(experimentNumber)
        {
            case 0:
                // From outside cube
                console.log("Outside cube")
                direction.subVectors( this.referenceCube.position, startVector).normalize();
                this.raycaster.set(startVector, direction);
                break;
            case 1:
                // From inside cube
                console.log("Inside cube")
                direction.subVectors(startVector, center).normalize();
                this.raycaster.set(this.referenceCube.position, direction);
                break;
            default:
                break;
        }
        showToast("Simulated raycast sent, current click count is " + this.referenceCube.clickCount);
        var intersections = this.raycaster.intersectObjects(this.scene.children);
        console.log(intersections);


        if ( intersections.length > 0 ) 
        {
            console.log("Synthetic Raycast");
            // intersections[0].object.material.color.setHex(Math.random() * 0xffffff);
            intersections[0].object.exploit();
            this.cubeInfoAreas[intersections[0].object.name - 1].textContent = makeClickText( intersections[0].object.name,  intersections[0].object.clickCount);
            return intersections[0].object;
        }
        return true;
 
    }
};