package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class CropLead extends CropCard
{
    public ItemStack mDrop;

    @Override
    public String owner() {
        return "UsefulCrops";
    }

    @Override
    public String displayName() {
        return "Plumbiscus";
    }

    @Override
    public String discoveredBy() {
        return "estebes";
    }

    @Override
    public String name() {
        return "plumbiscus";
    }

    @Override
    public int tier() {
        return 8;
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
        return new String[] { "Leaves", "Metal" };
    }

    @Override
    public int maxSize() {
        return 5;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return ((crop.getSize() == 4) && checkBelow(crop)) || ((crop.getSize() < 4));
    }

    @Override
    public int getrootslength(ICropTile crop) {
        return 3;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getSize() == 5;
    }

    @Override
    public int getOptimalHavestSize(ICropTile crop) {
        return 5;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        if (this.mDrop == null) {
            this.mDrop = IC2Items.getItem("smallLeadDust").copy();
        }
        return this.mDrop.copy();
    }

    @Override
    public float dropGainChance() {
        return super.dropGainChance() / 2.0F;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        return crop.getSize() == 4 ? 2200 : 800;
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSprites(IIconRegister iconRegister)
    {
        this.textures = new IIcon[maxSize()];
        for (int i = 1; i <= this.textures.length; i++) {
            this.textures[(i - 1)] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + "lead" + i);
        }
    }

    private boolean checkBelow(ICropTile crop) {
        if (crop == null) {
            return false;
        }
        for (int i = 1; i < getrootslength(crop); i++) {
            TileEntity teCrop = (TileEntity)crop;
            Block block = teCrop.getWorldObj().getBlock(teCrop.xCoord, teCrop.yCoord - i, teCrop.zCoord);
            int metaData = block.getDamageValue(teCrop.getWorldObj(), teCrop.xCoord, teCrop.yCoord - i, teCrop.zCoord);
            if (block.isAir(teCrop.getWorldObj(), teCrop.xCoord, teCrop.yCoord - i, teCrop.zCoord)) {
                return false;
            }
            for(ItemStack itemStack : OreDictionary.getOres("oreLead")) {
                if (Item.getItemFromBlock(block) == itemStack.getItem() && metaData == itemStack.getItemDamage()) {
                    return true;
                }
            }
            for(ItemStack itemStack : OreDictionary.getOres("blockLead")) {
                if (Item.getItemFromBlock(block) == itemStack.getItem() && metaData == itemStack.getItemDamage()) {
                    return true;
                }
            }
        }
        return false;
    }
}