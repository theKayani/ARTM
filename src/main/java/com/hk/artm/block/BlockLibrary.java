package com.hk.artm.block;

import com.hk.artm.Init;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLibrary extends BlockARTM
{
	public static final PropertyEnum<BlockLibrary.EnumType> MODE = PropertyEnum.create("mode", BlockLibrary.EnumType.class);

	public BlockLibrary()
	{
		super("library", Material.WOOD, 1.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MODE, BlockLibrary.EnumType.DEFAULT));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if (stack.getItem() == Init.ITEM_ARTM_GUIDE)
		{
			return true;
		}
		return false;
	}

	private void checkMultiblock(World worldIn, BlockPos pos, IBlockState state)
	{
		boolean onNorth = worldIn.getBlockState(pos.north()).getBlock() == Init.BLOCK_LIBRARY;
		boolean onSouth = worldIn.getBlockState(pos.south()).getBlock() == Init.BLOCK_LIBRARY;
		boolean onWest = worldIn.getBlockState(pos.west()).getBlock() == Init.BLOCK_LIBRARY;
		boolean onEast = worldIn.getBlockState(pos.east()).getBlock() == Init.BLOCK_LIBRARY;

		if (onNorth && onWest && worldIn.getBlockState(pos.north().west()).getBlock() == Init.BLOCK_LIBRARY)
		{
			worldIn.setBlockState(pos, state.withProperty(MODE, EnumType.BOTTOM_RIGHT), 3);
			return;
		}
		if (onNorth && onEast && worldIn.getBlockState(pos.north().east()).getBlock() == Init.BLOCK_LIBRARY)
		{
			worldIn.setBlockState(pos, state.withProperty(MODE, EnumType.TOP_RIGHT), 3);
			return;
		}
		if (onSouth && onWest && worldIn.getBlockState(pos.south().west()).getBlock() == Init.BLOCK_LIBRARY)
		{
			worldIn.setBlockState(pos, state.withProperty(MODE, EnumType.BOTTOM_LEFT), 3);
			return;
		}
		if (onSouth && onEast && worldIn.getBlockState(pos.south().east()).getBlock() == Init.BLOCK_LIBRARY)
		{
			worldIn.setBlockState(pos, state.withProperty(MODE, EnumType.TOP_LEFT), 3);
			return;
		}

		worldIn.setBlockState(pos, state.withProperty(MODE, EnumType.DEFAULT), 3);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		checkMultiblock(worldIn, pos, state);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Init.BLOCK_LIBRARY);
	}

	public int damageDropped(IBlockState state)
	{
		return BlockLibrary.EnumType.DEFAULT.ordinal();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (BlockLibrary.EnumType type : BlockLibrary.EnumType.values())
		{
			list.add(new ItemStack(itemIn, 1, type.ordinal()));
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(MODE, BlockLibrary.EnumType.values()[meta]);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MODE).ordinal();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]{MODE});
	}

	public enum EnumType implements IStringSerializable
	{
		DEFAULT,
		BOTTOM_LEFT,
		BOTTOM_RIGHT,
		TOP_LEFT,
		TOP_RIGHT;

		public final String unlocalizedName;

		EnumType()
		{
			unlocalizedName = name().toLowerCase();
		}

		@Override
		public String getName()
		{
			return unlocalizedName;
		}
	}
}
