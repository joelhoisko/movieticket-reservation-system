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
 * The main fullscreen Stage used to navigate between different parts of the program.
 * Conatins a HBox and VBox to contain the buttons used for navigation and a GridPane in the middle of the screen
 * used for displaying various controls and options. We then  swap the GridPane with another one when 
 * we want to show various things. At this point we do not change the Scene anymore.
 * @author joel
 *
 */
public class ReservationSystemActivities extends Stage {
	
	private DataBaseActions dataBaseActions;
	
	private final Button logoutButton = new Button("Logout");
	private final Button exitButton = new Button("Exit");
	private final Button browseMoviesButton = new Button("Browse movies");
	private final Button myReservationsButton = new Button("Reservations");
	
	private HBox hBoxTop = new HBox(10);
	private VBox vBoxSide = new VBox(30);
	private GridPane gridPane = new GridPane();
	private ScrollPane scrollPane = new ScrollPane();
	private String username;

	public ReservationSystemActivities(BorderPane borderPane, final DataBaseActions dataBaseActions2) {
		this.username = LoginScene.GLOBAL_USER.getUserName();
		this.dataBaseActions = dataBaseActions2;
		
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
					gridPane.getChildren().clear();
					new MyReservations(dataBaseActions, gridPane);
					
				}

				if (e.getSource()== browseMoviesButton) {
					System.out.println("Browse movies");
					
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
					LoginScene loginScene = new LoginScene(primaryStage, gridPane, dataBaseActions2);
					primaryStage.setScene(loginScene);
					primaryStage.show();
					// And then we close our main(this) window.
					ReservationSystemActivities.this.close();
				}
				if (e.getSource() == exitButton) {
					new CommonAlertDialogs(new GridPane(),"You will be logged off and program will be closed");
					dataBaseActions2.closeAll();
					ReservationSystemActivities.this.close();
				}
			}
		};

		// We call the EventHandler when a button is pressed.
		logoutButton.setOnAction(eventHandler);
		browseMoviesButton.setOnAction(eventHandler);
		myReservationsButton.setOnAction(eventHandler);
		exitButton.setOnAction(eventHandler);

		// adding Nodes(buttons) to their Parents(panels).
		hBoxTop.getChildren().addAll(new Text(username), logoutButton, exitButton);
		vBoxSide.getChildren().addAll(myReservationsButton, browseMoviesButton);
		
		// setting stuff to borderpane
		borderPane.setTop(hBoxTop);
		borderPane.setLeft(vBoxSide);
		borderPane.setCenter(scrollPane);
		borderPane.setPrefSize(1350.0, 700.0);
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
