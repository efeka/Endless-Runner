package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.KeyInput;
import framework.MouseInput;
import framework.ObjectId;
import framework.PlayerData;
import framework.Texture;
import window.Animation;
import window.Camera;
import window.GameMain;
import window.Handler;

public class Player extends GameObject {

	private Handler handler;
	private Texture tex = GameMain.getTexture();
	private Camera cam;
	private PlayerData playerData;

	private float gravity = 0.5f;
	private final int LIMIT_SPEED = 15;

	private boolean falling = true, jumping = false;
	private int jumpCooldown = 15, jumpTimer = jumpCooldown;
	private int shootCooldown = 15, shootTimer = shootCooldown;

	private int initialX, initialY;
	private float catchUpVelX;
	private int jumpVelY;
	
	private int invulnerableTimer = 0, invulnerableCooldown = 120;
	private boolean invulnerable = false;

	private Animation runningAnim;
	private Animation damagedRunAnim, damagedJumpAnim;
	private Animation gun1RunningAnim;

	public Player(int x, int y, int width, int height, PlayerData playerData, Camera cam, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.cam = cam;
		this.handler = handler;
		this.playerData = playerData;

		initialX = x;
		initialY = y;
		velX = -cam.getVelX();
		catchUpVelX = velX + 1;
		jumpVelY = -1 * (int) Math.ceil((GameMain.HEIGHT / GameMain.TILE_COUNT_Y) / 3.2);

		runningAnim = new Animation(4, tex.player[0], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[7]);
		damagedRunAnim = new Animation(4, tex.playerDamaged[0], tex.playerDamaged[1], tex.playerDamaged[2], tex.playerDamaged[3], tex.playerDamaged[4], tex.playerDamaged[5], tex.playerDamaged[6], tex.playerDamaged[7]);
		damagedJumpAnim = new Animation(4, tex.playerDamaged[8], tex.player[8]);
		gun1RunningAnim = new Animation(4, tex.gun1[0], tex.gun1[1], tex.gun1[2], tex.gun1[3], tex.gun1[4], tex.gun1[5], tex.gun1[6], tex.gun1[7]);
	}

	@Override
	public void tick() {
		//shooting
		if (shootTimer < shootCooldown)
			shootTimer++;
		else if (playerData.getAmmo() > 0 && MouseInput.leftPressed) {
			playerData.setAmmo(playerData.getAmmo() - 1);
			shootTimer = 0;
			handler.addObject(new BasicBullet(x + 3 * width / 4, y + height / 2 - 2, 12, 12, cam, handler, ObjectId.BasicBullet), Handler.MIDDLE_LAYER);
		}

		//respawning
		if (x < -cam.getX() - width || y > GameMain.HEIGHT) {
			if (!invulnerable) {
				takeDamage();
			}
			
			x = (int) -cam.getX() + initialX;
			y = initialY;
			jumping = true;
			velY = 0;
		}
		
		//invulnerable
		if (invulnerable && invulnerableTimer < invulnerableCooldown)
			invulnerableTimer++;
		else 
			invulnerable = false;

		//jumping
		if (jumpTimer < jumpCooldown)
			jumpTimer++;
		else if (KeyInput.pressedSpace && !jumping) {
			handler.addObject(new DustEffect(x + width / 2, y + height / 2, cam, handler, ObjectId.DustEffect), Handler.TOP_LAYER);
			jumpTimer = 0;
			jumping = true;
			velY = jumpVelY;
		}

		//if the user gets stuck on an obstacle or somehow gets ahead of
		//the initial x coordinate, the player character tries to go back to it's original position
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
		damagedRunAnim.runAnimation();
		damagedJumpAnim.runAnimation();
		gun1RunningAnim.runAnimation();
	}
	
	private void makeInvulnerable() {
		invulnerableTimer = 0;
		invulnerable = true;
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
			if (tempObject.getId() == ObjectId.JumpThroughTile) {
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
			if (tempObject.getId() == ObjectId.Coin || tempObject.getId() == ObjectId.Gem) {
				if (getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					if (tempObject.getId() == ObjectId.Coin) {
						playerData.setCoins(playerData.getCoins() + 1);
						handler.addObject(new PickUpEffect(tempObject.getX(), tempObject.getY(), cam, handler, ObjectId.Coin), Handler.TOP_LAYER);
					}
					else if (tempObject.getId() == ObjectId.Gem) {
						playerData.setGems(playerData.getGems() + 1);
						handler.addObject(new PickUpEffect(tempObject.getX(), tempObject.getY(), cam, handler, ObjectId.Gem), Handler.TOP_LAYER);
					}
				}
			}
			if (tempObject.getId() == ObjectId.BasicEnemy) {
				if (getBounds().intersects(tempObject.getBounds())) {
					takeDamage();
				}
			}
		}
		falling = !touchingFloor;
	}
	
	private void takeDamage() {
		if (!invulnerable)
			playerData.setHearts(playerData.getHearts() - 1);
		makeInvulnerable();
	}

	@Override
	public void render(Graphics g) {
		if (jumping) {
			if (invulnerable)
				damagedJumpAnim.drawAnimation(g, x, y, width, height);				
			else 
				g.drawImage(tex.player[8], x, y, width, height, null);
			
			g.drawImage(tex.gun1[2], x - width / 2, y, width * 2, height, null);
		}
		else {
			if (invulnerable) 
				damagedRunAnim.drawAnimation(g, x, y, width, height);
			else
				runningAnim.drawAnimation(g, x, y, width, height);
			
			gun1RunningAnim.drawAnimation(g, x - width / 2, y, width * 2, height);
		}

		//debug
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
