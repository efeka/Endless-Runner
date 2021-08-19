package window;

public class Camera {

	private float x = 0, y = 0;

	public void tick() {
		x -= GameMain.WIDTH / 256;
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
}
