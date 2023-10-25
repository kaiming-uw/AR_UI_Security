// Thread Model: Third party applications
import {ComponentA} from './componentA.js';
import {ComponentB} from './componentB.js';

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
        requiredFeatures: ['hit-test', 'dom-overlay', 'anchors'],
        domOverlay: { root: document.body }
      });
      // Create info box
      // this.createInfoScreen();

      // Create the canvas that will contain our camera's background and our virtual scene.
      this.createXRCanvas();

      // With everything set up, start the app.
      await this.onSessionStarted();
    } catch(e) {
      onNoXRDevice();
    }
  }

  /**
   * Creates UI screen that displays number of times meshes in this.scene have been clicked or tapped
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
    this.cubeInfoAreas = [document.createElement("P"), document.createElement("P"), document.createElement("P"), document.createElement("P"),document.createElement("P"), document.createElement("P")]
    for (let i = 0; i < this.cubeInfoAreas.length; i++) {
      if (i<2){
      this.cubeInfoAreas[i].textContent = "Cube " + (i + 1) + ": not initialized";
      }
      else{
        this.cubeInfoAreas[i].textContent = "Cube " + (3.0+0.1*(i-2)) + ": not initialized";
      }
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
   * renderer, scene, and camera and attach our XRWebGLLayer to the
   * XRSession and kick off the render loop.
   * 
   */
  onSessionStarted = async () => {
    // Add the `ar` class to our body, which will hide our 2D components
    document.body.classList.add('ar');

    // To help with working with 3D on the web, we'll use three.js.
    this.setupThreeJs();

    // Setup an XRReferenceSpace using the "local" coordinate system.
    this.localReferenceSpace = await this.xrSession.requestReferenceSpace('local');

    // Create another XRReferenceSpace that has the viewer as the origin.
    this.viewerSpace = await this.xrSession.requestReferenceSpace('viewer');
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
  onTouchEnd = (event) =>
  {
    // try make raycaster a field
    const raycaster = new THREE.Raycaster();
    const mouse = new THREE.Vector2(+(event.clientX / window.innerWidth) * 2 + -1, -(event.clientY / window.innerHeight) * 2 + 1);
    raycaster.setFromCamera(mouse, this.camera);

    // Performs a click on the first intersected object based on their order in the given array
    var intersects = raycaster.intersectObjects(this.anchoredObjects);
    if (intersects.length > 0) {
      // IMPORTANT: we view the first intersected object as the return result 
      const currObj = intersects[0].object;
      // We call the exploit function from the first returned AR object; Here the expolit() only increment the count, but we could modify it to an end-to-end attack 
      currObj.exploit();

      //Experiment 3.2 will cause TypeError: Cannot read properties of undefined (setting 'textContent')at onTouchEnd
      // if (this.cubeInfoAreas[currObj.name - 1]){
      //   this.cubeInfoAreas[currObj.name - 1].textContent = makeClickText(currObj.name, currObj.clickCount);
      // }
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
    console.log(selectedObject)
    console.log(this.scene.children);
    console.log(selectedObject);
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

  onNextClick = (event) =>
  {
    this.experimentNum++;
    
    //mapping between the experimentNum and the Cube name 
    var index_mapping = {
      3:"2", 
      4:"3.0",
      5:"3.1",
      6:"3.2",
      7:"3.3"
    }

    if (this.experimentNum == 1) 
    {
      // Start ComponentA.launch()
      let naiveAd = this.compAObject.launch()
      
      this.anchoredObjects.push(naiveAd);
      document.getElementById("nextButton").textContent="Launch Second Ad Platform";
      this.displayToUser("Click \"Launch Second Ad Platform\" to proceed.")
    }
    else if (this.experimentNum == 2) 
    {
      // Start ComponentB.launch()
      let maliciousAd = this.compBObject.launch()

      this.anchoredObjects.push(maliciousAd);
      document.getElementById("nextButton").textContent="Next";
      this.displayToUser("Advertising Proof of Concept");
      this.displayToUser("Click Next to proceed with app.")
      // document.getElementById("nextButton").style.visibility = 'hidden';

    } 
    else
    {
<<<<<<< Updated upstream
=======
      this.compBObject.next();
      
>>>>>>> Stashed changes
      this.displayToUser("Finished proof of concept, refresh page to run compromise again.")
      document.getElementById("nextButton").style.visibility = 'hidden';
    }
  }

  showToast = (content) => { //You can change the default value
    // Get the snackbar DIV
    var x = document.getElementById("snackbar");
    
    //Change the text (not mandatory, but I think you might be willing to do it)
    x.innerHTML = content;
  
    // Add the "show" class to DIV
    x.className = "show";
  
    // After 3 seconds, remove the show class from DIV
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
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

  /** Place a object when the screen is tapped, setup and call component.launch() on all components */
  onSelect = (event) => {
    if (!this.singleAnchor) {
      console.log("SELECTED")
      this.singleAnchor = true;

      let frame = event.frame;
      let anchorPose = new XRRigidTransform();
      let inputSource = event.inputSource;

      frame.createAnchor(anchorPose, inputSource.targetRaySpace).then((anchor) => {
        // Initiate both Component
        this.anchor = anchor;
        this.compAObject = new ComponentA(this.scene, anchor, this.reticle, this.cubeInfoAreas);
        this.compBObject = new ComponentB(this.scene, anchor, this.reticle, this.cubeInfoAreas);

        document.getElementById("nextButton").textContent="Launch First Ad Platform";

        this.displayToUser("After click \"Launch First Ad Platform\"");

      }, (error) => {
        console.error("Could not create anchor: " + error);
      });
    }
  }


  /**
   * Called on the XRSession's requestAnimationFrame.
   * Called with the time and XRPresentationFrame.
   */                                                                         
  onXRFrame = (time, frame) => {
    // Queue up the next draw request.
    this.xrSession.requestAnimationFrame(this.onXRFrame);

    // Bind the graphics framebuffer to the baseLayer's framebuffer.
    const framebuffer = this.xrSession.renderState.baseLayer.framebuffer
    this.gl.bindFramebuffer(this.gl.FRAMEBUFFER, framebuffer)
    this.renderer.setFramebuffer(framebuffer);

    // Retrieve the pose of the device.
    // XRFrame.getViewerPose can return null while the session attempts to establish tracking.
    const pose = frame.getViewerPose(this.localReferenceSpace);
    if (pose) {
      // In mobile AR, we only have one view.
      const view = pose.views[0];

      const viewport = this.xrSession.renderState.baseLayer.getViewport(view);
      this.renderer.setSize(viewport.width, viewport.height)

      // Use the view's transform matrix and projection matrix to configure the THREE.camera.
      this.camera.matrix.fromArray(view.transform.matrix)
      this.camera.projectionMatrix.fromArray(view.projectionMatrix);
      this.camera.updateMatrixWorld(true);

      // Conduct hit test.
      const hitTestResults = frame.getHitTestResults(this.hitTestSource);

      // If we have results, consider the environment stabilized. 
      if (!this.stabilized && hitTestResults.length > 0) {
        this.stabilized = true;
        document.body.classList.add('stabilized');
      }
      if (hitTestResults.length > 0) {
        const hitPose = hitTestResults[0].getPose(this.localReferenceSpace);

        // Update the reticle position
        this.reticle.visible = true;
        this.reticle.position.set(hitPose.transform.position.x, hitPose.transform.position.y, hitPose.transform.position.z)
        this.reticle.updateMatrixWorld(true);
      }

      // Render the scene with THREE.WebGLRenderer.
      this.renderer.render(this.scene, this.camera)
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


    // Add event handlers for DOM elements for experiment progression
    var next = document.getElementById("nextButton");
    // next.style.visibility = 'hidden';
    next.addEventListener("click", this.onNextClick);
    document.getElementById("nextButton").textContent="Set anchor and launch ComponentA";
  }
};

window.app = new App();
