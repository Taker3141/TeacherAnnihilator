package entity;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;

public class Entity implements ICollidable
{
	public TexturedModel model;
	public Vector3f position;
	public float rotX, rotY, rotZ;
	public float scale;
	protected AABB hitBox;
	protected List<Entity> entityList;
	public boolean invisible = false;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		hitBox = new AABB(position, new Vector3f(), new Vector3f());
		entityList = list;
		register();
	}
	
	public void update(Terrain t)
	{
		
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		rotX += dx;
		rotY += dy;
		rotZ += dz;
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
	
	public void register()
	{
		entityList.add(this);
	}
	
	public void unregister()
	{
		entityList.remove(this);
	}

	public Matrix4f getTransformationMatrix()
	{
		return Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
	}
}
