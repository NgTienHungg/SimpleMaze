package game.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import game.enums.GameScreen;
import game.entity.Destination;
import game.entity.Maze;
import game.entity.Source;
import game.entity.Block;
import static game.entity.Maze.N;
import static game.entity.Maze.M;
import static game.image.ImgManager.*;
import static game.manager.Game.DELTA_TIME;
import static game.manager.Game.screenChange;
import static game.manager.Global.*;

/**
 * @author NGUYEN TIEN HUNG
 */
public class GSPlay extends MyGScreen {

    private Maze maze;
    private Source src;
    private Destination des;

    private static int O_X, O_Y; // Origin
    private static int O_R, O_C;
    private int[][] path; // đường đi

    private int level = 1;
    private int length = 0;
    private float time = 0f;
    private float timeRemain = 90f;
    private boolean exit = false;
    private boolean showNotify = false;

    private boolean endGame = false;
    private boolean victory = false;
    private boolean ntfCongra = false;
    private float timeCongra = 0f;

    public GSPlay(GameScreen type) {
        super(type);
        maze = new Maze();
        src = new Source();
        des = new Destination();
        path = new int[N][M];
        imgBG = pixelImage(imgBG, 1280, 720);
    }

    @Override
    public void create() {
        maze.create();
        src.create();
        des.create();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                path[i][j] = -1;
            }
        }
        O_R = src.getR();
        O_C = src.getC();
        O_X = src.getX();
        O_Y = src.getY();
        path[O_R][O_C] = 0;
        length = 0;
        time = 0f;
        timeRemain = 90f;
        showNotify = false;
        exit = false;
        endGame = false;
        victory = false;
        timeCongra = 0f;
        ntfCongra = false;
    }

    @Override
    public void update() {
        // effect exit
        if (exit && !screenChange.isActive()) {
            if (victory) {
                this.create();
            } else {
                GSManager.getInstance().popScreen();
            }
            screenChange.setActive(true);
        }

        // end game
        if (endGame) {
            time += DELTA_TIME;
            if (time >= 0.3f) {
                time -= 0.3f;
                showNotify = !showNotify;
            }
            timeCongra += DELTA_TIME;
            if (timeCongra >= 3.f) {
                ntfCongra = false;
            }
            if (!ntfCongra && KEY[5] == true) {
                if (victory) {
                    level++;
                }
                exit = true;
                screenChange.setOpen(false);
                screenChange.setActive(true);
            }
            return;
        }

        // exit click
        if (inRange(MOUSE_X, 855, 855 + 85) && inRange(MOUSE_Y, 24, 24 + 30)) {
            if (MOUSE_CLICK) {
                MOUSE_CLICK = false;
                exit = true;
                screenChange.setOpen(false);
                screenChange.setActive(true);
            }
        }

        maze.update();
        if (!maze.isGenerated()) {
            time += DELTA_TIME;
            if (time >= 0.3f) {
                time -= 0.3f;
                showNotify = !showNotify;
            }
            return;
        }

        // time play
        timeRemain -= DELTA_TIME;
        if (timeRemain <= 0) {
            endGame = true;
            victory = false;
            ntfCongra = true;
        }

        // handle key press event
        time += DELTA_TIME;
        if (time >= 0.1f) {
            time = 0f;
            updateKeyEvent();
        }

        // game object
        src.update();
        des.update();
        checkWin();
    }

    @Override
    public void render(Graphics2D g) {
        // background and maze
        g.drawImage(imgBG, 0, 0, null);
        maze.render(g);

        // generating
        if (!maze.isGenerated()) {
            // level
            g.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 35));
            g.setColor(new Color(0, 255, 191));
            g.drawString("Level:" + level, 45, 50);

            if (showNotify) {
                g.drawString("Generating...", 380, 50);
            }

            // exit button
            if (inRange(MOUSE_X, 855, 855 + 85) && inRange(MOUSE_Y, 24, 24 + 30)) {
                g.setColor(Color.red);
                g.drawString("Exit", 855, 50);
            } else {
                g.setColor(Color.white);
                g.drawString("Exit", 855, 50);
            }
            return;
        }

        // game object
        drawPath(g);
        g.setColor(new Color(255, 77, 148));
        g.fillOval(O_X + 8, O_Y + 8, 19, 19);
        des.render(g);
        src.render(g);

        // end game
        if (endGame) {
            if (showNotify) {
                g.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 35));
                if (ntfCongra) {
                    if (victory) {
                        g.setColor(new Color(0, 255, 191));
                        g.drawString("Congratulations!", 300, 50);
                    } else {
                        g.setColor(new Color(255, 0, 102));
                        g.drawString("You're too bad, start over!", 200, 50);
                    }
                } else {
                    if (victory) {
                        g.setColor(new Color(0, 255, 191));
                        g.drawString("Press SPACE to play next level!", 190, 50);
                    } else {
                        g.setColor(new Color(255, 0, 102));
                        g.drawString("Press SPACE to return menu!", 210, 50);
                    }
                }
            }
            return;
        }

        g.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 35));
        g.setColor(new Color(0, 255, 191));
        g.drawString("Level:" + level, 45, 50);

        // exit button
        if (inRange(MOUSE_X, 855, 855 + 85) && inRange(MOUSE_Y, 24, 24 + 30)) {
            g.setColor(Color.red);
            g.drawString("Exit", 855, 50);
        } else {
            g.setColor(Color.white);
            g.drawString("Exit", 855, 50);
        }

        // UI
        g.setColor(new Color(0, 255, 191));
        g.drawString("Length:" + length, 255, 50);
        g.drawString("Time remain:" + (int) (timeRemain + 1), 505, 50);
    }

    private void updateKeyEvent() {
        for (int i = 1; i <= 4; ++i) {
            if (KEY[i] == true) {
                switch (i) {
                    case 1 ->
                        moveUp();
                    case 2 ->
                        moveDown();
                    case 3 ->
                        moveLeft();
                    case 4 ->
                        moveRight();
                }
                return;
            }
        }
    }

    private void checkWin() {
        if (src.getX() == des.getX() && src.getY() == des.getY()) {
            src.setR(des.getR());
            src.setC(des.getC());
            System.out.println("win");
            endGame = true;
            victory = true;
            ntfCongra = true;
        }
    }

    private void drawPath(Graphics2D g) {
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};
        int or = O_R, oc = O_C;
        int ox = O_X, oy = O_Y;
        int _r, _c, _x, _y;

        g.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g.setColor(new Color(255, 77, 148));

        while (path[or][oc] < length) {
            for (int i = 0; i < 4; ++i) {
                // o là hiện tại
                // _ là đích đến
                _r = or + dr[i];
                _c = oc + dc[i];
                if (_r >= 0 && _r < N && _c >= 0 && _c < M && path[_r][_c] == path[or][oc] + 1) {
                    _x = Maze.X + _c * Block.SIZE;
                    _y = Maze.Y + _r * Block.SIZE;

                    // Nếu src nằm trong khoảng di chuyển thì chỉ vẽ line đến src là dừng
                    if (ox == _x && ox == src.getX()) {
                        if ((src.getY() >= oy && src.getY() <= _y) || (src.getY() <= oy && src.getY() >= _y)) {
                            g.drawLine(ox + 18, oy + 18, src.getX() + 18, src.getY() + 18);
                            return;
                        }
                    } else if (oy == _y && oy == src.getY()) {
                        if ((src.getX() >= ox && src.getX() <= _x) || (src.getX() <= ox && src.getX() >= _x)) {
                            g.drawLine(ox + 18, oy + 18, src.getX() + 18, src.getY() + 18);
                            return;
                        }
                    }

                    g.drawLine(ox + 18, oy + 18, _x + 18, _y + 18);
                    or = _r;
                    oc = _c;
                    ox = _x;
                    oy = _y;
                    break;
                }
            }
        }

        // Vẽ tiếp khi src đi ngược đường quay lại
        if ((src.getX() == ox && src.getY() != oy) || (src.getX() != ox && src.getY() == oy)) {
            g.drawLine(ox + 18, oy + 18, src.getX() + 18, src.getY() + 18);
        }
    }

    private void moveUp() {
        System.out.println("up");
        int r = src.getR();
        int c = src.getC();
        while (r > 0 && maze.getMatrix()[r][c].getWalls()[0] == false) {
            r--;
            if (path[r][c] == -1) {
                path[r][c] = path[r + 1][c] + 1;
            } else {
                path[r + 1][c] = -1;
            }
            length = path[r][c];
            boolean[] walls = maze.getMatrix()[r][c].getWalls();
            if (walls[1] == false || walls[3] == false) {
                break;
            }
        }
        src.setR(r);
    }

    private void moveDown() {
        System.out.println("down");
        int r = src.getR();
        int c = src.getC();
        while (r < N - 1 && maze.getMatrix()[r][c].getWalls()[2] == false) {
            r++;
            if (path[r][c] == -1) {
                path[r][c] = path[r - 1][c] + 1;
            } else {
                path[r - 1][c] = -1;
            }
            length = path[r][c];
            boolean[] walls = maze.getMatrix()[r][c].getWalls();
            if (walls[1] == false || walls[3] == false) {
                break;
            }
        }
        src.setR(r);
    }

    private void moveLeft() {
        System.out.println("left");
        int r = src.getR();
        int c = src.getC();
        while (c > 0 && maze.getMatrix()[r][c].getWalls()[3] == false) {
            c--;
            if (path[r][c] == -1) {
                path[r][c] = path[r][c + 1] + 1;
            } else {
                path[r][c + 1] = -1;
            }
            length = path[r][c];
            boolean[] walls = maze.getMatrix()[r][c].getWalls();
            if (walls[0] == false || walls[2] == false) {
                break;
            }
        }
        src.setC(c);
    }

    private void moveRight() {
        System.out.println("right");
        int r = src.getR();
        int c = src.getC();
        while (c < M - 1 && maze.getMatrix()[r][c].getWalls()[1] == false) {
            c++;
            if (path[r][c] == -1) {
                path[r][c] = path[r][c - 1] + 1;
            } else {
                path[r][c - 1] = -1;
            }
            length = path[r][c];
            boolean[] walls = maze.getMatrix()[r][c].getWalls();
            if (walls[0] == false || walls[2] == false) {
                break;
            }
        }
        src.setC(c);
    }
}
