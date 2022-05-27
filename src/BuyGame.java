import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class BuyGame extends JFrame 
	implements 
		JFrameSettings, 
		ActionListener, ItemListener, MouseListener{
	
	DatabaseConnection db = new DatabaseConnection();
	
	JPanel southPanel, northPanel, gameIDPanel, gameNamePanel, gamepricePanel,
		gameGenrePanel, quantityPanel, checkPanel, buttonPanel;
	
	JLabel gameIDJLabel, gameNameJLabel, 
		gamePriceJLabel, gameGenreJLabel, quantityJLabel, checkLabel;
	
	JTextField gameidField, gameNameField, 
		gamePriceField, gamegenreField;
	
	JSpinner quantitySpinner;
	
	JCheckBox refundCheckBox;
	
	JButton backButton, buyGameButton;
	
	JTable table;
	DefaultTableModel dtm;
	
	JScrollPane scrollPane;
	
	User loggedUser;
	
	Vector<Vector<String>> gameData;
	Vector<Game> gameDataClass;
	
	Game selectedGameGlobal = null;
	
	public BuyGame(User loggedUser) {
		this.loggedUser = loggedUser;
		settings();
		initComponents();
		setVisible(true);
	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		setResizable(false);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
	}

	@Override
	public void initComponents() {
		// INIT PANEL
		northPanel = new JPanel();
		southPanel = new JPanel();
		
		gameIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gamepricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameGenrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		
		// Init Jtextfield
		gameidField = new JTextField();
		gameNameField = new JTextField();
		gamePriceField = new JTextField();
		gamegenreField = new JTextField();
		
		// Init JLabel
		gameIDJLabel = new JLabel("Game ID");
		gameNameJLabel = new JLabel("Game Name");
		gamePriceJLabel = new JLabel("Game Price");
		gameGenreJLabel = new JLabel("Game Genre");
		quantityJLabel = new JLabel("How many do you want to buy?");
		checkLabel = new JLabel("Once bought game cannot be returned!");
		
		// Init Spinner
		quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
		
		// Init check box
		refundCheckBox = new JCheckBox();
		
		// INit Button
		backButton = new JButton("Back");
		buyGameButton = new JButton("Buy Game");
		
		// Data to be displayed in the JTable
        gameData = populateData(db.getGamesData());
        gameDataClass = db.getGamesData();
 
        // Column Names
        Vector<String> columnNames = new Vector<String>(); 
        columnNames.add("Game ID");
        columnNames.add("Game Name");
        columnNames.add("Game Price");
        columnNames.add("Genre");
        columnNames.add("Quantity");
		
		// INIT
        dtm = new DefaultTableModel(gameData, columnNames);
		table = new JTable(dtm);
//		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1100, 300));
		
		// South Panel
		gameIDPanel.add(gameIDJLabel);
		gameIDPanel.add(gameidField);
		gameNamePanel.add(gameNameJLabel);
		gameNamePanel.add(gameNameField);
		gamepricePanel.add(gamePriceJLabel);
		gamepricePanel.add(gamePriceField);
		gameGenrePanel.add(gameGenreJLabel);
		gameGenrePanel.add(gamegenreField);
		quantityPanel.add(quantityJLabel);
		quantityPanel.add(quantitySpinner);
		checkPanel.add(checkLabel);
		checkPanel.add(refundCheckBox);
		buttonPanel.add(backButton);
		buttonPanel.add(buyGameButton);
		
		// Set Color
		setColor();
		
		// set size
		gameIDJLabel.setPreferredSize(new Dimension(300,30));
		gameidField.setPreferredSize(new Dimension(400,30));
		gameNameJLabel.setPreferredSize(new Dimension(300,30));
		gameNameField.setPreferredSize(new Dimension(400,30));
		gamePriceJLabel.setPreferredSize(new Dimension(300,30));
		gamePriceField.setPreferredSize(new Dimension(400,30));
		gameGenreJLabel.setPreferredSize(new Dimension(300,30));
		gamegenreField.setPreferredSize(new Dimension(400,30));
		quantityJLabel.setPreferredSize(new Dimension(300,30));
		quantitySpinner.setPreferredSize(new Dimension(400,30));
		checkLabel.setPreferredSize(new Dimension(300,30));
		refundCheckBox.setPreferredSize(new Dimension(400,30));
		backButton.setPreferredSize(new Dimension(100, 40));
		backButton.setPreferredSize(new Dimension(100, 40));
		buttonPanel.setPreferredSize(new Dimension(700, 40));
		
//		southPanel.setPreferredSize(new Dimension (500, 500));
		
		// Add to north Panel
		northPanel.add(scrollPane);
		
		// Add to south panel
		southPanel.add(gameIDPanel);
		southPanel.add(gameNamePanel);
		southPanel.add(gamepricePanel);
		southPanel.add(gameGenrePanel);
		southPanel.add(quantityPanel);
		southPanel.add(checkPanel);
		southPanel.add(buttonPanel);
		
		// Add Listener
		backButton.addActionListener(this);
		buyGameButton.addActionListener(this);
		
		// Add to main frame
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.CENTER);
		
		// Listener Table
		table.addMouseListener(this);
		
		// Set editable
		gameidField.setEditable(false);
		gameNameField.setEditable(false);
		gamePriceField.setEditable(false);
		gamegenreField.setEditable(false);
	}

	private void setColor() {	
//		northPanel.setBackground(Color.decode("#333333"));
//		table.setBackground(Color.decode("#333333"));
//		table.getTableHeader().setBackground(Color.decode("#333333"));
//		scrollPane.setBackground(Color.decode("#333333"));
//		scrollPane.setOpaque(false);
//		table.setOpaque(false);
	}

	private Vector<Vector<String>> populateData(Vector<Game> gameVector) {
		Vector<Vector<String>> allData = null;
		
		if(gameVector.size() > 0) {
			
			allData = new Vector<Vector<String>>();
			
			for (Game game : gameVector) {
				Vector<String> eachGame = new Vector<>();
				
				eachGame.add(game.getGameID());
				eachGame.add(game.getName());
				eachGame.add(game.getPrice().toString());
				eachGame.add(game.getGenreID());
				eachGame.add(game.getQuantity().toString());
				
				allData.add(eachGame);
			}
		}
		
		return allData;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			this.dispose();
			new MainForm(loggedUser);
		}else if(e.getSource() == buyGameButton) {
			
			// Add transaction
			if(checkForm()) {
				if(db.checkUserGameOwned(selectedGameGlobal.getGameID(), loggedUser.getUserId()) < 0) {
					if(db.insertNewTransaction(loggedUser.getUserId(), selectedGameGlobal.getGameID(), (int) quantitySpinner.getValue())) {
						JOptionPane.showMessageDialog(null, "Game Bought!");
					}
					else {
						System.out.println("error insert");
					}
				}else {
					System.out.println("error user owned");
				}
			}else {
				System.out.println("Error form");
			}
			
		}
	}

	private boolean checkForm() {
		// TODO Auto-generated method stub
		boolean allClear = true;
		
		if(selectedGameGlobal == null) {
			JOptionPane.showMessageDialog(null, "Please Select a Game");
			allClear = false;
		}else {			
			if((int)quantitySpinner.getValue() < 1 || (int)quantitySpinner.getValue() > selectedGameGlobal.getQuantity()) {
				JOptionPane.showMessageDialog(null, "Game quantity cannot be less than 1 or more than stock");
				allClear = false;
			}else if(!refundCheckBox.isSelected()) {
				JOptionPane.showMessageDialog(null, "Checkbox must be checked!");
				allClear = false;
			}
		}
		
		return allClear;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Integer rowIndex = table.getSelectedRow();
		int price = Integer.valueOf((String) dtm.getValueAt(rowIndex, 2));
		int quantity = Integer.valueOf((String) dtm.getValueAt(rowIndex, 4));
		
		Game selectedGame = gameDataClass.get(rowIndex);
		selectedGameGlobal = selectedGame;
		
		// Set to fields
		gameidField.setText(selectedGame.getGameID());
		gameNameField.setText(selectedGame.getName());
		gamePriceField.setText(String.valueOf(selectedGame.getPrice()));
		gamegenreField.setText(selectedGame.getGenreName());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
