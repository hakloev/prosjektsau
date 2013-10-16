package gui;

import java.awt.Dimension;

import utils.Constants;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ProgramFrame extends JFrame{
	
	//St���rrelsesvariabler for vinduet
	public static Dimension windowSize;
	public static Dimension minWindowSize;
	public static Dimension maxWindowSize;
	
	//Div panel og swing variabler
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
		setResizable(false);
		setVisible(true);
		setPreferredSize(windowSize);
		setMinimumSize(minWindowSize);
		setMaximumSize(maxWindowSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowSize);
		setTitle(Constants.title);
	}
	
	/**
	 * Henter brukerpanelet userPanel.
	 * 
	 * @return JPanel
	 */
	public UserPanel getUserPanel(){
		return userPanel;
	}
	
	/**
	 * Henter sauepanelet sheepPanel.
	 * 
	 * @return JPanel
	 */
	public SheepPanel getSheepPanel(){
		return sheepPanel;
	}
	
	/**
	 * Henter kartpanelet mapPanel.
	 * 
	 * @return JPanel
	 */
	public MapPanel getMapPanel(){
		return mapPanel;
	}
	
	/**
	 * Henter alarmpanelet alarmPanel.
	 * 
	 * @return JPanel
	 */
	public AlarmPanel getAlarmPanel(){
		return alarmPanel;
	}
	
	/**
	 * Henter loggpanelet logPanel.
	 * 
	 * @return JPanel
	 */
	public LogPanel getLogPanel(){
		return logPanel;
	}
	
	public JTabbedPane getJTabbedPane() {
		return jTabPane;
	}
	
	/**
	 * Initializes the different panels that goes to the JTabbedPane, along the JTabbedPane.
	 */
	private void initGuiTabs(){
		userPanel = new UserPanel(this);
		sheepPanel = new SheepPanel(this);
		mapPanel = new MapPanel(this);
		alarmPanel = new AlarmPanel(this);
		logPanel = new LogPanel(this);
		
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
		minWindowSize = new Dimension(600, 500);
		maxWindowSize = new Dimension(1200,900);
	}

}
