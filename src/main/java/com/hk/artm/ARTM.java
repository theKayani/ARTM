package com.hk.artm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ARTM.MODID, version = ARTM.VERSION)
public class ARTM
{
	public static final String MODID = "artm";
	public static final String VERSION = "1.0.0";
	@SidedProxy(modId = MODID, serverSide = "com.hk.artm.CommonARTMProxy", clientSide = "com.hk.artm.ClientARTMProxy")
	public static CommonARTMProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
	    proxy.preInit();
	    Init.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	    Init.addRecipes();
	    proxy.init();
    }

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
}
