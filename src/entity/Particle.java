package entity;

import java.util.*;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import raycasting.IHitBox;
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
	
	public Particle(String textureName, Vector3f position, List<Entity> list, Random r)
	{
		super(new TexturedModel(MODEL, new ModelTexture(MainManagerClass.loader.loadTexture("texture/particle/" + textureName))), new Vector3f(position), 0, 0, 0, PARTICLE_SIZE, list);
		model.getTexture().setUseFakeLightning(true);
		rotX = r.nextInt(360);
		rotY = r.nextInt(360);
		rotZ = r.nextInt(360);
		v = new Vector3f((r.nextFloat() * 2F) - 1, r.nextFloat() + 1, (r.nextFloat() * 2F) - 1);
		v.scale(2);
	}
	
	@Override
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		v.y += GRAVITY * delta * 0.1;
		lifetime -= DisplayManager.getFrameTimeSeconds();
		if(lifetime <= 0) unregister();
	}
	
	@Override
	public boolean isInsideHitBox(IHitBox box)
	{
		return false;
	}
	
	@Override
	public boolean isInsideHitBox(Vector3f point)
	{
		return false;
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
