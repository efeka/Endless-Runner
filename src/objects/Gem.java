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

public class Gem extends GameObject {
	
	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	private Animation gemAnim;
	
	private int floatUpLimit, floatDownLimit;
	private float floatY;
	private boolean movingUp;
	
	public Gem(int x, int y, int width, int height, Camera cam, Handler handler, ObjectId id) {
		super(x, y, width, height, id);
		this.cam = cam;
		this.handler = handler;
		
		floatUpLimit = y - 5;
		floatDownLimit = y + 5;
		floatY = y;
		
		int randomInitialDirection = (int) (Math.random() * 2);
		movingUp = randomInitialDirection == 0;
		
		gemAnim = new Animation(5, tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[0], tex.gem[1], tex.gem[2], tex.gem[3], tex.gem[4]);
	}

	@Override
	public void tick() {
		if (movingUp) {
			velY = -0.3f;
			if (floatY <= floatUpLimit) {
				movingUp = false;
				floatY = floatUpLimit;
			}
		}
		else {
			velY = 0.3f;
			if (floatY >= floatDownLimit) {
				movingUp = true;
				floatY = floatDownLimit;
			}
		}
		floatY += velY;
		y = (int) floatY;
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
		
		gemAnim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		gemAnim.drawAnimation(g, x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}
