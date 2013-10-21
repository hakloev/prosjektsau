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
	
	public AlarmPanel(ProgramFrame programFrame){
		this.programFrame = programFrame;
		initElements();
		initDesign();
	}

	private void initElements() {
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        alarmList = new DefaultListModel<Alarm>();
        
		list = new JList<Alarm>(alarmList);
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
	 * 
	 * @param alarm
	 */
	public void addAlarm(Alarm alarm) {
		alarmList.addElement(alarm);
	}
	
	class DeleteAlarmListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(programFrame.getAlarmPanel(), "Du må velge en alarm for å slette", 
						"Alarmfeil", JOptionPane.WARNING_MESSAGE);
			} else {
				list.remove(list.getSelectedIndex());
				sheepId.setText("ID");
				alarmTime.setText("Tid");
				alarmDesc.setText("Beskrivelse");
				alarmPos.setText("Posisjon");
			}
		}
		
	}
	
	class ShowAlarmListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Alarm alarm = list.getSelectedValue();
					sheepId.setText(alarm.getSheepId());
					alarmTime.setText(alarm.getAlarmDate());
					alarmDesc.setText(alarm.getAlarmDescription());
					alarmPos.setText(alarm.getAlarmPostition());
				} else {
				
			}
		}
	}

}
