package com.estebes.usefulcrops.util;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class SpriteHandler extends TextureAtlasSprite {
    private String iconName;
    private BufferedImage bufferedImage;
    private TextureMap textureMap;

    public SpriteHandler(String iconName, BufferedImage bufferedImage, IIconRegister iconRegister) {
        super(iconName);

        this.iconName = iconName;
        this.bufferedImage = bufferedImage;
        this.textureMap = (TextureMap)iconRegister;
        this.setIconWidth(bufferedImage.getWidth());
        this.setIconHeight(bufferedImage.getHeight());
        this.textureMap.setTextureEntry(this.iconName, this);
    }

    int[] rgb = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(),
            new int[bufferedImage.getWidth() * bufferedImage.getHeight()], 0, bufferedImage.getWidth());;

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        framesTextureData.clear();
        int[][] mipmapLevels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
        if (mipmapLevels.length <= 0) {
            return true;
        } else {
            for (int i = 0; i < mipmapLevels.length; i++) {
                mipmapLevels[i] = rgb;
            }
            framesTextureData.add(mipmapLevels);
        }
        return false;
    }
}
