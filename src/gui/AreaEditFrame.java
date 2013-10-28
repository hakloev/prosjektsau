package gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class AreaEditFrame extends JFrame{


	private JList<Integer[]> list;
	private DefaultListModel<Integer[]> vertList;
	private JScrollPane listScrollPane;
	
	private JButton addToList;
	private JButton deleteFromList;
	private JButton addArea;
	
	private JLabel longitudeText;
	private JLabel latitudeText;
	private JTextField longitude; //Lengdegrad
	private JTextField latitude; //Breddegrad
	
	private GroupLayout layout;
	
	public AreaEditFrame() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initElements();
		initDisplay();
	}

	private void initElements() {
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		vertList = new DefaultListModel<Integer[]>();
		list = new JList(vertList);
		listScrollPane = new JScrollPane(list);
		
		addToList = new JButton("Legg til punkt");
		deleteFromList = new JButton("Slett punkt");
		addArea = new JButton("Lag område");
		
		longitudeText = new JLabel("Lengdegrad:");
		latitudeText = new JLabel("Breddegrad:");
		longitude = new JTextField(); //Lengdegrad
		longitude.setMinimumSize(new Dimension(100,20));
		longitude.setPreferredSize(new Dimension(100,20));
		longitude.setMaximumSize(new Dimension(100,20));
		latitude = new JTextField(); //Breddegrad
		latitude.setMinimumSize(new Dimension(100,20));
		latitude.setPreferredSize(new Dimension(100,20));
		latitude.setMaximumSize(new Dimension(100,20));
	}
	
	private void initDisplay() {
		setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
		);
	}

}