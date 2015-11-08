package com.estebes.usefulcrops;

import net.minecraftforge.common.MinecraftForge;

import com.estebes.usefulcrops.crops.CropParser;
import com.estebes.usefulcrops.proxy.ServerProxy;
import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.EventListener;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class UsefulCrops {
    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
    public static ServerProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static UsefulCrops instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent preinit) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent init) {
        CropParser.init();
        EventListener el = new EventListener();
        MinecraftForge.EVENT_BUS.register(el);
        FMLCommonHandler.instance().bus().register(el);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
    }
}