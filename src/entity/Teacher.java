package entity;

import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Teacher extends Person
{
	protected String name;
	
	private int health = 10;
	
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
	
	@Override
	public void click()
	{
		if (health > 0)
		{
			health--;
			System.out.println(name + ".health == " + health);
			
			forces.add(new Vector3f(0, 5, 0));
		}
		else
		{
			System.out.println(name + " is dead");
			unregister();
		}
	}
}
