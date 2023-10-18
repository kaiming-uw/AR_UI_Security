// Thread Model: Third party applications
import { ComponentA } from './componentA.js';
import { ComponentB } from './componentB.js';

//UI layout
import { makeClickText } from './utils.js';

/**
 * Query for WebXR support. If there's no support for the `immersive-ar` mode,
 * show an error.
 */
(async function() {
  const isArSessionSupported = navigator.xr && navigator.xr.isSessionSupported && await navigator.xr.isSessionSupported("immersive-ar");
  if (isArSessionSupported) {
    document.getElementById("enter-ar").addEventListener("click", window.app.activateXR)
  } else {
    onNoXRDevice();
  }
})();

/**
 * Container class to manage connecting to the WebXR Device API
 * and handle rendering on every frame.
 */
class App {
  /**
   * Run when the Start AR button is pressed.
   */
  activateXR = async () => {
    try {
      // Initialize a WebXR session using "immersive-ar".
      this.xrSession = await navigator.xr.requestSession("immersive-ar", {
        requiredFeatures: ["anchors", "hit-test", "dom-overlay"],
        domOverlay: { root: document.body }
      });

      // Create info box
      this.createInfoScreen();

      // Create the canvas that will contain our camera's background and our virtual scene.
      this.createXRCanvas();

      // With everything set up, start the app.
      await this.onSessionStarted();
    } catch(e) {
      onNoXRDevice();
    }
  }

  /**
   * Creates UI screen that displays number of times meshes in this scene have been clicked or tapped
   */
  createInfoScreen() {
    this.infoBox = document.createElement("div");
    this.infoBox.style.backgroundColor = "white";
    this.infoBox.style.position = "fixed";
    this.infoBox.style.top = "0";
    document.body.appendChild(this.infoBox);
    const label = document.createElement("P");
    label.textContent = "Click counts";
    this.infoBox.appendChild(label);
    this.cubeInfoAreas = [document.createElement("P"), document.createElement("P"), document.createElement("P"), document.createElement("P")]
    for (let i = 0; i < this.cubeInfoAreas.length; i++) {
      this.cubeInfoAreas[i].textContent = "Cube " + (i + 1) + ": not initialized";
      this.infoBox.appendChild(this.cubeInfoAreas[i]);
    }
  }

  /**
   * Add a canvas element and initialize a WebGL context that is compatible with WebXR.
   */
  createXRCanvas() {
    this.canvas = document.createElement("canvas");
    document.body.appendChild(this.canvas);
    this.gl = this.canvas.getContext("webgl", {xrCompatible: true});

    this.xrSession.updateRenderState({
      baseLayer: new XRWebGLLayer(this.xrSession, this.gl)
    });
  }

  /**
   * Called when the XRSession has begun. Here we set up our three.js
   * renderer and kick off the render loop.
   */
  async onSessionStarted() {
    // Add the `ar` class to our body, which will hide our 2D components
    document.body.classList.add('ar');
    // document.getElementById("enter-ar-info").style.display = "none";
    // document.getElementById("unsupported-info").style.display = "none";

    // To help with working with 3D on the web, we'll use three.js.
    this.setupThreeJs();

    // Setup an XRReferenceSpace using the "local" coordinate system.
    this.localReferenceSpace = await this.xrSession.requestReferenceSpace("local");

    // Create another XRReferenceSpace that has the viewer as the origin.
    this.viewerSpace = await this.xrSession.requestReferenceSpace("viewer");
    // Perform hit testing using the viewer as origin.
    this.hitTestSource = await this.xrSession.requestHitTestSource({ space: this.viewerSpace });

    // Start a rendering loop using this.onXRFrame.
    this.xrSession.requestAnimationFrame(this.onXRFrame);

    // Set up event listener
    this.xrSession.addEventListener("select", this.onSelect);
    window.addEventListener("pointerdown", this.onTouchEnd);

    this.anchor = null // anchor information
    this.anchoredObjects = []; // list of objects on our anchor
    this.experimentNum = 0; // experiment ID number
  }

  /**
   * Called by the event listener for screen taps 
   */
  onTouchEnd = (event) => {
    // try make raycaster a field
    const raycaster = new THREE.Raycaster();
    const mouse = new THREE.Vector2(+(event.clientX / window.innerWidth) * 2 + -1, -(event.clientY / window.innerHeight) * 2 + 1);
    raycaster.setFromCamera(mouse, this.camera);

    // Performs a click on the first intersected object based on their order in the given array
    var intersects = raycaster.intersectObjects(this.anchoredObjects);
    if (intersects.length > 0) {
      // IMPORTANT: we view the first intersected object as the return result 
      const currObj = intersects[0].object;
      // We call the exploit function of the returned object; Here the expolit() only increment the count, but we could modify it to an end-to-end attack 
      currObj.exploit();
      this.cubeInfoAreas[currObj.name - 1].textContent = makeClickText(currObj.name, currObj.clickCount);
    }
  }

  /**
   * Place an anchor when the screen is tapped, setup and call 
   * component.launch() on all components
   */
  onSelect = (event) => {
    if (!this.singleAnchor) {
      this.singleAnchor = true;

      let frame = event.frame;
      let anchorPose = new XRRigidTransform();
      let inputSource = event.inputSource;

      frame.createAnchor(anchorPose, inputSource.targetRaySpace).then((anchor) => {
        // Initiate both Component
        this.anchor = anchor;
        this.compAObject = new ComponentA(this.scene, anchor, this.reticle, this.cubeInfoAreas);
        this.compBObject = new ComponentB(this.scene, anchor, this.reticle, this.cubeInfoAreas);

        document.getElementById("nextButton").textContent="Launch ComponentA";

        this.displayToUser("After click \"Launch ComponentA\", verify that you can register clicks on Cube 1 and Cube 2");

      }, (error) => {
        console.error("Could not create anchor: " + error);
      });
    }
  }

  /**
   * Removes the first object with objectName as its name from this.scene 
   * @param {*} objectName Name of object to be 
   */
  removeObjectByName = (objectName) =>
  {
    // remove object
    const selectedObject = this.scene.getObjectByName(objectName);
    selectedObject.geometry.dispose();
    selectedObject.material.dispose();
    this.scene.remove( selectedObject );
    const newMeshes = []
    for(let i = 0; i < this.anchoredObjects.length; i++) {
      if(this.anchoredObjects[i].name != objectName) {
        newMeshes.push(this.anchoredObjects[i])
      }
    }
    this.anchoredObjects = newMeshes;

    // remove label
    const selectedLabel = this.scene.getObjectByName("label_" + objectName);
    selectedLabel.geometry.dispose();
    this.scene.remove( selectedLabel );
    this.renderer.renderLists.dispose();
  }

  /**
   * Event handler for next button. Performs necessary
   * actions to advance to next experiemnt / trial.
   */
  onNextClick = (event) => {
    this.experimentNum++;
    if (this.experimentNum > 1) {
      // Start ComponentB.launch()
      let cubes = this.compBObject.launch()
      let cube3 = cubes[0];
      let cube4 = cubes[1];
      this.anchoredObjects.push(cube3);
      this.anchoredObjects.push(cube4);

      this.displayToUser("After verifying overlapped cubes, start a new experiment by refreshing the website");
      document.getElementById("nextButton").style.visibility = 'hidden';

    } else {
      // Start ComponentA.launch()
      let cubes = this.compAObject.launch()
      let cube1 = cubes[0];
      let cube2 = cubes[1];
      this.anchoredObjects.push(cube1);
      this.anchoredObjects.push(cube2);

      document.getElementById("nextButton").textContent="Launch ComponentB";

      // const cube1 = this.compAObject.next(this.experimentNum);
      // this.anchoredObjects.push(cube1);
      // const cube3 = this.compBObject.next(this.experimentNum);
      // this.anchoredObjects.push(cube3);

      this.displayToUser("Click \"Launch ComponentB\" to proceed.")
    }
  }

  /**
   * Displays a message to users on the snackbar
   * @param {*} content String message to display to user
   */
  displayToUser = (content) => {
    var snackbarText = document.getElementById("snackbar-content");
    snackbarText.innerHTML = content;
    var snackbarDiv = document.getElementById("snackbar");
    snackbarDiv.className = "show"
  }


  /**
   * Called on the XRSession's requestAnimationFrame.
   * Called with the time and XRPresentationFrame.
   */
  onXRFrame = (time, frame) => {
    /** TODO draw the application */
    this.xrSession.requestAnimationFrame(this.onXRFrame); // Queue up the next draw request.
    
    // Bind the graphics framebuffer to the baseLayer's framebuffer.
    const framebuffer = this.xrSession.renderState.baseLayer.framebuffer;
    this.gl.bindFramebuffer(this.gl.FRAMEBUFFER, framebuffer);
    this.renderer.setFramebuffer(framebuffer);

    // Retrieve the pose of the device.
    // XRFrame.getViewerPose can return null while the session attempts to establish tracking.
    const pose = frame.getViewerPose(this.localReferenceSpace);
    if (pose) {
      // In mobile AR, we only have one view.
      const view = pose.views[0];

      const viewport = this.xrSession.renderState.baseLayer.getViewport(view);
      this.renderer.setSize(viewport.width, viewport.height);

      // Use the view's transform matrix and projection matrix to configure the THREE.camera.
      this.camera.matrix.fromArray(view.transform.matrix);
      this.camera.projectionMatrix.fromArray(view.projectionMatrix);
      this.camera.updateMatrixWorld(true);

      // if (!this.singleAnchor) {
        // Perform hit test
        const hitTestResults = frame.getHitTestResults(this.hitTestSource);

        if (!this.stabilized && hitTestResults.length > 0) {
          this.stabilized = true;
          document.body.classList.add("stabilized");
        }
        if (hitTestResults.length > 0) {
          const hitPose = hitTestResults[0].getPose(this.localReferenceSpace);

          // update the reticle position
          this.reticle.visible = true;
          this.reticle.position.set(hitPose.transform.position.x, hitPose.transform.position.y, hitPose.transform.position.z)
          this.reticle.updateMatrixWorld(true);
        }
      // } 

      // Render the scene with THREE.WebGLRenderer.
      this.renderer.render(this.scene, this.camera);
    }
  }

  /**
   * Initialize three.js specific rendering code, including a WebGLRenderer,
   * a demo scene, and a camera for viewing the 3D content.
   */
  setupThreeJs() {
    // To help with working with 3D on the web, we'll use three.js.
    // Set up the WebGLRenderer, which handles rendering to our session's base layer.
    this.renderer = new THREE.WebGLRenderer({
      alpha: true,
      preserveDrawingBuffer: true,
      canvas: this.canvas,
      context: this.gl
    });
    this.renderer.autoClear = false;
    this.renderer.shadowMap.enabled = true;
    this.renderer.shadowMap.type = THREE.PCFSoftShadowMap;

    // Initialize our demo scene.
    this.scene = DemoUtils.createLitScene();
    this.reticle = new Reticle();
    this.scene.add(this.reticle);

    // We'll update the camera matrices directly from API, so
    // disable matrix auto updates so three.js doesn't attempt
    // to handle the matrices independently.
    this.camera = new THREE.PerspectiveCamera();
    this.camera.matrixAutoUpdate = false;

    // Set up event listener for next button
    document.getElementById("nextButton").addEventListener("click", this.onNextClick);
    document.getElementById("nextButton").textContent="Set anchor and launch ComponentA";
  }
};

window.app = new App();
