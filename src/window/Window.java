package window;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import framework.Texture;

public class Window {
	
	private Texture tex = GameMain.getTexture();

	public Window(int w, int h, String title, GameMain game) {
		game.setPreferredSize(new Dimension(w, h));
		game.setMaximumSize(new Dimension(w, h));
		game.setMinimumSize(new Dimension(w, h));
		
		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

//		BufferedImage cursor = tex.cursors[0];
//		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank cursor");
//		frame.getContentPane().setCursor(blankCursor);
	}
}

