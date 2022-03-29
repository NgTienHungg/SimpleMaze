package game.manager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import game.enums.Key;
import static game.manager.Global.KEY;

/**
 * @author NGUYEN TIEN HUNG
 */
public class InputKey extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
        char character = e.getKeyChar();
        switch (character) {
            case 'w' ->
                KEY[Key.Up.getID()] = true;
            case 's' ->
                KEY[Key.Down.getID()] = true;
            case 'a' ->
                KEY[Key.Left.getID()] = true;
            case 'd' ->
                KEY[Key.Right.getID()] = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP ->
                KEY[Key.Up.getID()] = true;
            case KeyEvent.VK_DOWN ->
                KEY[Key.Down.getID()] = true;
            case KeyEvent.VK_LEFT ->
                KEY[Key.Left.getID()] = true;
            case KeyEvent.VK_RIGHT ->
                KEY[Key.Right.getID()] = true;
            case KeyEvent.VK_SPACE ->
                KEY[Key.Space.getID()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP ->
                KEY[Key.Up.getID()] = false;
            case KeyEvent.VK_DOWN ->
                KEY[Key.Down.getID()] = false;
            case KeyEvent.VK_LEFT ->
                KEY[Key.Left.getID()] = false;
            case KeyEvent.VK_RIGHT ->
                KEY[Key.Right.getID()] = false;
            case KeyEvent.VK_SPACE ->
                KEY[Key.Space.getID()] = false;

            case KeyEvent.VK_W ->
                KEY[Key.Up.getID()] = false;
            case KeyEvent.VK_S ->
                KEY[Key.Down.getID()] = false;
            case KeyEvent.VK_A ->
                KEY[Key.Left.getID()] = false;
            case KeyEvent.VK_D ->
                KEY[Key.Right.getID()] = false;
        }
    }
}
