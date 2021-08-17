package framework;

import java.awt.image.BufferedImage;

import window.BufferedImageLoader;
import window.GameMain;

public class Texture {

	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	
	public BufferedImage[] groundTiles = new BufferedImage[9];
	public BufferedImage[] coin = new BufferedImage[6];
	
	public BufferedImage[] player = new BufferedImage[8];
	public BufferedImage[] gun1 = new BufferedImage[3];
	public BufferedImage[] basicBullet = new BufferedImage[4];
 	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		getTextures();
	}
	
	private void getTextures() {
		int tileWidth = GameMain.WIDTH / GameMain.TILE_COUNT_X;
		for (int i = 0; i < 6; i++) 
			groundTiles[i] = block_sheet.getSubimage(1 + i * (tileWidth + 1), 1, tileWidth, tileWidth);
		groundTiles[6] = block_sheet.getSubimage(1, 34, tileWidth * 8, tileWidth);
		groundTiles[7] = block_sheet.getSubimage(1, 67, tileWidth * 8, tileWidth);
		groundTiles[8] = block_sheet.getSubimage(199, 1, tileWidth, tileWidth);
				
		for (int i = 0; i < coin.length; i++)
			coin[i] = block_sheet.getSubimage(1 + i * 33, 100, 32, 32);
		
		for (int i = 0; i < player.length; i++)
			player[i] = player_sheet.getSubimage(1 + 41 * i, 1, 40, 64);
		
		for (int i = 0; i < gun1.length; i++)
			gun1[i] = player_sheet.getSubimage(1 + 40 * i, 56, 39, 18);
		
		for (int i = 0; i < basicBullet.length; i++)
			basicBullet[i] = player_sheet.getSubimage(121 + 25 * i, 56, 24, 24);
	}
}
