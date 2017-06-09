package com.hk.artm.item;

import com.hk.artm.ARTM;
import com.hk.artm.Init;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemARTMGuide extends ItemARTM
{
	public ItemARTMGuide()
	{
		super("artmguide");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
		{
			if (worldIn.getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE)
			{
				if (worldIn.isAirBlock(pos.up()))
				{
					List<EntityItem> lst = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.up()));
					for (EntityItem item : lst)
					{
						if (!item.getEntityItem().isEmpty() && item.getEntityItem().getItem() == Item.getItemFromBlock(Init.BLOCK_CONSTRUCTING_TABLE))
						{
							item.setDead();
							worldIn.setBlockState(pos, Init.BLOCK_CONSTRUCTING_TABLE.getDefaultState());
							break;
						}
					}
					return EnumActionResult.SUCCESS;
				}
				else
				{
					ARTMUtil.sendMessage(player, "item.needairblock.msg");
				}
				return EnumActionResult.FAIL;
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if (handIn.equals(EnumHand.MAIN_HAND))
		{
			ARTM.proxy.openARTMGuide();
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}