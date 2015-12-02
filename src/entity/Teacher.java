package entity;

import java.util.List;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;

public class Teacher extends Person
{	
	public Teacher(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.name = name;
		hitBox.size = new Vector3f(0.2F, 0.8F, 0.2F);
		hitBox.offset = new Vector3f(-0.1F, 0, -0.1F);
	}
	
	public Teacher(String texture, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name, List<Entity> list)
	{
		this(new TexturedModel(body, new ModelTexture(MainManagerClass.loader.loadTexture(texture))), position, rotX, rotY, rotZ, scale, name, list);
	}
	
	@Override
	public void update(Terrain t)
	{
		super.update(t);
	}
}
