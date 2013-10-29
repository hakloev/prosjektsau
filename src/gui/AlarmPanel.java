package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;

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
import mail.GMail;
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
	private JTextArea alarmEmail;
	
	private JButton deleteAlarm;
	private JButton showAlarm;
	private JButton sendAlarm;
	
	private GroupLayout layout;
	
	// Used to deactivate listSelectionListner when deleting a sheep
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
		showAlarm = new JButton("Vis alarm på kart");
		sendAlarm = new JButton("Send alarm som epost");
		alarmEmail = new JTextArea("Vil du sende noen alarmen?");
		alarmEmail.setMaximumSize(new Dimension(200, 25));

		setEditable(false);
		
		// Listener for buttons and list
		deleteAlarm.addActionListener(new DeleteAlarmListener());
		showAlarm.addActionListener(new ShowAlarmInMapListener());
		sendAlarm.addActionListener(new SendMailListener());
		list.addListSelectionListener(new ShowAlarmListener());
		
		// Alarms and sheeps for testing
		Sheep sheep1 = new Sheep(16, "Knut", 1994, this.programFrame.getUserPanel().getFarmer(), 100, 62.10745, 9.76686);
		Sheep sheep2 = new Sheep(15, "Link", 1992, this.programFrame.getUserPanel().getFarmer(), 100, 61.99745, 9.46686);
		addAlarm(new Alarm(sheep1, "Killed in Action"));
		addAlarm(new Alarm(sheep2, "Killed by a tourist bus from Germany"));
		sheep1.setAlarmStatus(true);
		sheep2.setAlarmStatus(true);
	}

	private void setEditable(boolean bool) {
		sheepId.setEditable(bool);
		alarmTime.setEditable(bool);
		alarmDesc.setEditable(bool);
		alarmPos.setEditable(bool);
		alarmEmail.setEditable(true);
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
					.addComponent(showAlarm)
					.addGroup(layout.createSequentialGroup()
							.addComponent(sendAlarm)
							.addComponent(alarmEmail)
					)
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
						.addComponent(showAlarm)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(sendAlarm)
								.addComponent(alarmEmail)
						)
				)
			)
		);
		
	}
	
	/**
	 * Method for adding an alarm
	 * @param alarm Takes in an alarm-object
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

	/**
	 * Method to get alarmList
	 * @return DefaultListModel holding alarms
	 */
	public DefaultListModel<Alarm> getAlarmList() {
		return alarmList;
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
					alarmList.getElementAt(index).getSheep().setAlarmStatus(false);
					alarmList.remove(index);
					list.clearSelection();
					clearAlarmSections();
					changing = false;
				} // ELSE HERE? TO RETURN  IF DONE??
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

	/**
	 * Class to show alarm in map
	 * @author Håkon Ødegård Løvdal
	 */
	class ShowAlarmInMapListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MapPanel map = programFrame.getMapPanel();
			if (!list.isSelectionEmpty()) {
				map.deleteMarkers();
				Alarm alarm = list.getSelectedValue();
				map.addMarker("ALARM: " + alarm.getSheep().getNick(), alarm.getSheep().getLocation().getLatitude(), alarm.getSheep().getLocation().getLongitude());
					programFrame.getJTabbedPane().setSelectedIndex(2);
			} else {
				JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Du må velge en alarm for å legge den til",
						"Kartfeil", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Class to send alarm as mail
	 * @author Håkon Ødegård Løvdal
	 */
	class SendMailListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		    if (!list.isSelectionEmpty()) {
			    Alarm alarm = list.getSelectedValue();
			    GMail sender = new GMail();
			    String recipient = alarmEmail.getText();
			    if (recipient.contains("@")) {
				    String subject = "ALARM: " + alarm.getSheep().toString();
				    String text = "Du har blitt tilsendt en epost med informasjon om alarmen til " + alarm.getSheep().toString()
						    + "\n\nTid: " + alarm.getAlarmDate() + "\nBeskrivelse: " + alarm.getAlarmDescription()
						    + "\nPosisjon: " + alarm.getAlarmPostition()
						    + "\n\nHilsen " + programFrame.getUserPanel().getFarmer().getUserName();
				    boolean sendStatus = sender.sendMail(recipient, subject, text);
				    if (sendStatus) {
					    JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Epost ble sendt! Mottaker: " + recipient,
							    "Epost", JOptionPane.INFORMATION_MESSAGE);
				    } else {
					    JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Vi klarte dessverre ikke å sende eposten!\nMottaker: " + recipient,
							    "Epostfeil", JOptionPane.WARNING_MESSAGE);
				    }
			    } else {
					JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Dette er en ugyldig epost-addresse\nMottaker: " + recipient + "\nVennligst legg inn en ny addresse på formen:\n\nsau@sau.no",
						    "Epostfeil", JOptionPane.WARNING_MESSAGE);
			    }
		    }
		}
	}
}
