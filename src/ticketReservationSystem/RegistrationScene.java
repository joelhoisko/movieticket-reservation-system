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
 * This Scene draws a registration-window and contains the functionality to 
 * register a new Customer.
 *
 */
public class RegistrationScene extends Scene {
	
	@SuppressWarnings("unused")
	private GridPane gridPane;
	
	private DataBaseActions dataBaseActions;
	private Text topTitle = new Text("Register"); 
	private Text bottomTitle = new Text("Create a new user!");
	
	private Label usernameLabel = new Label("Username");
	private Label nameLabel = new Label("Name");
	private Label passwordLabel = new Label("Password");
	private Label passwordAgainLabel = new Label("Write password again");
	
	final private TextField usernameField = new TextField();
	final private TextField nameField = new TextField();
	final private PasswordField passwordField = new PasswordField(); 
	final private PasswordField passwordAgainField = new PasswordField();

	final private Button registerButton = new Button("Register");
	final private Button backButton = new Button("Back");


	/**
	 * Contains the Nodes to draw when the user tries to register a new account.
	 * @param stage
	 * @param gridPane
	 * @param dataBaseActions 
	 */
	public RegistrationScene(final Stage stage, final GridPane gridPane, final DataBaseActions dataBaseActions) {
		super(gridPane);
		// We load the CSS.
		this.getStylesheets().add("res/stylesheet.css");
		
		this.gridPane = gridPane;
      
		this.dataBaseActions = dataBaseActions;
		
		// fonts for both titles
		topTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
		bottomTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		// EventHandler watches the buttons and notices which button gets pressed.
		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (event.getSource() == registerButton) {

					String userName = usernameField.getText();
					String name = nameField.getText();
					String password = passwordField.getText();
					
					if (checkCredentials(userName, name)) {
						if (createUser(userName, name, password))  {
							// notification here
							
					     
							
							BorderPane borderPane = new BorderPane();
							new ReservationSystemActivities(borderPane, dataBaseActions);
							stage.close();
						}
					}
				}
				if (event.getSource() == backButton) {
					System.out.println("Back");
					/*
					 * Every Scene can have only one root element and every Parent node
					 * can only be set as root for a just one scene.
					 * That's why we make a new Parent node(GridPane) every time we change the scene.
					 */
					stage.setScene(new LoginScene(stage, new GridPane(), RegistrationScene.this.dataBaseActions));
				}
			}
		};

		// Give the buttons the ActionHandler.
		registerButton.setOnAction(eventHandler);
		backButton.setOnAction(eventHandler);

		gridPane.setVgap(10); // space between rows
		gridPane.setHgap(10); // space between columns
		gridPane.setPadding(new Insets(20,20,20,20)); // free space around grid
		gridPane.setAlignment(Pos.TOP_CENTER); // grids position

		// Adds the title.
		
		gridPane.add(topTitle, 0, 0, 3, 1);
		gridPane.add(bottomTitle, 0, 1, 2, 1);

		// adding stuff to grid. parameters: component, column number, row number

		gridPane.add(nameLabel, 0, 2);
		gridPane.add(nameField, 1, 2);
		gridPane.add(usernameLabel, 0, 3); 
		gridPane.add(usernameField, 1, 3);
		gridPane.add(passwordLabel, 0, 4);
		gridPane.add(passwordField, 1, 4);
		gridPane.add(passwordAgainLabel, 0, 5);
		gridPane.add(passwordAgainField, 1, 5);

		gridPane.add(registerButton, 0, 6);
		gridPane.add(backButton, 1, 6);

		gridPane.setPrefSize(450, 300);
	}

	/**
	 * Tries to register a new user and saves it into the database.
	 * Also creates the "USERS" table.
	 * @param userName
	 * @param name
	 * @param password
	 * @param userNameField
	 */
	private boolean createUser(String userName, String name, String password) {
		// We connect to the database.
		//DataBaseActions dataBaseActions = new DataBaseActions();
		//dataBaseActions.connect();

		UserList userList = new UserList();
		userList.initializeLists();
		// We load the whole userList now.
		String usersQuery = "SELECT * FROM users;";	
		userList.parseUserList(dataBaseActions.selectQuery(usersQuery));

		try {
			// If an user already exists, we alert the user about it.
			if (userName.equals(userList.getUser(userName).getUserName())) {
				System.out.println("That username is already taken!");
			
				usernameField.clear();
				nameField.clear();
				passwordField.clear();
				passwordAgainField.clear();
				usernameField.setPromptText("Username taken!");
				return false;
			}
		} catch (UserNotFoundException e) {
			// If we catch the exception, we know that the userName is unique.
			System.out.println("Creating user...");
			// Creates a new Customer into the customerList.
			Customer customer = new Customer(userName, name, password);
			userList.addCustomer(customer);
			// Adds the Customer safely into the database.
			dataBaseActions.addCustomer(userName, name, password);
			
			LoginScene.GLOBAL_USER = customer;
			return true;	
		}
		return false;
	}

	private boolean checkCredentials(String userName, String name) {
		
		if (!(passwordField.getText().equals(passwordAgainField.getText()) && !passwordField.getText().isEmpty())) {
			passwordField.clear();
			passwordAgainField.clear();
			passwordField.setPromptText("Not matching!");
			passwordAgainField.setPromptText("Not matching!");
			return false;
		}
		if (userName.isEmpty()) {
			usernameField.clear();
			usernameField.setPromptText("Can't be empty!");
			return false;
		}
		if (name.isEmpty()) {
			nameField.clear();
			nameField.setPromptText("Can't be empty!");
			return false;
		}
		return true;
	}
}