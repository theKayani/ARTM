package com.hk.artm.gui;

import com.hk.artm.ARTM;
import com.hk.artm.block.tile.InventoryConstructing;
import com.hk.artm.container.ContainerConstructing;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiConstructing extends GuiContainer
{
	private final InventoryPlayer playerInv;
	private final InventoryConstructing tile;

	public GuiConstructing(InventoryPlayer inventory, BlockPos pos)
	{
		super(new ContainerConstructing(inventory, pos));
		this.playerInv = inventory;
		this.tile = ((ContainerConstructing) inventorySlots).tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String s = this.tile.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 8, 6, 0x404040);
		this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TABLE_GUI);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	public static final ResourceLocation TABLE_GUI = new ResourceLocation(ARTM.MODID + ":textures/gui/constructing_table.png");
}
