package gui;

import characters.LogItem;
import characters.Sheep;
import serverconnection.JsonHandler;
import serverconnection.Response;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class LogPanel extends JPanel{
	
	private ProgramFrame programFrame;
	
	private JList list;
	private DefaultListModel logList;
	private JScrollPane listScrollPane;
	
	private JComboBox yearBox;
	private JComboBox monthBox;
	private JComboBox dayBox;
	private JLabel datoTidText;
	private JLabel logDescText;
	
	private JTextArea datoTid;
	private JScrollPane logDesc;
	
	private JButton deleteLogItem;
	private JButton getDateList;
	
	private GroupLayout layout;
	private JTextArea logDescTextArea;

	public LogPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;

		initElements();
		initDesign();
	}

	private void initElements() {
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        logList = new DefaultListModel<Sheep>();
        
		list = new JList<Sheep>(logList);
		listScrollPane = new JScrollPane(list);
		listScrollPane.setMinimumSize(new Dimension(120,100));
		listScrollPane.setMaximumSize(new Dimension(120,2000));
		
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "Fox", "Elephat", "Fish", "1", "3", "2", "5"};
		
		yearBox = new JComboBox(petStrings);
		yearBox.setMinimumSize(new Dimension(120,20));
		yearBox.setMaximumSize(new Dimension(120,20));
		monthBox = new JComboBox(petStrings);
		monthBox.setMinimumSize(new Dimension(120,20));
		monthBox.setMaximumSize(new Dimension(120,20));
		dayBox = new JComboBox(petStrings);
		dayBox.setMaximumRowCount(15);
		dayBox.setMinimumSize(new Dimension(120,20));
		dayBox.setMaximumSize(new Dimension(120,20));
		
		getDateList = new JButton("Hent dagslogg");
		
		datoTidText = new JLabel("Dato: ");
		logDescText = new JLabel("Logg:");
		
		datoTid = new JTextArea("Du startet klienten: " + new Date().toString());
		datoTid.setMinimumSize(new Dimension(200, 20));
		datoTid.setMaximumSize(new Dimension(1000, 20));
		logDescTextArea = new JTextArea("Velg en sau og trykk \"Hent Dagslogg\"");
		logDesc = new JScrollPane(logDescTextArea);

		// Listners for buttons
		getDateList.addActionListener(new getLogListener());
	}
	
	private void initDesign() {
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(yearBox)
					.addComponent(monthBox)
					.addComponent(dayBox)
					.addComponent(listScrollPane)
					.addComponent(getDateList)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(datoTidText)
					.addComponent(datoTid)
					.addComponent(logDescText)
					.addComponent(logDesc)
			)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(yearBox)
						.addComponent(monthBox)
						.addComponent(dayBox)
						.addComponent(listScrollPane)
						.addComponent(getDateList)
					)
					.addGroup(layout.createSequentialGroup()
						.addComponent(datoTidText)
						.addComponent(datoTid)
						.addComponent(logDescText)
						.addComponent(logDesc)
						.addGap(33)
					)
				)
		);	
	}

	public void updateLogList(ListModel<Sheep> sheepList) {
		this.list.setModel(sheepList);
		logList = (DefaultListModel<Sheep>) sheepList;
	}

	private class getLogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Sheep s = (Sheep) logList.getElementAt(list.getSelectedIndex());
			Response r = programFrame.getNetHandler().getSheepLog(s.getIdNr());
			try {
				if (programFrame.getNetHandler().searchJSON("count", r.msg).equals("0")) {
					datoTid.setText("Ingen informasjon loggført enda");
					logDescTextArea.setText("Ingen informasjon loggført enda");
				}  else {
					ArrayList<LogItem> logs = JsonHandler.parseJsonAndReturnLog(r);
					datoTid.setText("Siste logg: " + logs.get(logs.size() - 1).getDateAsString());
					logDescTextArea.setText("");
					for (LogItem l : logs) {
						logDescTextArea.append(l.toString() + "\n");
					}
				}
				logDescTextArea.setCaretPosition(0);
			} catch (IOException e1) {
				System.out.println("Exception in getLogListener");
			}
		}
	}
}