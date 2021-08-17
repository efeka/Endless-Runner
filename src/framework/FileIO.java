package framework;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import level_designer.Grid;
import window.GameMain;

public class FileIO {
	
	private File file;
	private Scanner reader;
	private FileWriter writer;
	private String userDirectory;
	
	private Grid grid;
	private MapSections mapSections;
	
	public FileIO(Grid grid, MapSections mapSections) {
		this.grid = grid;
		this.mapSections = mapSections;
		
		userDirectory = new File("").getAbsolutePath();
		file = new File(userDirectory + "/levelSegments.txt");
		if (!file.exists())
			file = new File(userDirectory + "\\src\\levelSegments.txt");
		if (!file.exists()) {
			System.err.println("Could not locate levelSegments.txt");
			System.exit(0);
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
			writer = new FileWriter(file, true);
			for (int i = 0; i < lines.length; i++)
				writer.write(lines[i] + "\n");
			writer.write("#\n");
			writer.close();
		} catch(Exception e) {
			System.err.println("Failed to write into levelSegments.txt");
		}
	}
	
	public void readSegments() {
		try {
			reader = new Scanner(file);
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
	
}
