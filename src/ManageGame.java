import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageGame extends JFrame 
implements JFrameSettings, ActionListener, ItemListener, MouseListener{

	DatabaseConnection db = new DatabaseConnection();
	User loggedUser;
	
	JPanel southPanel, northPanel, southPanelLeft, southPanelRight, 
		gameIDPanel, gameNamePanel, gamepricePanel, gameGenrePanel, gameQuantityPanel, leftButtonPanel,
		newGameNamePanel, newGamePricePanel, newGameGenrePanel, newGameQuantityPanel, rightButtonPanel;
	
	JLabel gameIDJLabel, gameNameJLabel, gamePriceJLabel, gameGenreJLabel, gameQuantityJLabel,
		newGameNameJLabel, newGamePriceJLabel, newGameGenreJLabel, newGameQuantityJLabel;
	
	JTextField gameidField, gameNameField, gamePriceField,
		newGameNameField, newGamePriceField;

	JComboBox<String> genreComboBox, newGenreComboBox;
	
	JSpinner gameQuantitySpinner, newGameQuantitySpinner;
	
	JButton backButton, deleteButton, updateButton, insertButton;
	
	JTable table;
	DefaultTableModel dtm;
	JScrollPane scrollPane;

	Vector<Vector<String>> gameData;
	Vector<Game> gameDataClass;
	
	Vector<String> genreNames;
	
	Game selectedGameGlobal = null;
	
	public ManageGame(User loggedUser) {
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
		southPanel = new JPanel(new BorderLayout());
		southPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 100,0));
		southPanelRight = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 0));
		
		gameIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gamepricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameGenrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gameQuantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		leftButtonPanel = new JPanel(new GridLayout(1, 3, 20, 0));
		
		newGameNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		newGamePricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		newGameGenrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		newGameQuantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rightButtonPanel = new JPanel(new GridLayout(1,1,20,0));
		
		// Init JLabel
		gameIDJLabel = new JLabel("Game ID");
		gameNameJLabel = new JLabel("Game Name");
		gamePriceJLabel = new JLabel("Game Price");
		gameGenreJLabel = new JLabel("Game Genre");
		gameQuantityJLabel = new JLabel("Game Quantity");
		
		newGameNameJLabel = new JLabel("New Game Name");
		newGamePriceJLabel = new JLabel("New Game Price");
		newGameGenreJLabel = new JLabel("New Game Genre");
		newGameQuantityJLabel = new JLabel("New Game Quantity");
		
		// Init Field
		gameidField = new JTextField();
		gameNameField = new JTextField();
		gamePriceField = new JTextField();
		newGameNameField = new JTextField();
		newGamePriceField = new JTextField();
		
		// Init Button
		backButton = new JButton("Back");
		deleteButton = new JButton("Delete");
		updateButton = new JButton("Update");
		insertButton = new JButton("Insert");		
		
		// Init ComboBox
		genreNames = db.getGenreList();
		genreComboBox = new JComboBox<String>(genreNames);
		newGenreComboBox = new JComboBox<String>(genreNames);
		
		// Init Spinner
		gameQuantitySpinner = new JSpinner();
		newGameQuantitySpinner = new JSpinner();
		
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
        
        // INIT TABLE
        dtm = new DefaultTableModel(gameData, columnNames);
		table = new JTable(dtm);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1100, 300));
		
		// set size
		gameIDJLabel.setPreferredSize(new Dimension(100,30));
		gameidField.setPreferredSize(new Dimension(200,30));
		
		gameNameJLabel.setPreferredSize(new Dimension(100,30));
		gameNameField.setPreferredSize(new Dimension(200,30));
		
		gamePriceJLabel.setPreferredSize(new Dimension(100,30));
		gamePriceField.setPreferredSize(new Dimension(200,30));

		gameGenreJLabel.setPreferredSize(new Dimension(100,30));
		genreComboBox.setPreferredSize(new Dimension(200,30));

		gameQuantityJLabel.setPreferredSize(new Dimension(100,30));
		gameQuantitySpinner.setPreferredSize(new Dimension(200,30));
		
		backButton.setPreferredSize(new Dimension(100, 40));
		backButton.setPreferredSize(new Dimension(100, 40));		
		
		newGameNameJLabel.setPreferredSize(new Dimension(150,30));
		newGameNameField.setPreferredSize(new Dimension(200,30));
		
		newGamePriceJLabel.setPreferredSize(new Dimension(150,30));
		newGamePriceField.setPreferredSize(new Dimension(200,30));

		newGameGenreJLabel.setPreferredSize(new Dimension(150,30));
		newGenreComboBox.setPreferredSize(new Dimension(200,30));

		newGameQuantityJLabel.setPreferredSize(new Dimension(150,30));
		newGameQuantitySpinner.setPreferredSize(new Dimension(200,30));

		// South Left Panel
		gameIDPanel.add(gameIDJLabel);
		gameIDPanel.add(gameidField);
		gameNamePanel.add(gameNameJLabel);
		gameNamePanel.add(gameNameField);
		gamepricePanel.add(gamePriceJLabel);
		gamepricePanel.add(gamePriceField);
		gameGenrePanel.add(gameGenreJLabel);
		gameGenrePanel.add(genreComboBox);
		gameQuantityPanel.add(gameQuantityJLabel);
		gameQuantityPanel.add(gameQuantitySpinner);
		leftButtonPanel.add(backButton);
		leftButtonPanel.add(deleteButton);
		leftButtonPanel.add(updateButton);
		
		// South Right Panel
		newGameNamePanel.add(newGameNameJLabel);
		newGameNamePanel.add(newGameNameField);
		newGamePricePanel.add(newGamePriceJLabel);
		newGamePricePanel.add(newGamePriceField);
		newGameGenrePanel.add(newGameGenreJLabel);
		newGameGenrePanel.add(newGenreComboBox);
		newGameQuantityPanel.add(newGameQuantityJLabel);
		newGameQuantityPanel.add(newGameQuantitySpinner);
		rightButtonPanel.add(insertButton);
		
		// Add to north Panel
		northPanel.add(scrollPane);
		
		// Panel Prefered Size
		southPanelLeft.setPreferredSize(new Dimension(600,350));
		southPanelRight.setPreferredSize(new Dimension(600,350));
		
		// Add to left south panel
		southPanelLeft.add(gameIDPanel);
		southPanelLeft.add(gameNamePanel);
		southPanelLeft.add(gamepricePanel);
		southPanelLeft.add(gameGenrePanel);		
		southPanelLeft.add(gameQuantityPanel);
		southPanelLeft.add(leftButtonPanel);
		
		// Add to right south panel
		southPanelRight.add(newGameNamePanel);
		southPanelRight.add(newGamePricePanel);
		southPanelRight.add(newGameGenrePanel);
		southPanelRight.add(newGameQuantityPanel);
		southPanelRight.add(rightButtonPanel);
		
		// Add to south Panel
		southPanel.add(southPanelLeft, BorderLayout.WEST);
		southPanel.add(southPanelRight, BorderLayout.EAST);
		
		// Add to main frame
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.CENTER);
		
		// Add action listener
		backButton.addActionListener(this);
		deleteButton.addActionListener(this);
		updateButton.addActionListener(this);
		insertButton.addActionListener(this);
		table.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			this.dispose();
			new MainForm(loggedUser);
		}
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
				eachGame.add(game.getGenreName());
				eachGame.add(game.getQuantity().toString());
				
				allData.add(eachGame);
			}
		}
		
		return allData;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Integer rowIndex = table.getSelectedRow();
		System.out.println("Row Selected: " + rowIndex);
		
		int price = Integer.valueOf((String) dtm.getValueAt(rowIndex, 2));
		int quantity = Integer.valueOf((String) dtm.getValueAt(rowIndex, 4));
		
		Game selectedGame = gameDataClass.get(rowIndex);
		selectedGameGlobal = selectedGame;
		
		// Set to fields
		gameidField.setText(selectedGame.getGameID());
		gameNameField.setText(selectedGame.getName());
		gamePriceField.setText(String.valueOf(selectedGame.getPrice()));
//		gamegenreField.setText(selectedGame.getGenreName());
		genreComboBox.setSelectedItem(selectedGame.getGenreName());
		System.out.println("Combo Box: " + genreComboBox.getSelectedIndex());
		gameQuantitySpinner.setValue(selectedGame.getQuantity());
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}



}
