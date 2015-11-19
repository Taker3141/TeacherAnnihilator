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
		Vector3f corner = Vector3f.add(location, offset, null);
		if(point.x < corner.x || point.x > corner.x + size.x) return false;
		if(point.y < corner.y || point.y > corner.y + size.y) return false;
		if(point.z < corner.z || point.z > corner.z + size.z) return false;
		return true;
	}
}
