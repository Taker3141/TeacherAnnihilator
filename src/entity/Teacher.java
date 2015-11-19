package entity;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Teacher extends Entity
{
	protected String name;

	private static final float GRAVITY = -50;

	private float upwardsSpeed = 0;
	private float terrainHeight = 0;
	
	public Teacher(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name)
	{
		super(model, position, rotX, rotY, rotZ, scale);
		this.name = name;
		hitBox.size = new Vector3f(1, 2, 1);
		hitBox.offset = new Vector3f(-0.5F, 0, -0.5F);
	}
	
	@Override
	public void update(Terrain t)
	{
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		position.y += upwardsSpeed * DisplayManager.getFrameTimeSeconds();
		terrainHeight = t.getHeight(position.x, position.z);
		if(position.y < terrainHeight) 
		{
			upwardsSpeed = 0;
			position.y = terrainHeight;
		}
	}
}
