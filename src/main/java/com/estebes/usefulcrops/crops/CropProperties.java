package com.estebes.usefulcrops.crops;

import com.estebes.usefulcrops.crops.Crops.CropInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        return null;
    }

    // CropColor
    public BufferedImage getCropIcon() {
        BufferedImage background = null;
        BufferedImage foreground = null;
        try {
            background = ImageIO.read(getClass().getResource("/assets/usefulcrops/textures/blocks/" +
                    this.cropInfo.getCropType().toString().toLowerCase() + "_bg.png"));
            foreground = ImageIO.read(getClass().getResource("/assets/usefulcrops/textures/blocks/" +
                    this.cropInfo.getCropType().toString().toLowerCase() + "_fg.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Paint Crop
        BufferedImage aux = new BufferedImage(foreground.getWidth(), foreground.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D aux_g = aux.createGraphics();
        Color fgCropColor = Color.decode(this.cropInfo.getCropColor());
        aux_g.drawImage(foreground, null, 0, 0);
        aux_g.setColor(fgCropColor);
        aux_g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
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

    // CropDrops
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
}
