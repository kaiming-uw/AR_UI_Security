//
//  ComponentA.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation
import RealityKit
import UIKit

class ComponentA: ARComponent {
    
    // reference to the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    var text: ModelEntity = ModelEntity()
    public init () {}
    public init(location: AnchorEntity, text: ModelEntity) {
        self.location = location
        self.text = text;
    }
    
    /**
     *  Launch function for ComponentA (victim app) 
     */
    func launch() {
        // Load usdz of the victim app
//        let entity1 = try? Entity.load(named: "scene")
//        // Resize the model to fit the screen
//        entity1?.scale = SIMD3<Float>(0.2, 0.2, 0.2)
//        entity1?.transform.translation = [-1, 0, 0]
//        entity1!.setParent(location)

        // Create the text prompt by the victim app
//        let originalTextMesh = MeshResource.generateText("Ads", extrusionDepth: 0.001, font: .systemFont(ofSize: 0.05),containerFrame: .zero, alignment: .center, lineBreakMode: .byWordWrapping)
//        let materialVar = SimpleMaterial(color: .white, roughness: 0, isMetallic: false)
//        self.text = ModelEntity(mesh: originalTextMesh, materials: [materialVar])
//        self.text.position.z += 0.01
//        self.text.position.x -= 0.11
//        self.text.name = "textEntity"

        // Create a background entity for the text prompt --
//        let mesh01 = MeshResource.generateBox(size: 0.3)
//        let boxEntity = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .red, isMetallic: false)])
        
        let boxEntity = ModelEntity(mesh: MeshResource.generateBox(width: 0.3, height: 0.15, depth: 0.02), materials: [SimpleMaterial(color: .red, isMetallic: false)])
        boxEntity.generateCollisionShapes(recursive: true)
        boxEntity.name = "attackEntity"
        boxEntity.addChild(self.text)
        boxEntity.setParent(location)
    }
    
    func next(expNum: Int) {        
    }
    func eraseText(){
        if (self.text != nil){
            self.text.removeFromParent()
        }
    }
}
