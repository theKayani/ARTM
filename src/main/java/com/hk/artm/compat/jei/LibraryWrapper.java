package com.hk.artm.compat.jei;

import com.hk.artm.item.ItemBlueprint;
import com.hk.artm.util.recipes.LibraryRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.*;

public class LibraryWrapper extends BlankRecipeWrapper
{
	public final LibraryRecipes.LibraryRecipe recipe;

	public LibraryWrapper(LibraryRecipes.LibraryRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		List<List<ItemStack>> lst = new ArrayList<>(recipe.stacks);
		lst.add(Collections.singletonList(new ItemStack(Items.PAPER)));
		ingredients.setInputLists(ItemStack.class, lst);
		ingredients.setOutput(ItemStack.class, ItemBlueprint.getStackFor(recipe, null));
	}
}
