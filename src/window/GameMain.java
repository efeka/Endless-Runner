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
import objects.HUD;
import objects.MainMenu;
import objects.Player;
import objects.WheelMenu;

/*
 * ***TODO***: Calculate player gravity based on screen dimensions
 * TODO: Complete the HUD (*health, *ammo, show coins collected, gems collected, distance as a score (player can get money based on distance at the end of a run) 
 * TODO: Add jumping pads that bounces you higher than your jump
 * TODO: Add spikes
 * TODO: Add power ups (both in game and pre game)
 * TODO: Parallax background 
 * TODO: Add different weapons bought with coins
 * TODO: Add different skins for the player bought with gems
 * TODO: Add a score and a high score system
 * TODO: Add menus
 * 
 * IDEAS:
 * 1) Challenge Modes: No shooting
 * 2) Small gambling mini-games such as slots, spinning wheels
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
	
	private MainMenu mainMenu;
	private WheelMenu wheelMenu;
	
	public static final int WIDTH = 1024, HEIGHT = 512;
	public static final int TILE_COUNT_X = 32, TILE_COUNT_Y = 16;
	
	private int nextSectionCooldown, nextSectionTimer;
	
	public GameMain() {
		requestFocus();
		tex = new Texture();
		new Window(WIDTH, HEIGHT, "Chicken Runner", this);

		handler = new Handler();
		cam = new Camera();
		mapSections = new MapSections(handler, cam);
		PlayerData playerData = new PlayerData();
		FileIO fileIO = new FileIO(null, mapSections, playerData);
		fileIO.readSegments();
		fileIO.readPlayerData();
		mouse = new MouseInput();
		keyboard = new KeyInput();
		
		int tileWidth = WIDTH / TILE_COUNT_X;
		int tileHeight = HEIGHT / TILE_COUNT_Y;
		
		handler.addObject(new HUD(15, 15, playerData, cam, ObjectId.HUD), Handler.TOP_LAYER);
		
		Player player = new Player(TILE_COUNT_X * 5, tileHeight * 7, (int) Math.ceil(5 * tileWidth / 4.0), tileHeight * 2, playerData, cam, handler, ObjectId.Player);
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
		
		mainMenu = new MainMenu(0, 0, WIDTH, HEIGHT, ObjectId.Menu);
		wheelMenu = new WheelMenu(0, 0, WIDTH, HEIGHT, playerData, ObjectId.Menu);
		
		start();
	}
	
	public static enum STATE{
		MAIN_MENU,
		WHEEL_MENU,
		GAME
	};
	public static STATE state = STATE.WHEEL_MENU;

	private void tick() {
		try {
			switch (state) {
			case GAME:
				handler.tick();
				cam.tick();
				break;
			case MAIN_MENU:
				mainMenu.tick();
				break;
			case WHEEL_MENU:
				wheelMenu.tick();
				break;
			}
		} catch(Exception ignored) {
			ignored.printStackTrace();
		}
		
		if (state == STATE.GAME) {
			if (nextSectionTimer < nextSectionCooldown)
				nextSectionTimer++;
			else {
				nextSectionTimer = 0;
				mapSections.queueRandomSection();
			}
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
			switch (state) {
			case GAME:
				handler.render(g);
				break;
			case MAIN_MENU:
				mainMenu.render(g);
				break;
			case WHEEL_MENU:
				wheelMenu.render(g);
				break;
			}
		} catch(Exception ignored) {
			ignored.printStackTrace();
		}
		
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
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Texture getTexture() {
		return tex;
	}

	public static void main(String[] args) {
		new GameMain();	
	}

}
