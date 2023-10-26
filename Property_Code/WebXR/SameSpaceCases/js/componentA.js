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

export class ComponentA {
    constructor (scene, anchor, reticle, cubeInfoAreas) {
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
    launch() {
        let cube1 = null;
        let cube2 = null;

        cube1 = makeCube(this.scene, "1", this.reticle, this.anchor, this.reticlePosition.x + 2 * this.cubeSize, this.reticlePosition.y, this.reticlePosition.z, 0xff0000, Location.Left, this.cubeSize, this.cubeSize, this.cubeSize);
        this.cubeInfoAreas[0].textContent = makeClickText(1, 0);
        this.scene.add(cube1);
        
        cube2 = makeCube(this.scene, "2", this.reticle, this.anchor, this.reticlePosition.x , this.reticlePosition.y, this.reticlePosition.z, 0xff0000, Location.Above, this.cubeSize, this.cubeSize, this.cubeSize);
        this.cubeInfoAreas[1].textContent = makeClickText(2, 0);
        this.scene.add(cube2);
        return [cube1, cube2];
    }

    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber) {
 
    }
};
