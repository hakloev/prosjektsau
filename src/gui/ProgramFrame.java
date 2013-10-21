package gui;

import java.awt.Dimension;

import serverconnection.NetHandler;
import utils.Constants;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Andreas Lynbgy
 */

public class ProgramFrame extends JFrame{
	
	//Størrelsesvariabler for vinduet
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
	
	//Init a NetHandler
	private NetHandler handler;
	
	public ProgramFrame() {
		initFrame();
		initGuiTabs();
		handler = new NetHandler();
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
	 * Return NetHandler-object
	 * @return handler
	 */
	public NetHandler getNetHandler() {
		return handler;
	}
	
	/**
	 * Henter brukerpanelet userPanel.
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
	 * @return JPanel
	 */
	public MapPanel getMapPanel(){
		return mapPanel;
	}
	
	/**
	 * Henter alarmpanelet alarmPanel.
	 * @return JPanel
	 */
	public AlarmPanel getAlarmPanel(){
		return alarmPanel;
	}
	
	/**
	 * Henter loggpanelet logPanel.
	 * @return JPanel
	 */
	public LogPanel getLogPanel(){
		return logPanel;
	}
	
	/**
	 * Returns jTabPane
	 * @return jTabPane
	 */
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
		jTabPane.add("Bruker", userPanel);
		jTabPane.add("Sauer", sheepPanel);
		jTabPane.add("Kart", mapPanel);
		jTabPane.add("Alarmer", alarmPanel);
		jTabPane.add("Logg", logPanel);
		
		// Disable all panes until user is logged in
		jTabPane.setEnabledAt(1, false);
		jTabPane.setEnabledAt(2, false);
		jTabPane.setEnabledAt(3, false);
		jTabPane.setEnabledAt(4, false);

		this.add(jTabPane);
	}

	/**
	 * Main method for the client-application
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
