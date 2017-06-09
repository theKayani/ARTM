package com.hk.artm.block;

import com.hk.artm.ARTM;
import com.hk.artm.Init;
import com.hk.artm.gui.GuiIDs;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConstructing extends BlockARTM
{
	public BlockConstructing()
	{
		super("constructing_table", Material.IRON, 3.0F);
		setHarvestLevel("pickaxe", 1);
//		setCreativeTab(null);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (ARTMUtil.isServer())
		{
			if (playerIn.isSneaking())
			{
				InventoryHelper.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(Init.ITEM_CONSTRUCTING_TEMPLATE));
				worldIn.setBlockState(pos, Blocks.CRAFTING_TABLE.getDefaultState());
			}
			else
			{
				playerIn.openGui(ARTM.instance, GuiIDs.GUI_CONSTRUCTING_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		if (!worldIn.isAirBlock(pos.up()))
		{
			InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(Blocks.CRAFTING_TABLE));
			InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(Init.ITEM_CONSTRUCTING_TEMPLATE));
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos.up()) && super.canPlaceBlockAt(worldIn, pos);
	}
}
