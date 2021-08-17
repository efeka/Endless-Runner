package level_designer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import framework.Texture;

public class Cell {

	public static final int EMPTY = 0;
	public static final int GRASS = 1;
	public static final int GRASSX8 = 2;
	public static final int LEFT_GRASS = 3;
	public static final int RIGHT_GRASS = 4;
	public static final int WALL = 5;
	public static final int WALLX8 = 6;
	public static final int LEFT_WALL = 7;
	public static final int RIGHT_WALL = 8;
	public static final int COIN = 9;
	public static final int JUMP_THROUGH = 10;

	public BufferedImage image;
	public Rectangle rect;
	public int type, cellSize;

	public int cellSpanX, cellSpanY;
	
	private Texture tex;
	
	public Cell(int x, int y, int cellSize, int type, Texture tex) {
		this.type = type;
		this.tex = tex;
		this.cellSize = cellSize;
		
		rect = new Rectangle(x, y, cellSize, cellSize);
		fetchImage(type);
	}

	public void changeType(int type) {
		this.type = type;
		fetchImage(type);
	}

	private void fetchImage(int type) {
		switch (type) {
		case 0:
			cellSpanX = 1;
			cellSpanY = 1;
			image = null;
			break;
		case 1: //Grass
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[0];
			break;
		case 2: //Grass x8
			cellSpanX = 8;
			cellSpanY = 1;
			image = tex.groundTiles[6];
			break;
		case 3: //Left Grass
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[1];
			break;
		case 4: //Right Grass
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[2];
			break;
		case 5: //Wall
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[3];
			break;
		case 6: //Wall x8
			cellSpanX = 8;
			cellSpanY = 1;
			image = tex.groundTiles[7];
			break;
		case 7: //Left Wall
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[4];
			break;
		case 8: //Right Wall
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[5];
			break;
		case 9: //Coin
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.coin[3];
			break;
		case 10: //Jump Through Tile
			cellSpanX = 1;
			cellSpanY = 1;
			image = tex.groundTiles[4];
			break;
		default:
			cellSpanX = 1;
			cellSpanY = 1;
			image = null;
		}
	}
}