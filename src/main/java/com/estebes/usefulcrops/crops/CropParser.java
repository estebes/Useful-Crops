package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.UsefulCrops;
import com.estebes.usefulcrops.crops.Crops.CropInfo;
import com.estebes.usefulcrops.crops.cropspecial.CropSpecialEnet;
import com.estebes.usefulcrops.crops.cropspecial.CropSpecialWTF;
import com.estebes.usefulcrops.crops.croptypes.CropFlowerType1;
import com.estebes.usefulcrops.crops.croptypes.CropMushroomType1;
import com.estebes.usefulcrops.crops.croptypes.CropPlantType1;
import com.google.protobuf.TextFormat;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CropParser {
    public static Crops.CropsList.Builder cropList = Crops.CropsList.newBuilder();

    static {
        try {
            InputStream inputStream = UsefulCrops.class.getResourceAsStream("/assets/usefulcrops/config/crops.cfg");
            InputStreamReader reader = new InputStreamReader(inputStream);
            TextFormat.merge(reader, cropList);
            inputStream.close();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        for(CropInfo cropInfo : cropList.getCropInfoList()) {
            switch (cropInfo.getCropType()) {
                case CROP_PLANT_1:
                    ic2.api.crops.Crops.instance.registerCrop(new CropPlantType1(new CropProperties(cropInfo)));
                    break;
                case CROP_FLOWER_1:
                    ic2.api.crops.Crops.instance.registerCrop(new CropFlowerType1(new CropProperties(cropInfo)));
                    break;
                case CROP_MUSHROOM_1:
                    ic2.api.crops.Crops.instance.registerCrop(new CropMushroomType1(new CropProperties(cropInfo)));
                    break;
            }
        }
        ic2.api.crops.Crops.instance.registerCrop(new CropSpecialEnet());
        ic2.api.crops.Crops.instance.registerCrop(new CropSpecialWTF());
    }
}
