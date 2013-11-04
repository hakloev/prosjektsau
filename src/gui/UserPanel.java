package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import characters.Farmer;
import characters.Node;
import characters.Sheep;
import serverconnection.JsonHandler;
import serverconnection.NetHandler;

/**
 * 
 * @author Andreas Lyngby
 * @author Håkon Ødegård Løvdal
 */

public class UserPanel extends JPanel {

	private ProgramFrame programFrame;
	
	private ArrayList<ArrayList<Node>> areaList;
	
	private JList<ArrayList<Node>> list;
	private DefaultListModel<ArrayList<Node>> areaGuiList;
	private JScrollPane listScrollPane;
	
	private JComboBox areaBox;
	private JLabel areaBoxText;
	
	private JButton loginButton;
	private JButton addArea;
	private JButton editArea;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel usernameText;
	private JLabel passwordText;
	private GroupLayout layout;
	
	private JSeparator js;
	private JSeparator js1;
	
	private JLabel firstNameText;
	private JLabel lastNameText;
	private JLabel farmerIdText;
	private JLabel farmerEmailText;
	
	private JTextField firstName;
	private JTextField lastName;
	private JTextField farmerId;
	private JTextField farmerEmail;
	
	private NetHandler handler;
	private final String wrongUser = "ERROR=Brukernavnet eksisterer ikke.";
	private final String wrongPw = "ERROR=Feil passord!";
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
		addArea = new JButton("Legg til omr�de");
		editArea = new JButton("Endre omr�de");
		
		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Password:");
		
		usernameField = new JTextField(10);
		usernameField.setMaximumSize(new Dimension(1000,20));
		
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setMaximumSize(new Dimension(1000,20));
		
		js = new JSeparator();
		js1 = new JSeparator();
		
		areaBoxText = new JLabel("Omr�der:");
		areaList = new ArrayList<ArrayList<Node>>();
		
		areaBox = new JComboBox(areaList.toArray());
		areaBox.setMinimumSize(new Dimension(120,20));
		areaBox.setMaximumSize(new Dimension(120,20));
		
		areaGuiList = new DefaultListModel<ArrayList<Node>>();
		list = new JList(areaGuiList);
		listScrollPane = new JScrollPane(list);
		
		farmerEmailText = new JLabel("E-mail:");
		farmerEmail = new JTextField();
		farmerEmail.setMinimumSize(new Dimension(100,20));
		farmerEmail.setPreferredSize(new Dimension(100,20));
		farmerEmail.setMaximumSize(new Dimension(200,20));
		
		// Listeners
		loginButton.addActionListener(new LoginListener());
		addArea.addActionListener(new AddAreaListener(this));
		editArea.addActionListener(new EditAreaListener(this));
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
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(js)
				)

				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(js1)
				)

				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(farmerEmailText)
						.addComponent(farmerEmail)
						.addComponent(areaBoxText)
						.addComponent(listScrollPane)
						.addComponent(editArea)
						.addComponent(addArea)
				)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(usernameText)
						.addComponent(usernameField)
						.addComponent(passwordText)
						.addComponent(passwordField)
					)
					.addComponent(loginButton)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(js)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(js1)
					)
					.addComponent(farmerEmailText)
					.addComponent(farmerEmail)
					.addComponent(areaBoxText)
					.addComponent(listScrollPane)
					.addComponent(editArea)
					.addComponent(addArea)
					.addContainerGap()
				)
			)
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
	 * Listener for the EnterButton
	 * @author Thomas Mathisen
	 * 


		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	*/
	
	/**
	 * Disables or enables the addArea and editArea button if AreaEditFrame is opened or closed.
	 * @param bool
	 */
	public void setAreaOpenable(boolean bool){
		addArea.setEnabled(bool);
		editArea.setEnabled(bool);
	}
	
	/**
	 * Adds an area to the area list
	 * @param list - ArrayList<Node>
	 */
	
	public void addArea(ArrayList<Node> list){
		areaGuiList.addElement(list);
	}
	
	/**
	 * Class you implementing ActionLister to make the button to edit the farm area. 
	 * @author Andreas
	 *
	 */
	
	class AddAreaListener implements ActionListener{
		
		private UserPanel panel;
		/**
		 * Constructor
		 * @param panel - Userpanel for later use
		 */
		public AddAreaListener(UserPanel panel){
			this.panel = panel;
		}
		
		/**
		 * Called when clicking the button to create a new area. Opens AreaEditFrame.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new AreaEditFrame(panel.programFrame, null);
			panel.setAreaOpenable(false);
		}
	}
	
	
	/**
	 * Class you implementing ActionLister to make the button to edit the farm area.
	 * @author Andreas
	 *
	 */
	
	class EditAreaListener implements ActionListener{
		private UserPanel panel;
		
		/**
		 * Constructor
		 * @param panel - Userpanel for later use
		 */
		public EditAreaListener(UserPanel panel){
			this.panel = panel;
		}
		
		/**
		 * Action performed function to open AreaEditFrame to edit or make your area. Supposed to remove the selected item to handle multiples(not yet done).
		 * 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(panel.areaGuiList.size()!=0){
				ArrayList<Node> temp = (ArrayList<Node>)panel.areaGuiList.get(panel.list.getSelectedIndex());
				panel.areaGuiList.remove(panel.list.getSelectedIndex());
				new AreaEditFrame(panel.programFrame,temp);
				panel.setAreaOpenable(false);
			}
		}
	}
	
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
			String loginResult = handler.login(usernameField.getText(), 
					new String(passwordField.getPassword()));
			if (validUser(loginResult)) {
				loginButton.setEnabled(false);
				usernameField.setEditable(false);
				passwordField.setEditable(false);
				
				// parse result json to create farmer
				// set user hash
				farmer = JsonHandler.parseJsonAndReturnUser(loginResult);
				handler.setUserCode(farmer.getHash());
				
				//Get farmer info
				farmerEmail.setText(farmer.getEmail());
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
		 * @param loginStatus
		 * @return boolean
		 */
		private boolean validUser(String loginStatus) {
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
