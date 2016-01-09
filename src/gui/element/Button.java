package gui.element;

import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.handler.IClickHandler;
import gui.menu.Menu;

public class Button extends GuiElement implements IClickable
{
	private static int textureButton;
	private static int textureButtonClick;
	private static int textureButtonHover;
	
	public GUIText text = null;
	public Icon icon;
	
	private IClickHandler handler;
	
	public Button(Vector2f position, Vector2f size, Menu parent)
	{
		super(textureButton, position, size, parent);
	}
	
	public Button setText(String t, FontType f, float s, float r, float g, float b)
	{
		TextMaster.removeText(text);
		text = new GUIText(t, s, f, new Vector2f(position.x + 20, position.y + (size.y / 2) + 10), (size.x - 40) * 1 / Display.getWidth(), false);
		text.setColour(r, g, b);
		return this;
	}
	
	public Button setText(String t, FontType f, float s)
	{
		setText(t, f, s, 0, 0, 0);
		return this;
	}
	
	public Button setIcon(int texture, List<GuiElement> list)
	{
		icon = new Icon(texture, new Vector2f(position.x + size.x - 32, position.y), parent);
		list.add(icon);
		return this;
	}
	
	public Button setClickHandler(IClickHandler h)
	{
		handler = h;
		return this;
	}

	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		texture = textureButtonClick;
	}

	@Override public void rightClick(int mouseX, int mouseY){}

	@Override
	public void leftReleased(int mouseX, int mouseY)
	{
		texture = textureButtonHover;
		if (handler != null) handler.click(parent);
	}

	@Override public void rightReleased(int mouseX, int mouseY){}
	
	@Override
	public boolean isOver(int x, int y)
	{
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}
	
	public static void loadAllTextures(Loader loader)
	{
		textureButton = loader.loadTexture("/texture/gui/button");
		textureButtonClick = loader.loadTexture("/texture/gui/button_clicked");
		textureButtonHover = loader.loadTexture("/texture/gui/button_hover");
	}

	@Override 
	public void entered(int mouseX, int mouseY) 
	{
		texture = textureButtonHover;
	}

	@Override
	public void left(int mouseX, int mouseY)
	{
		texture = textureButton;
	}
}
