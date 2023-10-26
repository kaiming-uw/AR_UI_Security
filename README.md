<center>
<img src="./img/logo_object.png" alt="Logo Icon" width="250" />
</center>

## When the User Is Inside the User Interface: An Empirical Study of UI Security Properties in Augmented Reality
Authors: Kaiming Cheng, Arka Bhattacharya, Michelle Lin, Jaewook Lee, Aroosh Kumar, Jeffery F. Tian, Tadayoshi Kohno, Franziska Roesner

Appears in *USENIX Security 2024*. 

# Appendix Abstract

## Introduction
This artifact includes two main components: 

### Part 1 -- Property 
The test code AR UI properties on five leading XR platforms (ARCore, ARKit, Hololen2 Oculus, WebXR). Each platform has its own folder. Inside the folder, we included the README and the test code for three AR UI properties:
* **Same Space Property**: How do AR systems manage objects that share the same physical world mapping? For instance, when two AR objects with identical shapes and sizes are anchored at the same 3D coordinates, which object(s) become visible to the user? Which receive(s) the user’s input?
* **Invisibility Property**: How do AR systems handle virtual objects
in the AR world that are transparent? To what extent, if any, does an object’s visibility influence its functionality? For example, are transparent objects capable of receiving user input? What happens when a transparent object renders over another virtual object?
* **Simulated Raycast Property**: How do AR systems handle synthetic user input? For example, can adversarial code generate synthetic input to mimic human interaction, such as via a programmatically generated raycast? 



### Part 2 -- Proof-of-concept attacks
The proof-of-concept video for five attacks. (will upload the video soon)
* Input forgery attack on ARCore (Demo Link)
* Clickjacking attack: Implemented on ARKit (Demo Link) 
* User input denial-of-service attack on Hololens (Demo Link)
* Object-in-the-middle attack on Oculus (Demo Link)
* Object erasure attack on WebXR (Demo Link)

## Prerequisites

Please ensure you have the following environments set up with all the dependencies
to be able to reproduce the artifacts in the paper. 

#### ARCore
Hardware: Tested on Pixel 5a <br>
Software: 
* ARCore v1.32.0
* Sceneform SDK v 1.20.5
> Note: The Sceneform SDK is no longer supported by Google at the time this paper is published. 

#### ARKit
Hardware: Tested on iPhone 13 Pro <br> 
Software: Xcode 

#### Hololens
Hardware: Hololens 2
Software:
* Unity 2020.3.21f1
* MRTK 2.7.2
* Visual Studio 2022

#### Oculus
Hardware: Oculus Quest Pro
Software:
* Unity 2020.3.25f1
* Oculus Integration 44.0

#### WebXR
Hardware: Tested on Pixel 5a <br>

## Details of the 

## Property Experiment Results 
<center>
<img src="./img/Experiment_Result.png" alt="Result" width="750" />
</center>
The evaluation metrics are lised in the above figure. Each README provides instructions on how to reproduce our results.

[ARCore](Property_CodeARCore/README.md)
[ARKit](Property_CodeARKit/README.md)
[Hololens](Property_CodeHololens/README.md)
[Oculus](Property_CodeOculus/README.md)
[WebXR](Property_CodeWebXR/README.md)

 </br>

## Camera-ready version
See attached `paper.pdf`.

### Citation
