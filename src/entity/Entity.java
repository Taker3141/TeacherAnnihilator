package entity;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import raycasting.IHitBox;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;

public class Entity implements ICollidable
{
	public TexturedModel model;
	public Vector3f position;
	public float rotX, rotY, rotZ;
	public float scale;
	protected IHitBox hitBox;
	protected List<Entity> entityList;
	public boolean invisible = false;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, AABB hitBox)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.hitBox = hitBox;
		entityList = list;
		register();
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(model, position, rotX, rotY, rotZ, scale, list, new AABB(position, new Vector3f(), new Vector3f()));
	}
	
	public void update(Terrain t)
	{
		if(!(this instanceof BodyPart)) noCollision();
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}
	
	protected boolean noCollision()
	{
		for (ICollidable c : entityList)
		{
			if ((c instanceof BodyPart && ((BodyPart)c).isAttatched) || c == this) continue;
			if (c.isInsideHitBox(hitBox)) 
			{
				if(c instanceof Movable)
				{
					try
					{
						Movable m = (Movable) c;
						m.forces.add((Vector3f) Vector3f.sub(m.position, position, null).normalise().scale(2));
					}
					catch (Exception e)
					{
						System.out.println("Something strnge happened...");
						//TODO Strange Bug When Reloading Game
					}
				}
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isInsideHitBox(Vector3f point)
	{
		return hitBox.isInside(point);
	}
	
	@Override
	public boolean isInsideHitBox(IHitBox box)
	{
		return hitBox.isInside(box);
	}
	
	@Override
	public IHitBox getHitBox()
	{
		return hitBox;
	}
	
	public void setHitBox(IHitBox hitBox)
	{
		this.hitBox = hitBox;
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
