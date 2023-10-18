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
export class ComponentB {
    constructor (scene, anchor, reticle, cubeInfoAreas) {
        this.scene = scene;
        this.reticlePosition = new THREE.Vector3(reticle.position.x, reticle.position.y, reticle.position.z);
        this.anchor = anchor;
        this.cubeInfoAreas = cubeInfoAreas;
        this.cubeSize = 0.2;
    }
    
    /**
     * Launches this component and creates associated meshes for this component to prepare for experiments 
     * @returns Mesh created upon Component launch
     */
    launch() {
        let cube4 = makeCube(this.scene, "4", this.reticlePosition.x - 2 * this.cubeSize, this.reticlePosition.y, this.reticlePosition.z, 0x0000ff, Location.Above, 0);
        this.cubeInfoAreas[3].textContent = makeClickText(4, 0);
        this.scene.add(cube4);
        return cube4;
    }

    /**
     * Carries out the behavior of this component for next experiment
     * @param {int} experimentNumber Current experiment carried out
     */
    next(experimentNumber) {
        let cube3 = null;
        if (experimentNumber === 1) {
            cube3 = makeCube(this.scene, "3", this.reticlePosition.x, this.reticlePosition.y, this.reticlePosition.z, 0x0000ff, Location.Right, 0);
            this.cubeInfoAreas[2].textContent = makeClickText(3, 0);
            this.scene.add(cube3);
        }
        return cube3;
    }
};
