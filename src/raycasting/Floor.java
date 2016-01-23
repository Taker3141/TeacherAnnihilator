package raycasting;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Floor implements IHitBox
{
	public Vector3f location, size, offset;
	protected Vector2f[] polygon;
	protected float height;

	public Floor(Vector3f location, float height, Vector3f offset, Vector2f[] polygon)
	{
		this.location = location;
		this.height = height;
		this.offset = offset;
		this.polygon = polygon;
		size = new Vector3f(80, 0.5F, 50);
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
		return false;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}
}
