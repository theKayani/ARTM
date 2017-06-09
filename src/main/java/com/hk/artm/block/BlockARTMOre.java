package com.hk.artm.block;

import net.minecraft.block.material.Material;

public class BlockARTMOre extends BlockARTM
{
	public BlockARTMOre(String name, int harvestLevel, float hardness)
	{
		super(name, Material.ROCK, hardness);
		setHarvestLevel("pickaxe", harvestLevel);
	}
}
