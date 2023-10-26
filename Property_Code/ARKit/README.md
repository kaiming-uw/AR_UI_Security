# Requirements
- Requires an iOS device with an A9 or later processor
- ARKit & RealityKit


# Important Files
Under the directory of 
>[property_nameCase]/[propertyname]

You will find the following files:
- ComponentA.swift: runs test-case-specific code
- ComponentB.swift: runs test-case-specific code.
- ARComponent.swift: provides a programmatic interface for launching each component and transitioning to the next sub-experiment in a test case. Both ComponentA and ComponentB extend ARComponent
- ViewController.swift: provides the main activity for the app. It sets up the UI and launches the test case.
