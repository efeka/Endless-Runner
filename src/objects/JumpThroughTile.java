package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Camera;
import window.GameMain;
import window.Handler;

public class JumpThroughTile extends GameObject {
	
	public static enum TYPE {
		Left,
		Middle,
		Right
	}
	private TYPE type;
	
	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	public JumpThroughTile(int x, int y, TYPE type, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.cam = cam;
		this.handler = handler;
		this.type = type;
		
		width = GameMain.WIDTH / GameMain.TILE_COUNT_X;
		height = width / 2;
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
	}

	@Override
	public void render(Graphics g) {
		switch (type) {
		case Left:
			g.drawImage(tex.groundTiles[8], x, y, width, width, null);
			break;
		case Middle:
			g.drawImage(tex.groundTiles[9], x, y, width, width, null);
			break;
		case Right:
			g.drawImage(tex.groundTiles[10], x, y, width, width, null);
			break;
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y + (width - height), width, height);
	}
	
	public TYPE getType() {
		return type;
	}
	
}
