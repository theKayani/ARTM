package com.hk.artm.gui.guide;

import com.google.common.base.Preconditions;
import com.hk.artm.gui.GuiIDs;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class RecipePage extends GuidePage
{
	public final String extraInfo;
	public final List<List<ItemStack>> recipe1, recipe2;
	public final int w1, h1, w2, h2;
	public final int[] indx1, indx2;
	private long lastChange;

	public RecipePage(GuiARTMGuide screen, ItemStack stack1, ItemStack stack2, String extraInfo)
	{
		super(screen);
		this.extraInfo = extraInfo == null ? "" : extraInfo;
		IRecipe recipe1 = null;
		IRecipe recipe2 = null;
		for (IRecipe r : CraftingManager.getInstance().getRecipeList())
		{
			if (ItemStack.areItemStacksEqual(r.getRecipeOutput(), stack1))
			{
				recipe1 = r;
				if (stack2 == null)
				{
					break;
				}
			}
			if (stack2 != null)
			{
				if (ItemStack.areItemStacksEqual(r.getRecipeOutput(), stack2))
				{
					recipe2 = r;
				}

				if (recipe1 != null && recipe2 != null)
				{
					break;
				}
			}
		}
		Preconditions.checkNotNull(recipe1, "Didn't find recipe1 for " + stack1);
		this.recipe1 = ARTMUtil.getClientRecipe(recipe1);
		this.recipe2 = recipe2 == null ? null : ARTMUtil.getClientRecipe(recipe2);
		Dimension d1 = ARTMUtil.getRecipeSize(recipe1);
		Dimension d2 = recipe2 == null ? new Dimension() : ARTMUtil.getRecipeSize(recipe2);
		w1 = d1.width;
		h1 = d1.height;
		w2 = d2.width;
		h2 = d2.height;
		indx1 = new int[this.recipe1.size()];
		indx2 = recipe2 == null ? null : new int[this.recipe2.size()];
		Preconditions.checkArgument(!this.recipe1.isEmpty(), "Invalid recipe1 found for " + stack1 + ". Recipe found: " + recipe1);
		lastChange = Minecraft.getSystemTime();
	}

	@Override
	public void drawPage(int mouseX, int mouseY)
	{
		parent.mc.getTextureManager().bindTexture(GuiIDs.BUTTON_LOC);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		float scl = 1F + (1F / 3F);
		float sSize = 18 * scl;
		int bx = width - 57 - (int) (54 * ((scl - 1) / 1F));
		int by1 = 3, by2 = 56;
		GL11.glPushMatrix();
		GL11.glTranslatef(bx, by1, 0);
		GL11.glScalef(scl, scl, 1);
		parent.drawTexturedModalRect(0, 0, 0, 23, w1 * 18 - 2, h1 * 18 - 2);
		parent.drawTexturedModalRect(0, by2, 0, 23, w2 * 18 - 2, h2 * 18 - 2);
		parent.getItemRender().zLevel = 100F;
		boolean unicode = parent.getFROBJ().getUnicodeFlag();
		parent.getFROBJ().setUnicodeFlag(false);
		for (int i = 0; i < recipe1.size(); i++)
		{
			List<ItemStack> stacks = recipe1.get(i);
			ItemStack stack = stacks.get(indx1[i]);
			int x = i % w1;
			int y = i / w1;
			RenderHelper.disableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			parent.getItemRender().renderItemAndEffectIntoGUI(stack, x * 18, y * 18);
			parent.getItemRender().renderItemOverlayIntoGUI(parent.getFROBJ(), stack, x * 18, y * 18, null);
			RenderHelper.enableStandardItemLighting();
		}

		if (recipe2 != null)
		{
			for (int i = 0; i < recipe2.size(); i++)
			{
				List<ItemStack> stacks = recipe2.get(i);
				ItemStack stack = stacks.get(indx2[i]);
				int x = i % w2;
				int y = i / w2;
				RenderHelper.disableStandardItemLighting();
				RenderHelper.enableGUIStandardItemLighting();
				parent.getItemRender().renderItemAndEffectIntoGUI(stack, x * 18, y * 18 + by2);
				parent.getItemRender().renderItemOverlayIntoGUI(parent.getFROBJ(), stack, x * 18, y * 18 + by2, null);
				RenderHelper.enableStandardItemLighting();
			}
		}
		parent.getItemRender().zLevel = 0F;
		GL11.glPopMatrix();
		by2 = (int) (by2 * scl) + by1;
		for (int i = 0; i < recipe1.size(); i++)
		{
			List<ItemStack> stacks = recipe1.get(i);
			ItemStack stack = stacks.get(indx1[i]);
			if (stack.isEmpty()) continue;
			int x = i % w1;
			int y = i / w1;

			if (mouseX >= bx + x * sSize && mouseX < bx + x * sSize + sSize && mouseY >= by1 + y * sSize && mouseY < by1 + y * sSize + sSize)
			{
				parent.renderToolTip(stack, mouseX, mouseY);
			}
		}
		if (recipe2 != null)
		{
			for (int i = 0; i < recipe2.size(); i++)
			{
				List<ItemStack> stacks = recipe2.get(i);
				ItemStack stack = stacks.get(indx2[i]);
				if (stack.isEmpty()) continue;
				int x = i % w2;
				int y = i / w2;
				if (stack.isEmpty()) continue;

				if (mouseX >= bx + x * sSize && mouseX < bx + x * sSize + sSize && mouseY >= by2 + y * sSize && mouseY < by2 + y * sSize + sSize)
				{
					parent.renderToolTip(stack, mouseX, mouseY);
				}
			}
		}
		parent.getFROBJ().setUnicodeFlag(unicode);

		if (!extraInfo.isEmpty())
		{
			parent.getFROBJ().drawSplitString(extraInfo, 3, 3, bx - 3, 0xFF000000);
		}

		long time = Minecraft.getSystemTime();
		if (time - lastChange > 1000)
		{
			for (int i = 0; i < recipe1.size(); i++)
			{
				indx1[i] = (indx1[i] + 1) % recipe1.get(i).size();
			}
			if (recipe2 != null)
			{
				for (int i = 0; i < recipe2.size(); i++)
				{
					indx2[i] = (indx2[i] + 1) % recipe2.get(i).size();
				}
			}
			lastChange = time;
		}
	}
}
