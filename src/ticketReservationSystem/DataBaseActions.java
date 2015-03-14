package ticketReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * We can modify and query the database through this class. Here's a good tutorial:
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * @author joel
 *
 */
public class DataBaseActions {

	private Statement statement;
	private PreparedStatement preparedStatement;
	private  Connection connection;

	public DataBaseActions() {
		this.statement = null;
		this.preparedStatement = null;
		this.connection = null;

	}

	/**
	 * Connects to the database, this has to be done at least once before 
	 * we can modify the database. On first run creates the database.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void connect() {
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
	 * @param query
	 * @return
	 */
	public ResultSet selectQuery(String query) {

		try {
			statement = connection.createStatement();

			return  statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with the query!");
			return null;
		}	
	}

	/**
	 * Update the database(create tables, inserts stuff, remove things, drop tables, everything)
	 * with the given query.
	 * @param query
	 * @throws SQLException
	 */
	public void updateQuery(String query) {
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
	 * nobody can inject malicious code into our database as the query is prepared beforehand.
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
		}
	}
	/**
	 * Adds admin users to database
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
			// The given values(?'s) are identified by their number.
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, 1);

			preparedStatement.executeUpdate();
			connection.commit();

			System.out.println("Add customer complete!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Closes everything if possible. Should be called when exciting the application.
	 * @throws SQLException
	 */
	public void closeAll() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problem with closing!");
		}
	}
	/**
	 * Deletes an user based on username
	 * @param table
	 * @param Username
	 */
	public void DeleteUser(String table, String Username){
		try{
			connection.setAutoCommit(false);
			String action = "DELETE FROM " + table + " WHERE username = " + Username;
			preparedStatement = connection.prepareStatement(action);

			preparedStatement.executeUpdate();
			connection.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Run this everytime you need to recreate the tables in the database.
	 * Basically, tries to create the tables in the database.
	 */
	public void initAllTables() {

		try {
			statement = connection.createStatement();

			String createUsersTable = "CREATE TABLE users " +
					"(username		text	not null," +
					" name			text	not null," +	
					" password		text	not null," +
					" permission 	int 	not null)";

			String createReservationsTable = "CREATE TABLE reservations " +
					"(id			int		not null," +
					" show			text	references shows(id)," +
					" seatid		int		not null," +
					" customername	text	not null)";

			String createTheatersTable = "CREATE TABLE theaters " +
					"(id			int		not null," +
					" name			text	not null)";

			String createMoviesTable = "CREATE TABLE movies " +
					"(name			text	not null," +
					" length		int		not null," +
					" show			int 	references shows(id))";

			String createShowsTable = "CREATE TABLE shows " +
					"(id			int		not null," +
					" movie			text 	references movies(name)" +
					" hall			int		references halls(id)" +
					" time			text	not null)";

			String createTableHalls = "CREATE TABLE halls " +
					"(id			int		not null," +
					" seats			int		not null," +
					" theater		int		references theaters(id))";

			String createTableSeats = "CREATE TABLE seats " +
					"(id			int 	not null," +
					" seat number   int     not null " +
					" hall          text    not null " +
					" state			int		not null)";

			updateQuery(createUsersTable);
			updateQuery(createMoviesTable);
			updateQuery(createReservationsTable);
			System.out.println("Tables created!");
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Tables already created!");
		}

	}
}
