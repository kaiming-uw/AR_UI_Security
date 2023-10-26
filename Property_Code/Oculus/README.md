# Requirements
- Oculus Integeration v44 or higher 
- Unity 2020.3.25f1 or higher
- Oculus Quest 2, Pro, or 3

# Important Files
Under the directory of 
> Assets/Scenes/

You will find the following files:
- Invisivilit.unity: Scene for Invisibility property test case
- SameSpace.unity: Scene for SameSpace property test case
- SimulatedRaycast.unity: Scene for SimulatedRaycast property test case

Under the directory of
> Assets/Scripts/[property_name]/

You will find the following files:
- CompA[Property].cs: runs test-case-specific code
- CompB[Property].cs: runs test-case-specific code.
- AnchorUIManager[Property].cs provides a programmatic interface for launching each component and transitioning to the next sub-experiment in a test case. 
