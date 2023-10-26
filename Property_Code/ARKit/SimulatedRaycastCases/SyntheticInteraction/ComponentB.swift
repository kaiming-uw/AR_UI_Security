//
//  ComponentB.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation
import RealityKit
import UIKit
import ARKit

extension Entity {
    // Here we extend Entity class to pass in our own exploit function
    func exploit(count:Int) -> Int {
        var click = count
        click = count+1
        return click
    }
}

class ComponentB: ARComponent {
    // The location of the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    
    // A reference to the ViewController && Something to point out in the assumption --> access to arview
    var arView: ViewController? = nil
    
    public init (){}
    
    public init(location: AnchorEntity, view: ViewController) {
        self.location = location
        self.arView = view
    }
    
    /**
     *  The launch function for ComponentB, does nothing
     */
    func launch() {
        
        
    }
    
    /**
     *  The next function for ComponentB, has a switch statment for all of the experiments.
     */
    func next(expNum: Int) {

        // Switch statement for all experiments. Case number matchces experiment number as described
        // in the harness document.
        switch (expNum) {
        case 3:
            if let raycastResult: CollisionCastHit = arView!.arView.scene.raycast(from: [0,0,0], to: location.position(relativeTo: nil)).first {
                if (raycastResult.entity.name == "1") {
                    arView!.hit(result: raycastResult)
                }
            }
        case 4:
        
            if let raycastResult: CollisionCastHit = arView!.arView.scene.raycast(origin: self.location.position(relativeTo: nil), direction: [0,1,0], length: 0.1).first {
                
                if (raycastResult.entity.name == "1") {
                    arView!.hit(result: raycastResult)
                }

            }

        default:
            break;
        }
        
        
    }
}
