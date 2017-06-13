package com.hk.artm.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class ARTMUtil
{
	@SideOnly(Side.CLIENT)
	public static List<ItemStack> expandWildcard(ItemStack stack)
	{
		NonNullList<ItemStack> lst = NonNullList.create();
		if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
		{
			stack.getItem().getSubItems(stack.getItem(), null, lst);
		}
		else
		{
			lst.add(stack);
		}
		return lst;
	}

	@SideOnly(Side.CLIENT)
	public static List<List<ItemStack>> getClientRecipe(IRecipe recipe)
	{
		List<List<ItemStack>> lists = getRecipe(recipe);
		for (int i = 0; i < lists.size(); i++)
		{
			List<ItemStack> stacks = new ArrayList<>(lists.get(i));
			for (int j = 0; j < stacks.size(); j++)
			{
				ItemStack stack = stacks.get(j);
				if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					List<ItemStack> expanded = expandWildcard(stack);
					stacks.remove(j);
					stacks.addAll(expanded);
					j = j - 1 + expanded.size();
				}
			}
			lists.set(i, stacks);
		}
		return lists;
	}

	public static List<List<ItemStack>> getRecipe(IRecipe recipe)
	{
		List<List<ItemStack>> input = new ArrayList<>();
		if (recipe instanceof ShapedRecipes)
		{
			ShapedRecipes sr = (ShapedRecipes) recipe;
			for (ItemStack stack : sr.recipeItems)
			{
				input.add(Collections.singletonList(stack));
			}
		}
		else if (recipe instanceof ShapelessRecipes)
		{
			ShapelessRecipes slr = (ShapelessRecipes) recipe;
			for (ItemStack stack : slr.recipeItems)
			{
				input.add(Collections.singletonList(stack));
			}
		}
		else if (recipe instanceof ShapedOreRecipe)
		{
			ShapedOreRecipe slr = (ShapedOreRecipe) recipe;
			for (Object obj : slr.getInput())
			{
				input.add(ARTMUtil.toStackList(obj));
			}
		}
		else if (recipe instanceof ShapelessOreRecipe)
		{
			ShapelessOreRecipe slr = (ShapelessOreRecipe) recipe;
			for (Object obj : slr.getInput())
			{
				input.add(ARTMUtil.toStackList(obj));
			}
		}
		return input;
	}

	public static Dimension getRecipeSize(IRecipe recipe)
	{
		int w = -1;
		int h = -1;
		if (recipe instanceof ShapedRecipes)
		{
			ShapedRecipes sr = (ShapedRecipes) recipe;
			w = sr.recipeWidth;
			h = sr.recipeHeight;
		}
		else if (recipe instanceof ShapelessRecipes)
		{
			ShapelessRecipes slr = (ShapelessRecipes) recipe;
			int i = slr.getRecipeSize();
			double sq = Math.sqrt(i);
			w = Math.floor(sq) == sq ? (int) sq : Math.min(i, 3);
			h = Math.floor(sq) == sq ? (int) sq : (i - 1) / 3 + 1;
		}
		else if (recipe instanceof ShapedOreRecipe)
		{
			ShapedOreRecipe slr = (ShapedOreRecipe) recipe;
			w = slr.getWidth();
			h = slr.getHeight();
		}
		else if (recipe instanceof ShapelessOreRecipe)
		{
			ShapelessOreRecipe slr = (ShapelessOreRecipe) recipe;
			int i = slr.getRecipeSize();
			double sq = Math.sqrt(i);
			w = Math.floor(sq) == sq ? (int) sq : Math.min(i, 3);
			h = Math.floor(sq) == sq ? (int) sq : (i - 1) / 3 + 1;
		}
		return new Dimension(w, h);
	}

	public static List<ItemStack> setAmoutFor(List<ItemStack> lst, int amt)
	{
		for (ItemStack stack : lst)
		{
			if (!stack.isEmpty())
			{
				stack.setCount(amt);
			}
		}
		return lst;
	}

	public static List<ItemStack> toStackList(Object... objs)
	{
		List<ItemStack> lst = new ArrayList<>();
		for (Object obj : objs)
		{
			if (obj == null)
			{
				continue;
			}
			else if (obj instanceof ItemStack)
			{
				lst.add((ItemStack) obj);
			}
			else if (obj instanceof Item)
			{
				lst.add(new ItemStack((Item) obj));
			}
			else if (obj instanceof Block)
			{
				lst.add(new ItemStack((Block) obj));
			}
			else if (obj instanceof String)
			{
				lst.addAll(OreDictionary.getOres((String) obj));
			}
			else if (obj instanceof ItemStack[])
			{
				Collections.addAll(lst, (ItemStack[]) obj);
			}
			else if (obj.getClass().isArray())
			{
				for (int i = 0; i < Array.getLength(obj); i++)
				{
					lst.addAll(toStackList(Array.get(obj, i)));
				}
			}
			else if (obj instanceof Collection<?>)
			{
				lst.addAll(toStackList(((Collection<?>) obj).toArray()));
			}
		}
		return lst;
	}

	public static void sendMessage(EntityPlayer player, String message)
	{
		if (isServer())
		{
			player.sendMessage(new TextComponentTranslation(message));
		}
	}

	public static boolean isClient()
	{
		return getSide().isClient();
	}

	public static boolean isServer()
	{
		return getSide().isServer();
	}

	public static Side getSide()
	{
		return FMLCommonHandler.instance().getEffectiveSide();
	}
}
