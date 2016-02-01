package raycasting;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class TransformedHitBox extends AABB
{
	public Matrix4f matrix;
	
	public TransformedHitBox(Vector3f location, Vector3f size, Vector3f offset, Matrix4f matrix)
	{
		super(location, size, offset);
		this.matrix = matrix;
	}
	
	@Override
	public boolean isInside(Vector3f point)
	{
		Vector4f transformedPoint = Matrix4f.transform(matrix, new Vector4f(point.x, point.y, point.z, 1), null);
		return super.isInside(new Vector3f(transformedPoint.x, transformedPoint.y, transformedPoint.z));
	}
	
	@Override
	public boolean isInside(IHitBox box)
	{
		Vector3f t1 = Vector3f.add(location, offset, null);
		Vector4f c1 = Matrix4f.transform(matrix, new Vector4f(t1.x, t1.y, t1.z, 1), null);
		Vector4f c2 = Vector4f.add(c1, Matrix4f.transform(matrix, new Vector4f(size.x, size.y, size.z, 0), null), null);
		if(box.isInside(new Vector3f(c1.x, c1.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c1.x, c1.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c1.x, c2.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c1.x, c2.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c1.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c1.y, c2.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c2.y, c1.z))) return true;
		if(box.isInside(new Vector3f(c2.x, c2.y, c2.z))) return true;
		return false;
	}
}
