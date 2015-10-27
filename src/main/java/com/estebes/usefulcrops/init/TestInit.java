package com.estebes.usefulcrops.init;

import com.estebes.usefulcrops.crops.Crops.CropInfo;
import com.estebes.usefulcrops.crops.Crops.CropsList;
import com.google.protobuf.TextFormat;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestInit {
    public static BufferedImage result2;

    public void load() {
        /*Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher("Blue@Flower@Rofl@");
        while (matcher.find()) {
            if(matcher.group(1) != null) {
            }
        }*/
        //TextureAtlasSprite.loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_);

        CropsList.Builder cropsList = CropsList.newBuilder();
        CropInfo.CropDrops drops = CropInfo.CropDrops.newBuilder()
                .setCropDrop("IC2:itemDustSmall@7")
                .setCropDropChance(0.95F)
                .build();
        CropInfo lapis = CropInfo.newBuilder()
                .setCropName("Blue Orchidaceae")
                .setCropDiscoveredBy("estebes")
                .setCropAttributes("Blue@Flower@Rofl@")
                .setCropTier(7)
                .setCropColor("#1A7999")
                .setCropDrops(drops)
                .setCropType(CropInfo.CropType.CROP_PLANT_1)
                .build();
        cropsList.addCropInfo(lapis);

        try {
            /*InputStream inputStream = new FileInputStream("crops.cfg");
            InputStreamReader reader = new InputStreamReader(inputStream);
            TextFormat.merge(reader, cropsList);
            inputStream.close();*/

            FileWriter file = new FileWriter("crops.cfg");
            TextFormat.print(cropsList, file);
            file.flush();
            file.close();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void paintComponent() {
        BufferedImage test1 = null;
        try {
            test1 = ImageIO.read(getClass().getResource("/assets/usefulcrops/textures/blocks/test.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        BufferedImage test2 = null;
        try {
            test2 = ImageIO.read(getClass().getResource("/assets/usefulcrops/textures/blocks/test3.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        BufferedImage result = new BufferedImage(test1.getWidth(), test1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = result.createGraphics();
        Color newColor = Color.decode("#1A7999");
        gbi.drawImage(test2, null, 0, 0);
        gbi.setColor(newColor);
        gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
        gbi.fillRect(0, 0, test2.getWidth(), test2.getHeight());
        result2 = new BufferedImage(test1.getWidth(), test1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi2 = result2.createGraphics();
        gbi2.drawImage(test1, 0, 0, null);
        gbi2.drawImage(result, 0, 0, null);
        gbi.dispose();
        gbi2.dispose();
        //saveImage(result2, "Rofl.png");
    }

    public static void saveImage(BufferedImage img, String ref) {
        try {
            String format = (ref.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(ref));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
