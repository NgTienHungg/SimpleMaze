package game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import static game.manager.Global.random;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Block extends MyObject {

    public static final int SIZE = 35;
    private boolean walls[];
    private boolean visited = false;
    private ArrayList<Block> neighbors;

    public Block(int r, int c) {
        super(Maze.X + c * SIZE, Maze.Y + r * SIZE, SIZE, SIZE, r, c);
        neighbors = new ArrayList<>();
    }

    @Override
    public void create() {
        walls = new boolean[]{true, true, true, true};
        visited = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g.setColor(new Color(0, 255, 191));
        if (walls[0] == true) {
            g.drawLine(x, y, x + SIZE, y); // top
        }
        if (walls[1] == true) {
            g.drawLine(x + SIZE, y, x + SIZE, y + SIZE); // right
        }
        if (walls[2] == true) {
            g.drawLine(x + SIZE, y + SIZE, x, y + SIZE); // bottom
        }
        if (walls[3] == true) {
            g.drawLine(x, y + SIZE, x, y); // left
        }
        if (!visited) {
            g.setColor(new Color(179, 255, 237));
            g.fillRect(x + 1, y + 1, w - 2, h - 2);
        }
    }

    public boolean[] getWalls() {
        return walls;
    }

    public ArrayList<Block> getNeighbors() {
        return neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean hasUnvisitedNeighbor() {
        ArrayList<Block> visitedNeighbors = new ArrayList<>();
        for (Block b : neighbors) {
            // Không remove trực tiếp Block khi đang for-each neighbors
            // Vì như vậy sẽ throw ConcurrentModificationException
            if (b.isVisited()) {
                visitedNeighbors.add(b);
            }
        }
        neighbors.removeAll(visitedNeighbors);
        return (!neighbors.isEmpty());
    }

    public Block randomNeighbor() {
        return neighbors.get(random(neighbors.size()));
    }
}
