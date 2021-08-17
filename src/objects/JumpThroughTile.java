package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Camera;
import window.GameMain;
import window.Handler;

public class JumpThroughTile extends GameObject {

	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	public JumpThroughTile(int x, int y, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.cam = cam;
		this.handler = handler;
		
		width = GameMain.WIDTH / GameMain.TILE_COUNT_X;
		height = width / 3;
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);	
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(tex.groundTiles[4], x, y + 4, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y + (width - height), width, height);
	}
	
}
