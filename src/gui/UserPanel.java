package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

public class UserPanel extends JPanel {

	private ProgramFrame programFrame;
	
	private static DefaultListModel test2 = null;

	private JButton loginButton;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel usernameText;
	private JLabel passwordText;
	private GroupLayout layout;
	
	private JSeparator js;
	
	public UserPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		initElements();
		initDesign();
	}
	
	
	public void initElements(){
		layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
		
		loginButton = new JButton("Logg inn");
		
		usernameText = new JLabel("Brukernavn:");
		passwordText = new JLabel("Password:");
		
		usernameField = new JTextField(10);
		usernameField.setMaximumSize(new Dimension(1000,20));
		
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		passwordField.setMaximumSize(new Dimension(1000,20));
		
		js = new JSeparator();
	}
	
	public void initDesign(){
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(usernameText)
						.addComponent(loginButton)
					)
					.addComponent(usernameField)
					.addComponent(passwordText)
					.addComponent(passwordField)
				)
				.addComponent(js)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(usernameText)
				.addComponent(usernameField)
				.addComponent(passwordText)
				.addComponent(passwordField)
			)
			.addComponent(loginButton)
			.addComponent(js)
		);
	}
}
