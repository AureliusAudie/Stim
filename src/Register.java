import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame implements ActionListener, JFrameSettings{
	
	DatabaseConnection db = new DatabaseConnection();

	JPanel mainPanel, titlePanel, buttonPanel,
		usernamePanel, passwordPanel, genderPanel, countryPanel, rolePanel, genderRadioPanel, roleRadioPanel, southPanel;
	
	JLabel titleLabel, usernameLabel, passwordLabel, genderLabel, countryLabel, roleLabel;
	
	JTextField usernameTextField;
	JPasswordField passwordJPasswordField;
	
	JButton backButton, registerButton;
	
	JRadioButton maleRadio, femaleRadio, playerRadio, developerRadio;
	
	ButtonGroup genderRadioGroup, roleRadioGroup;
	
	JComboBox countryComboBox;
	
	public Register(){
		settings();
		initComponents();
		setVisible(true);
	}
	
	
	private void showError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	private boolean validasiInput() {
		
		if(usernameTextField.getText().isEmpty()) {
			showError("Username is empty", "Error");
			return false;
		} else if (passwordJPasswordField.getText().isEmpty()) {
			showError("Password is empty", "Error");			
			return false;
		} else if (genderRadioGroup.getSelection() == null) {
			showError("Please select a gender", "Error");						
			return false;
		} else if(roleRadioGroup.getSelection() == null) {
			showError("Please select a role", "Error");						
			return false;
		} else if(usernameTextField.getText().length() < 5 || usernameTextField.getText().length() > 15) {			
			showError("Username length must be at least 5-15 chars", "Error");						
			return false;
		} else if(passwordJPasswordField.getText().length() < 3 || passwordJPasswordField.getText().length() > 10) {
			showError("Password length must be at least 5-10 characters", "Error");						
			return false;
		}
		return true;
	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		setResizable(false);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
	}

	@Override
	public void initComponents() {
		// Init JPanel
		titlePanel = new JPanel();
		mainPanel = new JPanel();
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		genderRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		roleRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		southPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		
		// Set panel width
		usernamePanel.setPreferredSize(new Dimension(500, 40));
		passwordPanel.setPreferredSize(new Dimension(500, 40));
		genderPanel.setPreferredSize(new Dimension(500, 40));
		countryPanel.setPreferredSize(new Dimension(500, 40));
		rolePanel.setPreferredSize(new Dimension(500, 40));
		genderRadioPanel.setPreferredSize(new Dimension(350, 30));
		
		// Init JLabel
		titleLabel = new JLabel("Create an Account");
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		genderLabel = new JLabel("Gender");
		countryLabel = new JLabel("Country");
		roleLabel = new JLabel("Choose a role");

		// Set label width
		usernameLabel.setPreferredSize(new Dimension(100, 30));
		passwordLabel.setPreferredSize(new Dimension(100, 30));
		genderLabel.setPreferredSize(new Dimension(100, 30));
		countryLabel.setPreferredSize(new Dimension(100, 30));
		roleLabel.setPreferredSize(new Dimension(100, 30));
		
		// Init Button
		backButton = new JButton("Back");
		registerButton = new JButton("Register");
		
		// Add actionlistener to button
		backButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		// Init TextField
		usernameTextField = new JTextField();
		passwordJPasswordField = new JPasswordField();
		usernameTextField.setPreferredSize(new Dimension(350, 30));
		passwordJPasswordField.setPreferredSize(new Dimension(350, 30));
		
		// Init JRadioButton
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		playerRadio = new JRadioButton("Player");
		developerRadio = new JRadioButton("Developer");
		maleRadio.setActionCommand("Male");
		femaleRadio.setActionCommand("Female");
		playerRadio.setActionCommand("Player");
		developerRadio.setActionCommand("Developer");
		
		// Init button group
		genderRadioGroup = new ButtonGroup();
		roleRadioGroup = new ButtonGroup();
		
		// Init Jcombobox
		String[] country = {"Indonesia", "Malaysia"};
		countryComboBox = new JComboBox(country);
		countryComboBox.setPreferredSize(new Dimension(350, 30));
		
		// Button Group
		genderRadioGroup.add(maleRadio);
		genderRadioGroup.add(femaleRadio);
		roleRadioGroup.add(playerRadio);
		roleRadioGroup.add(developerRadio);
		
		// TitleLabel
		titleLabel.setFont(titleLabel.getFont().deriveFont(25f));
		
		// NorthPanel
		titlePanel.add(titleLabel);
		titlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		
		// Main Panel
		// UserName Panel
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		
		// PasswordPanel
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordJPasswordField);
		
		// GenderRadio Panel
		genderRadioPanel.add(maleRadio);
		genderRadioPanel.add(femaleRadio);
		genderRadioPanel.setPreferredSize(new Dimension(350, 30));
		
		// Gender panel
		genderPanel.add(genderLabel);
		genderPanel.add(genderRadioPanel);
		
		// Country Panel
		countryPanel.add(countryLabel);
		countryPanel.add(countryComboBox);
		
		// RoleRadioPanel
		roleRadioPanel.add(playerRadio);
		roleRadioPanel.add(developerRadio);
		
		// RolePanel
		rolePanel.add(roleLabel);
		rolePanel.add(roleRadioPanel);
		
		// Add to south panel
		southPanel.add(backButton);
		southPanel.add(registerButton);
		southPanel.setPreferredSize(new Dimension(500, 35));
		
		// Add to mainPanel
		mainPanel.add(usernamePanel);
		mainPanel.add(passwordPanel);
		mainPanel.add(genderPanel);
		mainPanel.add(countryPanel);
		mainPanel.add(rolePanel);
		mainPanel.add(southPanel);
		
		// set Caret Color
		usernameTextField.setCaretColor(Color.WHITE);
		passwordJPasswordField.setCaretColor(Color.WHITE);
		
		// Set Color
		setColor();
		
		// Add to JFrame
		add(titlePanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setColor() {
		// Panel
		titlePanel.setBackground(Color.decode("#333333"));
		mainPanel.setBackground(Color.decode("#333333"));
		usernamePanel.setBackground(Color.decode("#333333"));
		passwordPanel.setBackground(Color.decode("#333333"));
		genderPanel.setBackground(Color.decode("#333333"));
		countryPanel.setBackground(Color.decode("#333333"));
		rolePanel.setBackground(Color.decode("#333333"));
		genderRadioPanel.setBackground(Color.decode("#333333"));
		roleRadioPanel.setBackground(Color.decode("#333333"));
		southPanel.setBackground(Color.decode("#333333"));
		
		// Labels
		titleLabel.setForeground(Color.white);
		usernameLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		genderLabel.setForeground(Color.white);
		countryLabel.setForeground(Color.white);
		roleLabel.setForeground(Color.white);
		
		// Radio
		maleRadio.setBackground(Color.decode("#333333"));
		femaleRadio.setBackground(Color.decode("#333333"));
		playerRadio.setBackground(Color.decode("#333333"));
		developerRadio.setBackground(Color.decode("#333333"));
		developerRadio.setForeground(Color.white);
		playerRadio.setForeground(Color.white);
		femaleRadio.setForeground(Color.white);
		maleRadio.setForeground(Color.white);
		
		// TextField
		usernameTextField.setBackground(Color.decode("#333333"));
		passwordJPasswordField.setBackground(Color.decode("#333333"));
		countryComboBox.setBackground(Color.decode("#333333"));
		usernameTextField.setForeground(Color.white);
		passwordJPasswordField.setForeground(Color.white);
		countryComboBox.setForeground(Color.white);
		
		// Button
		backButton.setBackground(Color.decode("#333333"));
		registerButton.setBackground(Color.decode("#333333"));
		backButton.setForeground(Color.white);
		registerButton.setForeground(Color.white);
	}
	
	public String generateRandomID() {
	    Random r = new Random( System.currentTimeMillis() );
	    return Integer.toString(((1 + r.nextInt(2)) * 10000 + r.nextInt(10000)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			this.dispose();
			new Login();
		}else if(e.getSource() == registerButton) {
			if(validasiInput()){
				User getUser = db.getUserDataFromUsername(usernameTextField.getText());
				
				if(getUser != null) {
					showError("Username is already exists!", "Error");
					
				} else {
					User newUser = new User(
						generateRandomID(),
						usernameTextField.getText(),
						passwordJPasswordField.getText(),
						genderRadioGroup.getSelection().getActionCommand(),
						countryComboBox.getSelectedItem().toString(),
						roleRadioGroup.getSelection().getActionCommand()
					);
					
					try {						
						db.insertNewUser(newUser);
						JOptionPane.showMessageDialog(null, "Register success!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						new Login();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "There's something wrong!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

}
