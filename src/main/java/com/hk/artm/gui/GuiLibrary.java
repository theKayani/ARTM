package com.hk.artm.gui;

import com.hk.artm.ARTM;
import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.container.ContainerLibrary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLibrary extends GuiContainer
{
	private final InventoryPlayer playerInv;
	private final TileLibrary tile;

	public GuiLibrary(InventoryPlayer playerInv, TileLibrary tile)
	{
		super(new ContainerLibrary(playerInv, tile));
		this.playerInv = playerInv;
		this.tile = tile;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.tile.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 8, 6, 4210752);
		for (int i = 0; i < 5; i++)
		{
			this.fontRendererObj.drawString(String.valueOf(playerInv.player.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).randomNum), 50, 20 + (i * 10), 0x404040);
		}
		this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(LIBRARY_GUI);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	private static final ResourceLocation LIBRARY_GUI = new ResourceLocation(ARTM.MODID + ":textures/gui/library_gui.png");
}
