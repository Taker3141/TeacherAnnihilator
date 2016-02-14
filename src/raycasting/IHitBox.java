package raycasting;

import org.lwjgl.util.vector.Vector3f;

public interface IHitBox
{
	public CollisionData isInside(Vector3f point);
	public CollisionData isInside(IHitBox box);
	public boolean isPlatform();
	public Vector3f getCenter(Vector3f point);
	
	public static class CollisionData
	{
		public final Vector3f origin;
		public final boolean isPlatform;
		
		protected CollisionData(Vector3f o, boolean platform)
		{
			origin = o;
			isPlatform = platform;
		}
	}
}
