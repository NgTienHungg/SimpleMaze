package game.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author NGUYEN TIEN HUNG
 */
public class ImgManager {

    public static BufferedImage imgLogo, imgCover, imgBG;
    public static BufferedImage imgScLeft, imgScRight;
    public static BufferedImage[] sprCoin = new BufferedImage[5];

    public void loadImages() {
        ImgLoader loader = new ImgLoader();
        try {
            imgLogo = loader.load("/logo.png");
            imgCover = loader.load("/cover.png");
            imgBG = loader.load("/background.png");
            imgScLeft = loader.load("/sc_left.png");
            imgScRight = loader.load("/sc_right.png");

            for (int i = 0; i < 5; ++i) {
                sprCoin[i] = loader.load("/coin/" + i + ".png");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static BufferedImage pixelImage(BufferedImage image, int xpixel, int ypixel) {
        BufferedImage tmp = new BufferedImage(xpixel, ypixel, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tmp.getGraphics();
        AffineTransform at = new AffineTransform();
        at.scale((float) xpixel / image.getWidth(), (float) ypixel / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(image, op, 0, 0);
        return tmp;
    }

    public static BufferedImage alphaImage(BufferedImage image, int a) {
        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);
        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, A, R, G, B;
                p = tmp.getRGB(xx, yy);
                A = (p >> 24) & 0xff;
                if (A == 0) {
                    continue;
                }
                R = (p >> 16) & 0xff;
                G = (p >> 8) & 0xff;
                B = p & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, int R, int G, int B) {
        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, Color color) {
        int R = color.getRed(), G = color.getGreen(), B = color.getBlue();
        return colorImage(image, R, G, B);
    }
}
