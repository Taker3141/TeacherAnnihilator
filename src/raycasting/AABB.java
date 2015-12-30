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
	
	public boolean isInside(AABB box)
	{
		Vector3f l = box.location;
		Vector3f o = box.offset;
		if(isInside(l)) return false;
		if(isInside(new Vector3f(l.x, l.y, l.z + o.z))) return true;
		if(isInside(new Vector3f(l.x, l.y + o.y, l.z))) return true;
		if(isInside(new Vector3f(l.x, l.y + o.y, l.z + o.z))) return true;
		if(isInside(new Vector3f(l.x + o.x, l.y, l.z + o.z))) return true;
		if(isInside(new Vector3f(l.x + o.x, l.y + o.y, l.z))) return true;
		if(isInside(new Vector3f(l.x + o.x, l.y + o.y, l.z + o.z))) return true;
		if(isInside(Vector3f.add(l, box.offset, null))) return true;
		return false;
	}
}
