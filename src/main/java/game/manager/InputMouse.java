package game.manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static game.manager.Global.*;

/**
 * @author NGUYEN TIEN HUNG
 */
public class InputMouse extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        MOUSE_CLICK = true;
        MOUSE_X = e.getX();
        MOUSE_Y = e.getY();
        System.out.println("click");
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        MOUSE_PRESS = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MOUSE_CLICK = false;
        MOUSE_PRESS = false;
        MOUSE_X = e.getX();
        MOUSE_Y = e.getY();
    }
    
     @Override
    public void mouseMoved(MouseEvent e) {
        MOUSE_CLICK = false;
        MOUSE_X = e.getX();
        MOUSE_Y = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        MOUSE_PRESS = false;
        MOUSE_DRAG = true;
        MOUSE_X = e.getX();
        MOUSE_Y = e.getY();
    }
}
