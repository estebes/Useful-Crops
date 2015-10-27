package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.api.item.IC2Items;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class CropWood extends CropCard {
    public ItemStack mDrop;

    @Override
    public String owner() {
        return "UsefulCrops";
    }

    @Override
    public String displayName() {
        return "Midget Bamboo";
    }

    @Override
    public String discoveredBy() {
        return "estebes";
    }

    @Override
    public String name() {
        return "midget bamboo";
    }

    @Override
    public int tier() {
        return 4;
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
                return 2;
            case 4:
                return 0;
        }
        return 0;
    }

    @Override
    public String[] attributes() {
        return new String[]{ "Jungle", "Reed" };
    }

    @Override
    public int maxSize() {
        return 4;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return crop.getSize() < 4;
    }

    @Override
    public int getrootslength(ICropTile crop) {
        return 3;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getSize() == 4;
    }

    @Override
    public int getOptimalHavestSize(ICropTile crop) {
        return 4;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        if (this.mDrop == null) {
            this.mDrop = new ItemStack(Blocks.log, 1, 3).copy();
        }
        return this.mDrop.copy();
    }

    @Override
    public float dropGainChance() {
        return super.dropGainChance();
    }

    @Override
    public int growthDuration(ICropTile crop) {
        return 700;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSprites(IIconRegister iconRegister) {
        this.textures = new IIcon[maxSize()];
        for (int i = 1; i <= this.textures.length; i++) {
            this.textures[(i - 1)] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + "bamboo" + i);
        }
    }
}