package game.screen;

import java.awt.Graphics2D;
import game.enums.GameScreen;
import static game.manager.Global.clamp;
import static game.image.ImgManager.*;
import static game.manager.Game.screenChange;

/**
 * @author NGUYEN TIEN HUNG
 */
public class GSIntro extends MyGScreen {

    private boolean showLogo = true;
    private boolean fadeIn = false;
    private int alpha = 60;
    private int timeWait = 150;

    public GSIntro(GameScreen type) {
        super(type);
    }

    @Override
    public void create() {
        System.out.println("create intro screen");
        imgLogo = pixelImage(imgLogo, 400, 400);
        imgCover = pixelImage(imgCover, 1280, 720);
    }

    @Override
    public void update() {
        if (showLogo) {
            if (!fadeIn) {
                alpha = clamp(alpha + 2, 0, 230);
                timeWait--;
                if (alpha == 230 && timeWait == 0) {
                    fadeIn = true;
                    timeWait = 60;
                }
            } else {
                alpha = clamp(alpha - 2, 0, 230);
                if (alpha == 0) {
                    showLogo = false;
                    fadeIn = false;
                }
            }
            imgLogo = alphaImage(imgLogo, alpha);
        } else {
            if (!fadeIn) {
                alpha = clamp(alpha + 5, 0, 255);
                timeWait--;
                if (alpha == 255 && timeWait == 0) {
                    fadeIn = true;
                }
            } else {
                alpha = clamp(alpha - 5, 0, 255);
                if (alpha == 0) {
                    GSManager.getInstance().changeScreen(GameScreen.Menu);
                    screenChange.setOpen(true);
                    screenChange.setActive(true);
                }
            }
            imgCover = alphaImage(imgCover, alpha);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (showLogo) {
            g.drawImage(imgLogo, 300, 175, null);
        } else {
            if (alpha != 0) {
                g.drawImage(imgCover, -140, 0, null);
            }
        }
    }
}
