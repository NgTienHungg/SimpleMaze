package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Maze extends MyObject {

    public static final int X = 2, Y = 80; // position of maze
    public static final int N = 18, M = 28;

    private Block[][] matrix;
    private Stack<Block> stack;
    private Block currentBlock, nextBlock;
    private boolean generated = false;

    public Maze() {
        super(X, Y, M * Block.SIZE, N * Block.SIZE);
        matrix = new Block[N][M];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                matrix[i][j] = new Block(i, j);
            }
        }
        stack = new Stack<>();
    }

    @Override
    public void create() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                matrix[i][j].create();
                addNeighbors(i, j, matrix[i][j].getNeighbors());
            }
        }
        currentBlock = matrix[0][0];
        currentBlock.setVisited(true);
        stack.push(currentBlock);
        generated = false;
    }

    @Override
    public void update() {
        if (!generated) {
            genMaze();
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                matrix[i][j].render(g);
            }
        }
        if (!generated) {
            g.setColor(new Color(255, 77, 136));
            g.fillRect(currentBlock.x + 2, currentBlock.y + 2, Block.SIZE - 4, Block.SIZE - 4);
        }
    }

    public boolean isGenerated() {
        return generated;
    }

    public Block[][] getMatrix() {
        return matrix;
    }

    private void addNeighbors(int i, int j, ArrayList<Block> list) {
        if (i - 1 >= 0) {
            list.add(matrix[i - 1][j]);
        }
        if (i + 1 < N) {
            list.add(matrix[i + 1][j]);
        }
        if (j - 1 >= 0) {
            list.add(matrix[i][j - 1]);
        }
        if (j + 1 < M) {
            list.add(matrix[i][j + 1]);
        }
    }

    private void removeWall() {
        int _x = currentBlock.getC() - nextBlock.getC();
        int _y = currentBlock.getR() - nextBlock.getR();
        if (_x == 1) {
            currentBlock.getWalls()[3] = false;
            nextBlock.getWalls()[1] = false;
        } else if (_x == -1) {
            currentBlock.getWalls()[1] = false;
            nextBlock.getWalls()[3] = false;
        }
        if (_y == 1) {
            currentBlock.getWalls()[0] = false;
            nextBlock.getWalls()[2] = false;
        } else if (_y == -1) {
            currentBlock.getWalls()[2] = false;
            nextBlock.getWalls()[0] = false;
        }
    }

    private void genMaze() {
        // DFS
        while (!stack.isEmpty()) {
            currentBlock = stack.pop();
            if (currentBlock.hasUnvisitedNeighbor()) {
                nextBlock = currentBlock.randomNeighbor();
                nextBlock.setVisited(true);
                removeWall();
                stack.push(currentBlock);
                stack.push(nextBlock);
                currentBlock = nextBlock;
            }
            return;
        }
        generated = true;
        System.out.println("maze done");
    }
}
