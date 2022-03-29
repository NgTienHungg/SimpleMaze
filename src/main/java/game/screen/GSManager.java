package game.screen;

import java.util.ArrayList;
import game.manager.Game;
import game.enums.GameScreen;

/**
 * @author NGUYEN TIEN HUNG
 */
public class GSManager {

    private static GSManager instance;
    private ArrayList<MyGScreen> listScreen;
    private MyGScreen nextScreen;

    private GSManager() {
        listScreen = new ArrayList<>();
    }

    public static GSManager getInstance() {
        if (instance == null) {
            instance = new GSManager();
        }
        return instance;
    }

    public MyGScreen getCurrentScreen() {
        if (listScreen.isEmpty()) {
            return null;
        }
        return listScreen.get(listScreen.size() - 1);
    }

    public MyGScreen getNextScreen() {
        return nextScreen;
    }

    public void changeScreen(MyGScreen screen) {
        nextScreen = screen;
    }

    public void changeScreen(GameScreen type) {
        MyGScreen screen = MyGScreen.createScreen(type);
        changeScreen(screen);
    }

    public void pushScreen() {
        listScreen.add(nextScreen);
        getCurrentScreen().create();
        Game.getInstance().add(GSManager.getInstance().getCurrentScreen());
        nextScreen = null;
    }

    public void popScreen() {
        if (!listScreen.isEmpty()) {
            listScreen.remove(listScreen.size() - 1);
        }
        Game.getInstance().add(GSManager.getInstance().getCurrentScreen());
    }

    public boolean needToChangeScreen() {
        return nextScreen != null;
    }
}
