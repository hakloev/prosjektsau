package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JSeparator;

import characters.Farmer;
import serverconnection.NetHandler;
import serverconnection.Response;
import serverconnection.JsonHandler;

/**
 * Class holding the user information and login
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
	
	private NetHandler handler;
	private final String wrongUser = "Brukernavnet eksisterer ikke.";
	private final String wrongPw = "Feil passord.";
	private Farmer farmer;
	
	
	public UserPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
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
	 * @return farmer
	 */
	public Farmer getFarmer() {
		return this.farmer;
	}
	
	// All listeners is implemented as classes that implements the ActionListener-interface
	
	
	/**
	 * Listener for the loginButton
	 * @author Håkon Ødegård Løvdal
	 * 
	 */
	class LoginListener implements ActionListener {		
		/**
		 * Method that checks if user is valid and logs in
		 * It also calls the initUserSheeps()-method in SheepPanel to init sheeps. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			handler = programFrame.getNetHandler();
			Response loginResult = handler.login(usernameField.getText(),
					new String(passwordField.getPassword()));
			if (!handler.isError(loginResult.msg)) {
				loginButton.setEnabled(false);
				usernameField.setEditable(false);
				passwordField.setEditable(false);
				
				// Parse Response to create farmer
				farmer = JsonHandler.parseJsonAndReturnUser(loginResult);
				
				// Initiate sheeps
				programFrame.getSheepPanel().initUserSheeps(handler.getSheep(-1));

				// Activate other tabs
				programFrame.getJTabbedPane().setEnabledAt(1, true);
				programFrame.getJTabbedPane().setEnabledAt(2, true);
				programFrame.getJTabbedPane().setEnabledAt(3, true);
				programFrame.getJTabbedPane().setEnabledAt(4, true);

				// Set panel to SheepPanel
				programFrame.getJTabbedPane().setSelectedIndex(1); 
			} else {
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), loginResult.msg + "\nPrøv på nytt",
					"Innloggingsfeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
