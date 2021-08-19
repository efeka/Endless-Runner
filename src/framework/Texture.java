package framework;

import java.awt.image.BufferedImage;

import window.BufferedImageLoader;
import window.GameMain;

public class Texture {

	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	private BufferedImage weapon_sheet = null;
	
	public BufferedImage[] groundTiles = new BufferedImage[11];
	public BufferedImage[] coin = new BufferedImage[6];
	public BufferedImage[] gem = new BufferedImage[6];
	
	public BufferedImage[] player = new BufferedImage[10];
	public BufferedImage[] gun1 = new BufferedImage[8];
	public BufferedImage[] basicBullet = new BufferedImage[4];
	
	public BufferedImage[] dustEffect = new BufferedImage[5];
 	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
			weapon_sheet = loader.loadImage("/weapon_sheet.png");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		getTextures();
	}
	
	private void getTextures() {
		int tileWidth = 32;
		for (int i = 0; i < 6; i++) 
			groundTiles[i] = block_sheet.getSubimage(1 + i * (tileWidth + 1), 1, tileWidth, tileWidth);
		groundTiles[6] = block_sheet.getSubimage(1, 34, tileWidth * 8, tileWidth);
		groundTiles[7] = block_sheet.getSubimage(1, 67, tileWidth * 8, tileWidth);
		
		for (int i = 0; i < 3; i++)
			groundTiles[8 + i] = block_sheet.getSubimage(199 + i * (tileWidth + 1), 1, tileWidth, tileWidth);
				
		for (int i = 0; i < coin.length; i++)
			coin[i] = block_sheet.getSubimage(1 + i * 33, 100, 32, 32);
		
		for (int i = 0; i < player.length; i++)
			player[i] = player_sheet.getSubimage(1 + 41 * i, 1, 40, 64);
		
		for (int i = 0; i < gun1.length; i++)
			gun1[i] = player_sheet.getSubimage(1 + 40 * i, 56, 39, 18);
		
		for (int i = 0; i < basicBullet.length; i++)
			basicBullet[i] = player_sheet.getSubimage(121 + 25 * i, 56, 24, 24);
		
		for (int i = 0; i < gem.length; i++) 
			gem[i] = block_sheet.getSubimage(1 + i * 33, 133, 32, 32);
		
		for (int i = 0; i < 4; i++)
			gun1[i] = weapon_sheet.getSubimage(1 + i * 81, 1, 80, 64);
		for (int i = 4; i < 8; i++)
			gun1[i] = weapon_sheet.getSubimage(1 + (i - 4) * 81, 66, 80, 64);
		
		for (int i = 0; i < dustEffect.length; i++)
			dustEffect[i] = block_sheet.getSubimage(1 + i * 33, 166, 32, 32);
	}
}
