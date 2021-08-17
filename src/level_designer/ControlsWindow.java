package level_designer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import framework.FileIO;
import framework.Texture;

public class ControlsWindow {

	public static boolean showHelperFrames = true;
	public static boolean showGridLines;
	public static int selectedType = 1;

	public ControlsWindow(int x, int y, int width, int height, Texture tex, FileIO fileIO) {
		setup(x, y, width, height, tex, fileIO);
	}

	private void setup(int x, int y, int width, int height, Texture tex, FileIO fileIO) {
		JFrame frame = new JFrame("Controls");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(0, 0, width, height);
		frame.setLocation(x, y);
		frame.setLayout(null);

		JComboBox<String> objectTypeSelection = new JComboBox<String>();
		objectTypeSelection.addItem("Grass");
		objectTypeSelection.addItem("Grass x8");
		objectTypeSelection.addItem("Left Grass");
		objectTypeSelection.addItem("Right Grass");
		objectTypeSelection.addItem("Wall");
		objectTypeSelection.addItem("Wall x8");
		objectTypeSelection.addItem("Left Wall");
		objectTypeSelection.addItem("Right Wall");
		objectTypeSelection.addItem("Coin");
		objectTypeSelection.addItem("Jump Through Left");
		objectTypeSelection.addItem("Jump Through Middle");
		objectTypeSelection.addItem("Jump Through Right");
		objectTypeSelection.setSelectedIndex(0);
		objectTypeSelection.setBounds((width - 125) / 2, 120, 125, 20);
		objectTypeSelection.setVisible(true);
		frame.add(objectTypeSelection);

		JLabel typeSelectLabel = new JLabel("Select object type");
		typeSelectLabel.setBounds(objectTypeSelection.getX(), objectTypeSelection.getY() - 25, objectTypeSelection.getWidth(), objectTypeSelection.getHeight());
		typeSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(typeSelectLabel);

		JLabel selectedTypeImage = new JLabel();
		selectedTypeImage.setIcon(new ImageIcon(tex.groundTiles[0].getScaledInstance(64, 64, 0)));
		selectedTypeImage.setBounds((width - 64) / 2, -15, 128, 128);
		frame.add(selectedTypeImage);

		objectTypeSelection.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				selectedType = objectTypeSelection.getSelectedIndex() + 1;

				ImageIcon icon = null;
				switch (selectedType) {
				case Cell.GRASS:
					icon = new ImageIcon(tex.groundTiles[0].getScaledInstance(64, 64, 0));
					break;
				case Cell.GRASSX8:
					icon = new ImageIcon(tex.groundTiles[6].getScaledInstance(64, 16, 0));
					break;
				case Cell.LEFT_GRASS:
					icon = new ImageIcon(tex.groundTiles[1].getScaledInstance(64, 64, 0));
					break;
				case Cell.RIGHT_GRASS:
					icon = new ImageIcon(tex.groundTiles[2].getScaledInstance(64, 16, 0));
					break;
				case Cell.WALL:
					icon = new ImageIcon(tex.groundTiles[3].getScaledInstance(64, 16, 0));
					break;
				case Cell.WALLX8:
					icon = new ImageIcon(tex.groundTiles[7].getScaledInstance(64, 16, 0));
					break;
				case Cell.LEFT_WALL:
					icon = new ImageIcon(tex.groundTiles[4].getScaledInstance(64, 16, 0));
					break;
				case Cell.RIGHT_WALL:
					icon = new ImageIcon(tex.groundTiles[5].getScaledInstance(64, 16, 0));
					break;
				case Cell.COIN:
					icon = new ImageIcon(tex.coin[3].getScaledInstance(64, 64, 0));
					break;
				case Cell.JUMP_THROUGH_LEFT:
					icon = new ImageIcon(tex.groundTiles[8].getScaledInstance(64, 64, 0));
					break;
				case Cell.JUMP_THROUGH_MIDDLE:
					icon = new ImageIcon(tex.groundTiles[9].getScaledInstance(64, 64, 0));
					break;
				case Cell.JUMP_THROUGH_RIGHT:
					icon = new ImageIcon(tex.groundTiles[10].getScaledInstance(64, 64, 0));
					break;
				default:
					icon = null;
				}
				selectedTypeImage.setIcon(icon);
			}
		});

		JCheckBox showFrames = new JCheckBox("Show helper frames");
		showFrames.setBounds((width - 150) / 2, objectTypeSelection.getY() + objectTypeSelection.getHeight() + 15, 150, 15);
		showFrames.setSelected(true);
		showFrames.setVisible(true);
		showFrames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBox cb = (JCheckBox) event.getSource();
				if (cb.isSelected()) {
					showHelperFrames = true;
				} else {
					showHelperFrames = false;
				}
			}
		});
		frame.add(showFrames);

		JCheckBox showGrid = new JCheckBox("Show grid");
		showGrid.setBounds((width - 150) / 2, showFrames.getY() + showFrames.getHeight(), 150, 40);
		showGrid.setSelected(true);
		showGrid.setVisible(true);
		showGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBox cb = (JCheckBox) event.getSource();
				if (cb.isSelected()) {
					showGridLines = true;
				} else {
					showGridLines = false;
				}
			}
		});
		frame.add(showGrid);

		JButton saveButton = new JButton("Save Segment");
		saveButton.setBounds((width - 120) / 2, showGrid.getY() + showGrid.getHeight() + 5, 120, 30);
		saveButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int confirmed = JOptionPane.showConfirmDialog(saveButton, "Are you sure you want to save this segment?", "Confirm save operation",JOptionPane.YES_NO_OPTION);

					if (confirmed == JOptionPane.YES_OPTION)
						fileIO.saveSegment();
				}
			}
		});
		frame.add(saveButton);

		frame.setVisible(true);
	}
}
