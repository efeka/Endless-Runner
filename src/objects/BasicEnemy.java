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

public class BasicEnemy extends GameObject {

	private Camera cam;
	private Handler handler;
	private Texture tex = GameMain.getTexture();

	private int health = 100;
	private boolean stabilized = false;
	
	private Animation idleAnim;

	public BasicEnemy(int x, int y, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.cam = cam;
		this.handler = handler;

		int tileWidth = GameMain.WIDTH / GameMain.TILE_COUNT_X;
		int tileHeight = GameMain.HEIGHT / GameMain.TILE_COUNT_Y;
		width = (int) Math.ceil(5 * tileWidth / 4.0);
		height = tileHeight * 2;

		velY = 3;
		
		idleAnim = new Animation(4, tex.basicEnemy[0], tex.basicEnemy[0], tex.basicEnemy[0], tex.basicEnemy[0], tex.basicEnemy[1], tex.basicEnemy[2], tex.basicEnemy[2], tex.basicEnemy[2], tex.basicEnemy[2], tex.basicEnemy[3], tex.basicEnemy[4], tex.basicEnemy[5], tex.basicEnemy[6], tex.basicEnemy[7]);
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width || y > GameMain.HEIGHT)
			handler.removeObject(this);

		if (!stabilized) {
			y += velY;
			collision();
		}
		
		idleAnim.runAnimation();
	}

	private void collision() {
		for (int i = 0; i < handler.layerMiddle.size(); i++) {
			GameObject tempObject = handler.layerMiddle.get(i);
			if (tempObject.getId() == ObjectId.GroundTile) {
				if (getBoundsBottom().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					stabilized = true;
				}
			}
			if (tempObject.getId() == ObjectId.JumpThroughTile) {
				if (getBoundsBottom().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + tempObject.getWidth() - tempObject.getHeight() - height;
					velY = 0;
					stabilized = true;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		idleAnim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	private Rectangle getBoundsBottom() {
		return new Rectangle(x, y + 3 * height / 4, width, height / 4);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0)
			handler.removeObject(this);
	}

}
