package com.hk.artm.item;

import com.hk.artm.ARTM;
import com.hk.artm.Init;
import com.hk.artm.util.recipes.LibraryRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.xml.soap.Text;
import java.util.List;

public class ItemBlueprint extends ItemARTM
{
	public ItemBlueprint()
	{
		super("blueprint");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			String itemLearnt = tag.getString("ItemLearnt");
			tooltip.add(TextFormatting.DARK_BLUE + "Blueprint for: " + TextFormatting.RESET + itemLearnt);
			if (tag.hasKey("LearntBy", Constants.NBT.TAG_STRING))
			{
				String learntBy = tag.getString("LearntBy");
				tooltip.add(TextFormatting.DARK_GRAY + "Learnt by '" + TextFormatting.RESET + TextFormatting.GOLD + learntBy + TextFormatting.RESET + TextFormatting.DARK_GRAY + "'" + TextFormatting.RESET);
			}
		}
	}

	public static ItemStack getStackFor(LibraryRecipes.LibraryRecipe recipe, EntityPlayer learntBy)
	{
		ItemStack stack = new ItemStack(Init.ITEM_BLUEPRINT);

		NBTTagCompound tag = stack.getTagCompound() == null ? new NBTTagCompound() : stack.getTagCompound();

		tag.setString("ItemLearnt", recipe.itemLearnt.getItemStackDisplayName(stack));
		tag.setInteger("ItemLearntID", Item.getIdFromItem(recipe.itemLearnt));
		if (learntBy != null)
		{
			tag.setString("LearntBy", learntBy.getDisplayNameString());
		}

		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
//		super.getSubItems(itemIn, tab, subItems);
		List<LibraryRecipes.LibraryRecipe> recipes = LibraryRecipes.INSTANCE.getRecipes();
		subItems.add(getStackFor(recipes.get(ARTM.rand.nextInt(recipes.size())), Minecraft.getMinecraft().player));
	}
}
