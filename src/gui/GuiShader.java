package gui;

import org.lwjgl.util.vector.Matrix4f;
import renderer.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram
{
	public static final String VERTEX_FILE = "src/gui/guiVertexShader.txt";
	public static final String FRAGMENT_FILE = "src/gui/guiFragmentShader.txt";
	
	private int locationTransformationMatrix;
	private int locationLayer;
	
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

	@Override
	protected void getAllUniformLocations()
	{
		locationTransformationMatrix = super.getUniformLoacation("transformationMatrix");
		locationLayer = super.getUniformLoacation("layer");
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}	
}
