//
//  ViewController.swift
//  SecurityResearch
//
//  Created by stlp on 2/24/22.
//

import UIKit
import ARKit
import RealityKit
import Toast


class ViewController: UIViewController {
    
    @IBOutlet var arView: ARView!
    
    // Outlets for all UI Labels
    @IBOutlet var Cube1Count: UILabel!
    @IBOutlet var Cube2Count: UILabel!
    @IBOutlet var Cube3Count: UILabel!
    @IBOutlet var Cube4Count: UILabel!
    @IBOutlet var PlaceAnchorPrompt: UILabel!
    
    // Outlets for button
//    @IBOutlet weak var button: UIButton!
    @IBOutlet weak var button: UIButton!
    
    var labels: [String: UILabel] = [:];
    var counts: [String: Int] = [:]
    var anchorPlaced: Bool = false
    var ComponentA_Launched: Bool = false
    var ComponentB_Launched: Bool = false
    var expNum: Int = 1
    
    var location: AnchorEntity = AnchorEntity.init()
    var planeAnchor: AnchorEntity = AnchorEntity.init()

    var componentA: ARComponent = ComponentA()
    var componentB: ARComponent = ComponentB()
    
    /**
     *  Input handler for when the user starts touching the screen
     */
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        // Performs a hit test if an anchor has already been placed
        if (anchorPlaced) {
            let touch = arView.center
            let results: [CollisionCastHit] = arView.hitTest(touch)
            if let result: CollisionCastHit = results.first {
                // updates the labels to reflect the amount of times each cube has been hit
                let count = counts[result.entity.name]!
                counts[result.entity.name]! = result.entity.exploit(count: count)
                let CurrLabel: UILabel = labels[result.entity.name]!
                CurrLabel.text = "Cube " + result.entity.name + ", Clicked " + String(counts[result.entity.name]!)

            }
        } else {
            // No anchor yet, place an anchor down and launch both components
            if let raycastResult: ARRaycastResult = arView.raycast(from: arView.center, allowing: .estimatedPlane, alignment: .horizontal).last {
                
                let planeAnchor: AnchorEntity = AnchorEntity(raycastResult: raycastResult)
                anchorPlaced = true
                self.planeAnchor = planeAnchor
                self.view.makeToast("After click \"Launch CompA\", verify that you can register clicks on Cube 1 and Cube 2")
                button.setTitle("Launch CompA", for: .normal)
                PlaceAnchorPrompt.isHidden = true
                arView.scene.addAnchor(planeAnchor)
            }
        }
    }
    
    /**
     *  Initializer for all state variables in the harness
     */
    override func viewDidLoad() {
        super.viewDidLoad()
        self.labels = ["1": self.Cube1Count, "2": self.Cube2Count, "3": self.Cube3Count, "4": self.Cube4Count]
        self.counts = ["1": 0, "2": 0, "3" : 0, "4": 0]
        
        button.titleLabel?.adjustsFontSizeToFitWidth = true
        button.titleLabel?.minimumScaleFactor = 0.5
    }
    /**
     *  Handler for when the next button is pressed
     */
    @IBAction func onNextClick(_ sender: Any) {
        
        if (anchorPlaced && expNum == 1){
            self.componentA = ComponentA(location: planeAnchor)
            self.componentA.launch()
            ComponentA_Launched = true
            self.view.makeToast("Click \"Launch CompB\" to proceed")
            button.setTitle("Launch CompB", for: .normal)
        }
        
        if (ComponentA_Launched && anchorPlaced && expNum == 2){
            self.componentB = ComponentB(location: planeAnchor)
            self.componentB.launch()
            ComponentB_Launched = true
            button.isHidden = true
            self.view.makeToast("After verifying overlapped cubes, start a new experiment by restarting the app")
        }
    
        /**
         *  Handler for next Module
         */
        if (ComponentA_Launched&&ComponentB_Launched) {
            self.view.makeToast("Finished experiments, restart the app to run experiments again")
//            componentA.next(expNum: expNum)
//            componentB.next(expNum: expNum)
        }
        expNum += 1

        
    }
}

