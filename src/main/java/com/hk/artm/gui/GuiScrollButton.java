package com.hk.artm.gui;

import com.hk.artm.ARTM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiScrollButton extends GuiButton
{
	public final boolean up;

	public GuiScrollButton(int buttonId, int x, int y, boolean up)
	{
		super(buttonId, x, y, 9, 11, null);
		this.up = up;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (visible)
		{
			mc.getTextureManager().bindTexture(GuiIDs.BUTTON_LOC);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

			int textureX = 10;

			if (!enabled)
			{
				textureX = 0;
			}
			else if (hovered)
			{
				textureX = 20;
			}

			drawTexturedModalRect(xPosition, yPosition, textureX, up ? 0 : 12, 9, 11);
		}
	}
}
