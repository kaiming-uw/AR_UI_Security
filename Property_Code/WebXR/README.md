# Requirements
- Android Device that is ARCore supported

Launch index.js in a web server. We recommend using the Chrome browser and port forwarding the WebXR page there.

# Important Files
Under the directory of 
>[property_name]/js

You will find the following files:
- ComponentA.js: runs test-case-specific code
- ComponentB.js: runs test-case-specific code.
- utils.js: provides a programmatic interface for launching each component and transitioning to the next sub-experiment in a test case. Both ComponentA and ComponentB extend ARComponent
- scene-utils.js: provides provides code describing a cube. It is used in ComponentA and ComponentB.
- interaction.js: provides code to handle user interaction in AR 
- app.js: provides the main activity for the app. It sets up the UI and launches the test case.
