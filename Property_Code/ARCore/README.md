# Requirements
- Android Device that is ARCore supported
- ARCore v1.32.0


# Important Files
Under the directory of 
>[property_name]/[propertynameTest]/src/main/java/com/google/ar/scenform/samples/gltf

You will find the following files:
- ComponentA.java: runs test-case-specific code
- ComponentB.java: runs test-case-specific code.
- ARComponent.java: provides a programmatic interface for launching each component and transitioning to the next sub-experiment in a test case. Both ComponentA and ComponentB extend ARComponent
- Global.java: sets up global variables 
- ARCube.java: provides code describing a cube. It is used in ComponentA and ComponentB.
- MainActivity.java: provides the main activity for the app. It sets up the UI and launches the test case.
