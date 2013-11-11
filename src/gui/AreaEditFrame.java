package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JTextField longitude; //Lengdegrad
	private JTextField latitude; //Breddegrad
	
	private GroupLayout layout;
	private JPanel contentPanel;
	
	private ProgramFrame frame;
	private ArrayList<Position> areaList;
	
	/**
	 * Constructor. Takes inn ProgramFrame and Arraylist with points if you've clicked the button to edit areas in UserPanel.
	 * @param frame - ProgramFrame
	 * @param list - ArrayList<Position>
	 */
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

	/**
	 * Initializes the components.
	 */
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
		longitude = new JTextField(); //Lengdegrad
		longitude.setMinimumSize(new Dimension(100,20));
		longitude.setPreferredSize(new Dimension(100,20));
		longitude.setMaximumSize(new Dimension(100,20));
		longitude.setEditable(true);
		latitude = new JTextField(); //Breddegrad
		latitude.setMinimumSize(new Dimension(100,20));
		latitude.setPreferredSize(new Dimension(100,20));
		latitude.setMaximumSize(new Dimension(100,20));
		latitude.setEditable(true);
		
		addToList.addActionListener(new BAdapter(this,addToList.getName()));
		deleteFromList.addActionListener(new BAdapter(this,deleteFromList.getName()));
		createArea.addActionListener(new BAdapter(this,createArea.getName()));
	}
	
	/**
	 * Initializes the design for the components in the frame.
	 */
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
	
	/**
	 * Button adapter class with actionlistener for the butons to create the area and get it ready to send to userpanel,
	 * deleting points in the list in this frame and to add points to the list.
	 * @author Andreas
	 *
	 */
	private class BAdapter implements ActionListener{
		private String button;
		private AreaEditFrame frame;
		
		public BAdapter(AreaEditFrame frame, String button){
			this.button = button;
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(button.equals("add")){
				if(!latitude.getText().matches("^[0-9]{1,2}\\.[0-9]{2,15}$") || !longitude.getText().matches("^[0-9]{2}\\.[0-9]{2,15}$")){
					System.out.println("BAdapter in AreaEditFrame");
				}else{
					frame.vertList.addElement(new Position(Double.parseDouble(frame.latitude.getText()), Double.parseDouble(frame.longitude.getText())));
				}
			}else if(button.equals("delete")){
				if(!frame.list.isSelectionEmpty()){
					frame.vertList.remove(frame.list.getSelectedIndex());
				}
				
			}else if(button.equals("create")){
				if(frame.vertList.size() != 0){
					ArrayList<Position> nodes = new ArrayList<Position>();
					for(int i = 0;i<frame.vertList.getSize();i++){
						nodes.add(vertList.getElementAt(i));
					}
					frame.frame.getUserPanel().addArea(nodes);
					frame.frame.getUserPanel().setAreaOpenable(true);
					frame.dispose();
				}
			}
		}
	}
	
	/**
	 * Window adapter for this frame. Containing methods for pass areas to programframe.userpanel. 
	 * @author Andreas
	 *
	 */
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