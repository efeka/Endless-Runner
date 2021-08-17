package level_designer;

import java.awt.Color;
import java.awt.Graphics;

import framework.Texture;

public class Grid {

	private int rows, cols, cellSize;
	private Texture tex = Main.getTexture();

	private Cell[][] grid;

	public Grid(int rows, int cols, int cellSize, int offset) {
		this.rows = rows;
		this.cols = cols;
		this.cellSize = cellSize;

		grid = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new Cell(offset + j * cellSize, offset + i * cellSize, cellSize, Cell.EMPTY, tex);
			}
		}
	}

	public void tick() {

	}

	public void render(Graphics g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = grid[i][j];
				if (cell.image == null) {
					g.setColor(new Color(170, 170, 170));
					g.drawRect(cell.rect.x, cell.rect.y, cellSize, cellSize);
				}
				else {
					g.drawImage(cell.image, cell.rect.x, cell.rect.y, cell.cellSpanX * cellSize, cell.cellSpanY * cellSize, null);
					
					if (ControlsWindow.showHelperFrames && cell.cellSpanX > 1 || cell.cellSpanY > 1) {
						int outlineSize = cellSize / 8;
						g.setColor(Color.white);
						int spanWidth = cell.cellSpanX * cellSize;
						int spanHeight = cell.cellSpanY * cellSize;
						g.fillRect(cell.rect.x, cell.rect.y, spanWidth, outlineSize);
						g.fillRect(cell.rect.x, cell.rect.y, outlineSize, spanHeight);
						g.fillRect(cell.rect.x + spanWidth - outlineSize, cell.rect.y, outlineSize, spanHeight);
						g.fillRect(cell.rect.x, cell.rect.y + spanHeight - outlineSize, spanWidth, outlineSize);
						g.setColor(Color.yellow);
						g.fillRect(cell.rect.x, cell.rect.y, cellSize, outlineSize);
						g.fillRect(cell.rect.x, cell.rect.y, outlineSize, cellSize);
						g.fillRect(cell.rect.x, cell.rect.y + (cellSize - outlineSize), cellSize, outlineSize);
						g.fillRect(cell.rect.x + (cellSize - outlineSize), cell.rect.y, outlineSize, cellSize);
					}
				}
			}
		}
	}

	public void handleMouse(int mouseX, int mouseY, int button) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j].rect.contains(mouseX, mouseY)) {
					grid[i][j].changeType(ControlsWindow.selectedType * (button % 3));
				}
			}
		}
	}
	
	public int[][] getGrid() {
		int[][] segment = new int[grid.length][grid[0].length];
		for (int i = 0; i < segment.length; i++)
			for (int j = 0; j < segment[i].length; j++)
				segment[i][j] = grid[i][j].type;
		return segment;
	}
}
