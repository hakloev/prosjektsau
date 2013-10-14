package gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private JComboBox yearBox;
	private JComboBox monthBox;
	private JComboBox dayBox;
	
	private JLabel datoTidText;
	private JLabel logDescText;
	
	private JTextArea datoTid;
	private JTextArea logDesc;
	
	private JButton deleteLogItem;
	private JButton getDateList;
	
	private GroupLayout layout;

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
        
        logList = new DefaultListModel();
        
		list = new JList(logList);
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
		
		getDateList = new JButton("Hen dagslogg");
		
		datoTidText = new JLabel("Dato & tid: ");
		logDescText = new JLabel("Log:");
		
		datoTid = new JTextArea("Dato & tid");
		datoTid.setMinimumSize(new Dimension(200,20));
		datoTid.setMaximumSize(new Dimension(1000,20));
		logDesc = new JTextArea("Logggggg");
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

}
