package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static game.manager.Game.DELTA_TIME;
import static game.image.ImgManager.pixelImage;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Animation extends MyObject {

    private BufferedImage[] sprSheet;
    private float frmTime = 0.1f;
    private int currentFrm = 0;
    private float currentTime = 0f;

    public Animation(int x, int y, int w, int h, BufferedImage[] sprSheet) {
        super(x, y, w, h);
        this.sprSheet = sprSheet;
    }

    @Override
    public void create() {
        for (int i = 0; i < sprSheet.length; ++i) {
            sprSheet[i] = pixelImage(sprSheet[i], w, h);
        }
    }

    @Override
    public void update() {
        currentTime += DELTA_TIME;
        if (currentTime >= frmTime) {
            currentTime -= frmTime;
            currentFrm = (currentFrm + 1) % sprSheet.length;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(sprSheet[currentFrm], x, y, null);
    }
}
