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
    
    // A reference to the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    

    // keeps track of how many times the cube has been hit to cycle colors
    var counter: Int = 0
    // A reference to the cube
    var cube1: ModelEntity = .init()

    public init () {}
    
    public init(location: AnchorEntity) {
        self.location = location
    }
    
    /**
     *  Component A launch function, which sets up the cube and adds it to the scene
     */
    func launch() {
        let mesh01 = MeshResource.generateBox(size: 0.3)
        self.cube1 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .red, isMetallic: false)])
        self.cube1.generateCollisionShapes(recursive: true)
        self.cube1.name = "1"
        self.cube1.setParent(location)
        self.cube1.setOrientation(.init(), relativeTo: nil)

    }
    
    /**
     *  Resets the position, scale, and orientation of the cube
     */
    func next(expNum: Int) {
    }
    
    /**
     *  Helper function to handle changing the color of the cube every time it is hit. Called by the experiment harness.
     */

    
}
