/** 
 * Functions for mesh interaction
 */

/**
 * Change color of first mesh raycaster intersects with to a random color
 * @param {*} raycaster raycaster that has a casted ray in a particular direction
 * @param {*} meshes list of meshes to check for ray intersection
 * @returns reference to mesh that has had its color changed
 */
export function changeColorOnIntersect(raycaster, meshes)
{
  var intersections = raycaster.intersectObjects(meshes);
  if ( intersections.length > 0 && intersections[0].object.collisions) {
    intersections[0].object.material.color.setHex(Math.random() * 0xffffff);
    console.log(intersections[0]);

    return intersections[0].object;
  }
  return null;
}
