package entity;

import java.util.List;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;

public class Teacher extends Person
{	
	public Teacher(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name, List<Entity> list, float mass)
	{
		super(model, position, rotX, rotY, rotZ, scale, list, mass);
		this.name = name;
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
	}
	
	public Teacher(String texture, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name, List<Entity> list, float mass)
	{
		this(new TexturedModel(body, new ModelTexture(MainManagerClass.loader.loadTexture(texture))), position, rotX, rotY, rotZ, scale, name, list, mass);
	}
	
	@Override
	public void update(Terrain t)
	{
		super.update(t);
		v.z = 1;
	}
}
