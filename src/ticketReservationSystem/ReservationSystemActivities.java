package ticketReservationSystem;

import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// full Screen Window for the actual activities in  our system.
// Experimentally extends stage, as we need a new one for the activities
public class ReservationSystemActivities extends Stage {
	
	private DataBaseActions dataBaseActions = new DataBaseActions();
	
	private final Button logoutButton = new Button("Logout");
	private final Button exitButton = new Button("Exit");
	private final Button browseMoviesButton = new Button("Browse movies");
	private final Button myReservationsButton = new Button("Reservations");
	private final Button makeReservationButton = new Button("Print userList");
	
	private HBox hBoxTop = new HBox(10);
	private VBox vBoxSide = new VBox(30);
	private GridPane p = new GridPane();
	private ScrollPane gf = new ScrollPane();

	public ReservationSystemActivities(BorderPane borderPane) {
		dataBaseActions.connect();
	
		p.setHgap(10);
		p.setVgap(10);
		p.setPadding(new Insets(25,25,25,25));
		  p.add(new Movietile().getTile(),0,0);
		  p.add(new Movietile().getTile(),0,1);
		gf.setHbarPolicy(ScrollBarPolicy.NEVER);
		gf.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		gf.setContent(p);
		
		// Whatever is inside the the top bar, is located on the right.
		hBoxTop.setAlignment(Pos.BASELINE_RIGHT); 
		// Whatever is inside the VBox is in the center.
		vBoxSide.setAlignment(Pos.CENTER); 

		// EventHandler watches the buttons and notices which button gets pressed.
		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				
				if (e.getSource() == myReservationsButton) {
					System.out.println("My Reservations");
				}
				// For checking the userList.
				if (e.getSource() == makeReservationButton) {
					System.out.println("Make a Reservation");
			
					
					UserList userList = new UserList();
					userList.initializeLists();
					userList.parseUserList(dataBaseActions.selectQuery("SELECT * FROM users;"));
					userList.printuserList();
				} 
				if (e.getSource()== browseMoviesButton) {
					System.out.println("Browse movies");
				}
				if (e.getSource() == logoutButton) {
					System.out.println("Logout");

					// We go back to the loginscreen in the beginning.
					Stage primaryStage = new Stage();
					primaryStage.setTitle("Ticket-Reservation System"); 

					GridPane gridPane = new GridPane();

					// We create a new LoginScreen and go back to the start.
					LoginScene loginScene = new LoginScene(primaryStage, gridPane);
					primaryStage.setScene(loginScene);
					primaryStage.show();
					// And then we close our main(this) window.
					ReservationSystemActivities.this.close();
				}
				if (e.getSource() == exitButton) {
					new CommonAlertDialogs(new GridPane(),"You will be logged off and program will be closed");
					dataBaseActions.closeAll();
					ReservationSystemActivities.this.close();
				}
			}
		};

		// We call the EventHandler when a button is pressed.
		logoutButton.setOnAction(eventHandler);
		browseMoviesButton.setOnAction(eventHandler);
		myReservationsButton.setOnAction(eventHandler);
		makeReservationButton.setOnAction(eventHandler);
		exitButton.setOnAction(eventHandler);

		// adding buttons to elements
		hBoxTop.getChildren().addAll(logoutButton, exitButton);
		vBoxSide.getChildren().addAll(myReservationsButton, makeReservationButton, browseMoviesButton);
		// add content to grid panel
		

		// setting stuff to borderpane
		borderPane.setTop(hBoxTop);
		borderPane.setLeft(vBoxSide);
		borderPane.setCenter(gf);
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
