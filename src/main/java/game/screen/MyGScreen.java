package game.screen;

import java.awt.Graphics2D;
import javax.swing.JPanel;
import game.enums.GameScreen;

/**
 * @author NGUYEN TIEN HUNG
 */
public abstract class MyGScreen extends JPanel {

    protected GameScreen type;

    protected MyGScreen(GameScreen type) {
        this.type = type;
    }

    public abstract void create();

    public abstract void update();

    public abstract void render(Graphics2D g);

    public GameScreen getType() {
        return type;
    }

    static MyGScreen createScreen(GameScreen type) {
        MyGScreen screen = null;
        switch (type) {
            case Intro:
                screen = new GSIntro(GameScreen.Intro);
                break;
            case Menu:
                screen = new GSMenu(GameScreen.Menu);
                break;
            case Play:
                screen = new GSPlay(GameScreen.Play);
                break;
        }
        return screen;
    }
}
