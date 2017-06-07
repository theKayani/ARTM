package com.hk.artm.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;

@JEIPlugin
public class JEICompat implements IModPlugin
{
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
	{
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

	}

	@Override
	public void register(IModRegistry registry)
	{
		LibraryCategory.register(registry);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{

	}
}
