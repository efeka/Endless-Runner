package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.GameMain;
import window.Handler;

public class BasicBullet extends GameObject {

	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	private Animation bulletAnim;
	
	public BasicBullet(int x, int y, int width, int height, Camera cam, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.handler = handler;
		this.cam = cam;
		
		velX = 12;
		bulletAnim = new Animation(4, tex.basicBullet[0], tex.basicBullet[1], tex.basicBullet[2], tex.basicBullet[3], tex.basicBullet[2], tex.basicBullet[1]);
	}

	@Override
	public void tick() {
		x += velX;
		if (x > -cam.getX() + 3 * GameMain.WIDTH)
			handler.removeObject(this);
		
		collision();
		bulletAnim.runAnimation();
	}
	
	private void collision() {
		for (int i = 0; i < handler.layerMiddle.size(); i++) {
			GameObject tempObject = handler.layerMiddle.get(i);
			if (tempObject.getId() == ObjectId.GroundTile) {
				if (getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
					//TODO: play animation
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		bulletAnim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
}
