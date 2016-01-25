package raycasting;

import org.lwjgl.util.vector.Vector3f;

public interface IHitBox
{
	public boolean isInside(Vector3f point);
	public boolean isInside(IHitBox box);
	public boolean isPlatform();
	public Vector3f getCenter();
}
