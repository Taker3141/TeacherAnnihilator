package entity;

import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.Collidable;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Entity implements Collidable
{
	private TexturedModel model;
	protected Vector3f position;
	protected float rotX, rotY, rotZ;
	protected float scale;
	protected AABB hitBox;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		hitBox = new AABB(position, new Vector3f(), new Vector3f());
	}
	
	public void update(Terrain t)
	{
		
	}
	
	public void increasePosition(float dx, float dy, float dz)
	{
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}

	public TexturedModel getModel()
	{
		return model;
	}

	public void setModel(TexturedModel model)
	{
		this.model = model;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getRotX()
	{
		return rotX;
	}

	public void setRotX(float rotX)
	{
		this.rotX = rotX;
	}

	public float getRotY()
	{
		return rotY;
	}

	public void setRotY(float rotY)
	{
		this.rotY = rotY;
	}

	public float getRotZ()
	{
		return rotZ;
	}

	public void setRotZ(float rotZ)
	{
		this.rotZ = rotZ;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	@Override
	public boolean isInsideHitBox(Vector3f point)
	{
		return hitBox.isInside(point);
	}

	@Override
	public void hover()
	{
		
	}

	@Override
	public void click()
	{
		
	}
}
