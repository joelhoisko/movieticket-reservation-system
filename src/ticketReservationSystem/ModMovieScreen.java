package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/*
 * Screen added to the AdminSystemActivites-stage when admin presses the button "Modify movies".
 */

public class ModMovieScreen {
	private HBox buttons;
	private final Button addMovieButton = new Button("Add movie");
	private final Button modifyMovieButton = new Button("Modify movie");
	private final Button removeMovieButton = new Button("Remove movie");
	private DataBaseActions dataBaseActions = new DataBaseActions();
	private final GridPane gridPane;
	private Button addOkButton = new Button("OK");
	private TextField addTitle;
	private TextField addLength;

	public ModMovieScreen(final GridPane gridPane) {
		this.gridPane = gridPane;
		this.dataBaseActions.connect();

		initButtons();

		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				if (e.getSource() == addMovieButton) {
					createMovie();
				}
				if (e.getSource() == modifyMovieButton) {

				}
				if (e.getSource() == removeMovieButton) {

				}
				if(e.getSource() == addOkButton) {
					if(!addTitle.getText().isEmpty() && !addLength.getText().isEmpty()) {
						addMovieToDatabase();
						gridPane.getChildren().clear();
						
						new ShowModPane(gridPane);
						
						initButtons();
					}
				}
			}			
		};
		addMovieButton.setOnAction(eventHandler);
		modifyMovieButton.setOnAction(eventHandler);
		removeMovieButton.setOnAction(eventHandler);
		addOkButton.setOnAction(eventHandler);

	}

	public HBox getBox() {
		return buttons;
	}

	private void initButtons() {
		this.buttons = new HBox(10);
		buttons.getChildren().add(addMovieButton);
		buttons.getChildren().add(modifyMovieButton);
		buttons.getChildren().add(removeMovieButton);
		gridPane.add(buttons, 0, 2);
		System.out.println("JEE!");
	}

	private void createMovie() {
		// TODO Auto-generated method stub
		gridPane.add(new Label("Movie title: "), 0, 4);
		addTitle = new TextField();
		gridPane.add(addTitle, 1, 4);
		gridPane.add(new Label("Length: "), 0, 5);
		addLength = new TextField();
		gridPane.add(addLength, 1, 5);
		gridPane.add(addOkButton, 1, 6);
	}

	private void addMovieToDatabase() {
		// TODO Auto-generated method stub
		dataBaseActions.updateQuery("INSERT INTO movies (" + addTitle.getText() + "," + Integer.parseInt(addLength.getText()) + ");");
	}

}
