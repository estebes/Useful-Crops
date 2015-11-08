package com.estebes.usefulcrops.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class EventListener {
	
	private static final List<TileEntity> tickables = new ArrayList<TileEntity>();
	private static final List<TileEntity> toRemove = new ArrayList<TileEntity>();
	
	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase != Phase.END) return;
		for (TileEntity te : toRemove) {
			tickables.remove(te);
		}
		toRemove.clear();
		for (TileEntity te: tickables) {
			te.updateEntity();
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		int dimension = event.world.provider.dimensionId;
		if (dimension == 0) {
			tickables.clear();
			toRemove.clear();
		}
		else {
			List<TileEntity> toRemove = new ArrayList<TileEntity>();
			for (TileEntity te : tickables) {
				if (te.getWorldObj() != null && te.getWorldObj().provider.dimensionId == dimension) {
					toRemove.add(te);
				}
			}
			tickables.removeAll(toRemove);
		}
	}
	
	public static void addTileEntityTick(TileEntity te) {
		tickables.add(te);
	}
	
	public static void removeTileEntityTick(TileEntity te) {
		if (tickables.contains(te))
		toRemove.add(te);
	}

}
