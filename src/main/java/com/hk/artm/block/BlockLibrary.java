package com.hk.artm.block;

import com.hk.artm.ARTM;
import com.hk.artm.Init;
import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.gui.GuiIDs;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLibrary extends BlockARTM implements ITileEntityProvider
{
	public static final PropertyEnum<BlockLibrary.EnumType> MODE = PropertyEnum.create("mode", BlockLibrary.EnumType.class);

	public BlockLibrary()
	{
		super("library", Material.WOOD, 1.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MODE, BlockLibrary.EnumType.DEFAULT));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		System.out.println(ARTMUtil.getSide() + ", " + playerIn.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).randomNum);
		ItemStack stack = playerIn.getHeldItem(hand);
		if (stack.getItem() == Init.ITEM_ARTM_GUIDE)
		{
			EnumType type = state.getValue(MODE);
			if (type == EnumType.DEFAULT)
			{
				playerIn.sendMessage(new TextComponentTranslation("tile.multiblockneeded.msg"));
				return true;
			}
			if (type == EnumType.BOTTOM_RIGHT)
			{
				pos = pos.north();
			}
			else if (type == EnumType.TOP_LEFT)
			{
				pos = pos.east();
			}
			else if (type == EnumType.TOP_RIGHT)
			{
				pos = pos.north().east();
			}
			playerIn.openGui(ARTM.instance, GuiIDs.GUI_LIBRARY, worldIn, pos.getX(), pos.getY(), pos.getZ());
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
		return new BlockStateContainer(this, MODE);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileLibrary();
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
