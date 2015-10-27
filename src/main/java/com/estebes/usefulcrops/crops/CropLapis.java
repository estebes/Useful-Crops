package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.init.TestInit;
import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.SpriteHandler;
import com.estebes.usefulcrops.util.SpriteHelper;
import com.estebes.usefulcrops.util.Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class CropLapis extends CropCard {
    public ItemStack mDrop;

    @Override
    public String owner() {
        return "UsefulCrops";
    }

    @Override
    public String displayName() {
        return "Blue Orchidaceae";
    }

    @Override
    public String discoveredBy() {
        return "estebes";
    }

    @Override
    public String name() {
        return "blue orchidaceae";
    }

    @Override
    public int tier() {
        return 7;
    }

    @Override
    public int stat(int n) {
        switch (n) {
            case 0:
                return 2;
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 5;
            case 4:
                return 1;
        }
        return 0;
    }

    @Override
    public String[] attributes() {
        return new String[]{ "Blue", "Flower" };
    }

    @Override
    public int maxSize() {
        return 4;
    }

    @Override
    public boolean canGrow(ICropTile crop) {

        return (crop.getSize() < 4 && crop.getLightLevel() >= 12);
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
            this.mDrop = new ItemStack(Items.dye, 1, 4).copy();
        }
        return this.mDrop.copy();
    }

    @Override
    public float dropGainChance() {
        return 0.4F;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        if (crop.getSize() == 3) {
            return 2200;
        }
        return 400;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSprites(IIconRegister iconRegister) {
        this.textures = new IIcon[this.maxSize()];
        for (int size = 1; size <= this.maxSize() - 1; size++) {
            this.textures[(size - 1)] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + "CropPlantType1_" + size);
        }
        try {
            this.textures[(this.maxSize() - 1)] = SpriteHelper.registerCustomSprite(iconRegister, TestInit.result2,
                    "CropPlantType1_" + this.maxSize(), TestInit.result2.getHeight());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
