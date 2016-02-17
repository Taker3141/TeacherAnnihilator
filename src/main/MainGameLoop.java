package main;

import java.util.List;
import java.util.Random;
import objLoader.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entity.*;
import raycasting.AABB;
import raycasting.Floor;
import raycasting.Wall;
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
		{
			Building lmg = new Building(new TexturedModel(OBJLoader.loadOBJModel("lmg"), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(172, 33, 131), 0, 180, 0, 5, new Vector3f(80, 0.5F, 50), w.entities);
			Vector2f[] polygon = {new Vector2f(0.4F, 14.9F), new Vector2f(-16.1F, 21.0F), 
				new Vector2f(-27.6F, 15.4F), new Vector2f(-32.2F, 3.3F), new Vector2f(-27.1F, -8.5F), 
				new Vector2f(-15.1F, -13.2F), new Vector2f(-3.4F, -7.8F), new Vector2f(-3.5F, -7.3F), 
				new Vector2f(-9.9F, -1.4F), new Vector2f(-7.0F, 1.7F), new Vector2f(-0.4F, -4.1F), 
				new Vector2f(2.6F, -4.0F), new Vector2f(8.7F, 2.4F), new Vector2f(11.8F, -0.5F), 
				new Vector2f(5.8F, -7.0F), new Vector2f(5.8F, -7.5F), new Vector2f(18.2F, -12.2F), 
				new Vector2f(30.4F, -6.7F), new Vector2f(35.0F, 5.8F), new Vector2f(29.3F, 18.0F), 
				new Vector2f(16.8F, 22.5F), new Vector2f(0.4F, 14.9F)};
			lmg.setHitBox(new Floor(new Vector3f(lmg.position), 0.7F, new Vector3f(-33, -0.3F, -25), polygon));
			Wall[] walls = {new Wall(new Vector3f(0.4F, 0, 14.9F), new Vector3f(-16.1F, 0, 21.0F), new Vector3f(0.5F, 2, 0))};
			lmg.setWalls(walls);
		}
		{
			Building lmg = new Building(new TexturedModel(OBJLoader.loadOBJModel("2OG"), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(170.7F, 36, 135.7F), 0, 177, 0, 4, new Vector3f(40, 0.5F, 40), w.entities);
			Vector2f[] p = {new Vector2f(0.7F, 15.2F), new Vector2f(-9.6F, 9.8F), 
					new Vector2f(-13.6F, -1.2F), new Vector2f(-8.9F, -12.6F), new Vector2f(3.0F, -17.3F), 
					new Vector2f(14.7F, -11.4F), new Vector2f(18.4F, 1.2F), new Vector2f(12.2F, 12.0F), 
					new Vector2f(0.7F, 15.2F)};
			lmg.setHitBox(new Floor(new Vector3f(lmg.position), 0, new Vector3f(-20, -0.28F, -20), p));
			Wall[] walls = {new Wall(new Vector3f(0.7F, 0, 15.2F), new Vector3f(-9.6F, 0, 9.8F), new Vector3f(0.5F, 2, 0))};
			lmg.setWalls(walls);
		}
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
			entity.setHitBox(new Wall(new Vector3f(95, 29, 105), new Vector3f(105, 29, 115), new Vector3f(0.5F, 5, 0)));
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
