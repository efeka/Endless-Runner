package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.GameMain;
import window.Handler;

public class Coin extends GameObject {
	
	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	private Animation coinAnim;
	
	public Coin(int x, int y, int width, int height, Camera cam, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.cam = cam;
		this.handler = handler;
		
		coinAnim = new Animation(2, tex.coin[0], tex.coin[1], tex.coin[2], tex.coin[3], tex.coin[4], tex.coin[5]);
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
		
		coinAnim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		coinAnim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}
