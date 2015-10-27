package com.estebes.usefulcrops.init;

import com.estebes.usefulcrops.crops.*;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;

public class CropInit {
    public static CropCard cropLead = new CropLead();
    public static CropCard cropWood = new CropWood();
    public static CropCard cropLapis = new CropLapis();

    public static void init() {
        registerCrops();
        registerBaseSeed();
    }

    public static void registerCrops() {
    }

    public static void registerBaseSeed() {
        //Crops.instance.registerBaseSeed(new ItemStack(Blocks.sapling, 1, 3), cropWood, 1, 1, 1, 1);
    }
}