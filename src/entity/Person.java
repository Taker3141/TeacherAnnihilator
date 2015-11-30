package entity;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Person extends Entity
{
	protected static final float GRAVITY = -50;
	
	protected boolean isInAir = false;
	public Vector3f v = new Vector3f();
	public List<Vector3f> forces = new ArrayList<Vector3f>();
	protected float terrainHeight = 0;
	
	public Person(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void update(Terrain terrain)
	{
		for(Vector3f force : forces) v = Vector3f.add(v, force, v);
		forces.clear();
		v.y += GRAVITY * DisplayManager.getFrameTimeSeconds();
		
		if(Math.abs(v.x) < 0.01F) v.x = 0; else v.x *= 0.9F;
		if(Math.abs(v.z) < 0.01F) v.z = 0; else v.z *= 0.9F;
		position.x += v.x * DisplayManager.getFrameTimeSeconds();
		position.y += v.y * DisplayManager.getFrameTimeSeconds();
		position.z += v.z * DisplayManager.getFrameTimeSeconds();
		
		terrainHeight = terrain.getHeight(position.x, position.z);
		if(position.y <= terrainHeight && v.y <= 0) 
		{
			v.y = 0;
			position.y = terrainHeight + 0.01F;
			isInAir = false;
		}
	}
}
