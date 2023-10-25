import {makeCube, makeClickText, makeCubeMarker} from './utils.js';

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
        this.loader = new THREE.GLTFLoader();

        // Pre-load content
        this.loader.load( 'ice_cream_sandwich/scene.gltf', function ( gltf ) {
            console.log(gltf.scene)
            window.ice_cream_sandwich = gltf.scene;
            
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


        let cube2 = null
        cube2 = window.ice_cream_sandwich.clone();
        cube2.position.copy(new THREE.Vector3(this.reticle.position.x + 1, this.reticle.position.y, this.reticle.position.z));
        cube2.scale.copy(new THREE.Vector3(0.15, 0.15, 0.15));
        cube2.name = "ice cream sandwich";
        this.scene.add( cube2 );
        makeCubeMarker("Click for ice cream!", cube2.position.x, cube2.position.y + 0.2, cube2.position.z, 0xffffff, "ice cream sandwich", this.scene);

        return [cube2]
    }
    
    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber)
    {
        let cube3 = null
        // var geometry = new THREE.BoxGeometry(1, 1, 1).translate( 0, 0.1, 0 );
        var material = new THREE.MeshPhongMaterial( { color: 0x0000ff, transparent: true, opacity: 0} );
        cube3 = makeCube(this.scene, "3.2", this.reticle, this.anchor,
        this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
        0x0000ff, Location.Right, 4.5 * this.cubeSize, 
        4.5 * this.cubeSize, 4.5 * this.cubeSize);
        // this.cubeInfoAreas[4].textContent = makeClickText(3.2, 0);
        // Upload transparent mesh material
        const texture = new THREE.TextureLoader().load( 'transparent.png' );
        // immediately use the texture for material creation
        material = new THREE.MeshBasicMaterial( { map: texture } );
        cube3.material = material;
        this.scene.add(cube3);    }
};