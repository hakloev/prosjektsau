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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LogPanel extends JPanel{
	
	private ProgramFrame programFrame;
	
	private JList list;
	private DefaultListModel logList;
	private JScrollPane listScrollPane;
	
	private JComboBox logIdBox;
	private JLabel datoTidText;
	private JLabel logDescText;
	
	private JTextArea datoTid;
	private JScrollPane logDesc;
	
	private GroupLayout layout;
	private JTextArea logDescTextArea;

	private boolean editingLog;

	private ArrayList<LogItem> logCurrentSheep;

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

		logIdBox = new JComboBox(new String[]{"Logger:"});
		logIdBox.setMinimumSize(new Dimension(120, 20));
		logIdBox.setMaximumSize(new Dimension(120, 20));

		datoTidText = new JLabel("Dato: ");
		logDescText = new JLabel("Logg:");
		
		datoTid = new JTextArea("Du startet klienten: " + new Date().toString());
		datoTid.setMinimumSize(new Dimension(200, 20));
		datoTid.setMaximumSize(new Dimension(1000, 20));
		logDescTextArea = new JTextArea("Velg en sau og trykk \"Hent Dagslogg\"");
		logDesc = new JScrollPane(logDescTextArea);
		logCurrentSheep = new ArrayList<LogItem>();

		// Listners for buttons
		list.addListSelectionListener(new ListListener());
		logIdBox.addActionListener(new ComboBoxListner());
	}
	
	private void initDesign() {
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(logIdBox)
					.addComponent(listScrollPane)
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
						.addComponent(logIdBox)
						.addComponent(listScrollPane)
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

	private class ComboBoxListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!editingLog) {
				if ((logCurrentSheep.size() > 0)) {
					logDescTextArea.setText("");
					for (LogItem l : logCurrentSheep) {
						String idNr = (String) logIdBox.getSelectedItem();
						if (Integer.parseInt(idNr) == l.getLogId()) {
							datoTid.setText(l.getDateAsString());
							logDescTextArea.append(l.toString() + "\n");
						}
					}
				}
			}
		}
	}

	private class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!list.isSelectionEmpty())  {
				Sheep s = (Sheep) logList.getElementAt(list.getSelectedIndex());
				Response r = programFrame.getNetHandler().getSheepLog(s.getIdNr());
				editingLog = true;
				try {
					if (programFrame.getNetHandler().searchJSON("count", r.msg).equals("0")) {
						logIdBox.removeAllItems();
						logIdBox.addItem("Logger:");
						datoTid.setText("Ingen informasjon loggført enda");
						logDescTextArea.setText("Ingen informasjon loggført enda");
						logCurrentSheep.clear();
					}  else {
						logCurrentSheep = JsonHandler.parseJsonAndReturnLog(r);
						datoTid.setText("Siste logg: " + logCurrentSheep.get(logCurrentSheep.size() - 1).getDateAsString());
						logDescTextArea.setText("Velg logg i lista \"Logger\"");
						String[] logIds = new String[logCurrentSheep.size()];
						int count = 0;
						for (LogItem l : logCurrentSheep) {
							logIds[count] = String.valueOf(l.getLogId());
							count++;
						}
						logIdBox.removeAllItems();
						for (int i = 0; i < count; i++) {
							logIdBox.addItem(logIds[i]);
						}
					}
					editingLog = false;
					logDescTextArea.setCaretPosition(0);
				} catch (IOException e1) {
					System.out.println("Exception in ListListner");
				}
			}
		}
	}
}