package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import static game.manager.Global.random;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Source extends MyObject {

    public static final int SIZE = 25;
    private int velocity = 5;
    private boolean moving = false;

    public Source() {
        super(0, 0, SIZE, SIZE, 0, 0);
    }

    @Override
    public void create() {
        c = random(Maze.M);
        r = random(3);
        x = Maze.X + c * Block.SIZE;
        y = Maze.Y + r * Block.SIZE;
    }

    @Override
    public void update() {
        moving = false;
        int dx = x - (Maze.X + c * Block.SIZE);
        int dy = y - (Maze.Y + r * Block.SIZE);
        if (dx != 0) {
            moving = true;
            setX(x - velocity * dx / Math.abs(dx));
        }
        if (dy != 0) {
            moving = true;
            setY(y - velocity * dy / Math.abs(dy));
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(255, 0, 102));
        g.fillOval(x + 5, y + 5, w, h);
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
