package raycasting;

import org.lwjgl.util.vector.Vector3f;

/**
 * Axis-aligned bounding box
 * @author Taker
 */

public class AABB
{
	public Vector3f location, size, offset;

	public AABB(Vector3f location, Vector3f size, Vector3f offset)
	{
		this.location = location;
		this.size = size;
		this.offset = offset;
	}
	
	public boolean isInside(Vector3f point)
	{
		if(point.x < location.x + offset.x || point.x > location.x + offset.x + size.x) return false;
		if(point.y < location.y + offset.y || point.x > location.y + offset.y + size.y) return false;
		if(point.z < location.z + offset.z || point.x > location.z + offset.z + size.z) return false;
		return true;
	}
}
