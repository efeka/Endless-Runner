package framework;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import level_designer.Grid;
import window.GameMain;

public class FileIO {
	
	private File levelsFile, playerDataFile;
	private Scanner reader;
	private FileWriter writer;
	private String userDirectory;
	
	private Grid grid;
	private MapSections mapSections;
	private PlayerData playerData;
	
	public FileIO(Grid grid, MapSections mapSections, PlayerData playerData) {
		this.grid = grid;
		this.mapSections = mapSections;
		this.playerData = playerData;
		
		userDirectory = new File("").getAbsolutePath();
		levelsFile = new File(userDirectory + "/levelSegments.txt");
		if (!levelsFile.exists())
			levelsFile = new File(userDirectory + "\\src\\levelSegments.txt");
		if (!levelsFile.exists()) {
			System.err.println("Could not locate levelSegments.txt");
			System.exit(0);
		}
		
		playerDataFile = new File(userDirectory + "/playerData.txt");
		if (!playerDataFile.exists())
			playerDataFile = new File(userDirectory + "\\src\\playerData.txt");
		if (!playerDataFile.exists()) {
			System.err.println("Could not locate playerData.txt");
			System.exit(0);
		}
		
	}
	
	public void savePlayerData() {
		try {
			writer = new FileWriter(playerDataFile, false);
			writer.write("coins=" + playerData.getCoins() + "\n");
			writer.write("gems=" + playerData.getGems());
			writer.close();
		} catch (Exception e) {
			System.err.println("Failed to write into playerData.txt");
		}
	}
	
	public void readPlayerData() {
		try {
			reader = new Scanner(playerDataFile);
			String[] coins = reader.nextLine().split("=");
			playerData.setCoins(Integer.parseInt(coins[1]));
			String[] gems = reader.nextLine().split("=");
			playerData.setGems(Integer.parseInt(gems[1]));
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to read from playerData.txt");
		}
	}
	
	public void readSegments() {
		try {
			reader = new Scanner(levelsFile);
			String[][] segment = new String[GameMain.TILE_COUNT_Y][GameMain.TILE_COUNT_X];
			while (reader.hasNextLine()) {
				for (int i = 0; i < GameMain.TILE_COUNT_Y; i++) {
					segment[i] = reader.nextLine().split(" ");
				}
				reader.nextLine();
				
				int[][] sgmt = new int[segment.length][segment[0].length];
				for (int i = 0; i < sgmt.length; i++)
					for (int j = 0; j < sgmt[i].length; j++)
						sgmt[i][j] = Integer.parseInt(segment[i][j]);
				mapSections.addSection(sgmt);
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Failed to read from levelSegments.txt");
		}
	}
	
	public void saveSegment() {
		int[][] segment = grid.getGrid();
		String[] lines = new String[segment.length];
		for (int i = 0; i < segment.length; i++) {
			String line = "";
			for (int j = 0; j < segment[i].length; j++) {
				line += segment[i][j] + " ";
			}
			lines[i] = line;
		}
		
		try {
			writer = new FileWriter(levelsFile, true);
			for (int i = 0; i < lines.length; i++)
				writer.write(lines[i] + "\n");
			writer.write("#\n");
			writer.close();
		} catch(Exception e) {
			System.err.println("Failed to write into levelSegments.txt");
		}
	}
	
}
