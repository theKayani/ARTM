package com.hk.artm.compat.jei;

import com.hk.artm.Init;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin
{
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
	{
		subtypeRegistry.useNbtForSubtypes(Init.ITEM_BLUEPRINT);
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{
	}

	@Override
	public void register(IModRegistry registry)
	{
		LibraryCategory.register(registry);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Init.BLOCK_CONSTRUCTING_TABLE), VanillaRecipeCategoryUid.CRAFTING);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
	}
}
