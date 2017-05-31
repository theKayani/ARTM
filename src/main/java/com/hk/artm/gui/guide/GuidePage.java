package com.hk.artm.gui.guide;

public abstract class GuidePage
{
	public final GuiARTMGuide parent;
	public static final int width = 200, height = 160;
	private int pageNumber;

	public GuidePage(GuiARTMGuide screen)
	{
		parent = screen;
	}

	public void initPage()
	{
	}

	public void openedPage()
	{
	}

	public abstract void drawPage(int mouseX, int mouseY);

	public void closedPage()
	{
	}

	public GuidePage setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
		return this;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void mousePressed(int mouseX, int mouseY, int button)
	{
	}

	public void mouseReleased(int mouseX, int mouseY, int button)
	{
	}

	public void keyTyped(char keyChar, int keyCode)
	{
	}
}
