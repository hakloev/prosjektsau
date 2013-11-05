package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import characters.Farmer;
import characters.Position;
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

	private ArrayList<ArrayList<Node>> areaList;

	private JList<ArrayList<Position>> list;
	private DefaultListModel<ArrayList<Position>> areaGuiList;
	private JScrollPane listScrollPane;

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

	private JLabel farmerEmailText;

	private JTextField farmerEmail;
	
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
		addArea = new JButton("Legg til område");
		editArea = new JButton("Endre område");

		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Passord:");
		
		usernameField = new JTextField(10);
		usernameField.setMaximumSize(new Dimension(1000,20));
		
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setMaximumSize(new Dimension(1000,20));
		
		js = new JSeparator();

		areaBoxText = new JLabel("Områder:");
		areaList = new ArrayList<ArrayList<Node>>();

		areaGuiList = new DefaultListModel<ArrayList<Position>>();
		list = new JList(areaGuiList);
		listScrollPane = new JScrollPane(list);

		farmerEmailText = new JLabel("Epost:");
		farmerEmail = new JTextField();
		farmerEmail.setMinimumSize(new Dimension(100,20));
		farmerEmail.setPreferredSize(new Dimension(100,20));
		farmerEmail.setMaximumSize(new Dimension(200,20));

		listScrollPane.setEnabled(false);
		addArea.setEnabled(false);
		editArea.setEnabled(false);
		farmerEmail.setEnabled(false);
		
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
	 * @return farmer-object
	 */
	public Farmer getFarmer() {
		return this.farmer;
	}
	
	// All listeners is implemented as classes that implements the ActionListener-interface

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
	 * @param nodes - ArrayList<Node>
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
			if(panel.areaGuiList.size()!=0 && panel.list.getSelectedIndex() != -1){
				ArrayList<Position> temp = (ArrayList<Position>)panel.areaGuiList.get(panel.list.getSelectedIndex());
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
			NetHandler handler = programFrame.getNetHandler();
			Response loginResult = handler.login(usernameField.getText(),
					new String(passwordField.getPassword()));
			System.out.print("Logge inn: ");
			loginResult.consoletime();
			if (!handler.isError(loginResult.msg)) {
				loginButton.setEnabled(false);
				usernameField.setEditable(false);
				passwordField.setEditable(false);

				listScrollPane.setEnabled(true);
				addArea.setEnabled(true);
				editArea.setEnabled(true);
				farmerEmail.setEnabled(true);
				
				// Parse Response to create farmer
				farmer = JsonHandler.parseJsonAndReturnUser(loginResult);

				//Get farmer info
				farmerEmail.setText(farmer.getEmail());

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