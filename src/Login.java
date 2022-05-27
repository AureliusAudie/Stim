import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements JFrameSettings, ActionListener{

	DatabaseConnection db = new DatabaseConnection();
	
    JPanel mainPanel, titlePanel, usernamePanel, passwordPanel, loginButtonPanel;
    JLabel titleLabel, usernameLabel, passwordLabel;

    JTextField usernameTextField;
    JPasswordField passwordPasswordField;

    JButton loginButton, registerButton;

    public Login(){
        settings();
        initComponents();
        setVisible(true);
    }

    @Override
    public void settings() {
        setResizable(false);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    @Override
    public void initComponents() {
        mainPanel = new JPanel();
        titlePanel = new JPanel();
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        loginButtonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Title Label
        titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));

        // Title Panel
        titlePanel.setSize(700, 100);
        titlePanel.setBorder(new EmptyBorder(20,0,20,0));
        titlePanel.add(titleLabel);
        
        // Username TextField
        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(350, 30));
        
        // Username Label
        usernameLabel.setPreferredSize(new Dimension(100, 30));

        // Username Panel
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        usernamePanel.setPreferredSize(new Dimension(500, 40));

        // Password Password Field
        passwordPasswordField = new JPasswordField();
        passwordPasswordField.setPreferredSize(new Dimension(350, 30));
        
        // Password Label
        passwordLabel.setPreferredSize(new Dimension(100, 30));

        // Password Panel
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordPasswordField);
        passwordPanel.setPreferredSize(new Dimension(500, 40));

        // Login & Register Button
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(registerButton);
        loginButtonPanel.setPreferredSize(new Dimension(500,50));
        loginButtonPanel.setBorder(new EmptyBorder(20, 0,0,0));

        // Set Color
        setColor();
        
		// set Caret Color
		usernameTextField.setCaretColor(Color.WHITE);
		passwordPasswordField.setCaretColor(Color.WHITE);
        
        // Add action listener button
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Add to Main Panel
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(loginButtonPanel);

        // Add to Frame
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Untuk set color panel dan componen lainnya
     */
	private void setColor() {
		titlePanel.setBackground(Color.decode("#333333"));
		mainPanel.setBackground(Color.decode("#333333"));
		usernamePanel.setBackground(Color.decode("#333333"));
		passwordPanel.setBackground(Color.decode("#333333"));
		loginButtonPanel.setBackground(Color.decode("#333333"));
		
		titleLabel.setForeground(Color.white);
		usernameLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		
		usernameTextField.setBackground(Color.decode("#333333"));
		passwordPasswordField.setBackground(Color.decode("#333333"));
		usernameTextField.setForeground(Color.white);
		passwordPasswordField.setForeground(Color.white);
		
		loginButton.setBackground(Color.decode("#333333"));
		registerButton.setBackground(Color.decode("#333333"));
		loginButton.setForeground(Color.white);
		registerButton.setForeground(Color.white);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == loginButton) {
			if(usernameTextField.getText().isEmpty())
				showError("Username can't be empty", "Error");
			else if(passwordPasswordField.getText().isEmpty())
				showError("Password can't be empty", "Error");
			else {
				String uname = usernameTextField.getText();
				String pwd = passwordPasswordField.getText();
				
				if(checkUser(uname, pwd)) {
					
					User loggedUser = db.getUserDataFromUsername(uname);
					
					JOptionPane.showMessageDialog(null, "Login success!");
					this.dispose();
					new MainForm(loggedUser);
				}
			}
		}else if(e.getSource() == registerButton) {
			this.dispose();
			new Register();
		}
	}

	private boolean checkUser(String uname, String pwd) {

		User user = db.getUserDataFromUsername(uname);
		
		if(user == null) {
			showError("Username/Password is wrong", "Error");
		}else {
			if(!pwd.equals(user.getUserPassword())) {
				showError("Username/Password is wrong", "Error");				
			}else {
				return true;
			}
		}
		
		return false;
	}

	private void showError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
