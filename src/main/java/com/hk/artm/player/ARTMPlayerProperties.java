package com.hk.artm.player;

import com.hk.artm.ARTM;
import com.hk.artm.network.ARTMNetwork;
import com.hk.artm.network.PacketPlayerPropSync;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ARTMPlayerProperties implements ICapabilitySerializable<NBTTagCompound>
{
	public static final String CAP_ID = ARTM.MODID + ":player_cap";
	private EntityPlayer player;

	public final List<Item> learntItems;

	public ARTMPlayerProperties()
	{
		learntItems = new ArrayList<Item>();
	}

	public void setPlayer(EntityPlayer player)
	{
		if (this.player == null)
		{
			this.player = player;
		}
	}

	public void addLearntItem(Item item)
	{
		if (!learntItems.contains(item))
		{
			learntItems.add(item);
		}
	}

	public void sync()
	{
		if (ARTMUtil.isServer())
		{
			ARTMNetwork.INSTANCE.sendTo(new PacketPlayerPropSync(this), (EntityPlayerMP) player);
//			ARTMNetwork.INSTANCE.sendToAll(new PacketPlayerPropSync(this));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability != null && ARTM.ARTM_PLAYER_PROPERTIES == capability;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (hasCapability(capability, facing))
		{
			return (T) this;
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();

		NBTTagList lst = new NBTTagList();
		for (int i = 0; i < learntItems.size(); i++)
		{
			lst.appendTag(new NBTTagInt(Item.getIdFromItem(learntItems.get(i))));
		}
		tag.setTag("LearntItems", lst);

		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag)
	{
		learntItems.clear();
		NBTTagList lst = (NBTTagList) tag.getTag("LearntItems");
		for (int i = 0; i < lst.tagCount(); i++)
		{
			learntItems.add(Item.getItemById(lst.getIntAt(i)));
		}
	}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(ARTMPlayerProperties.class, new PPStorage(), ARTMPlayerProperties.class);
	}

	private static class PPStorage implements Capability.IStorage<ARTMPlayerProperties>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ARTMPlayerProperties> capability, ARTMPlayerProperties instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<ARTMPlayerProperties> capability, ARTMPlayerProperties instance, EnumFacing side, NBTBase nbt)
		{
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}
}
