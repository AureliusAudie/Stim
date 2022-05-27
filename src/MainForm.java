import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainForm extends JFrame implements JFrameSettings, ActionListener{
	
	User loggedUser;
	
	JPanel mainPanel;
	
	JLabel centerText;
	
	JMenuBar menubar;
	
	JMenu accountMenu, gamesMenu, manageMenu;
	
	JMenuItem menuItemLogOut, buyGamesMenuItem, ownedGamesMenuItem,
		manageGameMenuItem, manageGenresMenuItem;
	
	public MainForm(User loggedUser) {
		this.loggedUser = loggedUser;
		settings();
		initComponents();
		setVisible(true);
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
		mainPanel = new JPanel();
		centerText = new JLabel("Stim");
		menubar = new JMenuBar();
		accountMenu = new JMenu("Account");
		gamesMenu = new JMenu("Games");
		manageMenu = new JMenu("Manage");
		menuItemLogOut = new JMenuItem("Log Out");
		buyGamesMenuItem = new JMenuItem("Buy Games");
		ownedGamesMenuItem = new JMenuItem("Owned Games");
		manageGameMenuItem = new JMenuItem("Manage Games");
		manageGenresMenuItem = new JMenuItem("Manage Genres");
		
		// Set CenterText
		centerText.setFont(centerText.getFont().deriveFont(50f));
		
		// Add Menu Item to Account Menu
		accountMenu.add(menuItemLogOut);
		
		// Add menu item to Games Menu
		gamesMenu.add(buyGamesMenuItem);
		gamesMenu.add(ownedGamesMenuItem);
		
		// Add menu item to manage Menu
		manageMenu.add(manageGameMenuItem);
		manageMenu.add(manageGenresMenuItem);
		
		// Add to MenuBar
		menubar.add(accountMenu);
		if(loggedUser.getUserRole().equals("Player"))
			menubar.add(gamesMenu);
		else if(loggedUser.getUserRole().equals("Developer"))
			menubar.add(manageMenu);
		
		// Add to Main Panel
		mainPanel.add(centerText);
		
		// Set Menu Bar
		setJMenuBar(menubar);

		// Set Color
		setColors();
		
		// Add Action listener
		menuItemLogOut.addActionListener(this);
		buyGamesMenuItem.addActionListener(this);
		ownedGamesMenuItem.addActionListener(this);
		manageGameMenuItem.addActionListener(this);
		
		// Add to Main Frame
		add(mainPanel,  BorderLayout.CENTER);
	}

	private void setColors() {
		menubar.setBackground(Color.decode("#3d3d3d")); // Menubar
		
		// Menu
		accountMenu.setForeground(Color.white);
		manageMenu.setForeground(Color.white);
		gamesMenu.setForeground(Color.white);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == menuItemLogOut) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure want to log out?");
			switch (option) {
				case JOptionPane.YES_OPTION: {
					this.dispose();
					new Login();
					
					break;
				}
			}
			
		} else if(e.getSource() == buyGamesMenuItem) {
			this.dispose();
			new BuyGame(loggedUser);
		} else if (e.getSource() == ownedGamesMenuItem) {
			this.dispose();
			new OwnedGame(loggedUser);
		}else if (e.getSource() == manageGameMenuItem) {
			this.dispose();
			new ManageGame(loggedUser);
		}
	}
	
	

}
