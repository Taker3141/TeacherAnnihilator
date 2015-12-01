package entity;

import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Teacher extends Person
{	
	public Teacher(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name)
	{
		super(model, position, rotX, rotY, rotZ, scale);
		this.name = name;
		hitBox.size = new Vector3f(0.2F, 0.8F, 0.2F);
		hitBox.offset = new Vector3f(-0.1F, 0, -0.1F);
	}
	
	@Override
	public void update(Terrain t)
	{
		super.update(t);
	}
}
