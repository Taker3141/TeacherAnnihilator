package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class Floor implements IHitBox
{
	public Vector3f location, size, offset;

	public Floor(Vector3f location, Vector3f size, Vector3f offset)
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
		Vector3f c1 = Vector3f.add(location, offset, null);
		Vector3f c2 = Vector3f.add(c1, size, null);
		if(box.isInside(c1)) return true;
		if(box.isInside(new Vector3f(c1.x, c1.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c1.x, c2.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c1.x, c2.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c1.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c1.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c2.y, c1.z))) return true;
		if(box.isInside(c2)) return true;
		return false;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}
}
