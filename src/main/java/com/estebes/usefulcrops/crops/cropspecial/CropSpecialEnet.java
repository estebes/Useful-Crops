package com.estebes.usefulcrops.crops.cropspecial;

import com.estebes.usefulcrops.crops.CropProperties;
import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.EventListener;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.energy.EnergyNetLocal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class CropSpecialEnet extends CropCard {
    private CropProperties cropProperties;

    public CropSpecialEnet() {
    }

    @Override
    public String owner() {
        return "UsefulCrops";
    }

    @Override
    public String displayName() {
        return "E-net Crop";
    }

    @Override
    public String discoveredBy() {
        return "Player";
    }

    @Override
    public String name() {
        return "e-net crop";
    }

    @Override
    public String[] attributes() {
        return null;
    }

    @Override
    public int tier() {
        return 15;
    }

    @Override
    public int maxSize() {
        return 4;
    }

    @Override
    public int getrootslength(ICropTile crop) {
        return 3;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return crop.getSize() < this.maxSize();
    }

    @Override
    public int getOptimalHavestSize(ICropTile crop) {
        return 4;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return false;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 2;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        return crop.getSize() == 3 ? 2000 : 800;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        return null;
    }

    @Override
    public float dropGainChance() {
        return 0.0F;
    }

    @Override
    public int stat(int n) {
        switch (n) {
            case 0:
                return 2;
            case 1:
                return 0;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 0;
        }
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSprites(IIconRegister iconRegister) {
        this.textures = new IIcon[this.maxSize()];
        for (int size = 1; size <= this.maxSize() - 1; size++) {
            this.textures[(size - 1)] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + "CropPlantType1_" + size);
        }
    }

    @Override
    public void tick(ICropTile crop) {
    	TileEntity te = (TileEntity)crop;
    	if (EnergyNet.instance.getTileEntity(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord) == null) {
    		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(new FakeTileEntity(te)));
    	}
    }
    
    /**
     * This method returns the amount of EU the crop emits every tick.
     */
    private double producedEnergy(ICropTile crop) {
    	return crop.getSize() == maxSize() ? 1D : 1D;
    }
    
    public class FakeTileEntity extends TileEntity implements IEnergySource {
    	
    	private final TileEntity crop;
    	
    	private FakeTileEntity(TileEntity parent) {
    		this.xCoord = parent.xCoord;
    		this.yCoord = parent.yCoord;
    		this.zCoord = parent.zCoord;
    		this.worldObj = parent.getWorldObj();
    		if (!parent.isInvalid()) {
    			this.validate();
    			//If the parent isn't valid, the tile doesn't get registered and we try gain the next tick.
    		}
    		crop = parent;
    		EventListener.addTileEntityTick(this);
		}

		@Override
		public boolean emitsEnergyTo(TileEntity receiver,
				ForgeDirection direction) {
			return true;
		}

		@Override
		public double getOfferedEnergy() {
			return producedEnergy((ICropTile)crop); //Emitted energy per tick.
		}

		@Override
		public void drawEnergy(double amount) {} // we don't have a buffer

		@Override
		public int getSourceTier() {
			return 1;
		}
		
		@Override
		public void updateEntity() {
			TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if (te != crop || ((ICropTile)te).getCrop() != CropSpecialEnet.this) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
				EventListener.removeTileEntityTick(this);
			}
		}
    	
    }
}