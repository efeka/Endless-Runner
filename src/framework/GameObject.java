package framework;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject  {
	
	protected int x, y;
	protected float velX, velY;
	protected int width, height;
	protected ObjectId id;
	
	public GameObject(int x, int y, ObjectId id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public GameObject(int x, int y, int width, int height, ObjectId id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public ObjectId getId() {
		return id;
	}
	
	public void setX(int x) { 
		this.x = x; 
	}
	
	public void setY(int y) { 
		this.y = y; 
	}
	
	public int getX() { 
		return x; 
	}
	
	public int getY() { 
		return y; 
	}
	
	public float getVelX() {
		return velX;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}