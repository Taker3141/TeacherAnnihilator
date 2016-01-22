package main;

import java.util.List;
import java.util.Random;
import objLoader.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import entity.*;
import raycasting.AABB;
import raycasting.Floor;
import renderer.*;
import renderer.models.TexturedModel;
import renderer.textures.*;
import terrain.Terrain;
import world.World;

public class MainGameLoop
{
	public static World w;
	
	public static void doGame()
	{
		w = new World();
		Loader loader = MainManagerClass.loader;
		TexturedModel texturedModel = new TexturedModel(OBJLoader.loadOBJModel("klo"), new ModelTexture(loader.loadTexture("texture/test")));
		ModelTexture texture = texturedModel.getTexture();
		TexturedModel grass = new TexturedModel(OBJLoader.loadOBJModel("grass"), new ModelTexture(loader.loadTexture("texture/grass")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLightning(true);
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		Entity lmg = new Entity(new TexturedModel(OBJLoader.loadOBJModel("lmg"), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(172, 33, 131), 0, 180, 0, 5, w.entities);
		lmg.setHitBox(new Floor(new Vector3f(lmg.position), 0.5F, new Vector3f(-33, 0, -25), null));
		new Teacher("texture/person/hans", new Vector3f(105, 0, 105), 0, 0, 0, 0.1F, "teacher.hans", w.entities, 40);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("texture/tree"));
		TexturedModel tree = new TexturedModel(OBJLoader.loadOBJModel("tree"), treeTexture);
		{
			final int offsetX = 100;
			final int offsetZ = 200;
			for(int i = 0; i < 10; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					int x = i * 10 + offsetX;
					int z = j * 10 + offsetZ;
					Entity entity = new Entity(tree, new Vector3f(x, w.height(x, z), z), 0, 0, 0, 0.5F, w.entities);
					entity.setHitBox(new AABB(new Vector3f(entity.position), new Vector3f(0.8F, 10, 0.8F), new Vector3f(-0.4F, 0, -0.4F)));
				}
			}
		}
		TexturedModel bush = new TexturedModel(OBJLoader.loadOBJModel("bush"), treeTexture);
		{
			Entity entity = new Entity(bush, new Vector3f(100, w.height(100, 110), 110), 0, 0, 0, 0.2F, w.entities);
			entity.setHitBox(new AABB(new Vector3f(entity.position), new Vector3f(0.5F, 5, 0.5F), new Vector3f(-0.5F, 0, -0.5F)));
		}
		generateBushRow(63, 172, 132, 172, w.entities, 1, bush, w.getTerrain());
		generateBushRow(35, 77, 90, 83, w.entities, 1, bush, w.getTerrain());
		TexturedModel house = new TexturedModel( OBJLoader.loadOBJModel("house"), new ModelTexture(loader.loadTexture("texture/test")));
		new Entity(house, new Vector3f(122, w.height(122, 45), 45), 0, 120, 0, 6, w.entities);
		TexturedModel sheet = new TexturedModel(OBJLoader.loadOBJModel("sheet"), new ModelTexture(loader.loadTexture("texture/sheet")));
		new Entity(sheet, new Vector3f(102, w.height(102,  102) + 1, 102), 0, 0, 0, 0.1F, w.entities);
		w.updateRaycaster();
		while (!Display.isCloseRequested())
		{
			if(!w.tick()) break;
			DisplayManager.updateDisplay();
		}
	}
	
	private static void generateBushRow(float x1, float z1, float x2, float z2, List<Entity> list, float step, TexturedModel bush, Terrain t)
	{
		Random r = new Random();
		float dx = x2 - x1;
		float dz = z2 - z1;
		int count = (int)(dx / step);
		for(int i = 0; i < count; i++)
		{
			float x = x1 + i * step + r.nextFloat();
			float z = z1 + (dz / count) * i + r.nextFloat();
			Entity b = new Entity(bush, new Vector3f(x, t.getHeight(x, z), z), 0, 0, 0, 0.5F, list);
			b.setHitBox(new AABB(new Vector3f(b.position), new Vector3f(0.8F, 5, 0.8F), new Vector3f(-0.4F, 0, -0.4F)));
		}
	}
}
