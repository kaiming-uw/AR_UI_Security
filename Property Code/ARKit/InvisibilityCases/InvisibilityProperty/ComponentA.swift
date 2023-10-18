
import Foundation
import RealityKit

class ComponentA: ARComponent {
    
    // referene to the anchor for the experiment
    var location: AnchorEntity = AnchorEntity.init()
    
    public init () {}
    
    public init(location: AnchorEntity) {
        self.location = location
    }
    /**
     *  The Component A launch function as described in the experiment harness
     */
    func launch() {
        // generates cube1
        let mesh01 = MeshResource.generateBox(size: 0.3)
        let cube1 = ModelEntity(mesh: mesh01, materials: [SimpleMaterial(color: .red, isMetallic: false)])
        cube1.generateCollisionShapes(recursive: true)
        cube1.name = "1"
        // Places cube1 in the scene
        cube1.setParent(location)
    }
    
    /**
     *  Next function for Component A as described in the expermint harness document
     */
    func next(expNum: Int) {}
    
}
