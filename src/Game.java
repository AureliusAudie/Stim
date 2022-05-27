
public class Game {

	String gameID;
	String name;
	Integer price;
	String genreID;
	String genreName;
	Integer quantity;

	public Game(String gameID, String name, Integer price, String genreID, Integer quantity) {
		super();
		this.gameID = gameID;
		this.name = name;
		this.price = price;
		this.genreID = genreID;
		this.quantity = quantity;
	}

	public Game() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getGenreID() {
		return genreID;
	}

	public void setGenreID(String genreID) {
		this.genreID = genreID;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}
