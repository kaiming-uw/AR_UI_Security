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
        let mesh01 = MeshResource.generateBox(size: 0.3)

//        let mesh01 = MeshResource.generateBox(width: 0.3, height: 0.3, depth: 0.3)
//        
        let cube3 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 1), isMetallic: false)])
        cube3.generateCollisionShapes(recursive: true)
        cube3.name = "3"
        
        // creates cube4
        let cube4 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .blue, isMetallic: false)])
        cube4.generateCollisionShapes(recursive: true)
        cube4.name = "4"
        cube4.transform.translation = [-0.6, 0, 0]
        // places cube3 and cube4 in the scene
        cube3.setParent(location)
        cube4.setParent(location)
    }
    func next(expNum: Int) {
        
        }
        
}
