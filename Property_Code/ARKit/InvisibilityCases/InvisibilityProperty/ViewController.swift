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
    // Outlets for all of the UI Labels
    @IBOutlet var Cube1Count: UILabel!
    @IBOutlet var Cube2Count: UILabel!
    @IBOutlet var Cube3_1Count: UILabel!
    @IBOutlet var Cube3_2Count: UILabel!
    @IBOutlet var Cube3_3Count: UILabel!
    @IBOutlet weak var button: UIButton!
    @IBOutlet var Cube3_4Count: UILabel!
    
    @IBOutlet var PlaceAnchorPrompt: UILabel!
    
    // Dictionary to store Cube name to label mapping
    var labels: [String: UILabel] = [:];
    // Dictionary to store cube name to count of times hit
    var counts: [String: Int] = [:]
    var ComponentA_Launched: Bool = false
    var ComponentB_Launched: Bool = false
    var anchorPlaced: Bool = false
    var planeAnchor: AnchorEntity = AnchorEntity.init()
    
    
    var expNum: Int = 1
    var location: AnchorEntity = AnchorEntity.init()
    
    var componentA: ARComponent = ComponentA()
    var componentB: ARComponent = ComponentB()
    
    /**
     *  Input handler for when the user starts touching the screen
     */
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        if (anchorPlaced) {
            let touch = arView.center
            
            let results: [CollisionCastHit] = arView.hitTest(touch)
            print(results)
            // Updates counts of cubes getting hit
            if let result: CollisionCastHit = results.first {
                let count = counts[result.entity.name]!
                counts[result.entity.name]! = result.entity.exploit(count: count)
                let CurrLabel: UILabel = labels[result.entity.name]!
                CurrLabel.text = "Cube " + result.entity.name + ", Clicked " + String(counts[result.entity.name]!)
                
            }
        } else {
            // places an anchor down and launches the components
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
     *  Initializer on load
     */
    override func viewDidLoad() {
        super.viewDidLoad()
        self.labels = ["1": self.Cube1Count, "2": self.Cube2Count, "3.1": self.Cube3_1Count, "3.2": self.Cube3_2Count, "3.3": self.Cube3_3Count, "3.4": self.Cube3_4Count]
        self.counts = ["1": 0, "2": 0, "3.1" : 0, "3.2": 0, "3.3": 0, "3.4": 0]
    }
    
    /**
     *  Handles on next button click
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
            let verifyOcclusion = UIAlertController(title: "Confirm", message: "Verify that you can see Cube 2 but not Cube 1", preferredStyle: .alert)
            let ok = UIAlertAction(title: "Yes", style: .default, handler: {(action) -> Void in
            })
            verifyOcclusion.addAction(ok)
            self.present(verifyOcclusion, animated: true, completion: nil)
            let title  = "Next: expNum: "
            let expNumber = String(expNum + 1 - 2)
            let finalTitle = title + expNumber
            button.setTitle(finalTitle, for: .normal)
        }
        if (anchorPlaced && expNum > 2 && expNum <= 6) {
            componentA.next(expNum: expNum)
            componentB.next(expNum: expNum)
            if (expNum < 6) {
                let title  = "Next: expNum: "
                let expNumber = String(expNum + 1 - 2)
                let finalTitle = title + expNumber
                button.setTitle(finalTitle, for: .normal)
            } else {
                button.setTitle("Finished Experiments", for: .normal)
            }

        }
        if (expNum > 6) {
            self.view.makeToast("Finished experiments, restart the app to run experiments again")
        }
        expNum += 1

        
    }
}

