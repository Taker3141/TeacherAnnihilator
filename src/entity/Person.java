package entity;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Person extends Entity
{
	protected static final float GRAVITY = -50;
	protected boolean isInAir = false;
	
	public Vector3f v = new Vector3f();
	protected float terrainHeight = 0;
	
	public Person(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void update(Terrain terrain)
	{
		v.y += GRAVITY * DisplayManager.getFrameTimeSeconds();
		
		if(position.y <= terrainHeight && v.y <= 0) 
		{
			v.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
		
		terrainHeight = terrain.getHeight(position.x, position.z);
		
		position.x += v.x * DisplayManager.getFrameTimeSeconds();
		position.y += v.y * DisplayManager.getFrameTimeSeconds();
		position.z += v.z * DisplayManager.getFrameTimeSeconds();
	}
}
