package entity;

import org.lwjgl.util.vector.Vector3f;

public interface Collidable
{
	public boolean isInsideHitBox(Vector3f point);
}
