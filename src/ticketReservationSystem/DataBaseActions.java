package ticketReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DataBaseActions is used to modify the SQLite-database.
 * 
 * @author joel
 * 
 */
public class DataBaseActions {

	private Statement statement;
	private PreparedStatement preparedStatement;
	private Connection connection;

	public DataBaseActions() {
		this.statement = null;
		this.preparedStatement = null;
		this.connection = null;

	}

	public void initEverything() {
		initAllTables();
		initTheaters();
		initHalls();
		initSeats();
	}

	/**
	 * Connects to the database, this has to be done at least once before we can
	 * modify the database. On first run creates the database.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public synchronized void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Name of the database is "data.db" at the moment.
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:data.db");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problems with connecting to the database!");
		}
	}

	/**
	 * Query anything you want from the database with the given query.
	 * 
	 * @param query
	 * @return
	 */
	public synchronized ResultSet selectQuery(String query) {

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();

			ResultSet set = statement.executeQuery(query);
			connection.commit();
			return set;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with the query!");
			return null;
		}
	}

	public void firstTime() throws SQLException {
		connection.setAutoCommit(false);
		statement = connection.createStatement();
		@SuppressWarnings("unused")
		ResultSet set = statement.executeQuery("SELECT * FROM users;");
	}

	/**
	 * Update the database(create tables, inserts stuff, remove things, drop
	 * tables, everything) with the given query.
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public synchronized void updateQuery(String query) {
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error with the update!");

		} 
	}

	/**
	 * Is used to create new Customers. The PreapredStatement makes sure that
	 * nobody can inject malicious code into our database as the query is
	 * prepared beforehand.
	 * 
	 * @param userName
	 * @param name
	 * @param password
	 */
	public void addCustomer(String userName, String name, String password) {
		try {
			connection.setAutoCommit(false);
			// We prepare the query beforehand.
			String prepared = "INSERT INTO users (username,name,password,permission) values (?, ?, ?, ?);";
			preparedStatement = connection.prepareStatement(prepared);
			// The given values(?'s) are identified by their number.
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, 0);

			preparedStatement.executeUpdate();
			connection.commit();

			System.out.println("Add customer complete!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with adding a Customer!");

		}
	}

	/**
	 * Adds admin users to database
	 * 
	 * @param userName
	 * @param name
	 * @param password
	 */
	public void addAdmin(String userName, String name, String password) {
		try {
			connection.setAutoCommit(false);
			// We prepare the query beforehand.
			String prepared = "INSERT INTO users (username,name,password,permission) values (?, ?, ?, ?);";
			preparedStatement = connection.prepareStatement(prepared);
			// The given values(?'s) are identified by their order-number.
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, 1);

			preparedStatement.executeUpdate();
			connection.commit();

			System.out.println("Add admin complete!");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with adding an Admin!");
		}
	}

	/**
	 * Adds a Show to the database with the given values. A Show contains a
	 * Movie, the hall where it is(hallid isn't unique, can't know the theater
	 * through here!) and the time when the show is.
	 * 
	 * @param time
	 * @param hallId
	 */
	public void addShow(String moviename, int hallId, String time) {
		try {
			connection.setAutoCommit(false);
			String prepared = "INSERT INTO shows(moviename,hallid,time)values(?,?,?);";
			preparedStatement = connection.prepareStatement(prepared);

			preparedStatement.setString(1, moviename);
			preparedStatement.setInt(2, hallId);
			preparedStatement.setString(3, time);

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with adding a Show!");
		}
	}
	
	public void addConnection(int seatid, int showid, int state) {
		try {
			connection.setAutoCommit(false);
			String preString = "INSERT INTO connections(seatid,showid,state) VALUES(?,?,?);";
			preparedStatement = connection.prepareStatement(preString);
			preparedStatement.setInt(1, seatid);
			preparedStatement.setInt(2, showid);
			preparedStatement.setInt(3, state);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet askShowId(String time, int hallid) {
		try {
			connection.setAutoCommit(false);
			String preString = "SELECT id FROM shows WHERE hallid = ? AND time = ?;";
			preparedStatement = connection.prepareStatement(preString);
			preparedStatement.setInt(1, hallid);
			preparedStatement.setString(2, time);
			ResultSet set = preparedStatement.executeQuery();
			connection.commit();
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Inserts a new Movie-entry to the database.
	 * 
	 * @param name
	 * @param length
	 */
	public synchronized void addMovie(String name, int length) {
		try {
			connection.setAutoCommit(false);
			String prepared = "INSERT INTO movies(name,length)values(?,?);";
			preparedStatement = connection.prepareStatement(prepared);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, length);

			preparedStatement.toString();

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with adding a Movie!");
		} 

	}

	/**
	 * Adds a new reservation to the database.
	 * @param showId
	 * @param seatId
	 * @param customerName
	 */
	public void addReservation(int showId, int seatId, String customerName) {
		try {
			connection.setAutoCommit(false);
			String prepared = "INSERT INTO reservations(showid,seatid,customername) VALUES (?,?,?);";

			preparedStatement = connection.prepareStatement(prepared);
			preparedStatement.setInt(1, showId);
			preparedStatement.setInt(2, seatId);
			preparedStatement.setString(3, customerName);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Changes the state of a single "seats"-table element.
	 * @param state
	 * @param seatId
	 */
	public void updateSeatState(int state, int seatId, int showid) {
		try {
			connection.setAutoCommit(false);
			String prepared = "UPDATE connections SET state = ? WHERE seatid = " + seatId + ""
								+ "	AND showid = " + showid + ";";

			preparedStatement = connection.prepareStatement(prepared);
			preparedStatement.setInt(1, state);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets all the information regarding a single Theater-object from the database,
	 * identified by the String theaterName.
	 * It gives the Theater an ArrayList of it's own halls, movies, and shows
	 * @param theaterName
	 * @return Theater
	 */
	public synchronized Theater getTheaterInfo(String theaterName) {

		ResultSet hallSet = selectQuery("SELECT * FROM halls;");

		ResultSet allMoviesSet = selectQuery("SELECT * FROM movies;");
		ArrayList<Movie> allMoviesList = new ArrayList<Movie>();

		ArrayList<Movie> theaterMovies = new ArrayList<Movie>();
		ArrayList<Show> showList = new ArrayList<Show>();
		ArrayList<Hall> hallList = new ArrayList<Hall>();
		ArrayList<Seat> seatList = new ArrayList<Seat>();
		ArrayList<Show> ownShows = new ArrayList<Show>();

		// Populates the allMoviesList with all the possible Movie-objects in the database.
		try {
			while (allMoviesSet.next()) {
				String movieName = allMoviesSet.getString("name");
				int movieLength = allMoviesSet.getInt("length");
				allMoviesList.add(new Movie(movieName, movieLength));
			}
			allMoviesSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (theaterName.equals("Tennispalatsi")) {
			ResultSet firstHallShows = existingShows(3);
			ResultSet secondHallShows = existingShows(4);

			try {
				while (firstHallShows.next()) {
					String movieName = firstHallShows.getString("moviename");
					int hallid = 3;
					String time = firstHallShows.getString("time");
					int showId = firstHallShows.getInt("id");

					for (Movie movie : allMoviesList) {
						if (movie.getName().equals(movieName)) {
							showList.add(new Show(movie, new Hall(hallid), time, showId));
						}
					}

				}
				while (secondHallShows.next()) {
					String movieName = secondHallShows.getString("moviename");
					int hallid = 4;
					String time = secondHallShows.getString("time");
					int showId = secondHallShows.getInt("id");

					for (Movie movie : allMoviesList) {
						if (movie.getName().equals(movieName)) {
							showList.add(new Show(movie, new Hall(hallid), time, showId));
						}
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < showList.size(); i++) {

				if (!theaterMovies.contains(showList.get(i).getMovie())) {
					theaterMovies.add(showList.get(i).getMovie());
				}
			}

			try {
				while (hallSet.next()) {

					int id = hallSet.getInt("id");

					if (id > 2) {

						ResultSet seatSet = selectQuery("SELECT * FROM seats WHERE hallid = "
								+ id + ";");
						int row = 0;
						int column = 0;
						while (seatSet.next()) {

							int seatnumber = seatSet.getInt("seatnumber");
							int seatId = seatSet.getInt("id");

							Seat seat = new Seat(seatnumber, row, column, seatId);
							column++;
							if (column > 9) {
								row++;
								column = 0;
							}
							seatList.add(seat);
						}

						ArrayList<Seat> hallsSeats = new ArrayList<>();
						hallsSeats.addAll(seatList);
						Hall hall = new Hall(id);
						hall.setSeats(hallsSeats);
						seatList.clear();

						for (int i = 0; i < showList.size(); i++) {
							ownShows.add(showList.get(i));
						}

						hall.setOwnShows(ownShows);
						ownShows.clear();
						hallList.add(hall);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Tennispalatsi loaded from the database!");
		}

		if (theaterName.equals("Kinopalatsi")) {
			ResultSet firstHallShows = existingShows(0);
			ResultSet secondHallShows = existingShows(1);
			ResultSet thirdHallShows = existingShows(2);

			try {
				while (firstHallShows.next()) {
					String movieName = firstHallShows.getString("moviename");
					int hallid = 0;
					String time = firstHallShows.getString("time");
					int showId = firstHallShows.getInt("id");

					for (Movie movie : allMoviesList) {
						if (movie.getName().equals(movieName)) {
							showList.add(new Show(movie, new Hall(hallid), time, showId));
						}
					}

				}
				while (secondHallShows.next()) {
					String movieName = secondHallShows.getString("moviename");
					int hallid = 1;
					String time = secondHallShows.getString("time");
					int showId = secondHallShows.getInt("id");

					for (Movie movie : allMoviesList) {
						if (movie.getName().equals(movieName)) {
							showList.add(new Show(movie, new Hall(hallid), time, showId));
						}
					}

				}
				while (thirdHallShows.next()) {
					String movieName = thirdHallShows.getString("moviename");
					int hallid = 2;
					String time = thirdHallShows.getString("time");
					int showId = thirdHallShows.getInt("id");

					for (Movie movie : allMoviesList) {
						if (movie.getName().equals(movieName)) {
							showList.add(new Show(movie, new Hall(hallid), time, showId));
						}
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < showList.size(); i++) {

				if (!theaterMovies.contains(showList.get(i).getMovie())) {
					theaterMovies.add(showList.get(i).getMovie());
				}
			}
			try {
				while (hallSet.next()) {

					int id = hallSet.getInt("id");

					if (id < 3) {						

						ResultSet seatSet = selectQuery("SELECT * FROM seats WHERE hallid = "
								+ id + ";");
						int row = 0;
						int column = 0;
						while (seatSet.next()) {

							int seatnumber = seatSet.getInt("seatnumber");
							int seatId = seatSet.getInt("id");

							Seat seat = new Seat(seatnumber, row, column, seatId);
							column++;
							if (column > 9) {
								row++;
								column = 0;
							}
							seatList.add(seat);
						}
						Hall hall = new Hall(id);
						ArrayList<Seat> hallsSeats = new ArrayList<>();
						hallsSeats.addAll(seatList);
						hall.setSeats(hallsSeats);
						seatList.clear();
						for (int i = 0; i < showList.size(); i++) {
							if (showList.get(i).getHall().getHallNumber() == id) {
								ownShows.add(showList.get(i));
							}
						}
						hall.setOwnShows(ownShows);
						ownShows.clear();
						hallList.add(hall);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("Kinopalatsi loaded from the database!");
		}


		Theater theater = new Theater(hallList, theaterName, theaterMovies, showList);
		return theater;
	}

	public Boolean checkMovie(String title) {
		String query = "SELECT name,length FROM movies WHERE name =" + title
				+ ";";
		Boolean b = false;
		try {
			ResultSet set = selectQuery(query);
			if (set.next()) {
				b = true;

			}
			b = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * Deletes an user based on username.
	 * @param table
	 * @param Username
	 */
	public void DeleteUser(String Username) {
		try {
			connection.setAutoCommit(false);
			String action = "DELETE FROM users WHERE username = " + Username
					+ ";";
			preparedStatement = connection.prepareStatement(action);

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with deleting an User!");
		}
	}

	public void removeReservation(int reservationId) {
		try {
			connection.setAutoCommit(false);
			String prepared = "DELETE FROM reservations WHERE id = ?;";
			preparedStatement = connection.prepareStatement(prepared);
			preparedStatement.setInt(1, reservationId);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates a single Users username in the database.
	 * 
	 * @param oldName
	 * @param newName
	 */
	public void updateUsername(String oldName, String newName) {
		try {
			connection.setAutoCommit(false);
			String action = "UPDATE users SET username =" + newName
					+ "WHERE username =" + oldName + ";";
			preparedStatement = connection.prepareStatement(action);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates a single Users password in the database. Username is needed for
	 * identifying the user.
	 * 
	 * @param oldPW
	 * @param newPW
	 * @param userName
	 */
	public void updatePassword(String oldPW, String newPW, String userName) {
		try {
			connection.setAutoCommit(false);

			String action = "UPDATE users SET passsword =" + newPW
					+ "WHERE username =" + userName + ";";
			preparedStatement = connection.prepareStatement(action);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateReservation(int reservationId, int oldSeatId, int newSeatId, int showid) {
		updateSeatState(0, oldSeatId, showid);
		try {
			connection.setAutoCommit(false);
			String prepared = "UPDATE reservations SET seatid = ? WHERE id = " + reservationId + ";";
			preparedStatement = connection.prepareStatement(prepared);

			preparedStatement.setInt(1, newSeatId);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateSeatState(1, newSeatId, showid);
	}

	public synchronized ResultSet existingShows(int hallId) {

		String prepared = "SELECT * FROM shows WHERE hallid = " + hallId +";";

		try {
			preparedStatement = connection.prepareStatement(prepared);
			ResultSet set = preparedStatement.executeQuery();
			connection.commit();
			return set;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Closes everything if possible. Should be called when exciting the
	 * application, but it might do it automatically.
	 * 
	 * @throws SQLException
	 */
	public void closeAll() {
		try {

			if (statement != null && connection != null) {
				connection.close();

				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("Problem with closing, but probably doesn't matter!");
		}
	}


	/*-------------ONLY IN CASE OF EMERGENCY, OUT OF BOUNDS-------------*/

	/**
	 * Run this everytime you need to recreate the tables in the database.
	 * Basically, tries to create the tables in the database.
	 * https://www.sqlite.org/foreignkeys.html
	 */
	private void initAllTables() {

		String createUsersTable = "CREATE TABLE users " +
				"(username		text 	PRIMARY KEY," + " name			text," + " password		text," +
				" permission 	int)";

		String createReservationsTable = "CREATE TABLE reservations " +
				"(id			INTEGER		PRIMARY KEY," + 
				" showid		int," +
				" seatid		int," +
				" customername	text)";

		String createTheatersTable = "CREATE TABLE theaters " +
				"(id			int 	PRIMARY KEY," +
				" name			text)";

		String createMoviesTable = "CREATE TABLE movies " +
				"(id			INTEGER		PRIMARY KEY," +
				" name			text ," + 
				" length		int);";

		String createShowsTable = "CREATE TABLE shows " +
				"(id INTEGER PRIMARY KEY," + 
				" moviename text," +
				" hallid int," + 
				" time text);";

		String createHallsTable = "CREATE TABLE halls " + 
				"(id			int			PRIMARY KEY," +
				" seats			int," +
				" theaterid		int," +
				" FOREIGN KEY(theaterid)	REFERENCES theaters(id));";
		
		String createConnectionTable ="CREATE TABLE connections " +
				"(seatid		int," +
				" showid		int," +
				" state			int);";

		String createTableSeats = "CREATE TABLE seats " +
				"(id			INTEGER 	PRIMARY KEY," + 
				" seatnumber	int," + 
				" hallid        int);";

		updateQuery(createUsersTable); 
		updateQuery(createMoviesTable);
		updateQuery(createReservationsTable); 
		updateQuery(createHallsTable);
		updateQuery(createShowsTable);
		updateQuery(createTheatersTable);
		updateQuery(createConnectionTable);
		updateQuery(createTableSeats); 
		System.out.println("Tables created!"); 
	}

	/**
	 * Initializes the "theaters" table with Tennis- and Kinopalatsi.
	 */
	private void initTheaters() {

		try { 
			connection.setAutoCommit(false);

			String update = "INSERT INTO theaters(id,name) VALUES(?,?);";
			preparedStatement = connection.prepareStatement(update); // Creates a theater with id = 0 and name "Tennispalatsi" 
			preparedStatement.setInt(1,0); 
			preparedStatement.setString(2, "Tennispalatsi");
			preparedStatement.executeUpdate(); //Creates a theater with id = 1 and name "Kinopalatsi" 
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, "Kinopalatsi");
			preparedStatement.executeUpdate();

			connection.commit(); 
		} catch (SQLException e) { 
			e.printStackTrace();
			System.out.println("Error with initializing Theater-tables!"); 
		} 
	}

	private void initSeats() {

		try {
			connection.setAutoCommit(false);

			String update = "INSERT INTO seats(seatnumber,hallid) VALUES(?,?);";
			preparedStatement = connection.prepareStatement(update);

			for (int i = 1; i <= 100; i++) {
				preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 0);
				preparedStatement.executeUpdate();
			}
			for (int i = 1; i <= 40; i++) {
				preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 1);
				preparedStatement.executeUpdate();
			}
			for (int i = 1; i <= 60; i++) {
				preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 2);
				preparedStatement.executeUpdate();
			}
			for (int i = 1; i <= 150; i++) {
				preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 3);
				preparedStatement.executeUpdate();
			}
			for (int i = 1; i <= 50; i++) {
				preparedStatement.setInt(1, i);
				preparedStatement.setInt(2, 4);
				preparedStatement.executeUpdate();
			}
			connection.commit();
			System.out.println("Seats initialized!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the "halls" table with some predetermined halls.
	 */
	private void initHalls() {

		try { 
			connection.setAutoCommit(false);

			String update = "INSERT INTO halls(id,seats,theaterid) VALUES(?,?,?);";
			preparedStatement = connection.prepareStatement(update);

			// theaterid defines the theater where the hall is, 0 is Tennispalatsi, 1 Kinopalatsi.
			preparedStatement.setInt(1, 3); 	// ID of the hall, this case it's 0.
			preparedStatement.setInt(2, 150); 	// Number of seats.
			preparedStatement.setInt(3, 0); 	// theaterid = 0
			preparedStatement.executeUpdate();

			preparedStatement.setInt(1, 4); 
			preparedStatement.setInt(2, 50); 	
			preparedStatement.setInt(3, 0);		// theaterid = 0
			preparedStatement.executeUpdate();

			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, 100); 
			preparedStatement.setInt(3, 1);
			preparedStatement.executeUpdate();

			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, 40);
			preparedStatement.setInt(3, 1);
			preparedStatement.executeUpdate();

			preparedStatement.setInt(1, 2);
			preparedStatement.setInt(2, 60);
			preparedStatement.setInt(3, 1);
			preparedStatement.executeUpdate();

			connection.commit(); 
		} catch (SQLException e) { 
			e.printStackTrace();
			System.out.println("Error with initializing halls!"); 
		} 
	}

	public void updateMovie(int length, String modMovieName) {
		try {
			connection.setAutoCommit(false);
			String pre = "UPDATE movies SET length = ? WHERE name = '" + modMovieName + "';";
			
			preparedStatement = connection.prepareStatement(pre);
			preparedStatement.setInt(1, length);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


} // teehee