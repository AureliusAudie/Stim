import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

public class DatabaseConnection {
	
	public Connection connection;
	public Statement statement;
	
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	
	public PreparedStatement preparedStatement;
	
	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProjectBAD", "root", "");
			
			statement = connection.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet execQuery(String query) {
		try {
			resultSet = statement.executeQuery(query);
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public ResultSet getUsersData() {
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM User");
			
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public Vector<Game> getGamesData() {
		Vector<Game> gameVector = new Vector<Game>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT g.*, gn.GenreName FROM Game g JOIN Genre gn ON g.GenreID = gn.GenreID");
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			
			while (resultSet.next()) {
				Game game = new Game(
						resultSet.getString("GameID"), 
						resultSet.getString("Name"), 
						resultSet.getInt("price"), 
						resultSet.getString("GenreID"), 
						resultSet.getInt("Quantity")
					);
				
				game.setGenreName(resultSet.getString("GenreName"));
				
				gameVector.add(game);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gameVector;
	}
	
	public Vector<Vector<String>> getOwnedGamesData(String userID) {
		Vector<Vector<String>> ownedGamesVector = new Vector<>();
		
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT g.GameID, g.name AS GameName, gn.GenreName, SUM(tr.gameQuantity) AS quantity, g.price FROM `Transaction` tr"
						+ " JOIN Game g ON tr.GameID = g.GameID"
						+ " JOIN Genre gn ON gn.GenreID = g.GenreID"
						+ " WHERE tr.UserID = ?"
						+ " GROUP BY tr.GameID"
				);
			
			preparedStatement.setString(1, userID);
			
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			
			while (resultSet.next()) {
				Vector<String> game = new Vector<String>();
				
				game.add(resultSet.getString("GameID"));
				game.add(resultSet.getString("GameName"));
				game.add(resultSet.getString("GenreName"));
				game.add(resultSet.getString("quantity"));
				game.add(resultSet.getString("price"));
				
				ownedGamesVector.add(game);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ownedGamesVector;
	}
	
	public Integer checkUserGameOwned(String gameID, String userID) {
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM UserGame WHERE UserID = ? AND GameID = ?");
			preparedStatement.setString(1, userID);
			preparedStatement.setString(2, gameID);
			
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			
			if(resultSetMetaData.getColumnCount() > 0) {				
				while(resultSet.next()) {
					return resultSet.getInt("UserGameID");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public User getUserDataFromUsername(String username) {
		User userdata = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
			
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = preparedStatement.getMetaData();
		
			if(resultSet.next()) {
				userdata = new User();
				
				userdata.setUserId(resultSet.getString("UserID"));
				userdata.setUserUsername(resultSet.getString("Username"));
				userdata.setUserPassword(resultSet.getString("Password"));
				userdata.setUserGender(resultSet.getString("Gender"));
				userdata.setUserCountry(resultSet.getString("Country"));
				userdata.setUserRole(resultSet.getString("Role"));
			}
				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return userdata;		
	}
	
	public boolean insertNewUser(User newUser) {
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO User (`UserID`, `Username`, `Password`, `Gender`, `Country`, `Role`) "
					+ "VALUES (?,?,?,?,?,?)");
			
			preparedStatement.setString(1, newUser.getUserId());
			preparedStatement.setString(2, newUser.getUserUsername());
			preparedStatement.setString(3, newUser.getUserPassword());
			preparedStatement.setString(4, newUser.getUserGender());
			preparedStatement.setString(5, newUser.getUserCountry());
			preparedStatement.setString(6, newUser.getUserRole());
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewTransaction(String userID, String gameID, Integer quantity) {
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO transaction (`TransactionID`, `UserID`, `GameID`, `gameQuantity`) "
					+ "VALUES (?,?,?,?)");
			
			Random r = new Random( System.currentTimeMillis() );
		    int transactionID = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
			
			preparedStatement.setInt(1, transactionID);
			preparedStatement.setString(2, userID);
			preparedStatement.setString(3, gameID);
			preparedStatement.setInt(4, quantity);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Vector<String> getGenreList() {
		Vector<String> genreList = new Vector<String>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM genre");
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			
			while (resultSet.next()) {
				genreList.add(resultSet.getString("GenreName"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genreList;
	}

}
