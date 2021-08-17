package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Camera;
import window.GameMain;
import window.Handler;

public class GroundTile extends GameObject {

	public static enum TYPE {
		Grass,
		GrassX8,
		LeftGrass,
		RightGrass,
		Wall,
		WallX8,
		LeftWall,
		RightWall
	}
	private TYPE type;

	private Handler handler;
	private Camera cam;
	private Texture tex = GameMain.getTexture();
	
	public GroundTile(int x, int y, TYPE type, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.type = type;
		this.handler = handler;
		this.cam = cam;

		switch(type) {
		case Grass:
		case LeftGrass:
		case RightGrass:
		case Wall:
		case LeftWall:
		case RightWall:
			width = height = GameMain.WIDTH / GameMain.TILE_COUNT_X;
			break;
		case GrassX8:
		case WallX8:
			height = GameMain.WIDTH / GameMain.TILE_COUNT_X;
			width = 8 * height;
			break;
		}
	}

	@Override
	public void tick() {
		if (x < (int) -cam.getX() - width)
			handler.removeObject(this);
	}

	@Override
	public void render(Graphics g) {
		switch(type) {
		case Grass:
			g.drawImage(tex.groundTiles[0], x, y, width, height, null);
			break;
		case GrassX8:
			g.drawImage(tex.groundTiles[6], x, y, width, height, null);
			break;
		case LeftGrass:
			g.drawImage(tex.groundTiles[1], x, y, width, height, null);
			break;
		case RightGrass:
			g.drawImage(tex.groundTiles[2], x, y, width, height, null);
			break;
		case Wall:
			g.drawImage(tex.groundTiles[3], x, y, width, height, null);
			break;
		case WallX8:
			g.drawImage(tex.groundTiles[7], x, y, width, height, null);
			break;
		case LeftWall:
			g.drawImage(tex.groundTiles[4], x, y, width, height, null);
			break;
		case RightWall:
			g.drawImage(tex.groundTiles[5], x, y, width, height, null);
			break;
		default:
			g.setColor(Color.gray);
			g.drawRect(x, y, width, height);
			break;
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public TYPE getType() {
		return type;
	}

}
