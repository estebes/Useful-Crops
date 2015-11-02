package com.estebes.usefulcrops.util;

import com.google.common.base.Strings;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@SideOnly(Side.CLIENT)
public final class SpriteHelper {
    public BufferedImage getImage(String bg, String fg, Color cropColor) {
        BufferedImage background = null;
        BufferedImage foreground = null;
        try {
            background = ImageIO.read(getClass().getResource(bg));
            foreground = ImageIO.read(getClass().getResource(fg));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Paint Crop
        BufferedImage aux = new BufferedImage(foreground.getWidth(), foreground.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D aux_g = aux.createGraphics();
        aux_g.drawImage(foreground, null, 0, 0);
        aux_g.setColor(cropColor);
        aux_g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
        aux_g.fillRect(0, 0, foreground.getWidth(), foreground.getHeight());

        // Merge Images
        BufferedImage finalImage = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D final_g = finalImage.createGraphics();
        final_g.drawImage(background, 0, 0, null);
        final_g.drawImage(aux, 0, 0, null);

        // Clean-up
        aux_g.dispose();
        final_g.dispose();

        // Done
        return finalImage;
    }

    /*
    Credit to Master801
    https://github.com/master801/801-Library/blob/1669728d90a7f6ecae1eef6f56a17b033fd7fe45/src/test/java/coretest/SpriteHelper__NO_DEPENDENCIES.java
    */
    public static IIcon registerCustomSprite(IIconRegister iconRegistry, BufferedImage bufferedImage, final String spriteName, int spriteDimensions) throws IOException {
        TextureAtlasSprite sprite = null;
        if (iconRegistry == null || bufferedImage == null || Strings.isNullOrEmpty(spriteName)) {
            return null;
        }
        if (iconRegistry instanceof TextureMap) {
            TextureMap textureMap = (TextureMap)iconRegistry;
            sprite = textureMap.getTextureExtry(spriteName);
            if (sprite != null) {
                return sprite;
            }
            final int[] rgb = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), new int[bufferedImage.getWidth() * bufferedImage.getHeight()], 0, spriteDimensions);
            sprite = new TextureAtlasSprite(spriteName) {

                private final List<int[][]> framesTextureData = ObfuscationReflectionHelper.getPrivateValue(TextureAtlasSprite.class, this, "a", "field_110976_a", "framesTextureData");

                @Override
                public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
                    framesTextureData.clear();
                    int[][] mipmapLevels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
                    if (mipmapLevels.length <= 0) {
                        FMLLog.severe("Couldn't generate mipmap levels for sprite \"%s\"?", spriteName);
                        return true;
                    } else {
                        for (int i = 0; i < mipmapLevels.length; i++) {
                            mipmapLevels[i] = rgb;
                        }
                        framesTextureData.add(mipmapLevels);
                    }
                    return false;
                }

                @Override
                public void generateMipmaps(int mipmapLevel) {
                    try {
                        framesTextureData.clear();
                        if (rgb != null && rgb.length > 1) {
                            int[][] mipmapData = prepareAnisotropicFiltering(new int[mipmapLevel + 1][], getIconWidth(), getIconHeight());
                            if (mipmapData == null | (mipmapData != null && mipmapData.length <= 0)) {
                                FMLLog.severe("Couldn't generate mipmaps for sprite \"%s\"! Couldn't invoke method \"prepareAnisotropicFiltering\"?", getIconName());
                                return;
                            }
                            for (int i = 0; i < mipmapData.length; i++) {
                                mipmapData[i] = rgb;
                            }
                            framesTextureData.add(mipmapData);
                        } else {
                            FMLLog.severe("Couldn't get the RGB array for sprite \"%s\"!", getIconName());
                        }

                        return;
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    } catch(InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    FMLLog.severe("Failed to generate mipmaps for sprite \"%s\"!", spriteName);
                }

                @Override
                public boolean hasCustomLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
                    return true;
                }

                private int[][] prepareAnisotropicFiltering(int[][] par1, int par2, int par3) throws IllegalAccessException, InvocationTargetException {
                    final String[] methodNames = new String[] { "a", "func_147960_a", "prepareAnisotropicFiltering" };
                    int[][] var1 = null;
                    for(Method searchingMethod : TextureAtlasSprite.class.getDeclaredMethods()) {
                        final boolean isAccessible = searchingMethod.isAccessible();
                        if (!isAccessible) {
                            searchingMethod.setAccessible(true);
                        }
                        for(String methodName : methodNames) {
                            if (searchingMethod.getName().equals(methodName)) {
                                var1 = (int[][]) searchingMethod.invoke(this, par1, par2, par3);
                                break;
                            }
                        }
                        searchingMethod.setAccessible(isAccessible);
                    }
                    return var1;
                }

            };
            sprite.setIconWidth(bufferedImage.getWidth());
            sprite.setIconHeight(bufferedImage.getHeight());

            textureMap.setTextureEntry(spriteName, sprite);
        }
        return sprite;
    }

}