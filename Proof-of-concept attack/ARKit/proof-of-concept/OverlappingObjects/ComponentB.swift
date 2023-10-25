//
//  ComponentB.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation
import RealityKit
import UIKit


extension Entity {
    // Here we extend Entity class to pass in our own function
    func exploit(count:Int) -> Int {
        var click = count
        click = count+1
        return click
    }
}

class ComponentB: ARComponent {
    
    // reference to the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    public init (){}
    public init(location: AnchorEntity) {
        self.location = location
    }
    
    func launch() {
        /**
         *  Operations of the next function as defined in the experiment harness
         */
//                 Create the text prompt by the victim app
                let originalTextMesh = MeshResource.generateText("Click on the Blue Cube To \r\n Win Your Free Cookie", extrusionDepth: 0.001, font: .systemFont(ofSize: 0.02),containerFrame: .zero, alignment: .center, lineBreakMode: .byWordWrapping)
                let materialVar = SimpleMaterial(color: .white, roughness: 0, isMetallic: false)
                let textEntity = ModelEntity(mesh: originalTextMesh, materials: [materialVar])
                textEntity.position.z += 0.05
                textEntity.position.x -= 0.13
//                textEntity.position.y -= 0.13

                textEntity.name = "textEntity"
                textEntity.generateCollisionShapes(recursive: true)
//                textEntity.setParent(location)
        
//        let mesh01 = MeshResource.generateBox(size: 0.3)
//        let pocEntity = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 1), isMetallic: false)])

        
        let pocEntity = ModelEntity(mesh: MeshResource.generateBox(width: 0.3, height: 0.15, depth: 0.02), materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 1), isMetallic: false)])
        pocEntity.generateCollisionShapes(recursive: true)
        pocEntity.name = "victimEntity"
        pocEntity.addChild(textEntity)
        pocEntity.setParent(location)

    }
    func next(expNum: Int) {
        
        }
        
    func eraseText(){
        
    }
}
