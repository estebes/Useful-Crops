package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.crops.Crops.CropInfo;
import com.estebes.usefulcrops.util.Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CropProperties {
    private CropInfo cropInfo;

    public CropProperties(CropInfo cropInfo) {
        this.cropInfo = cropInfo;
    }

    // CropName
    public String getCropName() {
        return this.cropInfo.getCropName();
    }

    // CropDiscoveredBy
    public String getCropDiscoveredBy() {
        return this.cropInfo.getCropDiscoveredBy();
    }

    // CropAttributes
    public String[] getCropAttributes() {
        ArrayList<String> cropAttributes = new ArrayList<String>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(this.cropInfo.getCropAttributes());
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                if(cropAttributes.size() <= 3) {
                    cropAttributes.add(matcher.group(1));
                }
            }
        }
        return cropAttributes.toArray(new String[cropAttributes.size()]);
    }

    // CropTier
    public int getCropTier() {
        return this.cropInfo.getCropTier();
    }

    // CropGrowthDuration
    public int[] getCropGrowthDuration() {
        ArrayList<Integer> cropGrowthDuration = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(this.cropInfo.getCropGrowthDuration());
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                cropGrowthDuration.add(Integer.valueOf(matcher.group(1)));
            }
        }
        return Util.getIntArray(cropGrowthDuration);
    }

    // CropColor
    public Color getCropColor() {
        return Color.decode(this.cropInfo.getCropColor());
    }

    // CropDrop
    public ItemStack getCropDrop() {
        String modID = "";
        String itemID = "";
        int metaData = 0;
        Pattern pattern = Pattern.compile("([^:]+)([:])([^@]+)([@])?(\\d+)?");
        Matcher matcher = pattern.matcher(this.cropInfo.getCropDrops().getCropDrop());
        if (matcher.matches()) {
            if (matcher.group(1) != null) {
                modID = matcher.group(1);
            }
            if (matcher.group(3) != null) {
                itemID = matcher.group(3);
            }
            if (matcher.group(5) != null) {
                metaData = Integer.valueOf(matcher.group(5));
            }
        }
        if(GameRegistry.findItem(modID, itemID) != null) {
            return new ItemStack(GameRegistry.findItem(modID, itemID), 1, metaData);
        }
        return null;
    }

    public float getCropDropChance() {
        return this.cropInfo.getCropDrops().getCropDropChance();
    }

    // CropSpecialDrop
    public ItemStack getCropSpecialDrop() {
        String modID = "";
        String itemID = "";
        int metaData = 0;
        Pattern pattern = Pattern.compile("([^:]+)([:])([^@]+)([@])?(\\d+)?");
        Matcher matcher = pattern.matcher(this.cropInfo.getCropDrops().getCropSpecialDrop());
        if (matcher.matches()) {
            if (matcher.group(1) != null) {
                modID = matcher.group(1);
            }
            if (matcher.group(3) != null) {
                itemID = matcher.group(3);
            }
            if (matcher.group(5) != null) {
                metaData = Integer.valueOf(matcher.group(5));
            }
        }
        if(GameRegistry.findItem(modID, itemID) != null) {
            return new ItemStack(GameRegistry.findItem(modID, itemID), 1, metaData);
        }
        return null;
    }

    public float getCropSpecialDropChance() {
        return this.cropInfo.getCropDrops().getCropSpecialDropChance() > 0.5F ? 0.5F :
                this.cropInfo.getCropDrops().getCropSpecialDropChance();
    }

    // CropEffects
    public PotionEffect getCropDebuffs() {
        String potionName = "";
        Pattern pattern = Pattern.compile("([^@]+)([@])?(\\d+)?");
        Matcher matcher = pattern.matcher(this.cropInfo.getCropPoison());
        if (matcher.matches()) {
            if (matcher.group(1) != null) {
                potionName = matcher.group(1);
            }
        }
        for(int potion = 0; potion < Potion.potionTypes.length; potion++) {
            if(Potion.potionTypes[potion] != null) {
                if(Potion.potionTypes[potion].getName().equals(potionName)) {
                    System.out.println(Potion.potionTypes[potion].getId());
                    return new PotionEffect(Potion.potionTypes[potion].getId(), 64, 50);
                }
            }
        }
        return null;
    }

    public String getCropType() {
        return this.cropInfo.getCropType().toString().toLowerCase();
    }
}
