package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.KeyInput;
import framework.MouseInput;
import framework.ObjectId;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.GameMain;
import window.Handler;

public class Player extends GameObject {

	private Handler handler;
	private Texture tex = GameMain.getTexture();
	private Camera cam;

	private float gravity = 0.5f;
	private final int LIMIT_SPEED = 15;

	private boolean falling = true, jumping = false;
	private int jumpCooldown = 15, jumpTimer = jumpCooldown;
	private int shootCooldown = 15, shootTimer = shootCooldown;

	private int initialX, initialY;
	private float catchUpVelX;

	private Animation runningAnim;
	private Animation gun1RunningAnim;

	public Player(int x, int y, int width, int height, Camera cam, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		System.out.println(width + " " + height);
		this.cam = cam;
		this.handler = handler;
		initialX = x;
		initialY = y;
		velX = 4;
		catchUpVelX = velX + 1;
		runningAnim = new Animation(4, tex.player[0], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[7]);
		gun1RunningAnim = new Animation(4, tex.gun1[0], tex.gun1[1], tex.gun1[2], tex.gun1[0], tex.gun1[0], tex.gun1[1], tex.gun1[2], tex.gun1[0]);
	}
	
	@Override
	public void tick() {
		//shooting
		if (shootTimer < shootCooldown)
			shootTimer++;
		else if (MouseInput.leftPressed) {
			shootTimer = 0;
			handler.addObject(new BasicBullet(x + width - 35, y + height / 2 + 4, 12, 12, cam, handler, ObjectId.BasicBullet), Handler.MIDDLE_LAYER);
		}

		//respawning
		if (x < -cam.getX() - width || y > GameMain.HEIGHT) {
			x = (int) -cam.getX() + initialX;
			y = initialY;
			jumping = true;
			velY = 0;
		}

		//jumping
		if (jumpTimer < jumpCooldown)
			jumpTimer++;
		else if (KeyInput.pressedSpace && !jumping) {
			jumpTimer = 0;
			jumping = true;
			velY = -12;
		}

		//if the user gets stuck on an obstacle, the player tries to go back to its original position
		if (x + cam.getX() < initialX)
			velX = catchUpVelX;
		else if (x + cam.getX() > initialX)
			velX = catchUpVelX - 2;
		else 
			velX = catchUpVelX - 1;

		x += velX;
		y += velY;

		if (falling || jumping) {
			if (velY > 0)
				velY += gravity + 0.4;
			else
				velY += gravity;

			if (velY > LIMIT_SPEED)
				velY = LIMIT_SPEED;
		}

		collision();

		runningAnim.runAnimation();
		gun1RunningAnim.runAnimation();
	}

	private void collision() {
		boolean touchingFloor = false;
		for (int i = 0; i < handler.layerMiddle.size(); i++) {
			GameObject tempObject = handler.layerMiddle.get(i);
			if (tempObject.getId() == ObjectId.GroundTile) {
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + tempObject.getHeight();
					velY = 0;
					falling = true;
				}
				if (getBoundsBottom().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					velY = 0;
					jumping = false;
					touchingFloor = true;
				}
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
				}
			}
			else if (tempObject.getId() == ObjectId.JumpThroughTile) {
				if (velY >= 0 && getBoundsBottom().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + tempObject.getWidth() - tempObject.getHeight() - height;
					velY = 0;
					jumping = false;
					touchingFloor = true;
				}
				else if (!jumping && getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
				}
			}
			else if (tempObject.getId() == ObjectId.Coin) {
				if (getBounds().intersects(tempObject.getBounds())) 
					handler.removeObject(tempObject);
			}
		}
		falling = !touchingFloor;
	}

	@Override
	public void render(Graphics g) {
		runningAnim.drawAnimation(g, x, y, width, height);
		//gun1RunningAnim.drawAnimation(g, x, y + 35);
		//g.drawImage(tex.player[0], x, y, width, height, null);
		//		g.setColor(Color.orange);
		//		g.fillRect(x, y, width, height);

		//for debugging
		/*
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.fill(getBoundsBottom());
		g2d.setColor(Color.blue);
		g2d.fill(getBoundsRight());
		g2d.setColor(Color.green);
		g2d.fill(getBoundsTop());
		 */
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	private Rectangle getBoundsBottom() {
		return new Rectangle(x, y + 7 * height / 8, 4 * width / 5 - width / 10, height / 8);
	}

	private Rectangle getBoundsRight() {
		return new Rectangle(x + 7 * width / 8, y + height / 8, width / 8, 8 * height / 10 - height / 8);
	}

	private Rectangle getBoundsTop() {
		return new Rectangle(x, y, 4 * width / 5 - width / 10, height / 8);
	}

}
