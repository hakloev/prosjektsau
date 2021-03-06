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
	private JButton logoutButton;
	private JButton addArea;
	private JButton editArea;
	private JButton deleteArea;
	private JButton createFarm;
	private JButton editFarm;
	private JButton deleteFarm;
	private JButton createFarmCode;
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

	/**
	 * Constructor for UserPanel
	 * @param programFrame
	 */
	public UserPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		initElements();
		initDesign();
	}

	private void initElements(){
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		loginButton = new JButton("Logg inn");
		logoutButton = new JButton("Logg ut");
		logoutButton.setEnabled(false);
		addArea = new JButton("Legg til område");
		editArea = new JButton("Endre område");
		deleteArea = new JButton("Slett område");
		createFarm = new JButton("Lag gård");
		deleteFarm = new JButton("Slett gård");
		editFarm = new JButton("Nytt navn");
		createFarmCode = new JButton("Lag delekode");
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
		farmerEmail.setEditable(false);

		listScrollPane.setEnabled(false);
		addArea.setEnabled(false);
		editArea.setEnabled(false);
		farmerEmail.setEnabled(false);
		farmField.setEnabled(false);
		farmCodeField.setEnabled(false);
		deleteArea.setEnabled(false);
		createFarm.setEnabled(false);
		editFarm.setEnabled(false);
		deleteFarm.setEnabled(false);
		createFarmCode.setEnabled(false);
		addFarmCode.setEnabled(false);
		removeFarmCode.setEnabled(false);
		areaBoxText.setEnabled(false);
		farmerEmailText.setEnabled(false);
		farmText.setEnabled(false);
		farmCodeText.setEnabled(false);


		// Listeners
		loginButton.addActionListener(new LoginListener());
		logoutButton.addActionListener(new LogoutListener());
		addArea.addActionListener(new AddAreaListener(this));
		editArea.addActionListener(new EditAreaListener(this));
		createFarm.addActionListener(new CreateFarmListener());
		editFarm.addActionListener(new EditFarmNameListener());
		deleteFarm.addActionListener(new DeleteFarmListener());
		deleteArea.addActionListener(new DeleteAreaListener());
		createFarmCode.addActionListener(new CreateFarmCodeListener());
		addFarmCode.addActionListener(new AddFarmCodeListener());
		removeFarmCode.addActionListener(new RemoveFarmCodeListener());
	}

	private void initDesign(){
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameText)
										.addComponent(loginButton)
								)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameField)
										.addComponent(logoutButton)
								)
								.addComponent(passwordText)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(passwordField)
								)
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
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(loginButton)
										.addComponent(logoutButton)
								)
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
	 * @return arraylist of areas
	 */
	public ArrayList<Area>fetchAreas(){
		if (farmer.getFarmId() != 0){
			return JsonHandler.parseJsonAndReturnAreas(programFrame.getNetHandler().getAreas());
		}
		return new ArrayList<Area>();
	}

	/**
	 * Method for deleting an area from the server.
	 * Takes the area, gets the areaID and sends the request to server.
	 * @param area
	 */
	public void deleteAreafromServer(ArrayList<Position> area){
		ArrayList<Area>serverAreas = JsonHandler.parseJsonAndReturnAreas(programFrame.getNetHandler().getAreas());

		for (Area serverArea : serverAreas){
			int counter = 0;
			for (int index = 0; index < area.size(); index++) {
				if (area.size() == serverArea.getAreaPoints().size()){
					if (area.get(index).getLatitude() == (serverArea.getAreaPoints().get(index).getLatitude())){
						if (area.get(index).getLongitude() == (serverArea.getAreaPoints().get(index).getLongitude())){
							counter++;
						}
					}
				}
			}if (counter == area.size()){
				programFrame.getNetHandler().deleteArea(serverArea.getId());
			}
		}
	}



	/**
	 * Gets the farmers areas from the server and adds them to the GUI-list.
	 */
	public void addFetchedAreasToGuiList(){
		MapPanel map = programFrame.getMapPanel();
		ArrayList<Area> serverAreas = fetchAreas();
		if (serverAreas.isEmpty()) {
			return;
		}
		for (Area area : serverAreas){
			areaGuiList.add(0, area.getAreaPoints());
			farmer.addArea(area);
		}
		map.deleteAreas ();
		String coordinates = "";
		areaList = farmer.getAreaPositionList();
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
		areaList = farmer.getAreaPositionList();

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
	private class DeleteAreaListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(programFrame.getUserPanel().areaGuiList.size()!=0 && programFrame.getUserPanel().list.getSelectedIndex() != -1){
				ArrayList<Position> temp = (ArrayList<Position>)programFrame.getUserPanel().areaGuiList.get(programFrame.getUserPanel().list.getSelectedIndex());
				programFrame.getUserPanel().areaGuiList.remove(programFrame.getUserPanel().list.getSelectedIndex());
				programFrame.getUserPanel().deleteAreafromServer(temp);
				programFrame.getUserPanel().farmer.removeArea(temp);
				programFrame.getMapPanel().deleteAreas();
				addFetchedAreasToGuiList();
			}
		}
	}

	/**
	 * Class for implementing ActionLister to make the button to edit the farm area.
	 * @author Andreas Lynby
	 */
	private class AddAreaListener implements ActionListener{

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
			panel.setAreaOpenable(false);
			AreaEditFrame a = new AreaEditFrame(panel.programFrame, null);
			a.toFront();
			a.requestFocusInWindow();
		}
	}

	/**
	 * Class for the button to edit area
	 * @author Andreas Lyngby
	 */
	private class EditAreaListener implements ActionListener {

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
				panel.deleteAreafromServer(temp);
				panel.setAreaOpenable(false);
				AreaEditFrame a = new AreaEditFrame(panel.programFrame, temp);
				a.toFront();
				a.requestFocusInWindow();
				panel.farmer.removeArea(temp);
				programFrame.getMapPanel().deleteAreas();
				addFetchedAreasToGuiList();
			}
		}
	}

	/**
	 * CreateFarmListener - creates a farm for the user.
	 * @author Andreas Lyngby
	 */
	private class CreateFarmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(farmer.getFarm()==null){
				String farmName = farmField.getText();
				NetHandler nh = programFrame.getNetHandler();
				Response r;
				r = nh.newFarm();
				if(r == null){
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke lage gård",
							"Databasefeil", JOptionPane.WARNING_MESSAGE);
				}else{
					r = nh.getUser();
					farmer = JsonHandler.parseJsonAndReturnUser(r);
					farmer.setFarmId(nh.getFarmID());
					r = nh.updateFarm(farmName, null);
					if(r == null){
						JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke opprette navn.",
								"Databasefeil", JOptionPane.WARNING_MESSAGE);
					}
					r = nh.getUser();
					farmer = JsonHandler.parseJsonAndReturnUser(r);
					farmer.setFarmId(nh.getFarmID());
					r = nh.getFarm(farmer.getShareCode());
					farmer.setFarm(JsonHandler.parseJsonAndReturnNewFarm(r));
					String newFarmName = farmer.getFarm().getFarmName();
					farmField.setText(newFarmName);
					farmCodeField.setText(farmer.getShareCode());
					nh.setFarmCode(String.valueOf(farmer.getFarmerId()));
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Laget gård med navn: " + newFarmName,
							"Laget ny gård", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				programFrame.getUserPanel().farmField.setText(programFrame.getUserPanel().farmer.getFarm().getFarmName());
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Har allerede gård",
						"Har gård allerede", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Deletes the farm you have. Gives error message if you can't delete it.
	 * @author Andreas Lyngby
	 */
	class DeleteFarmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(farmer.getFarm()!=null){
				NetHandler nh = programFrame.getNetHandler();
				Response r = nh.deleteFarm();
				if(r==null){
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke slette gård",
							"Databasefeil", JOptionPane.WARNING_MESSAGE);
				}else{
					Response userInfo = nh.getUser();
					farmer = JsonHandler.parseJsonAndReturnUser(userInfo);
					farmer.setFarm(null);
					farmCodeField.setText("");
					nh.setFarmCode("");
					farmField.setText("Ingen farm");
					programFrame.getAlarmPanel().clearAlarmList();
					programFrame.getSheepPanel().clearSheepList();
					programFrame.getLogPanel().clearLogList();
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Slettet gård",
							"Slettet gård", JOptionPane.OK_OPTION);
				}
			}else{
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke slette gård",
						"Ingen gård å slette/Databasefeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Class for a possible edit farm name button. Needs more logic for proper functionality.
	 * Takes the tekst of the farmfield(gård - tekstboks) and uses the current text to give the current farm a new name.
	 * Probably needs some checkboxes or radio buttons, and/or its own text field.
	 * @author Andreas Lyngby
	 */
	class EditFarmNameListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(farmer.getFarm()!=null){
				NetHandler nh = programFrame.getNetHandler();
				String editedFarmName = farmField.getText();
				nh.updateFarm(editedFarmName, null);
				Response r = nh.getUser();
				farmer = JsonHandler.parseJsonAndReturnUser(r);
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Endret gård sitt navn til: " + editedFarmName,
						"Endret navn på gård", JOptionPane.OK_OPTION);
			}else{
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke endre navn",
						"Ingen gård å endre/Databasefeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Creates a farm share code the farmer can use to share his farm
	 * @author Andreas Lyngby
	 */
	private class CreateFarmCodeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(farmer.getFarm() != null){
				NetHandler nh = programFrame.getNetHandler();
				Response r = nh.newFarmShareCode(false);
				if(r == null){
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke lage ny delekode",
							"Databasefeil", JOptionPane.WARNING_MESSAGE);
				}else{
					nh.getUser();
					String farmCode = nh.getFarmCode();
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Ny delekode for gård: " + farmCode,
							"Ny delekode.", JOptionPane.OK_OPTION);
				}
			}
		}
	}

	/**
	 * Listener to add farmcode to farmer
	 * @author Andreas Lyngby
	 */
	private class AddFarmCodeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			String farmCode = programFrame.getUserPanel().farmCodeField.getText();
			NetHandler nh = programFrame.getNetHandler();
			if(nh.getFarmCode().equals("")){
				String farmShareCode = farmCodeField.getText();
				Response r = nh.useFarmShareCode(farmShareCode);
				if(r == null){
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke legge til delekode",
							"Gårdkode", JOptionPane.WARNING_MESSAGE);
				}else{
					r = nh.getUser();
					farmer = JsonHandler.parseJsonAndReturnUser(r);
					r = nh.getFarm(farmCode);
					farmer.setFarm(JsonHandler.parseJsonAndReturnNewFarm(r));
					farmer.setFarmId(farmer.getFarm().getfarmID());
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Delekode lagt til og gård hentet",
							"Gårdkode", JOptionPane.INFORMATION_MESSAGE);
					Response rr = nh.getSheep(-1);
					farmField.setText(farmer.getFarm().getFarmName());
					programFrame.getMapPanel().deleteAreas();
					addFetchedAreasToGuiList();
					programFrame.getSheepPanel().initUserSheeps(rr);
				}
			} else {
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Har allerede gårdkode",
						"Gårdkode", JOptionPane.WARNING_MESSAGE);
				farmCodeField.setText(nh.getFarmCode());
			}
		}
	}

	/**
	 * Removes the farmcode from the user who requests to remove it.
	 * @author Andreas Lyngby
	 */
	private class RemoveFarmCodeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			NetHandler nh = programFrame.getNetHandler();
			if(nh.getFarmCode()!=null && farmer.getFarm().getOwnerID()!=farmer.getFarmerId()){
				String farmShareCode = "";
				Response r = nh.useFarmShareCode(farmShareCode);
				if(r == null){
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kunne ikke slette delekode",
							"Gårdkode", JOptionPane.WARNING_MESSAGE);
				}else{
					r = nh.getUser();
					farmer = JsonHandler.parseJsonAndReturnUser(r);
					farmer.setFarm(null);
					programFrame.getSheepPanel().clearSheepList();
					nh.setFarmCode("");
					JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Delekode for gård fjernet. \nRestart programmet for best effekt.",
							"Gårdkode", JOptionPane.INFORMATION_MESSAGE);
					farmField.setText("Ingen gård");
					farmCodeField.setText("");
					areaGuiList.clear();
					list.clearSelection();
					areaList.clear();
					programFrame.getAlarmPanel().clearAlarmList();
					programFrame.getLogPanel().clearLogList();
				}
			}else{
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Kan ikke slette delekode: Gårdseier/ingen delkode å slette",
						"Gårdkode", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Listener for the loginButton
	 * @author Håkon Ødegård Løvdal
	 */
	private class LoginListener implements ActionListener {

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
				farmField.setEnabled(true);
				farmCodeField.setEnabled(true);
				deleteArea.setEnabled(true);
				createFarm.setEnabled(true);
				editFarm.setEnabled(true);
				deleteFarm.setEnabled(true);
				createFarmCode.setEnabled(true);
				addFarmCode.setEnabled(true);
				removeFarmCode.setEnabled(true);
				areaBoxText.setEnabled(true);
				farmerEmailText.setEnabled(true);
				farmText.setEnabled(true);
				farmCodeText.setEnabled(true);

				// Parse Response to create farmer
				farmer = JsonHandler.parseJsonAndReturnUser(loginResult);
				farmer.setFarmId(handler.getFarmID());
				if(!handler.getFarmCode().equals("")){
					Response getFarmResult = handler.getFarm(handler.getFarmCode());
					if(!handler.isError(getFarmResult.msg)){
						farmer.setFarm(JsonHandler.parseJsonAndReturnNewFarm(getFarmResult));
						farmField.setText(farmer.getFarm().getFarmName());
						farmCodeField.setText(handler.getFarmCode());
					}else{
						farmField.setText("Ingen farm");
						farmer.setFarm(null);
					}
				}else{
					farmField.setText("Ingen farm");
					farmer.setFarm(null);
				}
			
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

				logoutButton.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(programFrame.getUserPanel(), loginResult.msg + "\nPrøv på nytt",
						"Innloggingsfeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		    Response r = programFrame.getNetHandler().logout();
			System.out.print("Logget ut: ");
			r.consoletime();
			JOptionPane.showMessageDialog(programFrame.getUserPanel(), "Logget ut!",
					"Utlogging", JOptionPane.WARNING_MESSAGE);
			farmer = null;


			usernameField.setText("");
			passwordField.setText("");
			usernameField.setEditable(true);
			passwordField.setEditable(true);
			programFrame.getJTabbedPane().setEnabledAt(1, false);
			programFrame.getJTabbedPane().setEnabledAt(2, false);
			programFrame.getJTabbedPane().setEnabledAt(3, false);
			programFrame.getJTabbedPane().setEnabledAt(4, false);

			listScrollPane.setEnabled(false);
			addArea.setEnabled(false);
			editArea.setEnabled(false);
			farmerEmail.setEnabled(false);
			farmField.setEnabled(false);
			farmCodeField.setEnabled(false);
			deleteArea.setEnabled(false);
			createFarm.setEnabled(false);
			editFarm.setEnabled(false);
			deleteFarm.setEnabled(false);
			createFarmCode.setEnabled(false);
			addFarmCode.setEnabled(false);
			removeFarmCode.setEnabled(false);
			areaBoxText.setEnabled(false);
			farmerEmailText.setEnabled(false);
			farmText.setEnabled(false);
			farmCodeText.setEnabled(false);

			farmerEmail.setText("");
			farmField.setText("");
			farmCodeField.setText("");
			areaBoxText.setText("");

			areaGuiList.clear();
			list.clearSelection();
			areaList.clear();

			programFrame.getSheepPanel().clearSheepList();
			programFrame.getLogPanel().clearLogList();
			programFrame.getAlarmPanel().clearAlarmList();
			programFrame.getMapPanel().deleteMarkers();


			logoutButton.setEnabled(false);
			loginButton.setEnabled(true);
			repaint();

		}
	}
}