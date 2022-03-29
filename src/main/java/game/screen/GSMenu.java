package game.screen;

import java.awt.Graphics2D;
import game.enums.GameScreen;
import static game.manager.Global.*;
import static game.image.ImgManager.*;
import static game.manager.Game.screenChange;
import java.awt.Color;
import java.awt.Font;

/**
 * @author NGUYEN TIEN HUNG
 */
public class GSMenu extends MyGScreen {

    private MyGScreen nextScreen = null;
    private boolean showAbout = false;
    private boolean showTutorial = false;

    public GSMenu(GameScreen type) {
        super(type);
    }

    @Override
    public void create() {
        imgBG = pixelImage(imgBG, 1280, 720);
    }

    @Override
    public void update() {
        if (nextScreen != null) {
            if (!screenChange.isActive()) {
                // sau khi dừng close screen effect
                // chuyển sang screen play
                // kích hoạt open screen effect
                GSManager.getInstance().changeScreen(nextScreen);
                screenChange.setActive(true);
                nextScreen = null;
            }
            return;
        }

        if (MOUSE_CLICK) {
            MOUSE_CLICK = false;

            // exit show mode
            if (showAbout || showTutorial) {
                showAbout = false;
                showTutorial = false;
                return;
            }

            // check button click
            if (inRange(MOUSE_X, 360, 360 + 245) && inRange(MOUSE_Y, 360, 360 + 70)) {
                // play
                nextScreen = new GSPlay(GameScreen.Play);
                screenChange.setOpen(false);
                screenChange.setActive(true);
            } else if (inRange(MOUSE_X, 390, 360 + 160) && inRange(MOUSE_Y, 480, 480 + 50)) {
                // about
                showAbout = true;
            } else if (inRange(MOUSE_X, 355, 355 + 245) && inRange(MOUSE_Y, 580, 580 + 50)) {
                // tutorial
                showTutorial = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        // background
        g.drawImage(imgBG, 0, 0, null);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 80));
        g.setColor(new Color(102, 153, 153));
        g.drawString("Simple Maze", 220, 100);

//        check box
//        g.setColor(Color.white);
//        g.drawRect(360, 360, 245, 70);
//        g.drawRect(390, 480, 160, 50);
//        g.drawRect(355, 580, 245, 50);
        if (showAbout) {
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
            g.setColor(new Color(0, 255, 191));
            g.drawString("@infor: PROGAPP's product", 230, 380);
            g.drawString("@released: March 2022", 230, 460);
            g.drawString("@author: NgTienHungg", 230, 540);
            g.drawString("@club: ProPTIT", 230, 630);
            return;
        }
        if (showTutorial) {
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
            g.setColor(new Color(0, 255, 191));
            g.drawString("Use A-W-S-D keys to move", 230, 380);
            g.drawString("Use arrow keys to move", 230, 460);
            g.drawString("Start and get coin to win", 230, 540);
            g.drawString("Levels are unlimited", 230, 620);
            return;
        }

        // play
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
        if (inRange(MOUSE_X, 360, 360 + 245) && inRange(MOUSE_Y, 360, 360 + 70)) {
            g.setColor(Color.yellow);
            g.drawString("PLAY", 360, 425);
        } else {
            g.setColor(Color.white);
            g.drawString("PLAY", 360, 425);
        }

        // about
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
        if (inRange(MOUSE_X, 390, 360 + 160) && inRange(MOUSE_Y, 480, 480 + 50)) {
            g.setColor(Color.yellow);
            g.drawString("about", 395, 520);
        } else {
            g.setColor(Color.white);
            g.drawString("about", 395, 520);
        }

        // tutorial
        if (inRange(MOUSE_X, 355, 355 + 245) && inRange(MOUSE_Y, 580, 580 + 50)) {
            g.setColor(Color.yellow);
            g.drawString("tutorial", 355, 620);
        } else {
            g.setColor(Color.white);
            g.drawString("tutorial", 355, 620);
        }
    }
}
