package com.hk.artm.gui.guide;

import net.minecraft.util.text.TextFormatting;

public class TitlePage extends GuidePage
{
	public TitlePage(GuiARTMGuide screen)
	{
		super(screen);
	}

	@Override
	public void drawPage(int mouseX, int mouseY)
	{
		parent.getFROBJ().drawString(fmt(TextFormatting.BOLD, "ARTM") + " " + fmt(TextFormatting.ITALIC, "(Another Redundant Technology Mod)"), 5, 5, 0xFF000000);
		parent.getFROBJ().drawSplitString("    This mod introduces new and improved utilities into the world of Minecraft. This mod allows the player, you, to utilize a myriad of technologies never before seen in Minecraft! Before we get into that, you should know how to use this book!", 5, 15, width - 3, 0xFF000000);
		parent.getFROBJ().drawSplitString(fmt(TextFormatting.BOLD, "< and > arrow keys") + " for previous and next page, respectfully", 5, 65, width - 3, 0xFFAA0000);
		parent.getFROBJ().drawSplitString("You can also use the " + fmt(TextFormatting.BOLD, "number keys") + " to quickly navigate to a page. Just type in the page number to navigate to and it'll automatically take you there!", 5, 85, width - 3, 0xFFAA00AA);
		parent.getFROBJ().drawSplitString(fmt(TextFormatting.BOLD, "BACKSPACE") + " will take you to the previous page you visited.", 5, 115, width - 3, 0xFF0000AA);
		parent.drawCenteredString(parent.getFROBJ(), fmt(TextFormatting.BOLD, "Welcome to the world of Technology!"), width / 2, 145, 0xFF00AA00);
	}
}
