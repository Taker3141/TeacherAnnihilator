package entity;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.ICollidable;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Movable extends Entity
{
	protected static final float GRAVITY = -50F;
	protected boolean isInAir = false;
	public Vector3f v = new Vector3f();
	public List<Vector3f> forces = new ArrayList<Vector3f>();
	protected float terrainHeight = 0;
	
	public Movable(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}
	
	@Override
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		
		for (Vector3f force : forces) v = Vector3f.add(v, force, v);
		forces.clear();
		v.y += getGravity() * delta;
		
		if (!canMove(v.x * delta, v.z * delta, terrain))
		{
			v.x *= -1;
			v.y = 0;
			v.z *= -1;
		}
		calculateFriction(delta);
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		checkTerrain(terrain);
		if (position.x != position.x)
		{
			System.out.println("Oh, shit! Position is Not A Number!");
			position = new Vector3f();
		}
	}
	
	private void calculateFriction(float delta)
	{
		double factor = Math.pow(0.9F, delta * 50);
		if (Math.abs(v.x) < 0.05F) v.x = 0;
		else v.x *= factor;
		if (Math.abs(v.z) < 0.05F) v.z = 0;
		else v.z *= factor;
	}
	
	protected void checkTerrain(Terrain terrain)
	{
		terrainHeight = terrain.getHeight(position.x, position.z);
		if (position.y <= terrainHeight)
		{
			v.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	protected boolean canMove(float x, float z, Terrain terrain)
	{
		return (position.y > terrainHeight || (terrain.getHeight(position.x + x, position.z + z) - terrainHeight) < 0.2F) && noCollision();
	}
	
	private boolean noCollision()
	{
		for (ICollidable c : entityList)
		{
			if (c instanceof BodyPart || c == this) continue;
			if (c.isInsideHitBox(hitBox)) 
			{
				if(c instanceof Movable)
				{
					Movable m = (Movable)c;
					m.forces.add((Vector3f)Vector3f.sub(position, m.position, null).normalise().scale(v.length()));
				}
				return false;
			}
		}
		return true;
	}
	
	protected float getGravity()
	{
		return GRAVITY;
	}
}
