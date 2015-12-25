package renderer.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;
import entity.Camera;
import entity.Light;

public class TerrainShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/renderer/shaders/terrainVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/renderer/shaders/terrainFragmentShader.frag";
	private int locationMatrix;
	private int locationProjection;
	private int locationView;
	private int locationLightPosition;
	private int locationLightColor;
	private int locationShineDamper;
	private int locationReflectivity;
	private int locationBackground;
	private int locationR;
	private int locationG;
	private int locationB;
	private int locationMap;
	private int locationSkyColor;
	
	public TerrainShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoord");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationMatrix = super.getUniformLoacation("transformationMatrix");
		locationProjection = super.getUniformLoacation("projectionMatrix");
		locationView = super.getUniformLoacation("viewMatrix");
		locationLightPosition = super.getUniformLoacation("lightPosition");
		locationLightColor = super.getUniformLoacation("lightColor");
		locationShineDamper = super.getUniformLoacation("shineDamper");
		locationReflectivity = super.getUniformLoacation("reflectivity");
		locationBackground = super.getUniformLoacation("backgroundTexture");
		locationR = super.getUniformLoacation("rTexture");
		locationG = super.getUniformLoacation("gTexture");
		locationB = super.getUniformLoacation("bTexture");
		locationMap = super.getUniformLoacation("blendMap");
		locationSkyColor = super.getUniformLoacation("skyColor");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationProjection, matrix);
	}
	
	public void loadViewMatrix(Camera c)
	{
		Matrix4f matrix = Maths.createViewMatrix(c);
		super.loadMatrix(locationView, matrix);
	}
	
	public void loadLight(Light l)
	{
		super.loadVector(locationLightPosition, l.getPosition());
		super.loadVector(locationLightColor, l.getColor());
	}
	
	public void loadShineVariables(float damper, float reflectvity)
	{
		super.loadFloat(locationShineDamper, damper);
		super.loadFloat(locationReflectivity, reflectvity);
	}
	
	public void connectTextureUnits()
	{
		super.loadInt(locationBackground, 0);
		super.loadInt(locationR, 1);
		super.loadInt(locationG, 2);
		super.loadInt(locationB, 3);
		super.loadInt(locationMap, 4);
	}
	
	public void loadSkyColor(float r, float b, float g)
	{
		super.loadVector(locationSkyColor, new Vector3f(r, g, b));
	}
}
