package entity;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Person extends Entity
{
	protected static final float GRAVITY = -9.81F;
	
	protected boolean isInAir = false;
	public Vector3f v = new Vector3f();
	public List<Vector3f> forces = new ArrayList<Vector3f>();
	protected float terrainHeight = 0;
	protected int health = 10;
	protected String name;
	
	public Person(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		
		for(Vector3f force : forces) v = Vector3f.add(v, force, v);
		forces.clear();
		v.y += GRAVITY * delta;
		
		calculateFriction(delta);
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		
		terrainHeight = terrain.getHeight(position.x, position.z);
		if(position.y <= terrainHeight && v.y <= 0) 
		{
			v.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	private void calculateFriction(float delta)
	{
		if(Math.abs(v.x) < 0.01F) v.x = 0; else v.x *= Math.pow(0.9F, delta * 50);
		if(Math.abs(v.z) < 0.01F) v.z = 0; else v.z *= Math.pow(0.9F, delta * 50);
	}
	
	public void damage(int ammount)
	{
		health -= ammount;
		if(health <= 0)
		{
			health = 0;
			unregister();
			System.out.println(name + " is dead");
		}
	}
}
