package com.hk.artm.util.recipes;

import com.google.common.collect.ImmutableList;
import com.hk.artm.ARTM;
import com.hk.artm.util.ARTMUtil;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConstructingTableRecipes
{
	public static final ConstructingTableRecipes INSTANCE = new ConstructingTableRecipes();
	private final List<TableRecipe> recipes;
	private final InventoryCrafting inv = new InventoryCrafting(new Container()
	{
		@Override
		public boolean canInteractWith(EntityPlayer playerIn)
		{
			return true;
		}
	}, 3, 3);

	private ConstructingTableRecipes()
	{
		ARTM.log.info("Registering Constructing Table Recipes...");

		List<TableRecipe> recs = new ArrayList<>();
		List<IRecipe> lst = new ArrayList<>(CraftingManager.getInstance().getRecipeList());
		for (int i = 0; i < lst.size(); i++)
		{
			recs.add(new TableRecipe(lst.get(i), true));
		}
		int vanillaRecs = lst.size();
		lst.clear();
		initializeRecipes(lst);
		for (int i = 0; i < lst.size(); i++)
		{
			recs.add(new TableRecipe(lst.get(i), false));
		}

		ARTM.log.info("Registered " + ARTM.thousandFormat.format(recs.size()) + " Constructing Table Recipes. " + ARTM.thousandFormat.format(vanillaRecs) + " vanilla recipes, " + ARTM.thousandFormat.format(recs.size() - vanillaRecs) + " mod recipes.");
		recipes = ImmutableList.copyOf(recs);
	}

	private void initializeRecipes(List<IRecipe> lst)
	{
		lst.add(new ShapelessOreRecipe(new ItemStack(Items.DIAMOND), new ItemStack(Items.COAL), new ItemStack(Blocks.DIAMOND_ORE)));
	}

	public TableRecipe getMatchingRecipe(World worldIn, ItemStack[] stacks)
	{
		inv.clear();
		for (int i = 0; i < 9; i++)
		{
			inv.setInventorySlotContents(i, stacks[i]);
		}

		for (TableRecipe recipe : recipes)
		{
			if (recipe.matches(inv, worldIn))
			{
				return recipe;
			}
		}

		return null;
	}

	public TableRecipe getMatchingResult(ItemStack result)
	{
		for (TableRecipe recipe : recipes)
		{
			if (ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), result))
			{
				return recipe;
			}
		}

		return null;
	}

	public static void init()
	{
	}

	public static class TableRecipe implements IRecipe
	{
		public final IRecipe recipe;
		public final boolean isVanilla;
		public final List<List<ItemStack>> input;

		public TableRecipe(IRecipe recipe, boolean isVanilla)
		{
			this.recipe = recipe;
			this.isVanilla = isVanilla;
			input = new ArrayList<>();
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
			else if (recipe instanceof ShapelessOreRecipe)
			{
				ShapelessOreRecipe slr = (ShapelessOreRecipe) recipe;
				for (Object obj : slr.getInput())
				{
					input.add(ARTMUtil.toStackList(obj));
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
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn)
		{
			return recipe.matches(inv, worldIn);
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv)
		{
			return recipe.getCraftingResult(inv);
		}

		@Override
		public int getRecipeSize()
		{
			return recipe.getRecipeSize();
		}

		@Override
		public ItemStack getRecipeOutput()
		{
			return recipe.getRecipeOutput();
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
		{
			return recipe.getRemainingItems(inv);
		}
	}
}
