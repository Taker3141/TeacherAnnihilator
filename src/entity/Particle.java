package entity;

import java.util.*;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.SimpleModel;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;

public class Particle extends Movable
{
	protected static final float PARTICLE_SIZE = 0.2F;
	protected static SimpleModel MODEL;
	protected float lifetime = 2F;
	
	protected static Random r = new Random();
	
	public Particle(String textureName, Vector3f position, List<Entity> list)
	{
		super(new TexturedModel(MODEL, new ModelTexture(MainManagerClass.loader.loadTexture("texture/particle/" + textureName))), position, 0, 0, 0, PARTICLE_SIZE, list, 0.1F);
		model.getTexture().setUseFakeLightning(true);
		rotX = r.nextInt(360);
		rotY = r.nextInt(360);
		rotZ = r.nextInt(360);
		v.x = (r.nextFloat() * 2F) - 1;
		v.y = r.nextFloat();
		v.z = (r.nextFloat() * 2F) - 1;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		super.update(terrain);
		lifetime -= DisplayManager.getFrameTimeSeconds();
		if(lifetime <= 0) unregister();
	}
	
	@Override
	protected void checkTerrain(Terrain terrain)
	{
		terrainHeight = 0;
	}
	
	@Override
	protected float getGravity()
	{
		return -1F;
	}
	
	public static void init()
	{
		MODEL = MainManagerClass.loader.loadToVAO
				(new float[] {0, 0, 0,  0, 1, 0,  1, 0, 0,  0, 1, 0,  1, 1, 0,  1, 0, 0}, 
				new float[] {0, 0,  0, 1,  1, 0,  0, 1,  1, 1,  1, 0}, 
				new float[] {0, 0, 1,  0, 0, 1,  0, 0, 1,  0, 0, 1,  0, 0, 1,  0, 0, 1}, 
				new int[] {1, 2, 3,  3, 4, 5});
	}
}
