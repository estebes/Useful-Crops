package com.estebes.usefulcrops.crops.croptypes;

import com.estebes.usefulcrops.crops.CropProperties;
import com.estebes.usefulcrops.init.TestInit;
import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.SpriteHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class CropPlantType1 extends CropCard {
    private CropProperties cropProperties;

    public CropPlantType1(CropProperties cropProperties) {
        this.cropProperties = cropProperties;
    }

    @Override
    public String owner() {
        return "UsefulCrops";
    }

    @Override
    public String displayName() {
        return this.cropProperties.getCropName();
    }

    @Override
    public String discoveredBy() {
        return this.cropProperties.getCropDiscoveredBy();
    }

    @Override
    public String name() {
        return this.cropProperties.getCropName().toLowerCase();
    }

    @Override
    public String[] attributes() {
        return this.cropProperties.getCropAttributes();
    }

    @Override
    public int tier() {
        return this.cropProperties.getCropTier();
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
        return crop.getSize() == 4;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 2;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        if(this.cropProperties.getCropGrowthDuration() != null) {
            return this.cropProperties.getCropGrowthDuration()[crop.getSize() - 1];
        }
        return crop.getSize() == 3 ? 2000 : 800;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        return this.cropProperties.getCropDrop();
    }

    @Override
    public float dropGainChance() {
        return 0.95F;
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
        try {
            this.textures[(this.maxSize() - 1)] = SpriteHelper.registerCustomSprite(iconRegister, this.cropProperties.getCropIcon(),
                    "CropPlantType1_" + this.maxSize() + this.cropProperties.getCropName(), 16);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}