package level_designer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private Grid grid;
	private boolean leftPressed = false;

	public Mouse(Grid grid) {
		this.grid = grid;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		grid.handleMouse(e.getX(), e.getY(), leftPressed ? 1 : 0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		grid.handleMouse(e.getX(), e.getY(), e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
	}	
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
