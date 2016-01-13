package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import objLoader.ModelData;
import objLoader.OBJLoader;
import static org.lwjgl.input.Keyboard.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import entity.*;
import gui.menu.MenuInventory;
import raycasting.AABB;
import raycasting.Raycaster;
import renderer.*;
import renderer.models.TexturedModel;
import renderer.textures.*;
import terrain.Terrain;

public class MainGameLoop
{
	private static Input input;
	private static Raycaster ray;
	private static List<Entity> entities;
	private static Player player;
	private static Light light;
	private static Camera c;
	private static Terrain t;
	private static MasterRenderer renderer;

	public static void doGame()
	{
		Loader loader = MainManagerClass.loader;
		Person.init();
		Particle.init();
		ModelData kloData = OBJLoader.loadOBJModel("klo");
		TexturedModel texturedModel = new TexturedModel(loader.loadToVAO(kloData.getVertices(), kloData.getTextureCoords(), kloData.getNormals(), kloData.getIndices()), new ModelTexture(loader.loadTexture("texture/test")));
		ModelTexture texture = texturedModel.getTexture();
		ModelData grassData = OBJLoader.loadOBJModel("grass");
		TexturedModel grass = new TexturedModel(loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()), new ModelTexture(loader.loadTexture("texture/grass")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLightning(true);
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		entities = new ArrayList<Entity>();
		player = new Player("texture/player", new Vector3f(100, 0, 100), 0, 0, 0, 0.1F, entities, 20);
		light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		c = new Camera(player);
		t = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg")), "height_map_lmg");
		ModelData lmgData = OBJLoader.loadOBJModel("lmg");
		new Entity(new TexturedModel(loader.loadToVAO(lmgData.getVertices(), lmgData.getTextureCoords(), lmgData.getNormals(), lmgData.getIndices()), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(172, 33, 131), 0, 180, 0, 5, entities);
		new Teacher("texture/person/hans", new Vector3f(105, 0, 105), 0, 0, 0, 0.1F, "teacher.hans", entities, 30);
		ModelData treeData = OBJLoader.loadOBJModel("tree");
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("texture/tree"));
		TexturedModel tree = new TexturedModel(loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices()), treeTexture);
		{
			final int offsetX = 100;
			final int offsetZ = 200;
			for(int i = 0; i < 10; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					int x = i * 10 + offsetX;
					int z = j * 10 + offsetZ;
					Entity entity = new Entity(tree, new Vector3f(x, t.getHeight(x, z), z), 0, 0, 0, 0.5F, entities);
					entity.setHitBox(new AABB(new Vector3f(entity.position), new Vector3f(0.8F, 10, 0.8F), new Vector3f(-0.4F, 0, -0.4F)));
				}
			}
		}
		ModelData bushData = OBJLoader.loadOBJModel("bush");
		TexturedModel bush = new TexturedModel(loader.loadToVAO(bushData.getVertices(), bushData.getTextureCoords(), bushData.getNormals(), bushData.getIndices()), treeTexture);
		{
			Entity entity = new Entity(bush, new Vector3f(100, t.getHeight(100, 110), 110), 0, 0, 0, 0.2F, entities);
			entity.setHitBox(new AABB(new Vector3f(entity.position), new Vector3f(0.5F, 5, 0.5F), new Vector3f(-0.5F, 0, -0.5F)));
		}
		generateBushRow(63, 172, 132, 172, entities, 1, bush, t);
		generateBushRow(35, 77, 90, 83, entities, 1, bush, t);
		ModelData houseData = OBJLoader.loadOBJModel("house");
		TexturedModel house = new TexturedModel(loader.loadToVAO(houseData.getVertices(), houseData.getTextureCoords(), houseData.getNormals(), houseData.getIndices()), new ModelTexture(loader.loadTexture("texture/test")));
		new Entity(house, new Vector3f(122, t.getHeight(122, 45), 45), 0, 120, 0, 6, entities);
		ray = new Raycaster(player);
		ray.setList(entities);
		
		input = new Input(Display.getHeight());
		
		renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			render();
			DisplayManager.updateDisplay();
			if(isKeyDown(KEY_ESCAPE)) break;
			if(isKeyDown(KEY_F1))
			{
				MenuInventory inventory = new MenuInventory();
				inventory.doMenu(player.getInventory());
			}
			if(isKeyDown(KEY_F5)) t = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg")), "height_map_lmg");
		}
		renderer.cleanUp();
		loader.cleanUp();
	}

	public static void render()
	{
		input.poll(Display.getWidth(), Display.getHeight());
		player.update(t);
		c.update();
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(t);
			if(!e.invisible) renderer.processEntities(e);
		}
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		renderer.processTerrain(t);
		renderer.render(light, c);
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

	private static TerrainTexturePack loadTerrainTexturePack(Loader loader)
	{
		TerrainTexture back = new TerrainTexture(loader.loadTexture("texture/terrain_grass"));
		TerrainTexture r = new TerrainTexture(loader.loadTexture("texture/terrain_dirt"));
		TerrainTexture g = new TerrainTexture(loader.loadTexture("texture/terrain_sand"));
		TerrainTexture b = new TerrainTexture(loader.loadTexture("texture/terrain_path"));
		return new TerrainTexturePack(back, r, g, b);
	}
}
