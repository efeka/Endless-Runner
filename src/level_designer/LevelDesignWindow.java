package level_designer;

import java.awt.Dimension;

import javax.swing.JFrame;

public class LevelDesignWindow {
	
	private JFrame frame;
	
	public LevelDesignWindow(Main main, int width, int height, String title) {
		main.setPreferredSize(new Dimension(width, height));
		main.setMaximumSize(new Dimension(width, height));
		main.setMinimumSize(new Dimension(width, height));
		
		frame = new JFrame(title);
		frame.add(main);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);

		main.start();
	}
	
	public int getX() {
		return frame.getX();
	}
	
	public int getY() {
		return frame.getY();
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
}
