package gui;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;


public class SheepPanel extends JPanel implements ItemListener{
	
	private ProgramFrame programFrame;
	
	private JList list;
	private DefaultListModel sheepList;
	private JScrollPane listScrollPane;
	
	private JLabel sheepListText;
	private JLabel hasAlarm;
	
	private JButton getSheepInfo;
	private JButton deleteSheep;
	private JButton addSheep;
	private JButton updateSheep;
	private JButton showMap;
	
	private JRadioButton infoMode;
	private JRadioButton updateMode;
	private ButtonGroup radioGroup1;
	private JRadioButton mapAll;
	private JRadioButton mapSelected;
	private ButtonGroup radioGroup2;
	
	private JSeparator designSeperator;
	private JSeparator designSeperator2;
	private JSeparator designSeperator3;
	
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
	
	public SheepPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		
		initElements();
		initDesign();
		/* Bare for testing
		test2 = new DefaultListModel();
		JList list = new JList(test2);
		list.setVisibleRowCount(c5);
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
		listScrollPane.setMinimumSize(new Dimension(120,100));
		listScrollPane.setMaximumSize(new Dimension(120,2000));
		
		sheepListText = new JLabel("Saueliste");
		hasAlarm = new JLabel("Har alarm: Nei");
		
		sheepIdText = new JLabel("ID:");
		sheepAgeText = new JLabel("Alder:");
		sheepNickText = new JLabel("Kallenavn:");
		sheepPosText = new JLabel("Posisjon:");
		sheepWeightText = new JLabel("Vekt:");
		
		sheepId = new JTextArea("ID");
		//sheepId.setMinimumSize(new Dimension(150,17));
		//sheepId.setMaximumSize(new Dimension(200,17));
		sheepId.setEditable(false);
		
		sheepAge = new JTextArea("Alder");
		//sheepAge.setMinimumSize(new Dimension(150,17));
		//sheepAge.setMaximumSize(new Dimension(200,17));
		sheepAge.setEditable(false);
		
		sheepNick = new JTextArea("Kallenavn");
		//sheepNick.setMinimumSize(new Dimension(150,17));
		//sheepNick.setMaximumSize(new Dimension(200,17));
		sheepNick.setEditable(false);
		
		sheepPos = new JTextArea("Posisjon");
		//sheepPos.setMinimumSize(new Dimension(150,17));
		//sheepPos.setMaximumSize(new Dimension(200,17));
		sheepPos.setEditable(false);
		
		sheepWeight = new JTextArea("Vekt");
		//sheepWeight.setMinimumSize(new Dimension(150,17));
		//sheepWeight.setMaximumSize(new Dimension(200,17));
		sheepWeight.setEditable(false);
		
		infoMode = new JRadioButton("Info modus");
		updateMode = new JRadioButton("Oppdateringsmodus");
		radioGroup1 = new ButtonGroup();
		radioGroup1.add(infoMode);
		radioGroup1.add(updateMode);
		
		mapAll = new JRadioButton("Alle");
		mapSelected = new JRadioButton("Valgt");
		radioGroup2 = new ButtonGroup();
		radioGroup2.add(mapAll);
		radioGroup2.add(mapSelected);
		
		getSheepInfo = new JButton("Hent info");
		deleteSheep = new JButton("Slett sau");
		addSheep = new JButton("Legg til sau");
		updateSheep = new JButton("Oppdater sau");
		showMap = new JButton("Vis på kart");
		
		designSeperator = new JSeparator();
		designSeperator2 = new JSeparator();
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
													.addComponent(getSheepInfo)
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
										) 
									)
								)
								.addComponent(designSeperator2)
								.addGroup(layout.createSequentialGroup()
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
                        		.addComponent(getSheepInfo)
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
	                        )
	                        .addGap(20)
	                        .addComponent(designSeperator2)
	                        .addGap(20)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
		
	}
}
