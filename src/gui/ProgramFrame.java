package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class ProgramFrame extends JFrame{
	
	public static Dimension windowSize;
	public static Dimension minWindowSize;
	public static Dimension maxWindowSize;
	static MapPanel mapPanel;
	
	public ProgramFrame() {
		initFrame();
		mapPanel = new MapPanel();
		JTabbedPane jtb = new JTabbedPane();
		jtb.addTab("Tab", new JPanel());
		jtb.add("Map Panel", mapPanel);
		add(jtb);

	}

	private void initFrame() {
		setVisible(true);
		setPreferredSize(windowSize);
		setMinimumSize(minWindowSize);
		setMaximumSize(maxWindowSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowSize);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				ProgramFrame program = new ProgramFrame();
			}
		});
		
		windowSize = new Dimension(800,600);
		minWindowSize = new Dimension(660, 520);
		maxWindowSize = new Dimension(1200,900);
	}

}
