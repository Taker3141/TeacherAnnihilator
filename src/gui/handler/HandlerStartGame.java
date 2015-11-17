package gui.handler;

import gui.menu.Menu;

public class HandlerStartGame implements ClickHandler
{
	@Override
	public void click(Menu parent)
	{
		parent.requestGameStart();
	}
}
