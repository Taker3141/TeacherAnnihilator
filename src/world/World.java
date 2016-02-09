package world;

import static org.lwjgl.input.Keyboard.KEY_ESCAPE;
import static org.lwjgl.input.Keyboard.KEY_F1;
import static org.lwjgl.input.Keyboard.KEY_F5;
import static org.lwjgl.input.Keyboard.isKeyDown;
import java.util.ArrayList;
import java.util.List;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import raycasting.Raycaster;
import renderer.Loader;
import renderer.MasterRenderer;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;
import terrain.Terrain;
import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Particle;
import entity.Person;
import entity.Player;
import gui.menu.MenuInventory;

public class World
{
	private Raycaster ray;
	public List<Entity> entities;
	private Player player;
	private Light light;
	private Camera c;
	private Terrain[] t;
	private MasterRenderer renderer;
	private Loader loader = MainManagerClass.loader;
	private Input input;
	private boolean isInventoryOpen = false;
	
	public World()
	{
		Person.init();
		Particle.init();
		entities = new ArrayList<Entity>();
		player = new Player("texture/player", new Vector3f(100, 0, 100), 0, 0, 0, 0.1F, entities, 30);
		light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		c = new Camera(player);
		t = new Terrain[2];
		TerrainTexturePack pack = loadTerrainTexturePack(loader);
		t[0] = new Terrain(0, 0, loader, pack, new TerrainTexture(loader.loadTexture("texture/blend_map_lmg0")), "height_map_lmg0");
		t[1] = new Terrain(-1, 0, loader, pack, new TerrainTexture(loader.loadTexture("texture/blend_map_lmg1")), "height_map_lmg1");
		ray = new Raycaster(player);
		ray.setList(entities);
		input = new Input(Display.getHeight());
		renderer = new MasterRenderer();
	}
	
	public boolean tick()
	{
		input.poll(Display.getWidth(), Display.getHeight());
		player.update(terrain(player.position.x));
		c.update();
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(terrain(e.position.x));
			if(!e.invisible) renderer.processEntities(e);
		}
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		renderer.processTerrain(t[0]);
		renderer.processTerrain(t[1]);
		renderer.render(light, c);
		if(isKeyDown(KEY_ESCAPE)) return false;
		if(isKeyDown(KEY_F1) && !isInventoryOpen)
		{
			isInventoryOpen = true;
			MenuInventory inventory = new MenuInventory();
			inventory.doMenu(player);
			isInventoryOpen = false;
		}
		if(isKeyDown(KEY_F5)) t[0] = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg0")), "height_map_lmg0");
		return true;
	}
	
	public float height(float x, float z)
	{
		return terrain(x).getHeight(x, z);
	}
	
	public Terrain terrain(float positionX)
	{
		int x = (int)positionX;
		int terrainNumber = x > 0 ? 0 : 1;
		return t[terrainNumber];
	}
	
	public void cleanUp()
	{
		renderer.cleanUp();
		loader.cleanUp();
	}

	private TerrainTexturePack loadTerrainTexturePack(Loader loader)
	{
		TerrainTexture back = new TerrainTexture(loader.loadTexture("texture/terrain_grass"));
		TerrainTexture r = new TerrainTexture(loader.loadTexture("texture/terrain_dirt"));
		TerrainTexture g = new TerrainTexture(loader.loadTexture("texture/terrain_sand"));
		TerrainTexture b = new TerrainTexture(loader.loadTexture("texture/terrain_path"));
		return new TerrainTexturePack(back, r, g, b);
	}
	
	public void updateRaycaster()
	{
		ray.setList(entities);
	}
}
