import {makeCube, makeClickText} from './utils.js';

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
    }

    /**
     * Launches this component and creates associated meshes for this component to prepare for experiments 
     * @returns Mesh created upon Component launch
     */
    launch()
    {
        let cube2 = null;
        
        cube2 = makeCube(this.scene, "2", this.reticle, this.anchor,
                            this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
                            0x0000ff, Location.Right, 4 * this.cubeSize, 
                            4 * this.cubeSize, 0.1 * this.cubeSize);
        this.cubeInfoAreas[1].textContent = makeClickText(2, 0);
        this.scene.add(cube2);

        return [cube2];
    }
    
    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber)
    {
        let cube3 = null
        var geometry = new THREE.BoxGeometry(0.8, 0.8, 0.02 ).translate( 0, 0.1, 0 );
        var material = new THREE.MeshPhongMaterial( { color: 0x0000ff, transparent: true, opacity: 0} );
        switch(experimentNumber)
        {
            case 0:
                // Disable alpha
                cube3 = makeCube(this.scene, "3.0", this.reticle, this.anchor,
                                    this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
                                    0x0000ff, Location.Right, 4 * this.cubeSize, 
                                    4 * this.cubeSize, 0.1 * this.cubeSize);
                this.cubeInfoAreas[2].textContent = makeClickText(3.0, 0);
                var material = new THREE.MeshPhongMaterial( { color: 0x0000ff, transparent: true, opacity: 0} );
                cube3.material = material;
                this.scene.add(cube3);
                break;
            case 1:
                // Disable rendering 
                cube3 = makeCube(this.scene, "3.1", this.reticle, this.anchor,
                                    this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
                                    0x0000ff, Location.Right, 4 * this.cubeSize, 
                                    4 * this.cubeSize, 0.1 * this.cubeSize);
                this.cubeInfoAreas[3].textContent = makeClickText(3.1, 0);
                cube3.visible = false;
                this.scene.add(cube3);
                break;
            case 2:
                cube3 = makeCube(this.scene, "3.2", this.reticle, this.anchor,
                                    this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
                                    0x0000ff, Location.Right, 4 * this.cubeSize, 
                                    4 * this.cubeSize, 0.1 * this.cubeSize);
                this.cubeInfoAreas[4].textContent = makeClickText(3.2, 0);
                // Upload transparent mesh material
                const texture = new THREE.TextureLoader().load( 'transparent.png' );
                // immediately use the texture for material creation
                material = new THREE.MeshBasicMaterial( { map: texture } );
                cube3.material = material;
                this.scene.add(cube3);
                break;
                
            case 3:
                cube3 = makeCube(this.scene, "3.3", this.reticle, this.anchor,
                                    this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z + 2 * this.cubeSize, 
                                    0x0000ff, Location.Right, 4 * this.cubeSize, 
                                    4 * this.cubeSize, 0.1 * this.cubeSize);
                this.cubeInfoAreas[5].textContent = makeClickText(3.3, 0);
                // Null material
                material = new THREE.MeshBasicMaterial();
                cube3.material = material;
                this.scene.add(cube3);
                break;
            default:
                console.log("ERROR");
                break;
        }
        return [cube3];
    }
};