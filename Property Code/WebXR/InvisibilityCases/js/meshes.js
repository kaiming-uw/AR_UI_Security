export function addAnchoredBoxToScene(reticle, anchor)
{

  console.debug("Anchor created");

  anchor.context = {};

  var geometry = new THREE.BoxGeometry( 0.3, 0.3, 0.3 ).translate( 0, 0.1, 0 );
  var material = new THREE.MeshPhongMaterial( { color: 0xffffff * Math.random() } );
  var mesh = new THREE.Mesh( geometry, material );
  reticle.matrix.decompose( mesh.position, mesh.quaternion, mesh.scale );
  // mesh.scale.y = Math.random() * 2 + 1;
  mesh.geometry.computeBoundingBox();
  mesh.geometry.boundingBox.expandByScalar(3.0);
  anchor.context.sceneObject = mesh;
  mesh.anchor = anchor;
  this.anchoredObjects.push(mesh);
  this.scene.add( mesh );
  this.meshes.push(mesh);
  console.log("Added mesh");

}