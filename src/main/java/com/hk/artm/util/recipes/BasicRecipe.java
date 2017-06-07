package com.hk.artm.util.recipes;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface BasicRecipe
{
	public List<List<ItemStack>> getAllValidInputs();

	public List<ItemStack> getAllOutputs();
}
