package com.estebes.usefulcrops.crops.croptypes;

import com.estebes.usefulcrops.crops.CropCommon;
import com.estebes.usefulcrops.crops.CropProperties;
import com.estebes.usefulcrops.reference.Reference;
import com.estebes.usefulcrops.util.SpriteHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.ICropTile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class CropMushroomType1 extends CropCommon {

    public CropMushroomType1(CropProperties cropProperties) {
        super(cropProperties);
    }

    @Override
    public int maxSize() {
        return 3;
    }

    @Override
    public int getOptimalHavestSize(ICropTile crop) {
        return this.maxSize();
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getSize() == this.maxSize();
    }

    @Override
    public byte getSizeAfterHarvest(ICropTile crop) {
        return 1;
    }

    @Override
    public int growthDuration(ICropTile crop) {
        if(this.cropProperties.getCropGrowthDuration() != null && this.cropProperties.getCropGrowthDuration().length
                >= this.maxSize()) {
            return this.cropProperties.getCropGrowthDuration()[crop.getSize() - 1];
        }
        return crop.getSize() == 2 ? 2000 : 800;
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
        this.textures[0] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + this.cropProperties.getCropType() + "_" + 1);
        try {
            this.textures[1] = SpriteHelper.registerCustomSprite(iconRegister, new SpriteHelper().getImage("/assets/usefulcrops/textures/blocks/" +
                            this.cropProperties.getCropType() + "_" + 2 + "_bg.png", "/assets/usefulcrops/textures/blocks/" +
                            this.cropProperties.getCropType() + "_" + 2 + "_fg.png", this.cropProperties.getCropColor()),
                    this.cropProperties.getCropType() + 2 + this.cropProperties.getCropName(), 16);
            this.textures[2] = SpriteHelper.registerCustomSprite(iconRegister, new SpriteHelper().getImage("/assets/usefulcrops/textures/blocks/" +
                            this.cropProperties.getCropType() + "_" + 3 + "_bg.png", "/assets/usefulcrops/textures/blocks/" +
                            this.cropProperties.getCropType() + "_" + 3 + "_fg.png", this.cropProperties.getCropColor()),
                    this.cropProperties.getCropType() + 3 + this.cropProperties.getCropName(), 16);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}