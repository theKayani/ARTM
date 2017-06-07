package com.hk.artm.compat.jei;

import com.hk.artm.util.recipes.BasicRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BasicWrapper extends BlankRecipeWrapper
{
	public final BasicRecipe recipe;

	public BasicWrapper(BasicRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputLists(ItemStack.class, recipe.getAllValidInputs());
		ingredients.setOutputs(ItemStack.class, recipe.getAllOutputs());
	}
}
