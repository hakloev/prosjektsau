package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import characters.Farmer;
import serverconnection.GetAndParseJson;
import serverconnection.NetHandler;

/**
 * 
 * @author Andreas Lyngby
 * @author Håkon Ødegård Løvdal
 */

public class UserPanel extends JPanel {

	private ProgramFrame programFrame;
	
	private static DefaultListModel test2 = null;

	private JButton loginButton;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel usernameText;
	private JLabel passwordText;
	private GroupLayout layout;
	
	private JSeparator js;
	
	private JLabel firstNameText;
	private JLabel lastNameText;
	private JLabel farmerIdText;
	
	private JTextField firstName;
	private JTextField lastName;
	private JTextField farmerId;
	
	private final String wrongUser = "ERROR=Brukernavnet eksisterer ikke.";
	private final String wrongPw = "ERROR=Feil passord!";
	private boolean loggedIn;
	private Farmer farmer;
	
	
	public UserPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		this.loggedIn = false;
		initElements();
		initDesign();
	}
	
	
	public void initElements(){
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
		
		loginButton = new JButton("Logg inn");
		
		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Password:");
		
		usernameField = new JTextField(10);
		usernameField.setMaximumSize(new Dimension(1000,20));
		
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setMaximumSize(new Dimension(1000,20));
		
		js = new JSeparator();
		
		// Listeners
		loginButton.addActionListener(new LoginListener());
	}

	
	public void initDesign(){
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(usernameText)
						.addComponent(loginButton)
					)
					.addComponent(usernameField)
					.addComponent(passwordText)
					.addComponent(passwordField)
				)
				.addComponent(js)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(usernameText)
				.addComponent(usernameField)
				.addComponent(passwordText)
				.addComponent(passwordField)
			)
			.addComponent(loginButton)
			.addComponent(js)
		);
	}
	
	
	/**
	 * Method that returns the farmer-object currently logged in
	 * 
	 * @return farmer
	 */
	public Farmer getFarmer() {
		return this.farmer;
	}
	
	/**
	 * Method that returns the current logged in status
	 * @return bool
	 */
	public boolean getLoggedInStatus() {
		return loggedIn;
	}
	
	// All listeners is implemented as classes that implements the ActionListener-interface
	
	/**
	 * 
	 * Listener for the loginButton
	 * @author Håkon Ødegård Løvdal
	 */
	class LoginListener implements ActionListener {
		
		/**
		 * loginButton, actionPerformed-method
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = programFrame.getNetHandler().login(
					usernameField.getText(), new String(passwordField.getPassword()));
			if (validUser(result)) {
				loginButton.setEnabled(false);
				usernameField.setEditable(false);
				passwordField.setEditable(false);
				loggedIn = true;
				// parse result json to create farmer
				String testFarmer = "{\"Farmer\":{\"farmerId\":\"1243556\",\"farmerHash\":\"aslfkewj234HÅKONølk324jl2\",\"farmerUsername\":\"hakloev\",\"farmerEmail\":\"hakloev@derp.com\",\"SheepObject\":{\"sheepId\":\"123456789\",\"nick\":\"Link\",\"birthYear\":\"1986\",\"lat\":\"62.38123\",\"long\":\"9.16686\"}}}";
				farmer = new GetAndParseJson(testFarmer).getFarmer();
				
				// Initiate sheeps
				programFrame.getSheepPanel().initUserSheeps();
				
				// Activate other tabs
				programFrame.getJTabbedPane().setEnabledAt(1, true);
				programFrame.getJTabbedPane().setEnabledAt(2, true);
				programFrame.getJTabbedPane().setEnabledAt(3, true);
				programFrame.getJTabbedPane().setEnabledAt(4, true);

				// Set panel to SheepPanel
				programFrame.getJTabbedPane().setSelectedIndex(1); 
			} 
		}
		
		/**
		 * Method to check with database that user is valid
		 * 
		 * @param text
		 * @param password
		 * @return boolean
		 */
		private boolean validUser(String loginStatus) {
			// sjekk mot database
			if (loginStatus.equals(wrongPw)) {
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Feil passord!\nPrøv på nytt", 
						"Innloggingsfeil", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (loginStatus.equals(wrongUser)) {
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Feil brukernavn!\nPrøv på nytt", 
						"Innloggingsfeil", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		}
		
	}
}
