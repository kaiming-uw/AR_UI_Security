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
The test code AR UI properties on five leading XR platforms (ARCore, ARKit, Hololen2 Oculus, WebXR). Each platform has its own folder. Inside the folder, we included the README and the test code for three UI properties:
* Same Space Property
* Invisibility Property
* Simulated Raycast Property

### Property Experiment Results 
<center>
<img src="./img/Experiment_Result.png" alt="Result" width="750" />
</center>
The evaluation metrics are lised in the above figure. Each README provides instructions on how to reproduce our results.

 </br></br>

### Part 2 -- Proof-of-concept attacks 
The proof-of-concept code for five attacks.  
* Input forgery attack on ARCore (Demo Link)
* Clickjacking attack: Implemented on ARKit (Demo Link) 
* User input denial-of-service attack on Hololens (Demo Link)
* Object-in-the-middle attack on Oculus (Demo Link)
* Object erasure attack on WebXR (Demo Link)



## Prerequisites

Please ensure you have the following environments setup with all the dependencies
to be able to reproduce the artifacts in the paper. 

#### ARCore
Hardware: Pixel 4a <br>
Software: 
* Android Studio 4.1.2
* 

#### ARKit
Hardware: IPhone 
Software: 


#### Hololens
Hardware: Hololens 2

#### Oculus
Hardware: Oculus Quest Pro

#### WebXR
Hardware: Pixel 4a <br>




## Camera-ready version
See attached `paper.pdf`.

### Citation