package game.effect;

import java.awt.Graphics2D;
import static game.image.ImgManager.*;

/**
 * @author NGUYEN TIEN HUNG
 */
public class ScreenChange {

    private boolean open;
    private boolean active;
    private int x1, x2;
    private int speed = 10;

    public ScreenChange(boolean open) {
        this.open = open;
        active = true;
    }

    public void create() {
        imgScLeft = pixelImage(imgScLeft, 1000, 750);
        imgScRight = pixelImage(imgScRight, 1000, 750);
        this.setOpen(open);
    }

    public void update() {
        if (!active) {
            return;
        }
        if (open) {
            x1 = Math.min(0, x1 - speed);
            x2 = Math.max(0, x2 + speed);
            if (x1 == -1000 && x2 == 1000) {
                active = false;
                open = false;
            }
        } else {
            x1 = Math.min(0, x1 + speed);
            x2 = Math.max(0, x2 - speed);
            if (x1 == 0 && x2 == 0) {
                active = false;
                open = true;
            }
        }
    }

    public void render(Graphics2D g) {
        if (!active) {
            return;
        }
        g.drawImage(imgScLeft, x1, 0, null);
        g.drawImage(imgScRight, x2, 0, null);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        if (open) {
            x1 = 0;
            x2 = 0;
        } else {
            x1 = -1000;
            x2 = 1000;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
