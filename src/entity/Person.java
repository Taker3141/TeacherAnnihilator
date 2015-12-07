package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import main.MainManagerClass;
import objLoader.ModelData;
import objLoader.OBJLoader;
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
		if(health <= 0) die();
	}
	
	protected void addBodyParts()
	{
		bodyParts = new HashMap<String, BodyPart>();
		ModelTexture tex = model.getTexture();
		bodyParts.put("head", new BodyPart(new TexturedModel(head, tex), this, new Vector3f(0, 4.3F, 0), new Vector3f(0.2F, 0.2F, 0.2F), new Vector3f(-0.1F, 0, -0.1F)));
		bodyParts.put("leftLeg", new BodyPart(new TexturedModel(leg, tex), this, new Vector3f(-0.4F, 1.7F, 0), new Vector3f(0.05F, 0.15F, 0.05F), new Vector3f(-0.025F, -0.15F, -0.025F)));
		bodyParts.put("rightLeg", new BodyPart(new TexturedModel(leg, tex), this, new Vector3f(0.4F, 1.7F, 0), new Vector3f(0.05F, 0.15F, 0.05F), new Vector3f(-0.025F, -0.15F, -0.025F)));
		bodyParts.put("leftArm", new BodyPart(new TexturedModel(arm, tex), this, new Vector3f(1F, 3.7F, 0), new Vector3f(0.05F, 0.1F, 0.05F), new Vector3f(-0.025F, -0.1F, -0.025F)));
		bodyParts.put("rightArm", new BodyPart(new TexturedModel(arm, tex), this, new Vector3f(-1F, 3.7F, 0), new Vector3f(0.05F, 0.1F, 0.05F), new Vector3f(-0.025F, -0.1F, -0.025F)));
		
		bodyParts.get("head").rotY = 90;
		bodyParts.get("leftArm").rotZ = 15;
		bodyParts.get("rightArm").rotZ = -15;
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
	
	protected void die()
	{
		health = 0;
		unregister();
		for(Entry<String, BodyPart> e : bodyParts.entrySet()) e.getValue().ripOff();
		System.out.println(name + " is dead");
	}
}
