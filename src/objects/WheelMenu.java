package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import framework.GameObject;
import framework.MouseInput;
import framework.ObjectId;
import framework.PlayerData;
import framework.Texture;
import window.Animation;
import window.GameMain;

public class WheelMenu extends GameObject {

	private PlayerData playerData;
	private Texture tex = GameMain.getTexture();

	private boolean spinning = false;
	private double angle = 0;
	private double randomSpin;
	private double minSpinSpeed = 720, spinSpeed = minSpinSpeed, maxSpinSpeed = 1;
	private long spinCooldown = 1500, spinTimer = 0;
	
	private Animation wheelAnim;

	public WheelMenu(int x, int y, int width, int height, PlayerData playerData, ObjectId id) {
		super(x, y, width, height, id);
		this.playerData = playerData;
		
		wheelAnim = new Animation(20, tex.fortuneWheel[0], tex.fortuneWheel[1]);
	}

	@Override
	public void tick() {
		wheelAnim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(51, 51, 51));
		g.fillRect(0, 0, width, height);

		g.setColor(Color.white);
		int wheelSize = 3 * height / 5;
		int wheelX = x + width / 2 - wheelSize / 2;
		int wheelY = y + height / 2 - wheelSize / 2;
		Graphics2D g2d = (Graphics2D) g;

		final double PI = Math.PI;

		if (!spinning && MouseInput.leftPressed) {
			randomSpin = (Math.random() * (PI * 16)) + (PI * 16);
			angle %= 2 * PI;
			spinSpeed = minSpinSpeed;
			spinning = true;
			spinTimer = 0;
		}
		else if (spinning) {
			if (angle < randomSpin / 2) {
				spinSpeed--;
				if (spinSpeed < maxSpinSpeed)
					spinSpeed = maxSpinSpeed;
				angle += PI / spinSpeed;
			}
			else if (angle < randomSpin) {
				if (spinSpeed >= minSpinSpeed) {
					if (spinTimer < spinCooldown) 
						spinTimer++;
					else
						spinning = false;
				}

				spinSpeed++;
				angle += PI / spinSpeed;
			}
			else  
				spinning = false;
		}
		int reward = (int) ((angle % (2 * PI)) / (PI / 4));
		reward = 8 - reward;

		g2d.rotate(angle, wheelX + wheelSize / 2, wheelY + wheelSize / 2);	
		wheelAnim.drawAnimation(g, wheelX, wheelY, wheelSize, wheelSize);
		g2d.rotate(-angle, wheelX + wheelSize / 2, wheelY + wheelSize / 2);

		int pointerWidth = wheelSize / 5;
		int pointerHeight = pointerWidth * 2;
		g.drawImage(tex.fortuneWheel[2], wheelX + 2 * wheelSize / 5, wheelY - pointerHeight / 2, pointerWidth, pointerHeight, null);
		
		g.setColor(Color.white);
		g.drawString("angle = " + angle, 150, height / 2);
		g.drawString("randomSpin = " + randomSpin, 150, height / 2 + 30);
		g.setColor(Color.cyan);
		g.drawString("reward = " + reward, 150, height / 2 + 60);
		g.setColor(Color.white);
		g.drawString("spin speed = " + spinSpeed, 150, height / 2 + 90);
		g.drawString("PI / speed = " + (PI / spinSpeed), 150, height / 2 + 120);
		g.drawString("min speed = " + minSpinSpeed, 150, height / 2 + 150);
		g.drawString("timer = " + spinTimer + "/" + spinCooldown, 150, height / 2 + 180);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
