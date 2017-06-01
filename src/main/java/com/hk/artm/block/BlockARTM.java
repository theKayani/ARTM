package com.hk.artm.block;

import com.hk.artm.Init;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockARTM extends Block
{
	public BlockARTM(String name, Material materialIn, float hardness)
	{
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(Init.artmTab);
		setHardness(hardness);
	}
}
