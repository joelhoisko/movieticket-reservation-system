package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The same main Stage as ResrevationSystemActivities, expect its got some additional buttons 
 * and controls for admins.
 * @author joel
 *
 */
public class AdminSystemActivities extends Stage {

	private DataBaseActions dataBaseActions;
	

	private final Button logoutButton = new Button("Logout");
	private final Button exitButton = new Button("Exit");
	private final Button browseMoviesButton = new Button("Browse movies");
	private final Button myReservationsButton = new Button("Reservations");

	private final Button ModMovies = new Button(" Modify Movies");
	private final Button ModShows = new Button("Modify Shows");
	private final Button myInfo = new Button("Check my info");

	private HBox hBoxTop = new HBox(10);
	private VBox vBoxSide = new VBox(30);
	private GridPane gridPane = new GridPane();
	private ScrollPane scrollPane = new ScrollPane();
	private String username;

	public AdminSystemActivities(BorderPane borderPane, String username, DataBaseActions dataBaseActions2) {
		
		this.dataBaseActions = dataBaseActions2;
		
		//this.dataBaseActions.connect();
		this.username = username;
		
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25,25,25,25));
	
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setContent(gridPane);

		// Whatever is inside the the top bar, is located on the right.
		hBoxTop.setAlignment(Pos.BASELINE_RIGHT); 
		// Whatever is inside the VBox is in the center.
		vBoxSide.setAlignment(Pos.CENTER); 

		// EventHandler watches the buttons and notices which button gets pressed.
		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				if (e.getSource() == myReservationsButton) {
					System.out.println("My Reservations");
					new MyReservations(dataBaseActions, gridPane);
				}
				
				if (e.getSource()== browseMoviesButton) {
					gridPane.getChildren().clear();

				
					new MovieDisplayScreen(gridPane, dataBaseActions);
					
				}
				if (e.getSource() == logoutButton) {
					System.out.println("Logout");
					// We go back to the loginscreen in the beginning.
					Stage primaryStage = new Stage();
					primaryStage.setTitle("Ticket-Reservation System"); 

					GridPane gridPane = new GridPane();

					// We create a new LoginScreen and go back to the start.
					LoginScene loginScene = new LoginScene(primaryStage, gridPane, dataBaseActions);
					primaryStage.setScene(loginScene);
					primaryStage.show();
					// And then we close our main(this) window.
					AdminSystemActivities.this.close();
				}
				if (e.getSource() == exitButton) {
					new CommonAlertDialogs(new GridPane(),"You will be logged off and program will be closed");
					dataBaseActions.closeAll();
					AdminSystemActivities.this.close();
				}
				if(e.getSource()== ModMovies){
					gridPane.getChildren().clear();
					new ModMovieScreen(gridPane, dataBaseActions);
				}
				if(e.getSource()== ModShows){
					gridPane.getChildren().clear();
					new ShowModPane(gridPane, dataBaseActions);
				}
				
				if(e.getSource()== myInfo){

				}
			}
		};

		// We call the EventHandler when a button is pressed.
		logoutButton.setOnAction(eventHandler);
		browseMoviesButton.setOnAction(eventHandler);
		myReservationsButton.setOnAction(eventHandler);
		exitButton.setOnAction(eventHandler);
		ModMovies.setOnAction(eventHandler);
		ModShows.setOnAction(eventHandler);
		myInfo.setOnAction(eventHandler);
		// adding buttons to elements
		hBoxTop.getChildren().addAll(new Text(this.username), logoutButton, exitButton);
		vBoxSide.getChildren().addAll(myReservationsButton, browseMoviesButton,ModMovies,ModShows);
		// add content to grid panel


		// setting stuff to borderpane
		borderPane.setTop(hBoxTop);
		borderPane.setLeft(vBoxSide);
		borderPane.setCenter(scrollPane);

		// If we want true fullscreen, we set the Stage fullscreen.
		this.setFullScreen(true);

		// Create a new Scene, give it the pane, CSS and its children some CSS too.
		Scene scene = new Scene(borderPane);
		scene.getStylesheets().add("res/stylesheet.css");
		hBoxTop.getStyleClass().add("hbox");
		vBoxSide.getStyleClass().add("vbox");

		this.setTitle("Ticketex");
		this.setScene(scene);
		this.show();
	}
}



