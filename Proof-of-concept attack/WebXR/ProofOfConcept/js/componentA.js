import {makeCube, makeClickText, makeCubeMarker} from './utils.js';
// "Chocolate Truffle" (https://skfb.ly/6WSyS) by M.Reslan is licensed under Creative Commons Attribution (http://creativecommons.org/licenses/by/4.0/).

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
export class ComponentA
{
    constructor (scene, anchor, reticle, cubeInfoAreas)
    {
        this.scene = scene;
        this.reticlePosition = new THREE.Vector3(reticle.position.x, reticle.position.y, reticle.position.z);
        this.anchor = anchor;
        this.cubeInfoAreas = cubeInfoAreas;
        this.cubeSize = 0.2;
        this.reticle = reticle;
        this.loader = new THREE.GLTFLoader();
        this.loader.load( 'chocolate_truffle/scene.gltf', function ( gltf ) {
            console.log(gltf.scene)
            window.chocolate_truffle = gltf.scene;
            
        }, undefined, function ( error ) {
            console.error( error );
        } );
    }
    
    /**
     * Launches this component and creates associated meshes for this component to prepare for experiments 
     * @returns Mesh created upon Component launch
     */
    launch()
    {
        console.log(window.chocolate_truffle);
        let cube1 = null;
        cube1 = window.chocolate_truffle.clone()
        console.log(cube1)
        cube1.position.copy(this.reticle.position);
        cube1.scale.copy(new THREE.Vector3(0.15, 0.15, 0.15));
        cube1.name = "truffle";
        this.scene.add( cube1 );
        makeCubeMarker("Click for truffles!", cube1.position.x, cube1.position.y + 0.2, cube1.position.z, 0xffffff, "truffle", this.scene);
        return [cube1];
    }

    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber)
    {
        // Do nothing
    }
};

