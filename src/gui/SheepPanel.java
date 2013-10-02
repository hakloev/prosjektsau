package gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SheepPanel extends JPanel{

	private JList list;
	private DefaultListModel sheepList;
	private JScrollPane listScrollPane;
	
	private JLabel sheepListText;
	
	private JButton addSheep;
	private JButton getSheepInfo;
	private JButton deleteSheep;
	
	private JLabel sheepIdText;
	private JLabel sheepAgeText;
	private JLabel sheepNickText;
	private JLabel sheepGenderText;
	private JLabel sheepPosText;
	
	private JTextArea sheepId;
	private JTextArea sheepAge;
	private JTextArea sheepNick;
	private JTextArea sheepGender;
	private JTextArea sheepPos;
	
	private GroupLayout layout;
	
	public SheepPanel() {
		initElements();
		initDesign();
		/* Bare for testing
		test2 = new DefaultListModel();
		JList list = new JList(test2);
		list.setVisibleRowCount(5);
		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Password:");
		JScrollPane listScrollPane = new JScrollPane(list);
		*/
	}
	
	public void initElements(){
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
		
        sheepList = new DefaultListModel();
        
		list = new JList(sheepList);
		listScrollPane = new JScrollPane(list);
		listScrollPane.setMinimumSize(new Dimension(100,100));
		listScrollPane.setMaximumSize(new Dimension(100,1000));
		sheepListText = new JLabel("Saueliste");
		
		sheepIdText = new JLabel("ID:");
		sheepAgeText = new JLabel("Alder:");
		sheepNickText = new JLabel("Kallenavn:");
		sheepGenderText = new JLabel("Kjønn:");
		sheepPosText = new JLabel("Posisjon:");
		
		sheepId = new JTextArea("ID");
		sheepId.setMinimumSize(new Dimension(100,17));
		sheepId.setMaximumSize(new Dimension(1000,17));
		sheepId.setEditable(false);
		sheepAge = new JTextArea("Alder");
		sheepAge.setMinimumSize(new Dimension(100,17));
		sheepAge.setMaximumSize(new Dimension(1000,17));
		sheepAge.setEditable(false);
		sheepNick = new JTextArea("Kallenavn");
		sheepNick.setMinimumSize(new Dimension(100,17));
		sheepNick.setMaximumSize(new Dimension(1000,17));
		sheepNick.setEditable(false);
		sheepGender = new JTextArea("Kjønn");
		sheepGender.setMinimumSize(new Dimension(100,17));
		sheepGender.setMaximumSize(new Dimension(1000,17));
		sheepGender.setEditable(false);
		sheepPos = new JTextArea("Posisjon");
		sheepPos.setMinimumSize(new Dimension(100,17));
		sheepPos.setMaximumSize(new Dimension(1000,17));
		sheepPos.setEditable(false);
		
		addSheep = new JButton("Legg til sau");
	}
	
	public void initDesign(){
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(sheepListText)
					.addComponent(listScrollPane)
				)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(sheepIdText)
					.addComponent(sheepAgeText)
					.addComponent(sheepNickText)
					.addComponent(sheepGenderText)
					.addComponent(sheepPosText)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(sheepId)
					.addComponent(sheepAge)
					.addComponent(sheepNick)
					.addComponent(sheepGender)
					.addComponent(sheepPos)
			)
			.addComponent(addSheep)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addGroup(layout.createSequentialGroup()
					.addComponent(sheepListText)
					.addComponent(listScrollPane)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(sheepIdText)
					.addComponent(sheepAgeText)
					.addComponent(sheepNickText)
					.addComponent(sheepGenderText)
					.addComponent(sheepPosText)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(sheepId)
					.addComponent(sheepAge)
					.addComponent(sheepNick)
					.addComponent(sheepGender)
					.addComponent(sheepPos)
				)
				.addComponent(addSheep)
			)
		);
	}
}
