package level_designer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;

import framework.FileIO;
import framework.Texture;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {

	private Thread thread;
	public static boolean running = false;

	private Mouse mouse;
	private static Texture tex;
	private Grid grid;
	
	public static int width, height, cellSize;
	
	public Main(int rows, int cols, int cellSize, int offset) {
		width = offset * 2 + cols * cellSize;
		height = offset * 2 + rows * cellSize;
		
		requestFocus();
		tex = new Texture();
		grid = new Grid(rows, cols, cellSize, offset);
		LevelDesignWindow ldw = new LevelDesignWindow(this, width, height, "Level Designer");
		FileIO fileIO = new FileIO(grid, null, null);
		new ControlsWindow(ldw.getX() + ldw.getWidth() + 1, ldw.getY() + (ldw.getHeight() - 300) / 2, 300, 300, tex, fileIO);
		
		mouse = new Mouse(grid);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	private void tick() {
		grid.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//---draw begin---
		
		//background
		g.setColor(new Color(37, 35, 72));
		g.fillRect(0, 0, width, height);
		
		//cells
		grid.render(g);

		//---draw end---	
		g.dispose();
		bs.show();
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			render();

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(InterruptedException e) {}
	}

	public static Texture getTexture() {
		return tex;
	}

	public static void main(String[] args) {
		new Main(16, 32, 24, 24);	
	}

}
