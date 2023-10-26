//
//  ComponentB.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation
import RealityKit
import UIKit
import SceneKit.ModelIO

extension Entity {
    // Here we extend Entity class to pass in our own function
    func exploit(count:Int) -> Int {
        var click = count
        click = count+1
        return click
    }
}

class ComponentB: ARComponent {
    // The location of the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    // The last placed cube by ComponentB
    var currCube: ModelEntity = ModelEntity.init()
    
    public init (){}
    
    public init(location: AnchorEntity) {
        self.location = location
    }
    
    /**
     *   The launch function as described by the experiment harness
     */
    func launch() {
        
        let mesh01 = MeshResource.generateBox(size: [1.2, 1.2, 0.03])
        let cube2 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .blue, isMetallic: false)])
        cube2.generateCollisionShapes(recursive: true)
        cube2.name = "2"
        
        cube2.transform.translation = [0, 0.6, 0.6]
        cube2.setParent(self.location)
        self.currCube = cube2
        
    }
    /**
     *  The next function as described by the experiment harness document
     */
    func next(expNum: Int) {
        // The case number is the experiment number
        switch (expNum) {
            
            case 3: //In this case, we test the condition where alpha value equals to zero
            
                // remove the last cube from the parent, removing it from the scene
                self.currCube.removeFromParent()
            
                // create the new cube, with the correct dimensions and offset
                let mesh01 = MeshResource.generateBox(size: [1.2, 1.2, 0.03])
                let cube3_1 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 0), isMetallic: false)])
                cube3_1.generateCollisionShapes(recursive: true)
                cube3_1.name = "3.1"
                cube3_1.transform.translation = [0, 0.6, 0.6]
                cube3_1.setParent(location)
                self.currCube = cube3_1
                print(self.currCube.name)

            case 4: //In this case, we test the condition with rendered disabled
            
                self.currCube.removeFromParent()
                let mesh01 = MeshResource.generateBox(size: [1.2, 1.2, 0.03])
                // This cube has no material
                let cube3_2 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 0), isMetallic: false)])
                cube3_2.generateCollisionShapes(recursive: true)
            
                //RealityKit doesn’t simulate or render inactive entities
                cube3_2.isEnabled = false
                cube3_2.generateCollisionShapes(recursive: true)
                cube3_2.name = "3.2"
                cube3_2.transform.translation = [0, 0.6, 0.6]
                cube3_2.setParent(location)
                self.currCube = cube3_2
                print("3.2 executed")
                print(self.currCube.name)



            case 6: //In this case, we test the condition with null material
                self.currCube.removeFromParent()
                let mesh01 = MeshResource.generateBox(size: [1.2, 1.2, 0.03])
                let cube3_4 = ModelEntity(mesh: mesh01, materials: [])
                cube3_4.generateCollisionShapes(recursive: true)
                cube3_4.name = "3.4"
                cube3_4.transform.translation = [0, 0.6, 0.6]
                cube3_4.setParent(location)
                self.currCube = cube3_4
                print(self.currCube.name)
            
            case 5:  // this cube has the custom texture.
                self.currCube.removeFromParent()
//                let path = Bundle.main.path(forResource: "cube", ofType: "usdz")!

//                let url = URL(fileURLWithPath: path)
                do {
                    var myMaterial = SimpleMaterial()
                    if let baseResource = try? TextureResource.load(named: "cube") {
//                        let baseColor = MaterialParameters.Texture(baseResource)
                        myMaterial.baseColor = MaterialColorParameter.texture(baseResource)
                    }
                    let mesh01 = MeshResource.generateBox(size: 0.3)
                    let trans_cube = ModelEntity(mesh: mesh01, materials: [myMaterial])
                    trans_cube.name = "3.3"
                    trans_cube.scale = SIMD3<Float>(1.2, 1.2, 0.03)
                    trans_cube.transform.translation = [0, 0.6, 0.6]
                    trans_cube.generateCollisionShapes(recursive: true)
                    location.addChild(trans_cube)
                    trans_cube.setParent(location)
                    self.currCube = trans_cube
                } catch {
                    print(error)
                }
//
//                // this cube is supposed to have the custom texture.
//                let path = Bundle.main.path(forResource: "cube", ofType: "usdz")!
//                let url = URL(fileURLWithPath: path)
//                do {
//                    let trans_cube = try! Entity.loadModel(contentsOf: url)
//                    trans_cube.name = "3.4"
//                    trans_cube.scale = SIMD3<Float>(1.2, 1.2, 0.03)
//                    trans_cube.transform.translation = [0, 0.6, 0.6]
//                    trans_cube.generateCollisionShapes(recursive: true)
//                    trans_cube.setParent(location)
//                    self.currCube = trans_cube
//                    print(self.currCube.name)
//                    print(self.currCube.transform.translation)
//                } catch {
//                    print(error)
//                }
//
//        case 5:
//            let mesh01 = MeshResource.generateBox(size: [120, 120, 120])
//            let cube3_5 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: UIColor(red: 0, green: 0, blue: 255, alpha: 0.2), isMetallic: false)])
//            cube3_5.generateCollisionShapes(recursive: true)
//            cube3_5.name = "3.3"
//            cube3_5.transform.translation = [0, 0.6, 0.6]
//            cube3_5.setParent(location)
//            self.currCube = cube3_3
//            print(self.currCube.name)
//                let cube3_4 = ModelEntity(mesh: mesh01, materials: [entity_transparent])
//                cube3_4.generateCollisionShapes(recursive: true)
//                cube3_4.name = "3.4"
//                cube3_4.transform.translation = [0, 0.6, 0.6]
//                cube3_4.setParent(location)
//                self.currCube = cube3_4
            
            default:
                return
        }
        
        
        
    }
}
