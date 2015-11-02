package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.XorShiftRandom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class CropCommon extends CropCard {
    protected CropProperties cropProperties;

    public CropCommon(CropProperties cropProperties) {
        this.cropProperties = cropProperties;
    }

    @Override
    public String owner() {
        return Reference.MOD_ID;
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
        return 0;
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
        return 0;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return false;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 0;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        return 500;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        XorShiftRandom specialDropRandom = new XorShiftRandom(100);
        if(this.cropProperties.getCropSpecialDrop() != null) {
            if(this.cropProperties.getCropSpecialDropChance() * 100 > specialDropRandom.nextInt()) {
                return this.cropProperties.getCropSpecialDrop();
            }
        }
        return this.cropProperties.getCropDrop();
    }

    @Override
    public float dropGainChance() {
        return this.cropProperties.getCropDropChance();
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
    }

    @Override
    public boolean onEntityCollision(ICropTile crop, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if(this.cropProperties.getCropDebuffs() != null && crop.getSize() >= 2) {
                ((EntityLivingBase) entity).addPotionEffect(this.cropProperties.getCropDebuffs());
            }
            return ((EntityLivingBase) entity).isSprinting();
        }
        return false;
    }
}
