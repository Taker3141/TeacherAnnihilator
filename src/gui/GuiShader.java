package gui;

import org.lwjgl.util.vector.Matrix4f;
import renderer.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram
{
	public static final String VERTEX_FILE = "src/gui/guiVertexShader.vert";
	public static final String FRAGMENT_FILE = "src/gui/guiFragmentShader.frag";
	
	private int locationTransformationMatrix;
	private int locationLayer;
	private int locationOffset;
	private int locationHeight;
	
	public GuiShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix)
	{
		super.loadMatrix(locationTransformationMatrix, transformationMatrix);
	}
	
	public void loadLayer(int layer)
	{
		super.loadInt(locationLayer, layer);
	}
	
	public void loadOffset(float offset)
	{
		super.loadFloat(locationOffset, offset);
	}
	
	public void loadHeight(float height)
	{
		super.loadFloat(locationHeight, height);
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationTransformationMatrix = super.getUniformLoacation("transformationMatrix");
		locationLayer = super.getUniformLoacation("layer");
		locationOffset = super.getUniformLoacation("offset");
		locationHeight = super.getUniformLoacation("height");
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}	
}
