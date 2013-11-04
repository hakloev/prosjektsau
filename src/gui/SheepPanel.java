package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import serverconnection.JsonHandler;
import characters.Sheep;
import serverconnection.Alarm;
import serverconnection.Response;

/**
 * Class to show and edit sheeps
 * @author Andreas Lyngby
 * @author Håkon Ødegård Løvdal
 * @author Thomas Mathisen
 */

public class SheepPanel extends JPanel implements ItemListener{
	
	private ProgramFrame programFrame;
	
	private JList<Sheep> list;
	private DefaultListModel<Sheep> sheepList;
	private JScrollPane listScrollPane;
	
	private JLabel sheepListText;
	private JLabel hasAlarm;
	
	private JButton deleteSheep;
	private JButton addSheep;
	private JButton newSheep;
	private JButton updateSheep;
	private JButton showMap;
	private JButton deleteMap;
	
	private JRadioButton infoMode;
	private JRadioButton updateMode;
	private ButtonGroup radioGroup1;
	private JRadioButton mapAll;
	private JRadioButton mapSelected;
	private ButtonGroup radioGroup2;
	
	private JSeparator designSeperator;
	private JSeparator designSeperator2;

	private JLabel sheepIdText;
	private JLabel sheepAgeText;
	private JLabel sheepNickText;
	private JLabel sheepPosText;
	private JLabel sheepWeightText;
	
	private JTextArea sheepId;
	private JTextArea sheepAge;
	private JTextArea sheepNick;
	private JTextArea sheepPos;
	private JTextArea sheepWeight;
	
	private GroupLayout layout;

	private boolean changingSheep; // Boolean telling if a sheep is changing or not
	private boolean creatingNewSheep;  // Boolean telling if a new sheep is being created

	
	public SheepPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		this.changingSheep = false;
		this.creatingNewSheep = false;
		initElements();
		initDesign();
	}
	
	public void initElements(){
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        sheepList = new DefaultListModel<Sheep>();
        
		list = new JList<Sheep>(sheepList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScrollPane = new JScrollPane(list);
		listScrollPane.setMinimumSize(new Dimension(120,100));
		listScrollPane.setMaximumSize(new Dimension(120,2000));
		
		sheepListText = new JLabel("Saueliste");
		hasAlarm = new JLabel("Har alarm: NEI");
		
		sheepIdText = new JLabel("ID:");
		sheepAgeText = new JLabel("Alder:");
		sheepNickText = new JLabel("Kallenavn:");
		sheepPosText = new JLabel("Posisjon:");
		sheepWeightText = new JLabel("Vekt:");
		
		sheepId = new JTextArea("ID");
		sheepId.setEditable(false);
		
		sheepAge = new JTextArea("Alder");
		sheepAge.setEditable(false);
		
		sheepNick = new JTextArea("Kallenavn");
		sheepNick.setEditable(false);
		
		sheepPos = new JTextArea("Posisjon");
		sheepPos.setEditable(false);
		
		sheepWeight = new JTextArea("Vekt");
		sheepWeight.setEditable(false);
		
		infoMode = new JRadioButton("Infomodus");
		updateMode = new JRadioButton("Oppdateringsmodus");
		updateMode.setSelected(true);
		radioGroup1 = new ButtonGroup();
		radioGroup1.add(infoMode);
		radioGroup1.add(updateMode);
		infoMode.setSelected(true);
		radioGroup1.setSelected(infoMode.getModel(), true);
		
		mapAll = new JRadioButton("Alle");
		mapSelected = new JRadioButton("Valgt");
		radioGroup2 = new ButtonGroup();
		radioGroup2.add(mapAll);
		radioGroup2.add(mapSelected);
		
		deleteSheep = new JButton("Slett sau");
		addSheep = new JButton("Legg til ny sau");
		newSheep = new JButton("Ny sau");
		updateSheep = new JButton("Oppdater sau");
		showMap = new JButton("Vis på kart");
		deleteMap = new JButton("Slett alle på kart");
		
		designSeperator = new JSeparator();
		designSeperator2 = new JSeparator();
		
		// Listeners for every button in the sheep panel, description given in each class
		list.addListSelectionListener(new ListListener());
		showMap.addActionListener(new ShowInMapListener());
		deleteMap.addActionListener(new DeleteMapListener());
		newSheep.addActionListener(new newSheepListener());
		addSheep.addActionListener(new addNewSheepListener());
		updateSheep.addActionListener(new EditSheepInfoListener());
		updateMode.addActionListener(new UpdateModeListener());
		infoMode.addActionListener(new InfoModeListener());
		deleteSheep.addActionListener(new DeleteSheepListener());
	}
	
	public void initDesign(){
		setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(sheepListText)
						)
						.addGroup(layout.createSequentialGroup()
							.addComponent(listScrollPane)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(designSeperator)
								.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
											.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(sheepIdText)
												.addComponent(sheepNickText)
												.addComponent(sheepAgeText)
												.addComponent(sheepWeightText)
												.addComponent(sheepPosText)
											)
											.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(sheepId)
												.addComponent(sheepNick)
												.addComponent(sheepAge)
												.addComponent(sheepWeight)
												.addComponent(sheepPos)
											)
											.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
													.addComponent(updateSheep)
												)
												.addComponent(infoMode)
												.addComponent(updateMode)
											)
										)
										.addComponent(hasAlarm)
										.addGroup(layout.createSequentialGroup()
											.addComponent(showMap)
											.addComponent(mapSelected)
											.addComponent(mapAll)
											.addComponent(deleteMap)
										) 
									)
								)
								.addComponent(designSeperator2)
								.addGroup(layout.createSequentialGroup()
										.addComponent(newSheep)
										.addComponent(addSheep)
										.addComponent(deleteSheep)
								)
							)
						)
					)
					.addContainerGap()
				)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(sheepListText)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(sheepIdText)
									.addComponent(sheepId)
								)
								.addComponent(infoMode,GroupLayout.Alignment.TRAILING)
							)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(sheepNickText)
								.addComponent(sheepNick)
								.addComponent(updateMode)
							)
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(sheepAgeText)
                        		.addComponent(sheepAge)
                        		.addComponent(updateSheep)
	                        )
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(sheepWeightText)
                        		.addComponent(sheepWeight)
	                        )
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        		.addComponent(sheepPosText)
                        		.addComponent(sheepPos)
	                        )
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(designSeperator)
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(hasAlarm)
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        		.addComponent(showMap)
                        		.addComponent(mapAll)
                        		.addComponent(mapSelected)
                        		.addComponent(deleteMap)
	                        )
	                        .addGap(20)
	                        .addComponent(designSeperator2)
	                        .addGap(20)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                        		.addComponent(newSheep)
	                        		.addComponent(addSheep)
	                        		.addComponent(deleteSheep)
	                        )
	                        .addGap(0, 300, Short.MAX_VALUE)
						)
						.addComponent(listScrollPane)
					)
					.addContainerGap()
				)
			)
		);
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		// WHAT DOES THE FOX SAY
	}
	
	/**
	 * Method called when user is logged in, adds all the sheep that the current user owns
	 * Should take a parameter userId or something like that.
	 */
	public void initUserSheeps(Response listOfSheeps) {
		System.out.println("Tid brukt for å legge til samtlige sauer: ");
		listOfSheeps.consoletime();
		ArrayList<Sheep> sheeps = JsonHandler.parseJsonAndReturnSheepList(listOfSheeps, programFrame.getUserPanel().getFarmer());
		if (!sheeps.isEmpty()) {
			for (Sheep s : sheeps) {
				addSheep(s);
			}
		}
	}

	/**
	 * Method to add sheep to sheepList
	 * @param sheep
	 */
	public void addSheep(Sheep sheep) {
		sheepList.addElement(sheep);
	}

	/**
	 * Method to add sheep to db
	 */
	private void addSheepToDb(Sheep s) {
		Response r = programFrame.getNetHandler().createSheep(s);
		System.out.println("Tid for å legge sau til i database: ");
		r.consoletime();
		changingSheep = true;
		sheepList.clear();
		initUserSheeps(programFrame.getNetHandler().getSheep(-1));
		changingSheep = false;
	}

	/**
	 * Method used to update the edited sheep to the database and locally to the sheepList
	 */
	private void updateSheepInDb() {
		// UPDATE AND SEND SHEEP TO SERVER, MUST BE DONE ASAP WHEN ONE CHARACTHER IS EDITED
		Sheep sheep = sheepList.getElementAt(list.getSelectedIndex());

		String posInput = sheepPos.getText();
		boolean posRegEx = posInput.matches("^[0-9]{1,2}\\.[0-9]{5,15},[0-9]{1,2}\\.[0-9]{5,15}$");
		boolean weightRegEx = sheepWeight.getText().matches("[0-9]+");
		boolean nameReqEx = sheepNick.getText().matches("[a-zA-ZæøåÆØÅ\\s]+");
		if ((posRegEx) && (nameReqEx) && (weightRegEx)) {  // RegEx that checks if it is correct position, name and weight. Can't change age
			String[] pos = posInput.split(",");
			sheep.setLocation(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]));
			sheep.setWeight(Integer.parseInt(sheepWeight.getText()));
			sheep.setNick(sheepNick.getText());
			// Update server below here
			Response r = programFrame.getNetHandler().updateSheep(sheep);
			System.out.println("Tid brukt for å oppdatere sau i database: ");
			r.consoletime();
		} else {
			JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Posisisjon angis på formen: 63.345343,10.435334\nDu kan ha opptil seks desimaler" +
					"\n\nKallenavn kan kun inneholde bokstaver\n\n" +
					"Vekt angis i gram på formen: 654354",
					"Inputfeil", JOptionPane.WARNING_MESSAGE);
		}



	}

	/**
	 * Method to remove a given sheep from the sheepList and database
	 * @param index Index tells which sheep is selected
	 */
	private void removeSheepInDb(int index) {
		int sheepId = sheepList.getElementAt(index).getIdNr();
		sheepList.remove(index);
		list.clearSelection();
		setEditable(false);
		// remove sheep s in db here with handler
		Response r = programFrame.getNetHandler().deleteSheep(sheepId);
		System.out.println("Tid brukt for å fjerne en sau i databasen: ");
		r.consoletime();
	}
	
	/**
	 * Method that sets sheep editable or not by what the parameter is. Also sets the textareas to standard text
	 * @param bool boolean declaring if the user can edit the sheep
	 */
	private void setEditable(boolean bool) {
		sheepId.setEditable(false); // id må genereres selv
		sheepNick.setEditable(bool);
		sheepAge.setEditable(bool);
		sheepWeight.setEditable(bool);
		sheepPos.setEditable(bool);
		
		sheepId.setText("ID genereres av serveren");
		sheepNick.setText("Kallenavn");
		sheepAge.setText("Tast inn fødselsår");
		sheepWeight.setText("Vekt");
		sheepPos.setText("Breddegrad: Lengdegrad: ");
		hasAlarm.setText("Har Alarm: NEI");
	}
	/**
	 * Method that sets sheep etidable or not by what the parameter is. Sets the textareas to the sheep info currently displayed
	 * @param bool boolean declaring if the user can edit the sheep
	 */
	private void setEditableWithSheepInfo(boolean bool) {
		sheepId.setEditable(false); // id må genereres selv
		sheepNick.setEditable(bool);
		sheepAge.setEditable(false);
		sheepWeight.setEditable(bool);
		sheepPos.setEditable(bool);
	}
	
	// All the button listeners, implemented as classes with listener-interfaces
	
	/**
	 * Listener for what sheep is currently marked in the sheepList
	 * @author Håkon Ødegård Løvdal
	 */
	class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!changingSheep) {
				if (!updateMode.isSelected()) {
					if (!e.getValueIsAdjusting()) {
						AlarmPanel alarm = programFrame.getAlarmPanel();
						Sheep sheep = list.getSelectedValue();
						Response json = programFrame.getNetHandler().getSheep(sheep.getIdNr());
						System.out.println("Tid brukt for å hente en sau: ");
						json.consoletime();
						sheep = JsonHandler.parseJsonAndReturnSheep(json, programFrame.getUserPanel().getFarmer());
						sheepId.setText(Integer.toString(sheep.getIdNr()));
						sheepNick.setText(sheep.getNick());
						sheepAge.setText(Integer.toString(sheep.getAgeOfSheep()));
						sheepWeight.setText(Integer.toString(sheep.getWeight()));
						sheepPos.setText(sheep.getLocation().getLatitude() + "," + sheep.getLocation().getLongitude());
						if (sheep.getAlarmStatus()) {
							hasAlarm.setText("Har alarm: JA");
						}  else {
							hasAlarm.setText("Har alarm: NEI");
						}
					}
				} else {
					JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du er i oppdateringsmodus, endre til infomodus",
						"Modusfeil", JOptionPane.WARNING_MESSAGE);
					changingSheep = true; // To avoid double clearing
					list.clearSelection();
					changingSheep = false;
				}
			}
		}
	}
	
	/**
	 * Listener for the "Vis på kart"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class ShowInMapListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonModel bm = radioGroup2.getSelection();
			if (bm == null) {
				JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du må velge om du vil vise en eller alle sauer", 
						"Kartfeil", JOptionPane.WARNING_MESSAGE);
			} else {
				MapPanel map = programFrame.getMapPanel();
				if (mapSelected.isSelected()) {
					if (!list.isSelectionEmpty()) {
						Sheep sheep = list.getSelectedValue();
						if (sheep.getAlarmStatus()) {
							map.addMarker("ALARM: " + sheep.getNick(), sheep.getLocation().getLatitude(), sheep.getLocation().getLongitude());
						} else {
							map.addMarker(sheep.getNick(), sheep.getLocation().getLatitude(), sheep.getLocation().getLongitude());
						}
						programFrame.getJTabbedPane().setSelectedIndex(2);
					} else {
						JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du må velge en sau for å legge den til", 
								"Kartfeil", JOptionPane.WARNING_MESSAGE);
					}
				} else if (mapAll.isSelected() && (!sheepList.isEmpty())) {
					for (int i = 0; i < list.getModel().getSize(); i++) {
						Sheep sheep = list.getModel().getElementAt(i);
						if (sheep.getAlarmStatus()) {
							map.addMarker("ALARM: " + sheep.getNick(), sheep.getLocation().getLatitude(), sheep.getLocation().getLongitude());
						} else {
							map.addMarker(sheep.getNick(), sheep.getLocation().getLatitude(), sheep.getLocation().getLongitude());
						}
/////////////////////////////////////////////SKAL FJERNES SENERE (ADDPOLY)///////////////////////						
						map.addArea();
					}
					programFrame.getJTabbedPane().setSelectedIndex(2);
				} else {
					JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du har ingen sauer å vise i kartet!",
							"Kartfeil", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	
	/**
	 * Listener for the "Slett alle på kart"-button 
	 * @author Håkon Ødegård Løvdal
	 */
	class DeleteMapListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			programFrame.getMapPanel().deleteMarkers();
		}
	}
	
	/**
	 * Listener for the "Ny sau"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class newSheepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			changingSheep = true;
			list.clearSelection();
			if (infoMode.isSelected()) {
				updateMode.setSelected(true);
				infoMode.setSelected(false);
				radioGroup1.setSelected(updateMode.getModel(), true);
			}
			setEditable(true);
			creatingNewSheep = true;
			changingSheep = false;
		}
	}
	
	/**
	 * Listener for "Legg til ny sau"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class addNewSheepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (updateMode.isSelected()) {
				String posInput = sheepPos.getText();
				boolean posRegEx = posInput.matches("^[0-9]{1,2}\\.[0-9]{5,15},[0-9]{1,2}\\.[0-9]{5,15}$");
				boolean yearRegEx = sheepAge.getText().matches("[2][0]([0][0-9]|[1][0-3])");
				boolean weightRegEx = sheepWeight.getText().matches("[0-9]+");
				boolean nameReqEx = sheepNick.getText().matches("[a-zA-ZæøåÆØÅ\\s]+");
				if ((posRegEx) && (yearRegEx) && (nameReqEx) && (weightRegEx)) {  // RegEx that checks if it is correct position, name and age format
					String[] pos = posInput.split(",");
					Sheep sheep = new Sheep(666, sheepNick.getText(), Integer.parseInt(sheepAge.getText()), Integer.parseInt(sheepWeight.getText()),
							programFrame.getUserPanel().getFarmer(), (new Random().nextInt(50) + 50), Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), new Integer(1));   // satt puls til 100
					sheepList.addElement(sheep);
					addSheepToDb(sheep);
					creatingNewSheep = false;
					setEditable(false);
					updateMode.setSelected(false);
					infoMode.setSelected(true);
					radioGroup1.setSelected(infoMode.getModel(), true);
				} else {
					JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Posisisjon angis på formen: 63.345343,10.435334\nDu kan ha opptil seks desimaler" +
							"\n\nAlder angis på formen 2000\n" +
							"Du kan ha alder fra 2000-2013\n\n" +
							"Vekt angis i gram på formen: 643454\n\n" +
							"Kallenavn kan kun inneholde bokstaver",
							"Inputfeil", JOptionPane.WARNING_MESSAGE);
					System.out.println("" + posRegEx + yearRegEx + nameReqEx);
				}
			}  else {
				JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du kan legge til ny sau uten å trykke \"Ny sau\"",
						"Modusfeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Listener for the "Oppdater sau"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class EditSheepInfoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (updateMode.isSelected() && !creatingNewSheep) {
				setEditableWithSheepInfo(false);
				updateSheepInDb();
				updateMode.setSelected(false);
				infoMode.setSelected(true);
				radioGroup1.setSelected(infoMode.getModel(), true);
			} else {
				if (creatingNewSheep) {
					JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du kan ikke oppdatere en sau når\ndu lager en ny sau.",
							"Modusfeil", JOptionPane.WARNING_MESSAGE);
					updateMode.setSelected(true);
					radioGroup1.setSelected(updateMode.getModel(), true);
				} else {
					JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du kan ikke oppdatere en sau uten å\nvære i oppdateringsmodus",
							"Modusfeil", JOptionPane.WARNING_MESSAGE);
					setEditableWithSheepInfo(false);
				}
			}
		}
	}

	
	/**
	 * Listener for the "Oppdateringsmodus"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class UpdateModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(programFrame.getSheepPanel(), "Du må ha valgt en sau for å gå i editeringsmodus",
						"Posisjonsfeil", JOptionPane.WARNING_MESSAGE);
				updateMode.setSelected(false);
				infoMode.setSelected(true);
				radioGroup1.setSelected(infoMode.getModel(), true);
			} else {
				setEditableWithSheepInfo(true);
			}
		}
	}
	
	/**
	 * Listener for the "Infomodus"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class InfoModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// kan nå gå fra oppdatering til info uten å lagre endringer.. 
			// må gjøres noe med
			setEditableWithSheepInfo(false);
			infoMode.setSelected(true);
			radioGroup1.setSelected(infoMode.getModel(), true);
		}
	}

	/**
	 * Listener for the "Slett sau"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class DeleteSheepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Du må velge en sau for å slette",
						"Seleksjonsfeil", JOptionPane.WARNING_MESSAGE);
			} else {
				changingSheep = true;
				int index = list.getSelectedIndex();
				if (index >= 0) {
					removeSheepInDb(index);
					changingSheep = false;
					// TO RETURN IF DONE??
				} // The else-condition here should not happen
			}
		}
	}
}
