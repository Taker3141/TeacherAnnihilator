package raycasting;

import org.lwjgl.util.vector.Vector3f;

public interface ICollidable
{	
	public boolean isInsideHitBox(Vector3f point);
	public boolean isInsideHitBox(IHitBox box);
	public IHitBox getHitBox();
	public void hover();
	public void click();
}
