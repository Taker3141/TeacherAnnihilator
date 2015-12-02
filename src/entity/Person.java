package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.MainManagerClass;
import objLoader.ModelData;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.SimpleModel;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;

public class Person extends Movable
{
	public static SimpleModel body;
	public static SimpleModel arm;
	public static SimpleModel leg;
	public static SimpleModel head;
	
	protected int health = 10;
	protected String name;
	protected Map<String, BodyPart> bodyParts;
	
	public Person(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		addBodyParts();
	}
	
	public Person(String texture, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(new TexturedModel(body, new ModelTexture(MainManagerClass.loader.loadTexture(texture))), position, rotX, rotY, rotZ, scale, list);
	}
	
	public void damage(int ammount)
	{
		health -= ammount;
		System.out.println(name + ".health == " + health);
		if(health <= 0)
		{
			health = 0;
			unregister();
			System.out.println(name + " is dead");
		}
	}
	
	protected void addBodyParts()
	{
		bodyParts = new HashMap<String, BodyPart>();
		ModelTexture tex = model.getTexture();
		bodyParts.put("head", new BodyPart(new TexturedModel(head, tex), this, getNewMatrix(false), new Vector3f(0.2F, 0.2F, 0.2F), new Vector3f(-0.1F, 0.45F, -0.1F)));
		bodyParts.put("leftLeg", new BodyPart(new TexturedModel(leg, tex), this, getNewMatrix(false)));
		bodyParts.put("rightLeg", new BodyPart(new TexturedModel(leg, tex), this, getNewMatrix(true)));
		bodyParts.put("leftArm", new BodyPart(new TexturedModel(arm, tex), this, getNewMatrix(false)));
		bodyParts.put("rightArm", new BodyPart(new TexturedModel(arm, tex), this, getNewMatrix(true)));
	}
	
	private Matrix4f getNewMatrix(boolean mirrored)
	{
		Matrix4f ret = Matrix4f.setIdentity(new Matrix4f());
		if(mirrored)
		{
			ret.m00 *= -1;
			ret.m22 *= -1;
		}
		ret = Matrix4f.rotate((float)(Math.PI / 2), new Vector3f(0, 1, 0), ret, ret);
		return ret;
	}
	
	public static void init()
	{
		ModelData bodyData = OBJLoader.loadOBJModel("body");
		body = MainManagerClass.loader.loadToVAO(bodyData.getVertices(), bodyData.getTextureCoords(), bodyData.getNormals(), bodyData.getIndices());
		ModelData armData = OBJLoader.loadOBJModel("arm");
		arm = MainManagerClass.loader.loadToVAO(armData.getVertices(), armData.getTextureCoords(), armData.getNormals(), armData.getIndices());
		ModelData legData = OBJLoader.loadOBJModel("leg");
		leg = MainManagerClass.loader.loadToVAO(legData.getVertices(), legData.getTextureCoords(), legData.getNormals(), legData.getIndices());
		ModelData headData = OBJLoader.loadOBJModel("head");
		head = MainManagerClass.loader.loadToVAO(headData.getVertices(), headData.getTextureCoords(), headData.getNormals(), headData.getIndices());
	}
}
