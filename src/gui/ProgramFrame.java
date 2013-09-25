package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ProgramFrame extends JFrame{
	
	public static Dimension windowSize;
	public static Dimension minWindowSize;
	public static Dimension maxWindowSize;
	
	public ProgramFrame() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProgramFrame program = new ProgramFrame();
		program.setVisible(true);
		
		windowSize = new Dimension(800,600);
		minWindowSize = new Dimension(660, 520);
		maxWindowSize = new Dimension(1200,900);
		
		program.setPreferredSize(windowSize);
		program.setMinimumSize(minWindowSize);
		program.setMaximumSize(maxWindowSize);
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.setSize(windowSize);
		
		MapPanel mapPanel = new MapPanel();
		mapPanel.
		program.add(mapPanel);
	}

}
