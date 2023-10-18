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
    
    // The labels for the hit counts
    @IBOutlet var Cube1Count: UILabel!
    @IBOutlet var Cube2Count: UILabel!
    @IBOutlet weak var button: UIButton!
    var ComponentA_Launched: Bool = false
    var ComponentB_Launched: Bool = false
    @IBOutlet weak var PlaceAnchorPrompt: UILabel!
    
    
    var labels: [String: UILabel] = [:];
    var counts: [String: Int] = [:]

    var anchorPlaced: Bool = false
    var planeAnchor: AnchorEntity = AnchorEntity.init()
    
    var expNum: Int = 1
    var location: AnchorEntity = AnchorEntity.init()
    
    var componentA: ARComponent = ComponentA()
    var componentB: ARComponent = ComponentB()
    
    /**
     *  This function is called whenever the cube is hit with  a colission
     */
    public func hit(result: CollisionCastHit) {
        
//        counts[result.entity.name]! += 1
        let count = counts[result.entity.name]!
        counts[result.entity.name]! = result.entity.exploit(count: count)
        
        
        let CurrLabel: UILabel = labels[result.entity.name]!
        CurrLabel.text = "Cube " + result.entity.name + ", Clicked " + String(counts[result.entity.name]!)

        
    }
    
    /**
     *  Input handler for when the the user touches the screen
     */
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        // Check to see if anchor is placed, then perform the hit test action
        if (anchorPlaced) {
            let touch = arView.center
            let results: [CollisionCastHit] = arView.hitTest(touch)
            if let result: CollisionCastHit = results.first {
                if (anchorPlaced) {
                    self.hit(result: result)
                }
            }
        } else {
            // Place an anchor
            if let raycastResult: ARRaycastResult = arView.raycast(from: arView.center, allowing: .estimatedPlane, alignment: .horizontal).last {
                
                let planeAnchor: AnchorEntity = AnchorEntity(raycastResult: raycastResult)
                anchorPlaced = true
                self.planeAnchor = planeAnchor
                self.view.makeToast("After click \"Launch CompA\", verify that you can register clicks on Cube 1")
                button.setTitle("Launch CompA", for: .normal)
                PlaceAnchorPrompt.isHidden = true
                arView.scene.addAnchor(planeAnchor)
    
            }
        }
    
    }
    
    /**
     *  Initializer for all state values in the harness
     */
    override func viewDidLoad() {
        super.viewDidLoad()
        self.labels = ["1": self.Cube1Count]
        self.counts = ["1": 0]
    }
    

    /**
     *  Handler for when the next button is clicked
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
            self.componentB = ComponentB(location: planeAnchor, view: self)
            self.componentB.launch()
            ComponentB_Launched = true
            let title  = "Next: expNum: "
            let expNumber = String(expNum + 1 - 2)
            let finalTitle = title + expNumber
            button.setTitle(finalTitle, for: .normal)
        }

        /**
         *  Handler for next Module
         */

        if (ComponentA_Launched && anchorPlaced && expNum <= 7){
            let output = "Simulated raycast sent, current click count is " + String(counts["1"]!)
            self.view.makeToast(output)

            self.componentB.next(expNum:3 )
            let title  = "Next: expNum: "
            let expNumber = String(expNum + 1 - 2)
            let finalTitle = title + expNumber
            button.setTitle(finalTitle, for: .normal)
        }
        if (ComponentA_Launched && anchorPlaced && expNum <= 12){
            let output = "Simulated raycast sent, current click count is " + String(counts["1"]!)
            self.view.makeToast(output)
            self.componentB.next(expNum:4 )
            button.setTitle("Finished Experiments", for: .normal)
        }
    
        if (ComponentA_Launched && anchorPlaced && expNum > 12){
            self.view.makeToast("Finished experiments, restart the app to run experiments again")
        }

        expNum += 1
    }
}


