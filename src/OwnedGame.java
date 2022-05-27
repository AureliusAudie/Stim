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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OwnedGame extends JFrame 
implements 
JFrameSettings, 
ActionListener, ItemListener, MouseListener{
	
	User loggedUser;
	
	DatabaseConnection db = new DatabaseConnection();
	
	JPanel southPanel, northPanel, gameIDPanel, gameNamePanel, gamepricePanel,
		gameGenrePanel, quantityPanel, totalSpentPanel ,buttonPanel;
	
	JLabel gameIDJLabel, gameNameJLabel, 
		gamePriceJLabel, gameGenreJLabel, quantityJLabel, totalSpentJLabel;
	
	JTextField gameidField, gameNameField, 
		gamePriceField, gamegenreField, quantityField, totalSpentField;
	
	JButton backButton;
	
	JTable table;
	DefaultTableModel dtm;
	
	JScrollPane scrollPane;
	
	Vector<Vector<String>> gameData;
	Vector<Game> gameDataClass;

	public OwnedGame(User loggedUser) {
		this.loggedUser = loggedUser;
		settings();
		initComponents();
		setVisible(true);
	}
	
	public OwnedGame() {}
	

	@Override
	public void settings() {
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
		totalSpentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		
		// Init Jtextfield
		gameidField = new JTextField();
		gameNameField = new JTextField();
		gamePriceField = new JTextField();
		gamegenreField = new JTextField();
		quantityField = new JTextField();
		totalSpentField = new JTextField();
		
		// Init JLabel
		gameIDJLabel = new JLabel("Game ID");
		gameNameJLabel = new JLabel("Game Name");
		gamePriceJLabel = new JLabel("Game Price");
		gameGenreJLabel = new JLabel("Game Genre");
		quantityJLabel = new JLabel("Owned Quantity");
		totalSpentJLabel = new JLabel("Total Spent on Games");
		
		// INit Button
		backButton = new JButton("Back");
		
		// Column Names
        Vector<String> columnNames = new Vector<String>(); 
        columnNames.add("Game ID");
        columnNames.add("Game Name");
        columnNames.add("Game Price");
        columnNames.add("Genre");
        columnNames.add("Quantity");
        
        // Populate Data
        gameData = db.getOwnedGamesData(loggedUser.getUserId());
        Integer totalSpent = getTotalSpent(gameData);
        totalSpentField.setText(String.valueOf(totalSpent));
        
        // INIT TABLE
        dtm = new DefaultTableModel(gameData, columnNames);
		table = new JTable(dtm);
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
		quantityPanel.add(quantityField);
		totalSpentPanel.add(totalSpentJLabel);
		totalSpentPanel.add(totalSpentField);
		buttonPanel.add(backButton);
		
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
		quantityField.setPreferredSize(new Dimension(400,30));
		totalSpentJLabel.setPreferredSize(new Dimension(300,30));
		totalSpentField.setPreferredSize(new Dimension(400,30));
		backButton.setPreferredSize(new Dimension(100, 40));
		backButton.setPreferredSize(new Dimension(100, 40));
		buttonPanel.setPreferredSize(new Dimension(700, 40));
		
		// Add to north Panel
		northPanel.add(scrollPane);
		
		// Add to south panel
		southPanel.add(gameIDPanel);
		southPanel.add(gameNamePanel);
		southPanel.add(gamepricePanel);
		southPanel.add(gameGenrePanel);
		southPanel.add(quantityPanel);
		southPanel.add(totalSpentPanel);
		southPanel.add(buttonPanel);

		// Add Listener
		backButton.addActionListener(this);
		
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
		quantityField.setEditable(false);
		totalSpentField.setEditable(false);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Integer rowIndex = table.getSelectedRow();
//		int price = Integer.valueOf((String) dtm.getValueAt(rowIndex, 2));
//		int quantity = Integer.valueOf((String) dtm.getValueAt(rowIndex, 4));
		
		Vector<String> selectedGame = gameData.get(rowIndex);
		
		// Set to fields
		gameidField.setText(selectedGame.get(0));
		gameNameField.setText(selectedGame.get(1));
		gamePriceField.setText(String.valueOf(selectedGame.get(4)));
		gamegenreField.setText(selectedGame.get(2));
		quantityField.setText(selectedGame.get(3));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			this.dispose();
			new MainForm(loggedUser);
		}
	}
	
	private int getTotalSpent(Vector<Vector<String>> gameData){
		int total = 0;
		
		for (Vector<String> vector : gameData) {
			Integer price = Integer.parseInt(vector.get(4)) * Integer.parseInt(vector.get(3));
			total += price;
		}
		
		return total;
	}

}
