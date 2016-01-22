package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class Floor implements IHitBox
{
	public Vector3f location, size, offset;
	protected Vector3f[] polygon;
	protected float height;

	public Floor(Vector3f location, float height, Vector3f offset, Vector3f[] polygon)
	{
		this.location = location;
		this.height = height;
		this.offset = offset;
		this.polygon = polygon;
	}
	
	@Override
	public boolean isInside(Vector3f point)
	{
		//TODO Polygon stuff
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
