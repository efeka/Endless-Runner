package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.PlayerData;
import framework.Texture;
import window.Camera;
import window.GameMain;

public class HUD extends GameObject {

	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	private PlayerData playerData;
	
	public HUD(int x, int y, PlayerData playerData, Camera cam, ObjectId id) {
		super(x, y, id);
		this.playerData = playerData;
		this.cam = cam;
	}

	@Override
	public void tick() {
		x += -cam.getVelX();
	}

	@Override
	public void render(Graphics g) {
		int tempX = 0;
		int tileWidth = GameMain.WIDTH / GameMain.TILE_COUNT_X;
		for (int i = 0; i < playerData.getHearts(); i++) {
			g.drawImage(tex.heart[0], x + i * tileWidth, y, tileWidth, tileWidth, null);
			tempX += tileWidth;
		}

		if (playerData.getMaxHearts() - playerData.getHearts() > 0) 
			for (int i = 0; i < playerData.getMaxHearts() - playerData.getHearts(); i++) 
				g.drawImage(tex.heart[1], tempX + x + i * tileWidth, y, tileWidth, tileWidth, null);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
