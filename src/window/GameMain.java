package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import framework.FileIO;
import framework.KeyInput;
import framework.MapSections;
import framework.MouseInput;
import framework.ObjectId;
import framework.PlayerData;
import framework.Texture;
import objects.GroundTile;
import objects.Player;

/*
 * TODO: Calculate player gravity based on screen dimensions
 * TODO: Add player health
 * TODO: Add jumping pads that bounces you higher than your jump
 * TODO: Add spikes
 * TODO: Add enemies
 * TODO: Add ammo mechanic
 * TODO: Player damage / jump animation
 * TODO: Add power ups (both in game and pre game)
 * TODO: Parallax background 
 * TODO: Add different weapons bought with coins
 * TODO: Add different skins for the player bought with gems
 * TODO: Add a score and a high score system
 * TODO: Add menus
 */
@SuppressWarnings("serial")
public class GameMain extends Canvas implements Runnable {

	private Thread thread;
	public static boolean running = false;

	private MouseInput mouse;
	private KeyInput keyboard;
	
	private MapSections mapSections;
	private Handler handler;
	private static Texture tex;
	private Camera cam;
	
	public static final int WIDTH = 1024, HEIGHT = 512;
	public static final int TILE_COUNT_X = 32, TILE_COUNT_Y = 16;
	
	private int nextSectionCooldown, nextSectionTimer;
	
	public GameMain() {
		requestFocus();
		tex = new Texture();
		new Window(WIDTH, HEIGHT, "Endless Runner", this);

		handler = new Handler();
		cam = new Camera();
		mapSections = new MapSections(handler, cam);
		FileIO fileIO = new FileIO(null, mapSections);
		fileIO.readSegments();
		mouse = new MouseInput();
		keyboard = new KeyInput();
		
		PlayerData playerData = new PlayerData();
		
		int tileWidth = WIDTH / TILE_COUNT_X;
		int tileHeight = HEIGHT / TILE_COUNT_Y;
		Player player = new Player(TILE_COUNT_X * 5, tileHeight * 7, 5 * tileWidth / 4, tileHeight * 2, playerData, cam, handler, ObjectId.Player);
		handler.setPlayer(player);
		handler.addObject(player, Handler.MIDDLE_LAYER);
		
		nextSectionCooldown = WIDTH / (int) player.getVelX() - 4;
		nextSectionTimer = nextSectionCooldown;
		
		//initial ground
		for (int i = 0; i < TILE_COUNT_X / 8; i++) {
			handler.addObject(new GroundTile(i * 8 * WIDTH / TILE_COUNT_X + (int) player.getVelX(), HEIGHT - 3 * HEIGHT / TILE_COUNT_Y, GroundTile.TYPE.GrassX8, cam, handler, ObjectId.GroundTile), Handler.MIDDLE_LAYER);
			handler.addObject(new GroundTile(i * 8 * WIDTH / TILE_COUNT_X + (int) player.getVelX(), HEIGHT - 2 * HEIGHT / TILE_COUNT_Y, GroundTile.TYPE.WallX8, cam, handler, ObjectId.GroundTile), Handler.MIDDLE_LAYER);
			handler.addObject(new GroundTile(i * 8 * WIDTH / TILE_COUNT_X + (int) player.getVelX(), HEIGHT - HEIGHT / TILE_COUNT_Y, GroundTile.TYPE.WallX8, cam, handler, ObjectId.GroundTile), Handler.MIDDLE_LAYER);
		}

		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addKeyListener(keyboard);
		
		start();
	}
	
	public static enum STATE{
		MAIN_MENU,
		GAME
	};
	public static STATE state = STATE.GAME;

	private void tick() {
		try {
			handler.tick();
			cam.tick();
		} catch(Exception ignored) {
			ignored.printStackTrace();
		}
		
		if (nextSectionTimer < nextSectionCooldown)
			nextSectionTimer++;
		else {
			nextSectionTimer = 0;
			mapSections.queueRandomSection();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//---draw begin---

		//background
		g.setColor(new Color(180, 177, 181));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.translate(cam.getX(), cam.getY());
		
		//objects
		try {
			handler.render(g);
		} catch(Exception ignored) {}
		
		g2d.translate(cam.getX(), -cam.getY());
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
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				if (state == STATE.GAME)
					tick();
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
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
		new GameMain();	
	}

}
