package game.manager;

import game.effect.ScreenChange;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import game.screen.GSManager;
import game.enums.GameScreen;
import game.image.ImgManager;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Game extends Canvas {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    public static final String TITLE = "Simple Maze";

    public static final int FPS = 120;
    public static final float DELTA_TIME = 1f / FPS;
    public static final float PRESS_TIME = 0.1f;

    private static Game instance;
    public static ScreenChange screenChange;
    private JFrame window;
    private ImgManager imgManager;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Game() {
        window = new JFrame(TITLE);
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(this);

        imgManager = new ImgManager();
        screenChange = new ScreenChange(true);
        screenChange.setActive(false);

        InputMouse mouseInput = new InputMouse();
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.addKeyListener(new InputKey());
        this.setFocusable(true);
    }

    private void create() {
        imgManager.loadImages();
        screenChange.create();
        GSManager.getInstance().changeScreen(GameScreen.Intro);
    }

    private void update() {
        if (GSManager.getInstance().needToChangeScreen()) {
            GSManager.getInstance().pushScreen();
        }
        screenChange.update();
        if (screenChange.isActive()) {
            return;
        }
        GSManager.getInstance().getCurrentScreen().update();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        GSManager.getInstance().getCurrentScreen().render(g);
        screenChange.render(g);
        g.dispose();
        bs.show();
    }

    public void run() {
        create();
        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                render();
                delta--;
            }
        }
    }

    public void add(JPanel screen) {
        window.add(screen);
    }
}
