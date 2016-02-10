package raycasting;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

public class Wall extends AABB
{
	public float angle;
	
	public Wall(Vector3f location, Vector3f size, Vector3f offset, float angle)
	{
		super(location, size, offset);
		this.angle = angle;
	}
	
	@Override
	public boolean isInside(Vector3f point)
	{
		return super.isInside(transform(point));
	}
	
	@Override
	public boolean isInside(IHitBox box)
	{
		Vector3f c1 = Vector3f.add(location, offset, null);
		Vector3f c2 = Vector3f.add(c1, size, null);
		if(box.isInside(transform(new Vector3f(c1.x, c1.y, c1.z)))) return true;
		if(box.isInside(transform(new Vector3f(c1.x, c1.y, c2.z)))) return true;
		if(box.isInside(transform(new Vector3f(c1.x, c2.y, c1.z)))) return true;
		if(box.isInside(transform(new Vector3f(c1.x, c2.y, c2.z)))) return true;
		if(box.isInside(transform(new Vector3f(c2.x, c1.y, c1.z)))) return true;
		if(box.isInside(transform(new Vector3f(c2.x, c1.y, c2.z)))) return true;
		if(box.isInside(transform(new Vector3f(c2.x, c2.y, c1.z)))) return true;
		if(box.isInside(transform(new Vector3f(c2.x, c2.y, c2.z)))) return true;
		return false;
	}
	
	private Vector3f transform(Vector3f point)
	{
		return Vector3f.add(Maths.rotateVector(Vector3f.sub(point, location, null), angle), location, null);
	}
}
