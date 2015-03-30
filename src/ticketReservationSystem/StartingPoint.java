package ticketReservationSystem;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class for main method and a "Base for javafx UI ".
 * This might look a bit weird on your laptop, if you have JavaSE - 1.7 as your JRE.
 * Change it to jre 1.8.0_31 and it should work just fine. 
 * From Window -> Preferences -> Java -> Installed JREs.
 * @author Markus
 *
 */
public class StartingPoint extends Application {
	private DataBaseActions dataBaseActions = new DataBaseActions();

	public static void main(String args[]){
		launch(args);
	}

	/**
	 * The login window where everything starts.
	 */
	public void start(Stage primaryStage) throws Exception {

		dataBaseActions.connect();

		primaryStage.setTitle("Ticket-Reservation System"); // window title
		checkAdmins();
		GridPane gridPane = new GridPane();

		// We start the LoginScene first and go on from there.
		LoginScene loginScene = new LoginScene(primaryStage, gridPane, dataBaseActions);
		loginScene.getStylesheets().add("res/stylesheet.css");

		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	/**
	 * A method that creates the first admin with admin admin username password combination.
	 *When an admin loggs in using these attributes, the user is asked to create a new username entirely.
	 */
	public void checkAdmins(){

		try {
			
			dataBaseActions.firstTime();
			
		} catch (SQLException e) {
			System.out.println("Creating the database, might take a moment");

			dataBaseActions.initEverything();
			dataBaseActions.addAdmin("admin","admin","admin");

		}
	}
}