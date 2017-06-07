package com.hk.artm.compat.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RecipeHandler<T extends IRecipeWrapper> implements IRecipeHandler<T>
{
	public final String uid;
	public final Class<T> recipeClass;

	public RecipeHandler(String uid, Class<T> recipeClass)
	{
		this.uid = uid;
		this.recipeClass = recipeClass;
	}

	@Override
	public Class<T> getRecipeClass()
	{
		return recipeClass;
	}

	@Override
	public String getRecipeCategoryUid(T recipe)
	{
		return uid;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(T recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(T recipe)
	{
		return true;
	}
}
