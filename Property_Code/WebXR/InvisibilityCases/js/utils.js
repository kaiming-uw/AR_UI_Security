/** 
 * A set of utility functions for use in experiments
 */


/**
 * Adds an anchored box to user-passed in scene, using position from user-passed in reticle (from hit testing tutorial of Google)
 * @param {*} scene Scene to add cube to 
 * @param {*} name Name of the cube
 * @param {*} x X position for the cube
 * @param {*} y Y position for the cube
 * @param {*} z Z position for the cube
 * @param {*} hexColor Color for the cube
 * @param {*} labelLocation Location enum value of the cube marker
 * @returns Reference to newly created cube
 */
 export function makeCube(scene, name, reticle, anchor, x, y, z, hexColor, labelLocation, size_x, size_y, size_z) {
  const size = 0.2;
  const geometry = new THREE.BoxGeometry(size_x, size_y, size_z).translate(x - reticle.position.x, y - reticle.position.y, z - reticle.position.z);
  const material = new THREE.MeshBasicMaterial({color: hexColor});
  const cube = new THREE.Mesh(geometry, material);
  reticle.matrix.decompose(cube.position, cube.quaternion, cube.scale);
  cube.geometry.computeBoundingBox();
  cube.clickCount = 0;
  cube.name = name;
  // placeholder for end-to-end attack; for this experiment, we simply just increment the clickcount.
  cube.exploit =()=>{
    cube.clickCount++; 
  };


  // create new label for the cube but don't add it to object list so it's not interactable
  switch (labelLocation) {
    case Location.Above:
      makeCubeMarker(name, x, y + size, z, hexColor, name, scene);
      break;
    case Location.Left:
      makeCubeMarker(name, x + size / 2, y + size, z, hexColor, name, scene);
      break;
    case Location.Right:
      makeCubeMarker(name, x - size / 2, y + size, z, hexColor, name, scene);
      break;
    default:
      break;
  }

  console.log(cube.name, Date.now());
  return cube;
}

/**
 * Create text to display in info panel
 * @param {*} z Z position for label
 * @returns String representing message to display
 */
 export function makeClickText(cubeId, clickCount) {
  return "Cube " + cubeId + ": " + clickCount;
}

/**
 * Creates and adds a label at a particular position in the scene
 * @param {*} name Name to display for a cube label
 * @param {*} x X position for label
 * @param {*} y Y position for label
 * @param {*} z Z position for label
 * @param {*} hexColor Color for label text
 * @param {*} parentName Name of parent mesh label is associated with
 * @param {*} scene Scene to add label to
 */
 export function makeCubeMarker(name, x, y, z, hexColor, parentName, scene) {
  const loader = new THREE.FontLoader();
  const finalScene = scene;

  loader.load( './fonts/helvetiker_regular.typeface.json', function (font) {
    const textGeometry = new THREE.TextGeometry(name, {
            font: font,
            size: 0.05,
            height: 0.001,
            curveSegments: 12}); 
    const textMaterials = [
          new THREE.MeshPhongMaterial( { color: hexColor, flatShading: true } ), // front
          new THREE.MeshPhongMaterial( { color: hexColor } ) // side
        ];
    const text = new THREE.Mesh( textGeometry, textMaterials );
    text.geometry.translate(x, y, z);
    text.name = "label_" + name;
    text.visible = true;
    finalScene.add(text);
  });
}
