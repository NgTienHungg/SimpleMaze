package game.entity;

import java.awt.Graphics2D;
import static game.image.ImgManager.sprCoin;
import static game.manager.Global.random;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Destination extends MyObject {

    public static final int SIZE = 25;
    private Animation anim;

    public Destination() {
        super(0, 0, SIZE, SIZE, 0, 0);
    }

    @Override
    public void create() {
        c = random(Maze.M);
        r = (Maze.N - 1) - random(3);
        x = c * Block.SIZE + Maze.X;
        y = r * Block.SIZE + Maze.Y;
        anim = new Animation(this.x + 5, this.y + 5, this.w, this.h, sprCoin);
        anim.create();
    }

    @Override
    public void update() {
        anim.update();
    }

    @Override
    public void render(Graphics2D g) {
        anim.render(g);
    }
}
