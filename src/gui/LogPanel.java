package gui;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel{
	
	private ProgramFrame programFrame;
	
	private JList list;
	private DefaultListModel logList;
	private JScrollPane listScrollPane;
	
	private JList list2;
	private DefaultListModel logList2;
	private JScrollPane listScrollPane2;
	
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

	public LogPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		
		initElements();
		initDesign();
	}

	private void initElements() {
		// TODO Auto-generated method stub
		
	}
	
	private void initDesign() {
		// TODO Auto-generated method stub
		
	}

}
