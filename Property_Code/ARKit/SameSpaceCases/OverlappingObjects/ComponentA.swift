//
//  ComponentA.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation
import RealityKit


class ComponentA: ARComponent {
    
    // reference to the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    public init () {}
    public init(location: AnchorEntity) {
        self.location = location
    }
    
    /**
     *  Launch function for ComponentA, follows steps in the experiment harness
     */
    func launch() {
        // Creates cube1
        let mesh01 = MeshResource.generateBox(size: 0.3)

//        let mesh01 = MeshResource.generateBox(width: 0.3, height: 0.3, depth: 0.3)
        let cube1 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .red, isMetallic: false)])
        cube1.generateCollisionShapes(recursive: true)
        cube1.name = "1"
        cube1.transform.translation = [0.6, 0, 0]

        
        // Creates cube2
        let cube2 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .red, isMetallic: false)])
        cube2.generateCollisionShapes(recursive: true)
        cube2.name = "2"

        // Attach both cubes to the same anchor entity
        cube1.setParent(location)
        cube2.setParent(location)
        print(location.transform)
        print(cube2.model?.mesh.bounds.max.x)
        print(cube2.model?.mesh.bounds.max.y)
        print(cube2.model?.mesh.bounds.max.z)
        print(cube2.model?.mesh.bounds.min.x)
        print(cube2.model?.mesh.bounds.min.y)
        print(cube2.model?.mesh.bounds.min.z)

    }
    
    func next(expNum: Int) {}
    
}
