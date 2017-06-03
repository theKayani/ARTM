package com.hk.artm.network;

import com.hk.artm.ARTM;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ARTMNetwork implements IMessageHandler<ARTMNetwork.ARTMMessage, IMessage>
{
	public static final ARTMNetwork INSTANCE = new ARTMNetwork();
	private SimpleNetworkWrapper packetPipeline;
	private List<Class<? extends ARTMPacket>> clss = new ArrayList<Class<? extends ARTMPacket>>();

	public void preInit()
	{
		packetPipeline = NetworkRegistry.INSTANCE.newSimpleChannel(ARTM.MODID + "_network");
		packetPipeline.registerMessage(this, ARTMMessage.class, 0, Side.CLIENT);
		packetPipeline.registerMessage(this, ARTMMessage.class, 1, Side.SERVER);
		registerPackets();
	}

	private void registerPackets()
	{
		registerPacket(PacketPlayerPropSync.class);
	}

	private void registerPacket(Class<? extends ARTMPacket> packetClass)
	{
		clss.add(packetClass);
	}

	public void sendToServer(ARTMPacket packet)
	{
		packetPipeline.sendToServer(new ARTMMessage(packet));
	}

	public void sendTo(ARTMPacket packet, EntityPlayerMP playerMP)
	{
		packetPipeline.sendTo(new ARTMMessage(packet), playerMP);
	}

	public void sendToAll(ARTMPacket packet)
	{
		packetPipeline.sendToAll(new ARTMMessage(packet));
	}

	public void sendToAllAround(ARTMPacket packet, NetworkRegistry.TargetPoint point)
	{
		packetPipeline.sendToAllAround(new ARTMMessage(packet), point);
	}

	@Override
	public IMessage onMessage(final ARTMMessage message, final MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					message.packet.onClientReceive(Minecraft.getMinecraft().player);
				}
			});
		}
		else
		{
			ctx.getServerHandler().playerEntity.getServer().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					message.packet.onServerReceive(ctx.getServerHandler().playerEntity);
				}
			});
		}
		return null;
	}

	public static final class ARTMMessage implements IMessage
	{
		private ARTMPacket packet;

		public ARTMMessage()
		{
		}

		public ARTMMessage(ARTMPacket packet)
		{
			this.packet = packet;
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeShort(INSTANCE.clss.indexOf(packet.getClass()));

			NBTTagCompound tag = new NBTTagCompound();
			packet.writeToNBT(tag);
			ByteBufUtils.writeTag(buf, tag);
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			try
			{
				packet = INSTANCE.clss.get(buf.readShort()).newInstance();
				NBTTagCompound tag = ByteBufUtils.readTag(buf);
				packet.readFromNBT(tag);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	public static abstract class ARTMPacket
	{
		public abstract void writeToNBT(NBTTagCompound tag);

		public abstract void readFromNBT(NBTTagCompound tag);

		public void onServerReceive(EntityPlayerMP player)
		{
		}

		@SideOnly(Side.CLIENT)
		public void onClientReceive(EntityPlayerSP player)
		{
		}
	}
}
