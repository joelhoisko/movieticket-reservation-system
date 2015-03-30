package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Screen added to the AdminSystemActivites-stage when admin presses the button "Modify movies".
 * @author joel
 *
 */
public class ModMovieScreen {
	private DataBaseActions dataBaseActions;
	private HBox hBox;
	private final GridPane gridPane;
	private String modMovieName;
	
	private final Button addMovieButton = new Button("Add movie");
	private final Button modifyMovieButton = new Button("Modify movie");
	private final Button removeMovieButton = new Button("Remove movie");
	private TextField selectMovieField = new TextField();
	private Button modButton = new Button("Modify");
	private Button removeButton = new Button("Remove");
	private Button changeValuesButton = new Button("Change values");
	private Button addOkButton = new Button("OK");
	private TextField addTitle;
	private TextField addLength;

	public ModMovieScreen(final GridPane gridPane, DataBaseActions dataBaseActions2) {
		this.gridPane = gridPane;
		this.dataBaseActions = dataBaseActions2;

		initButtons();

		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				if (e.getSource() == addMovieButton) {
					gridPane.getChildren().clear();
					initButtons();
					createMovie();
				}
				if (e.getSource() == modifyMovieButton) {
					selectMovieField.setText(null);
					gridPane.getChildren().clear();
					initButtons();
					createModifyMovieScreen();
				}
				if (e.getSource() == removeMovieButton) {
					selectMovieField.setText(null);
					gridPane.getChildren().clear();
					initButtons();
					gridPane.add(new Label("Choose movie"), 0, 4);
					gridPane.add(selectMovieField, 0, 5);
					gridPane.add(removeButton, 1, 5);
				}
				if (e.getSource() == modButton) {
					if(!selectMovieField.getText().isEmpty()) {
						modMovieName = selectMovieField.getText();
						gridPane.getChildren().clear();
						initButtons();
						modifyMovie();
					}
				}
				if (e.getSource() == addOkButton) {
					if(!addTitle.getText().isEmpty() && !addLength.getText().isEmpty()) {
						//addMovieToDatabase();
						gridPane.getChildren().clear();
						dataBaseActions.addMovie(addTitle.getText(),Integer.parseInt(addLength.getText()));
						new ShowModPane(gridPane, dataBaseActions);
					}
				}
				if (e.getSource() == changeValuesButton) {
					changeValuesFromDatabase();
				}
				if (e.getSource() == removeButton) {
					if(!selectMovieField.getText().isEmpty()) {
						dataBaseActions.updateQuery("DELETE FROM connections WHERE showid = " +
								"(SELECT id FROM shows WHERE moviename = '" + selectMovieField.getText() + "');");
						dataBaseActions.updateQuery("DELETE FROM reservations WHERE showid = " +
								"(SELECT id FROM shows WHERE moviename = '" + selectMovieField.getText() + "');");
						dataBaseActions.updateQuery("DELETE FROM movies WHERE name = '" + selectMovieField.getText() + "';");
						dataBaseActions.updateQuery("DELETE FROM shows WHERE moviename = '" + selectMovieField.getText() + "';");
						selectMovieField.setText(null);
					}
				}
			}			
		};
		addMovieButton.setOnAction(eventHandler);
		modifyMovieButton.setOnAction(eventHandler);
		removeMovieButton.setOnAction(eventHandler);
		addOkButton.setOnAction(eventHandler);
		modButton.setOnAction(eventHandler);
		removeButton.setOnAction(eventHandler);
		changeValuesButton.setOnAction(eventHandler);
		
	}

	public HBox getBox() {
		return hBox;
	}

	private void initButtons() {
		this.hBox = new HBox(10);
		hBox.getChildren().add(addMovieButton);
		hBox.getChildren().add(modifyMovieButton);
		hBox.getChildren().add(removeMovieButton);
		hBox.setVisible(false);
		
		gridPane.add(hBox, 0, 2);
		Transitions.fadeIn(hBox);
	}

	private void createMovie() {
		gridPane.add(new Label("Movie title: "), 0, 4);
		addTitle = new TextField();
		gridPane.add(addTitle, 1, 4);
		gridPane.add(new Label("Length: "), 0, 5);
		addLength = new TextField();
		gridPane.add(addLength, 1, 5);
		gridPane.add(addOkButton, 1, 6);
		
		Group group = new Group();
		group.getChildren().addAll(addTitle, addLength, addOkButton);
		group.setVisible(false);
		Transitions.fadeIn(group);
	}

	private void createModifyMovieScreen() {
		gridPane.add(new Label("Select movie"), 0, 4);
		gridPane.add(selectMovieField, 0, 5);
		gridPane.add(modButton, 1, 5);
	}

	private void modifyMovie() {
		gridPane.add(new Label("Length: "), 0, 5);
		addLength = new TextField();
		gridPane.add(addLength, 1, 5);
		gridPane.add(changeValuesButton, 1, 6);
	}

	private void changeValuesFromDatabase() {
		dataBaseActions.updateMovie(Integer.parseInt(addLength.getText()), modMovieName);
	}

}