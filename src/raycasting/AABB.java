package raycasting;

import org.lwjgl.util.vector.Vector3f;

/**
 * Axis-aligned bounding box
 * @author Taker
 */

public class AABB implements IHitBox
{
	public Vector3f location, size, offset;

	public AABB(Vector3f location, Vector3f size, Vector3f offset)
	{
		this.location = location;
		this.size = size;
		this.offset = offset;
	}
	
	@Override
	public boolean isInside(Vector3f point)
	{
		Vector3f corner = Vector3f.add(location, offset, null);
		if(point.x < corner.x || point.x > corner.x + size.x) return false;
		if(point.y < corner.y || point.y > corner.y + size.y) return false;
		if(point.z < corner.z || point.z > corner.z + size.z) return false;
		return true;
	}
	
	@Override
	public boolean isInside(IHitBox box)
	{
		Vector3f l = Vector3f.add(location, offset, null);
		Vector3f s = size;
		if(isInside(l)) return false;
		if(box.isInside(new Vector3f(l.x, l.y, l.z + s.z))) return true;
		if(box.isInside(new Vector3f(l.x, l.y + s.y, l.z))) return true;
		if(box.isInside(new Vector3f(l.x, l.y + s.y, l.z + s.z))) return true;
		if(box.isInside(new Vector3f(l.x + s.x, l.y, l.z + s.z))) return true;
		if(box.isInside(new Vector3f(l.x + s.x, l.y + s.y, l.z))) return true;
		if(box.isInside(new Vector3f(l.x + s.x, l.y + s.y, l.z + s.z))) return true;
		if(box.isInside(Vector3f.add(l, offset, null))) return true;
		return false;
	}
}
