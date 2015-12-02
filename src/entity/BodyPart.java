package entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;

public class BodyPart extends Movable
{
	protected boolean isAttatched = true;
	protected Person p;
	protected Matrix4f transformFromParent;
	
	BodyPart(TexturedModel model, Person parent, Matrix4f transform)
	{
		super(model, new Vector3f(0, 0, 0), 0, 0, 0, transform.m00, parent.entityList);
		p = parent;
		position = copyPosition();
		transformFromParent = transform;
	}
	
	BodyPart(TexturedModel model, Person parent, Matrix4f transform, Vector3f hitboxSize, Vector3f hitboxOffset)
	{
		this(model, parent, transform);
		hitBox.location = p.position;
		hitBox.size = hitboxSize;
		hitBox.offset = hitboxOffset;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		if(!isAttatched) 
		{
			super.update(terrain);
			hitBox.location = position;
		}
		else 
		{
			position = copyPosition();
			hitBox.location = position;
		}
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		if(isAttatched) return Matrix4f.mul(p.getTransformationMatrix(), transformFromParent, null);
		else return Maths.createTransformationMatrix(position, rotX, rotY, rotZ, p.scale);
	}
	
	private Vector3f copyPosition()
	{
		return new Vector3f(p.position);
	}
	
	public void ripOff()
	{
		isAttatched = false;
		position = new Vector3f(position.x, position.y - hitBox.offset.y, position.z);
		yOffset = hitBox.offset.y;
	}
	
	@Override
	public void click()
	{
		if(isAttatched) ripOff();
	}
}
