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

public class PickUpEffect extends GameObject {

	private Camera cam;
	private Handler handler;
	private Texture tex = GameMain.getTexture();

	private Animation anim;

	public PickUpEffect(int x, int y, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.cam = cam;
		this.handler = handler;

		width = (int) Math.ceil((double) (GameMain.WIDTH) / GameMain.TILE_COUNT_X);
		height = (int) Math.ceil((double) (GameMain.HEIGHT) / GameMain.TILE_COUNT_Y);

		switch (id) {
		case Coin:
			anim = new Animation(4, tex.coinPickup[0], tex.coinPickup[1], tex.coinPickup[2], tex.coinPickup[3], tex.coinPickup[4]);
			break;
		case Gem:
			anim = new Animation(4, tex.gemPickup[0], tex.gemPickup[1], tex.gemPickup[2], tex.gemPickup[3], tex.gemPickup[4]);
			break;
		default:
			anim = null;
		}
	}

	@Override
	public void tick() {
		y -= 2;
		
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
		if (anim.getPlayedOnce())
			handler.removeObject(this); 

		anim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
