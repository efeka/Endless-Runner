package window;

public class Camera {

	private float x = 0, y = 0;
	private float velX = -GameMain.WIDTH / 256;

	public void tick() {
		x += velX;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = (int) x;
	}

	public void setY(float y) {
		this.y = (int) y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

}
