package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import characters.Sheep;
import serverconnection.Alarm;

public class AlarmPanel extends JPanel{

	private ProgramFrame programFrame;
	
	private JList<Alarm> list;
	private DefaultListModel<Alarm> alarmList;
	private JScrollPane listScrollPane;
	
	private JLabel sheepIdText;
	private JLabel alarmTimeText;
	private JLabel alarmDescText;
	private JLabel alarmPosText;
	
	private JTextArea sheepId;
	private JTextArea alarmTime;
	private JTextArea alarmDesc;
	private JTextArea alarmPos;
	
	private JButton deleteAlarm;
	
	private GroupLayout layout;
	
	// Used to deactivate listselectionlistner when deleting a sheep
	private boolean changing;
	
	public AlarmPanel(ProgramFrame programFrame){
		this.programFrame = programFrame;
		initElements();
		initDesign();
		changing = false;
	}

	private void initElements() {
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        alarmList = new DefaultListModel<Alarm>();
        
		list = new JList<Alarm>(alarmList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScrollPane = new JScrollPane(list);
		listScrollPane.setMinimumSize(new Dimension(120,100));
		listScrollPane.setMaximumSize(new Dimension(120,2000));
		
		sheepIdText = new JLabel("Id:");
		alarmTimeText = new JLabel("Alarmtid:");
		alarmDescText = new JLabel("Beskrivelse:");
		alarmPosText = new JLabel("Posisjon:");
		
		sheepId = new JTextArea("ID");
		sheepId.setMinimumSize(new Dimension(100,20));
		sheepId.setMaximumSize(new Dimension(2000,20));
		alarmTime = new JTextArea("Tid");
		alarmTime.setMinimumSize(new Dimension(100,20));
		alarmTime.setMaximumSize(new Dimension(2000,20));
		alarmDesc = new JTextArea("Beskrivelse");
		alarmDesc.setMinimumSize(new Dimension(100,40));
		alarmDesc.setMaximumSize(new Dimension(2000,40));
		alarmPos = new JTextArea("Posisjon");
		alarmPos.setMinimumSize(new Dimension(100,20));
		alarmPos.setMaximumSize(new Dimension(2000,20));
		
		deleteAlarm = new JButton("Slett alarm");
		
		// Listener for buttons and list
		deleteAlarm.addActionListener(new DeleteAlarmListener());
		list.addListSelectionListener(new ShowAlarmListener());
		
		// Alarms for testing
		addAlarm(new Alarm(new Sheep(16, "Knut", 1994, this.programFrame.getUserPanel().getFarmer(), 64.2555, 11.44334), "Killed in Iraq"));
		addAlarm(new Alarm(new Sheep(15, "Link", 1992, this.programFrame.getUserPanel().getFarmer(), 63.2555, 10.44334), "Killed in Action"));
	}
	
	private void initDesign() {
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(listScrollPane)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(sheepIdText)
				.addComponent(sheepId)
				.addComponent(alarmTimeText)
				.addComponent(alarmTime)
				.addComponent(alarmDescText)
				.addComponent(alarmDesc)
				.addComponent(alarmPosText)
				.addComponent(alarmPos)
				.addComponent(deleteAlarm)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(listScrollPane)
				.addGroup(layout.createSequentialGroup()
					.addComponent(sheepIdText)
					.addComponent(sheepId)
					.addComponent(alarmTimeText)
					.addComponent(alarmTime)
					.addComponent(alarmDescText)
					.addComponent(alarmDesc)
					.addComponent(alarmPosText)
					.addComponent(alarmPos)
					.addComponent(deleteAlarm)
				)
			)
		);
		
	}
	
	/**
	 * Method for adding an alarm, takes the Alarm-object as paramteter
	 * @param alarm
	 */
	public void addAlarm(Alarm alarm) {
		alarmList.addElement(alarm);
		list.setSelectedIndex(alarmList.size() - 1); // Set alarm to the latest
		//programFrame.getJTabbedPane().setSelectedIndex(4); // Must not be wile init
	}
	
	/**
	 * Method for reseting alarm-textfields to the standard text
	 */
	private void clearAlarmSections() {
		sheepId.setText("ID");
		alarmTime.setText("Tid");
		alarmDesc.setText("Beskrivelse");
		alarmPos.setText("Posisjon");
	}
	
	// Classes for listners to ListSelection and DeleteAlarmButton
	
	/**
	 * Listener for the "Slett alarm"-button
	 * @author Håkon Ødegård Løvdal
	 */
	class DeleteAlarmListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Du må velge en alarm for å slette", 
						"Alarmfeil", JOptionPane.WARNING_MESSAGE);
			} else {
				changing = true;
				int index = list.getSelectedIndex();
				if (index >= 0) {
					alarmList.remove(index);
					list.clearSelection();
					clearAlarmSections();
					changing = false;
				} // ELSE HERE? TO RETURN
			}
		}
		
	}
	
	/**
	 * Class for showing the selected alarm
	 * @author Håkon Ødegård Løvdal
	 */
	class ShowAlarmListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!changing) { // Check if this event is called from DeleteAlarmListener
				if (!e.getValueIsAdjusting()) {
					Alarm alarm = list.getSelectedValue();
					sheepId.setText(alarm.getSheepId());
					alarmTime.setText(alarm.getAlarmDate());
					alarmDesc.setText(alarm.getAlarmDescription());
					alarmPos.setText(alarm.getAlarmPostition());
				} // No need for ELSE?
			}
		}
	}

}
