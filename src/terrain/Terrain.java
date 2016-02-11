package terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.Loader;
import renderer.models.SimpleModel;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;
import toolbox.Maths;

public class Terrain
{
	public static final float SIZE = 250;
	private static final float MAX_HEIGHT = 20;
	private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	private float x;
	private float z;
	private SimpleModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private float[][] heights;
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack pack, TerrainTexture map, String heightMap)
	{
		texturePack = pack;
		blendMap = map;
		x = gridX * SIZE;
		z = gridZ * SIZE;
		model = generateTerrain(loader, heightMap);
	}
	
	private SimpleModel generateTerrain(Loader loader, String heightMap)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File("res/texture/" + heightMap + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		heights = new float[image.getHeight()][image.getWidth()];
		for(int i = 0; i < image.getHeight(); i++)
		{
			for(int j = 0; j < image.getWidth(); j++)
			{
				heights[i][j] = getHeight(i, j, image);
			}
		}
		
		int vertexCount = image.getHeight();
		
		int count = vertexCount * vertexCount;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < vertexCount; i++)
		{
			for (int j = 0; j < vertexCount; j++)
			{
				vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = heights[j][i];
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, heights);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < vertexCount - 1; gz++)
		{
			for (int gx = 0; gx < vertexCount - 1; gx++)
			{
				int topLeft = (gz * vertexCount) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * vertexCount) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	public float getHeight(float x, float z)
	{
		float terrainX = x - this.x;
		float terrainZ = z - this.z;
		float gridSquareSize = SIZE / (float)(heights.length - 1);
		int gridX = (int)Math.floor(terrainX / gridSquareSize);
		int gridZ = (int)Math.floor(terrainZ / gridSquareSize);
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) return 0;
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord))
		{
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		else
		{
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}
	
	private float getHeight(int x, int y, BufferedImage image)
	{
		if(x < 0 || y < 0 || x > image.getHeight() - 1 || y > image.getWidth() - 1) return 0;
		float height = image.getRGB(x, y);
		height /= MAX_PIXEL_COLOR / 2;
		height *= MAX_HEIGHT;
		height += MAX_HEIGHT * 2;
		return height;
	}
	
	private Vector3f calculateNormal(int x, int y, float[][] heights)
	{
		float heightL = heights[x == 0 ? 0 : x - 1][y];
		float heightR = heights[x == heights.length - 1 ? heights.length - 1 : x + 1][y];
		float heightD = heights[x][y == 0 ? 0 : y - 1];
		float heightU = heights[x][y == heights[x].length - 1 ? heights[x].length - 1 : y + 1];
		Vector3f normal = new Vector3f(heightL - heightR, 2, heightD - heightU);
		normal.normalise();
		return normal;
	}

	public float getX()
	{
		return x;
	}

	public float getZ()
	{
		return z;
	}

	public SimpleModel getModel()
	{
		return model;
	}

	public TerrainTexturePack getTexturePack()
	{
		return texturePack;
	}
	
	public TerrainTexture getBlendMap()
	{
		return blendMap;
	}
}
