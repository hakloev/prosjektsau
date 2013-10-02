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
	
	private MapPanel mapPanel;
	private UserPanel userPanel;
	private SheepPanel sheepPanel;
	private AlarmPanel alarmPanel;
	private LogPanel logPanel;
	private JTabbedPane jTabPane;

	public ProgramFrame() {
		initFrame();
		initGuiTabs();
		//jTabPane.setEnabled(false);
	}
	/**
	 * Initializes the program frame.
	 */
	private void initFrame() {
		setVisible(true);
		setPreferredSize(windowSize);
		setMinimumSize(minWindowSize);
		setMaximumSize(maxWindowSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowSize);
	}
	
	/**
	 * Initializes the different panels that goes to the JTabbedPane, along the JTabbedPane.
	 */
	private void initGuiTabs(){
		userPanel = new UserPanel();
		sheepPanel = new SheepPanel();
		mapPanel = new MapPanel();
		alarmPanel = new AlarmPanel();
		logPanel = new LogPanel();
		
		jTabPane = new JTabbedPane();
		jTabPane.add("User Panel", userPanel);
		jTabPane.add("Sheep Panel", sheepPanel);
		jTabPane.add("Map Panel", mapPanel);
		jTabPane.add("Alarm Panel", alarmPanel);
		jTabPane.add("Log Panel", logPanel);
		this.add(jTabPane);
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
		minWindowSize = new Dimension(760, 570);
		maxWindowSize = new Dimension(1200,900);
	}

}
