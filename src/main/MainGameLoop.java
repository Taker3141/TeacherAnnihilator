package main;

import java.util.ArrayList;
import java.util.List;
import objLoader.ModelData;
import objLoader.OBJLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import entity.Teacher;
import renderer.DisplayManager;
import renderer.MasterRenderer;
import renderer.Loader;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;
import terrain.Terrain;

public class MainGameLoop
{
	public static void doGame()
	{
		Loader loader = new Loader();
		ModelData kloData = OBJLoader.loadOBJModel("klo");
		TexturedModel texturedModel = new TexturedModel(loader.loadToVAO(kloData.getVertices(), kloData.getTextureCoords(), kloData.getNormals(), kloData.getIndices()), new ModelTexture(loader.loadTexture("texture/test")));
		ModelTexture texture = texturedModel.getTexture();
		ModelData grassData = OBJLoader.loadOBJModel("grass");
		TexturedModel grass = new TexturedModel(loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()), new ModelTexture(loader.loadTexture("texture/grass")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLightning(true);
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		ModelData personData = OBJLoader.loadOBJModel("person");
		Player player = new Player(new TexturedModel(loader.loadToVAO(personData.getVertices(), personData.getTextureCoords(), personData.getNormals(), personData.getIndices()), new ModelTexture(loader.loadTexture("texture/test"))), new Vector3f(100, 0, 100), 0, 0, 0, 0.1F);
		List<Entity> entities = new ArrayList<Entity>();
		Light light = new Light(new Vector3f(0, 100, 0), new Vector3f(1, 1, 1));
		Camera c = new Camera(player);
		Terrain t = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg")), "height_map_lmg");
		ModelData lmgData = OBJLoader.loadOBJModel("lmg");
		Entity lmg = new Entity(new TexturedModel(loader.loadToVAO(lmgData.getVertices(), lmgData.getTextureCoords(), lmgData.getNormals(), lmgData.getIndices()), new ModelTexture(loader.loadTexture("texture/lmg_texture"))), new Vector3f(172, 33, 131), 0, 180, 0, 5);
		entities.add(lmg);
		Entity hans = new Teacher(new TexturedModel(loader.loadToVAO(personData.getVertices(), personData.getTextureCoords(), personData.getNormals(), personData.getIndices()), new ModelTexture(loader.loadTexture("texture/person/hans"))), new Vector3f(105, 0, 105), 0, 0, 0, 0.1F, "teacher.hans");
		entities.add(hans);
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			player.update(t);
			c.update();
			for(Entity e:entities)
			{
				e.update(t);
				renderer.processEntities(e);
			}
			renderer.processEntities(player);
			renderer.processTerrain(t);
			renderer.render(light, c);
			DisplayManager.updateDisplay();
			if(Keyboard.isKeyDown(Keyboard.KEY_F5)) 
			{
				t = new Terrain(0, 0, loader, loadTerrainTexturePack(loader), new TerrainTexture(loader.loadTexture("texture/blend_map_lmg")), "height_map_lmg");
			}
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
