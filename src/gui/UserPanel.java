package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import characters.Area;
import characters.Farmer;
import characters.Position;
import serverconnection.NetHandler;
import serverconnection.Response;
import serverconnection.JsonHandler;

/**
 * Class holding the user information and login
 * @author Andreas Lyngby
 * @author Håkon Ødegård Løvdal
 * @author Thomas Mathisen
 */
public class UserPanel extends JPanel {

	private ProgramFrame programFrame;

	private ArrayList<ArrayList<Position>> areaList;

	private JList<ArrayList<Position>> list;
	private DefaultListModel<ArrayList<Position>> areaGuiList;
	private JScrollPane listScrollPane;

	private JLabel areaBoxText;

	private JButton loginButton;
	private JButton addArea;
	private JButton editArea;
	private JButton deleteArea;
	private JButton createFarm;
	private JButton deleteFarm;
	private JButton addFarmCode;
	private JButton removeFarmCode;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField farmField;
	private JTextField farmCodeField;

	private JLabel usernameText;
	private JLabel passwordText;
	private JLabel farmText;
	private JLabel farmCodeText;

	private GroupLayout layout;

	private JSeparator js;
	private JSeparator js1;
	private JSeparator js2;

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
		deleteArea = new JButton("Slett område");
		createFarm = new JButton("Lag gård");
		deleteFarm = new JButton("Slett gård");
		addFarmCode = new JButton("Legg til kode");
		removeFarmCode = new JButton("Fjern kode");

		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Passord:");
		farmText = new JLabel("Gård:");
		farmCodeText = new JLabel("Gårdkode");

		usernameField = new JTextField(10);
		usernameField.setMaximumSize(new Dimension(1000,20));

		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setMaximumSize(new Dimension(1000,20));

		farmField = new JTextField();
		farmCodeField = new JTextField();

		js = new JSeparator();
		js1 = new JSeparator();
		js2 = new JSeparator();

		areaBoxText = new JLabel("Områder:");
		areaList = new ArrayList<ArrayList<Position>>();

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
		deleteArea.addActionListener(new DeleteAreaListener());
		addFarmCode.addActionListener(new AddFarmCode());
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
										.addGroup(layout.createSequentialGroup() 
												.addComponent(farmCodeText)
												.addComponent(farmCodeField)
												.addComponent(addFarmCode)
												.addComponent(removeFarmCode)
												)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addComponent(js1)
														)
														.addGroup(layout.createSequentialGroup()
																.addComponent(farmText)
																.addComponent(farmField)
																.addComponent(createFarm)
																.addComponent(deleteFarm)
																)
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																		.addComponent(js2)
																		)
																		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																				.addComponent(farmerEmailText)
																				.addComponent(farmerEmail)
																				.addComponent(areaBoxText)
																				.addComponent(listScrollPane)
																				.addGroup(layout.createSequentialGroup()
																						.addComponent(editArea)
																						.addComponent(addArea)
																						.addComponent(deleteArea)
																						)
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
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE) 
														.addComponent(farmCodeText)
														.addComponent(farmCodeField)
														.addComponent(addFarmCode)
														.addComponent(removeFarmCode)
														)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(js1)
																)
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																		.addComponent(farmText)
																		.addComponent(farmField)
																		.addComponent(createFarm)
																		.addComponent(deleteFarm)
																		)
																		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																				.addComponent(js2)
																				)
																				.addComponent(farmerEmailText)
																				.addComponent(farmerEmail)
																				.addComponent(areaBoxText)
																				.addComponent(listScrollPane)
																				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																						.addComponent(editArea)
																						.addComponent(addArea)
																						.addComponent(deleteArea)
																						)
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

	/**
	 * Disables or enables the addArea and editArea button if AreaEditFrame is opened or closed.
	 * @param bool
	 */
	public void setAreaOpenable(boolean bool){
		addArea.setEnabled(bool);
		editArea.setEnabled(bool);
	}

	/**
	 * Uses jsonHandler to get the farmer's areas from the server via netHandler.
	 * @return
	 */
	public ArrayList<Area>fetchAreas(){
		return JsonHandler.parseJsonAndReturnAreas(programFrame.getNetHandler().getAreas());
	}

	/**
	 * Gets the farmers areas from the server and adds them to the GUI-list.
	 */
	public void addFetchedAreasToGuiList(){
		MapPanel map = programFrame.getMapPanel();
		ArrayList<Area> serverAreas = fetchAreas();
		for (Area area : serverAreas){
			areaGuiList.add(0, area.getAreaPoints());
			farmer.addArea(area);
		}
		map.deleteAreas ();
		String coordinates = "";
		areaList = farmer.getAreaList();
		for (ArrayList<Position> positionList : areaList){//for hvert area i storlista
			coordinates = "";
			for (Position posObject : positionList){//for hvert positionelement i area
				coordinates+= posObject.getLatitude();
				coordinates += ",";
				coordinates+= posObject.getLongitude();
				coordinates += ",";
			}
			coordinates += positionList.get(0).getLatitude();
			coordinates += ",";
			coordinates += positionList.get(0).getLongitude();
			map.addArea (coordinates);
			map.showArea();
		}
	}


//public void removeServerArea(ArrayList<Position> )

//////////////////FIXFIXFIX

/**
 * Adds an area to the area list and 
 * Sends the whole list as strings to mapPanel.addArea() 
 * Adds the area to the farmer's list.
 * Sendt the area to the server.
 * 
 * @param list - ArrayList<Position>
 */
public void addArea(ArrayList<Position> list){
	MapPanel map = programFrame.getMapPanel();
	Area tempArea = farmer.addAreaAndReturn(list);
	programFrame.getNetHandler().createArea(tempArea);

	areaGuiList.addElement(list);
	map.deleteAreas ();
	String coordinates = "";
	areaList = farmer.getAreaList();

	for (ArrayList<Position> positionList : areaList){//for hvert area i storlista
		coordinates = "";
		for (Position posObject : positionList){//for hvert positionelement i area
			coordinates+= posObject.getLatitude();
			coordinates += ",";
			coordinates+= posObject.getLongitude();
			coordinates += ",";
		}
		coordinates += positionList.get(0).getLatitude();
		coordinates += ",";
		coordinates += positionList.get(0).getLongitude();
		map.addArea (coordinates);
		map.showArea();
	}

}

// All listeners is implemented as classes that implements the ActionListener-interface

class DeleteAreaListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		if(programFrame.getUserPanel().areaGuiList.size()!=0 && programFrame.getUserPanel().list.getSelectedIndex() != -1){
			ArrayList<Position> temp = (ArrayList<Position>)programFrame.getUserPanel().areaGuiList.get(programFrame.getUserPanel().list.getSelectedIndex());
			programFrame.getUserPanel().areaGuiList.remove(programFrame.getUserPanel().list.getSelectedIndex());
		}
	}
}

/**
 * Class for implementing ActionLister to make the button to edit the farm area.
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

class EditAreaListener implements ActionListener {

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
 * CreateFarmListener - creates a farm for the user.
 *
 * @author Andreas
 *
 */

class CreateFarmListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		if(programFrame.getUserPanel().farmer.getFarm()==null){
			String farmName = programFrame.getUserPanel().farmer.getFarm().toString();
			NetHandler nh = programFrame.getNetHandler();
			//nh.newFarm(farmName);
			nh.getUser();
		}
	}
}

/*
	class DeleteFarmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(programFrame.getUserPanel().farmer.getFarm()!=null){
				NetHandler nh = programFrame.getNetHandler();
				//deletefarm
			}
		}
	}*/

/**
 * Creates a farm share code the farmer can use to share his farm
 * @author Andreas
 *
 */

class CreateFarmCode implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		//if(programFrame.getUserPanel().farmer.getFarmCode()==null){
		NetHandler nh = programFrame.getNetHandler();
		nh.newFarmShareCode();
		nh.getUser();
		//String farmCode = programFrame.getUserPanel().farmer.getFarmCode();

		//}
	}
}

class AddFarmCode implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		String farmCode = programFrame.getUserPanel().farmCodeField.getText();
		NetHandler nh = programFrame.getNetHandler();
		if(nh.getFarmCode()==null){
			//nh.addFarmCode(farmCode);
		}
		System.out.println(nh.getFarmCode());
	}
}

/**
 * Removes the farmcode from the user who requests to remove it.
 * @author Andreas
 *
 */

class RemoveFarmCode implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		NetHandler nh = programFrame.getNetHandler();
		if(nh.getFarmCode()!=null){
			//noe for å fjerne farmcode
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

			//Fetches areas from server
			addFetchedAreasToGuiList();

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