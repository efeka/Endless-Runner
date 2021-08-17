package framework;

import java.util.ArrayList;

import objects.Coin;
import objects.GroundTile;
import objects.JumpThroughTile;
import window.Camera;
import window.GameMain;
import window.Handler;

public class MapSections {
	
	private ArrayList<ArrayList<GameObject>> sections;
	
	private Handler handler;
	private Camera cam;
	
	public MapSections(Handler handler, Camera cam) {
		sections = new ArrayList<ArrayList<GameObject>>();
		this.handler = handler;
		this.cam = cam;
	}

	@SuppressWarnings("incomplete-switch")
	public void queueRandomSection() {
		int random = (int) (Math.random() * sections.size());
		for (GameObject object : sections.get(random)) {
			switch(object.getId()) {
			case GroundTile:
				handler.addObject(new GroundTile(object.getX() - (int) cam.getX(), object.getY(), ((GroundTile) object).getType(), cam, handler, object.getId()), Handler.MIDDLE_LAYER);
				break;
			case Coin:
				handler.addObject(new Coin(object.getX() - (int) cam.getX(), object.getY(), object.getWidth(), object.getHeight(), cam, handler, ObjectId.Coin), Handler.MIDDLE_LAYER);
				break;
			case JumpThroughTile:
				handler.addObject(new JumpThroughTile(object.getX() - (int) cam.getX(), object.getY(), ((JumpThroughTile) object).getType() ,cam, handler, object.getId()), Handler.MIDDLE_LAYER);
				break;
			}
		}
		handler.renderPlayerLast();
	}

	/**
	 * Adds section array as a segment in the game.
	 * @param section : ID's of objects are defined as a JComboBox's indices, in ControlsWindow.java 
	 */
	public void addSection(int[][] section) {
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		for (int i = 0; i < section.length; i++) {
			for (int j = 0; j < section[i].length; j++) {
				int width, height;
				int cellX = GameMain.WIDTH + j * GameMain.WIDTH / GameMain.TILE_COUNT_X;
				int cellY = i * GameMain.WIDTH / GameMain.TILE_COUNT_X;
				switch(section[i][j]) {
				case 1: //Grass
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.Grass, cam, handler, ObjectId.GroundTile));
					break;
				case 2: //Grass x8
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.GrassX8, cam, handler, ObjectId.GroundTile));
					break;
				case 3: //Left Grass
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.LeftGrass, cam, handler, ObjectId.GroundTile));
					break;
				case 4: //Right Grass
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.RightGrass, cam, handler, ObjectId.GroundTile));
					break;
				case 5: //Wall
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.Wall, cam, handler, ObjectId.GroundTile));
					break;
				case 6: //Wall x8
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.WallX8, cam, handler, ObjectId.GroundTile));
					break;
				case 7: //Left Wall
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.LeftWall, cam, handler, ObjectId.GroundTile));
					break;
				case 8: //Right Wall
					list.add(new GroundTile(cellX, cellY, GroundTile.TYPE.RightWall, cam, handler, ObjectId.GroundTile));
					break;
				case 9: //Coin
					width = 5 * (GameMain.WIDTH / GameMain.TILE_COUNT_X) / 8;
					height = width;
					list.add(new Coin(cellX + width / 4, cellY + height / 4, width, height, cam, handler, ObjectId.Coin));
					break;
				case 10: //Jump Through Left
					list.add(new JumpThroughTile(cellX, cellY, JumpThroughTile.TYPE.Left, cam, handler, ObjectId.JumpThroughTile));
					break;
				case 11: //Jump Through Middle
					list.add(new JumpThroughTile(cellX, cellY, JumpThroughTile.TYPE.Middle, cam, handler, ObjectId.JumpThroughTile));
					break;
				case 12: //Jump Through Right
					list.add(new JumpThroughTile(cellX, cellY, JumpThroughTile.TYPE.Right, cam, handler, ObjectId.JumpThroughTile));
					break;
				}
			}
		}
		sections.add(list);
	}
	
}
