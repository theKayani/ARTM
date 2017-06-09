package com.hk.artm.gui;

import com.hk.artm.ARTM;
import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.container.ContainerLibrary;
import com.hk.artm.network.ARTMNetwork;
import com.hk.artm.network.PacketLibraryRecipePicked;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiLibrary extends GuiContainer
{
	private final InventoryPlayer playerInv;
	private final TileLibrary tile;
	private GuiScrollButton up, down;
	private int learntIndex = 0;
	private final List<Item> learntItems;

	public GuiLibrary(InventoryPlayer playerInv, TileLibrary tile)
	{
		super(new ContainerLibrary(playerInv, tile));
		this.playerInv = playerInv;
		this.tile = tile;
		learntItems = playerInv.player.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).learntItems;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		up.enabled = learntIndex > 0;
		down.enabled = learntIndex < learntItems.size() - 5;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addButton(up = new GuiScrollButton(0, guiLeft + 162, guiTop + 17, true));
		addButton(down = new GuiScrollButton(1, guiLeft + 162, guiTop + 62, false));
		up.enabled = false;
		down.enabled = 0 < learntItems.size() - 5;
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
			int j1 = 47;
			int k1 = 18 + (i * 11);
			String str = i < learntItems.size() ? learntItems.get(learntIndex + i).getItemStackDisplayName(new ItemStack(learntItems.get(learntIndex + i))) : "";
			this.fontRendererObj.drawString(str, j1 + 1, k1 + 1, 0x404040);

			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.colorMask(true, true, true, false);
			if (tile.selectedRecipe == learntIndex + i)
			{
				drawGradientRect(j1, k1, j1 + 112, k1 + 9, 0x80000000, 0x80000000);
			}
			else if (isMouseOver(i, mouseX, mouseY))
			{
				drawGradientRect(j1, k1, j1 + 112, k1 + 9, 0x80ffffff, 0x80ffffff);
			}
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
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

	private boolean isMouseOver(int slot, int mouseX, int mouseY)
	{
		return isPointInRegion(47, 18 + (slot * 11), 112, 9, mouseX, mouseY);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		if (state == 0)
		{
			int prev = tile.selectedRecipe;
			int next = prev;
			for (int i = 0; i < 5; i++)
			{
				if (isMouseOver(i, mouseX, mouseY))
				{
					int sel = i + learntIndex;
					if (sel == tile.selectedRecipe)
					{
						next = -1;
					}
					else
					{
						next = sel;
					}
					up.playPressSound(mc.getSoundHandler());
					break;
				}
			}
			if (prev != next && next < learntItems.size())
			{
				tile.selectedRecipe = next;
				tile.recheck = true;
				ARTMNetwork.INSTANCE.sendToServer(new PacketLibraryRecipePicked(tile.getPos(), tile.selectedRecipe));
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if (button.id == 0)
		{
			if (learntIndex > 0)
			{
				learntIndex--;
			}
		}
		else if (button.id == 1)
		{
			if (learntIndex < learntItems.size() - 5)
			{
				learntIndex++;
			}
		}
		up.enabled = learntIndex > 0;
		down.enabled = learntIndex < learntItems.size() - 5;
	}

	public static final ResourceLocation LIBRARY_GUI = new ResourceLocation(ARTM.MODID + ":textures/gui/library_gui.png");
}
