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

public class DustEffect extends GameObject {

	private Camera cam;
	private Handler handler;
	private Texture tex = GameMain.getTexture();
	
	private Animation dustAnim;
	
	public DustEffect(int x, int y, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.cam = cam;
		this.handler = handler;
		
		width = height = (int) Math.ceil((double) (GameMain.WIDTH) / GameMain.TILE_COUNT_X);
		dustAnim = new Animation(4, tex.dustEffect[0], tex.dustEffect[1], tex.dustEffect[2], tex.dustEffect[3], tex.dustEffect[4]);
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
		if (dustAnim.getPlayedOnce())
			handler.removeObject(this); 
		
		dustAnim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		dustAnim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
