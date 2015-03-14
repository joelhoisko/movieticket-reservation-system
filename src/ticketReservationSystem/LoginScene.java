package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Contains a Scene made for a login-window. 
 * You can log from this view or go to the registration-view.
 * @author joel
 *
 */
public class LoginScene extends Scene {
	
	private DataBaseActions dataBaseActions = new DataBaseActions();
	
	private GridPane gridPane;
	
	private Text topTitle = new Text("Ticket Reservation System"); 
	private Text bottomTitle = new Text("Login");
	
	private Label usernameLabel = new Label("User Name");
	private Label passwordLabel = new Label("Password");
	
	final private TextField usernameField = new TextField();
	final private PasswordField passwordField = new PasswordField();
	
	final private Button loginButton = new Button("Login");
	final private Button createAccountButton = new Button("Create account");
	final private Button exitButton = new Button("Exit");

	/**
	 * Contains the Nodes to draw on the login-screen.
	 * @param stage
	 * @param gridPane
	 */
	public LoginScene(final Stage stage, final GridPane gridPane) {
		super(gridPane);
		// We load the CSS.
		this.getStylesheets().add("res/stylesheet.css");
		//this.dataBaseActions = new DataBaseActions();
		
		this.gridPane = gridPane;

		gridPane.setAlignment(Pos.TOP_CENTER); // grids position in stage
		gridPane.setHgap(15); // space between rows
		gridPane.setVgap(10); // space between columns
		gridPane.setPadding(new Insets(25,25,25,25)); // the space around all the sides of grid pane

		// fonts for both titles
		topTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
		bottomTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		// EventHandler watches the buttons and notices which button gets pressed.
		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (event.getSource() == loginButton) {

					// Returns true if everything goes fine, but we still just ignore it.
					if (loginChecker(usernameField.getText(), passwordField.getText())) {

						/*
						 * In the case that the user logs off and reloggs it throws an error 
						 * about scenes having only one root element.
						 * That's why we need to give it a new BorderPane every time, 
						 * the same one cannot be used multiple times.
						 * And then we close ourselves after the new window has opened.
						 */
						if(isAdmin(usernameField.getText())){
							BorderPane Panel = new BorderPane();
							dataBaseActions.closeAll();
							new AdminSystemActivities(Panel);
							stage.close();
							
						}else{
						BorderPane borderPane = new BorderPane();
						// Just to be sure, we close the connection to the database too.
						dataBaseActions.closeAll();
						new ReservationSystemActivities(borderPane);
						stage.close();
					}
					}
				}
				if (event.getSource() == createAccountButton) {
					/*
					 * Every Scene can have only one root element and every Parent node
					 * can only be set as root for a just one scene.
					 * That's why we make a new Parent node(GridPane) every time we change the scene.
					 */
					stage.setScene(new RegistrationScene(stage, new GridPane()));
				}
				if (event.getSource() == exitButton) {
					// Just to be sure, we close the connection to the database too.
					dataBaseActions.closeAll();
					stage.close();
				}
			}
		};

		// Give the ActionHandler to the buttons.
		loginButton.setOnAction(eventHandler);
		createAccountButton.setOnAction(eventHandler);
		exitButton.setOnAction(eventHandler);

		// Column row coordinates. The last two parameters are width using columns as measurement 
		// and height using rows.
		gridPane.add(topTitle,0,0,3,1);
		gridPane.add(bottomTitle,0,1,2,1);
		// stuff to grid using column, row coordinates
		gridPane.add(usernameLabel,0,2);
		gridPane.add(passwordLabel,0,3);
		gridPane.add(usernameField,1,2);
		gridPane.add(passwordField,1,3);
		gridPane.add(loginButton,1,4);
		gridPane.add(createAccountButton,0,4);
		gridPane.add(exitButton,2,4);

		gridPane.setPrefSize(450, 300);
	}

	/**
	 * Creates a new dataBaseActions, connects to the database/creates it 
	 * and runs a check on users. If a user with the matching credentials 
	 * is found, return true. Otherwise, false.
	 * @param userName
	 * @param password
	 */
	private boolean loginChecker(String userName, String password) {
		dataBaseActions.connect();
		UserList userList = new UserList();
		userList.initializeLists();
		
		String query = "SELECT * FROM users;";
		userList.parseUserList(dataBaseActions.selectQuery(query));

		try {
			// If both the username an password are correct, continue!
			if (userName.equals(userList.getUser(userName).getUserName()) 
					&& password.equals(userList.getUser(userName).getPassword())) {
				System.out.println("Match!");
				return true;
			}
		} catch (UserNotFoundException e) {
			GridPane p = new GridPane();
			System.out.println("User not found");
			usernameField.clear();
			passwordField.clear();
			usernameField.setPromptText("Try again!");
			passwordField.setPromptText("Try again!");
			gridPane.add(new Label("Invalid username and/or password!"), 1, 1, 3, 1);
		}
		return false;
	}
	/**
	 *  Checks if a user has admin status. IF yes, true is returned otherwise false.
	 * @param userName
	 * @return boolean
	 */
	public boolean isAdmin(String userName){
		dataBaseActions.connect();
		UserList list = new UserList();
		list.initializeLists();
		String query = "SELECT * FROM users;";
		list.parseUserList(dataBaseActions.selectQuery(query));
		try {
			if(list.getUser(userName).getPermission() == true){
				return true;
			}
		} catch (UserNotFoundException e) {
			
			e.printStackTrace();
		}
		return false;
	}
}