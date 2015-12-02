package entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;

public class BodyPart extends Entity
{
	protected boolean isAttatched = true;
	protected Person p;
	protected Matrix4f transformFromParent;
	
	BodyPart(TexturedModel model, Person parent, Matrix4f transform)
	{
		super(model, new Vector3f(transform.m03, transform.m13, transform.m23), 0, 0, 0, transform.m00, parent.entityList);
		p = parent;
		transformFromParent = transform;
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		return Matrix4f.mul(p.getTransformationMatrix(), transformFromParent, null);
	}
}
