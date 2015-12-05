package main;

import java.util.ArrayList;
import java.util.List;
import objLoader.ModelData;
import objLoader.OBJLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import entity.*;
import raycasting.Raycaster;
import renderer.*;
import renderer.models.TexturedModel;
import renderer.textures.*;
import terrain.Terrain;

public class MainGameLoop
{
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
		List<Entity> entities = new ArrayList<Entity>();
		Player player = new Player("texture/test", new Vector3f(100, 0, 100), 0, 0, 0, 0.1F, entities);
		Light light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		Camera c = new Camera(player);
		Terrain t = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg")), "height_map_lmg");
		ModelData lmgData = OBJLoader.loadOBJModel("lmg");
		new Entity(new TexturedModel(loader.loadToVAO(lmgData.getVertices(), lmgData.getTextureCoords(), lmgData.getNormals(), lmgData.getIndices()), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(172, 33, 131), 0, 180, 0, 5, entities);
		new Teacher("texture/person/hans", new Vector3f(105, 0, 105), 0, 0, 0, 0.1F, "teacher.hans", entities);
		Raycaster ray = new Raycaster(player);
		ray.setList(entities);
		
		Input input = new Input(Display.getHeight());
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
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
			DisplayManager.updateDisplay();
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) break;
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
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
