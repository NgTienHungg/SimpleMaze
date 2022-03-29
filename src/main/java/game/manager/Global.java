package game.manager;

/**
 * @author NGUYEN TIEN HUNG
 */
public class Global {

    public static boolean[] KEY = {false, false, false, false, false, false};
    public static boolean MOUSE_CLICK = false;
    public static boolean MOUSE_PRESS = false;
    public static boolean MOUSE_DRAG = false;
    public static int MOUSE_X, MOUSE_Y;

    public static int random(int n) {
        return (int) ((Math.random() * 100 + 1) % n);
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static boolean inRange(int value, int min, int max) {
        return (value >= min && value <= max);
    }
}
