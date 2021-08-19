package window;

import java.awt.Graphics;
import java.util.ArrayList;

import framework.GameObject;
import objects.Player;

public class Handler {

	//public BufferedImage level1 = null;

	public static final int BOTTOM_LAYER = 0;
	public static final int MIDDLE_LAYER = 1;
	public static final int TOP_LAYER = 2;
	public static final int MENU_LAYER = 3;

	public ArrayList<GameObject> layerBottom = new ArrayList<GameObject>();
	public ArrayList<GameObject> layerMiddle = new ArrayList<GameObject>();
	public ArrayList<GameObject> layerTop = new ArrayList<GameObject>();
	public ArrayList<GameObject> layerMenu = new ArrayList<GameObject>();
	
	public Player player;

	public Handler() {
		//BufferedImageLoader loader = new BufferedImageLoader();
		/* 
		level1 = loader.loadImage("/level1.png");
		loadImageLevel(level1);
		*/
	}

//	public void loadImageLevel(BufferedImage image) {
//		int w = image.getWidth();
//		int h = image.getHeight();
//
//		for (int xx = 0; xx < h; xx++) {
//			for (int yy = 0; yy < w; yy++) {
//				int pixel = image.getRGB(xx, yy);
//				int red = (pixel >> 16) & 0xff;
//				int green = (pixel >> 8) & 0xff;
//				int blue = (pixel) & 0xff;
//				
//				//Example
//				if (red == 166 && green == 93 && blue == 53)
//					addObject(new PathingHelper(xx * 32, yy * 32, PathingHelper.UP, false, ObjectId.PathingHelper), BOTTOM_LAYER);
//				
//			}
//		}
//	}

	public void tick() {
		for (int i = 0; i < layerMiddle.size(); i++) 
			layerMiddle.get(i).tick();
		for (int i = 0; i < layerBottom.size(); i++) 
			layerBottom.get(i).tick();
		for (int i = 0; i < layerTop.size(); i++) 
			layerTop.get(i).tick();
		for (int i = 0; i < layerMenu.size(); i++) 
			layerMenu.get(i).tick();
		
	}

	public void render(Graphics g) {
		for (int i = 0; i < layerBottom.size(); i++) 
			layerBottom.get(i).render(g);
		for (int i = 0; i < layerMiddle.size(); i++) 
			layerMiddle.get(i).render(g);
		for (int i = 0; i < layerTop.size(); i++) 
			layerTop.get(i).render(g);
		for (int i = 0; i < layerMenu.size(); i++) 
			layerMenu.get(i).render(g);
	}

	public void addObject(GameObject object, int layer) {
		switch(layer) {
		case BOTTOM_LAYER:
			layerBottom.add(object);
			break;
		case MIDDLE_LAYER:
			layerMiddle.add(object);
			break;
		case TOP_LAYER:
			layerTop.add(object);
			break;
		case MENU_LAYER:
			layerMenu.add(object);
			break;
		}
	}
	
	public void addObject(GameObject object, int layer, int index) {
		switch(layer) {
		case BOTTOM_LAYER:
			layerBottom.add(index, object);
			break;
		case MIDDLE_LAYER:
			layerMiddle.add(index, object);
			break;
		case TOP_LAYER:
			layerTop.add(index, object);
			break;
		case MENU_LAYER:
			layerMenu.add(index, object);
			break;
		}
	}

	public void removeObject(GameObject object) {
		if (layerMiddle.contains(object)) {
			layerMiddle.remove(object);
			return;
		}

		if (layerBottom.contains(object)) {
			layerBottom.remove(object);
			return;
		}

		if (layerTop.contains(object)) {
			layerTop.remove(object);
			return;
		}
		
		if (layerMenu.contains(object)) {
			layerMenu.remove(object);
			return;
		}
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void renderPlayerLast() {
		GameObject temp = player;
		removeObject(player);
		addObject(temp, MIDDLE_LAYER);
	}

}
