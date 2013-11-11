package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import characters.Position;

/**
 * @author Andreas Lyngby
 */
public class AreaEditFrame extends JDialog{


	private JList<Position> list;
	private DefaultListModel<Position> vertList;
	private JScrollPane listScrollPane;
	
	private JButton addToList;
	private JButton deleteFromList;
	private JButton createArea;
	
	private JLabel longitudeText;
	private JLabel latitudeText;
	private JTextArea longitude; //Lengdegrad
	private JTextArea latitude; //Breddegrad
	
	private GroupLayout layout;

	private ProgramFrame frame;
	private ArrayList<Position> areaList;
	
	public AreaEditFrame(ProgramFrame frame, ArrayList<Position> list) {
		this.frame = frame;  
		this.areaList = list;
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WAdapter(this, frame));

		initElements();
		initDisplay();

		this.pack();
	}

	private void initElements() {
		layout = new GroupLayout(this.getContentPane());
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		vertList = new DefaultListModel<Position>();
		if(areaList != null){
			for(Position a : areaList){
				vertList.addElement(a);
			}
		}else{
			areaList = new ArrayList<Position>();
		}
		list = new JList(vertList);
		listScrollPane = new JScrollPane(list);
		listScrollPane.setMinimumSize(new Dimension(100,100));
		listScrollPane.setMaximumSize(new Dimension(100,300));
		
		addToList = new JButton("Legg til punkt");
		addToList.setMinimumSize(new Dimension(100,20));
		addToList.setPreferredSize(new Dimension(100,20));
		addToList.setMaximumSize(new Dimension(200,20));
		addToList.setName("add");
		deleteFromList = new JButton("Slett punkt");
		deleteFromList.setMinimumSize(new Dimension(100,20));
		deleteFromList.setPreferredSize(new Dimension(100,20));
		deleteFromList.setMaximumSize(new Dimension(200,20));
		deleteFromList.setName("delete");
		createArea = new JButton("Lag område");
		createArea.setMinimumSize(new Dimension(100,20));
		createArea.setPreferredSize(new Dimension(100,20));
		createArea.setMaximumSize(new Dimension(200,20));
		createArea.setName("create");
		
		longitudeText = new JLabel("Lengdegrad:");
		latitudeText = new JLabel("Breddegrad:");
		longitude = new JTextArea(); //Lengdegrad
		longitude.setMinimumSize(new Dimension(100,20));
		longitude.setPreferredSize(new Dimension(100, 20));
		longitude.setMaximumSize(new Dimension(100, 20));
		longitude.setEnabled(true);
		longitude.setEditable(true);
		latitude = new JTextArea(); //Breddegrad
		latitude.setMinimumSize(new Dimension(100,20));
		latitude.setPreferredSize(new Dimension(100, 20));
		latitude.setMaximumSize(new Dimension(100, 20));
		latitude.setEnabled(true);
		latitude.setEditable(true);
		
		addToList.addActionListener(new BAdapter(this,addToList.getName()));
		deleteFromList.addActionListener(new BAdapter(this,deleteFromList.getName()));
		createArea.addActionListener(new BAdapter(this,createArea.getName()));
	}
	
	private void initDisplay() {
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(listScrollPane)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
									.addComponent(createArea)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(latitudeText)
									.addComponent(latitude)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(longitudeText)
									.addComponent(longitude)
							)
							.addComponent(addToList)
							.addComponent(deleteFromList)
							.addComponent(createArea)
					)
				)
			)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(listScrollPane)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(latitudeText)
							.addComponent(latitude)
					)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(longitudeText)
							.addComponent(longitude)
					)
					.addComponent(addToList)
					.addComponent(deleteFromList)
					.addComponent(createArea)
				)
			)
		);
	}
	
	private class BAdapter implements ActionListener{
		private String button;

		public BAdapter(AreaEditFrame frame, String button){
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(button.equals("add")){
				if(!latitude.getText().matches("^[0-9]{1,2}\\.[0-9]{2,15}$") || !longitude.getText().matches("^[0-9]{2}\\.[0-9]{2,15}$")){
					System.out.println("BAdapter in AreaEditFrame");
				}else{
					vertList.addElement(new Position(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText())));
				}
			}else if(button.equals("delete")){
				if(!list.isSelectionEmpty()){
					vertList.remove(list.getSelectedIndex());
				}
				
			}else if(button.equals("create")){
				if(vertList.size() != 0){
					ArrayList<Position> nodes = new ArrayList<Position>();
					for(int i = 0;i<vertList.getSize();i++){
						nodes.add(vertList.getElementAt(i));
					}
					frame.getUserPanel().addArea(nodes);
					frame.getUserPanel().setAreaOpenable(true);
					dispose();
				}
			}
		}
	}
	
	private class WAdapter extends WindowAdapter{
		private ProgramFrame pframe;
		AreaEditFrame frame;
		
		public WAdapter(AreaEditFrame frame, ProgramFrame pframe){
			this.pframe = pframe;
			this.frame = frame;  
		}
		
		@Override
		public void windowClosed(WindowEvent arg0) {
			frame.dispose();
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			pframe.getUserPanel().setAreaOpenable(true);
			if(frame.areaList.size()!=0){
				pframe.getUserPanel().addArea(frame.areaList);
			}
			frame.dispose();
		}
		
	}
}