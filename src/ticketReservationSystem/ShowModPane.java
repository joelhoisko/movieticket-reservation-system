package ticketReservationSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/*
 * When a new instance of this class is created, it asks a movie to be modified.
 * Then it creates a view where you can add new showtimes.
 * Sorry it's a total mess...
 * t. Sauli
 */


/**
 * Used to add new shows to a Movie. Requires the admin to input a Movie, 
 * Theater, Hall and a Time when the show runs.
 * @author joel
 *
 */
public class ShowModPane {
	private final GridPane gridPane;

	private ChoiceBox<String> theaterBox = new ChoiceBox<String>(FXCollections.observableArrayList("Kinopalatsi","Tennispalatsi"));
	private ChoiceBox<Integer> hallsKino = new ChoiceBox<Integer>(FXCollections.observableArrayList(1,2,3));
	private ChoiceBox<Integer> hallsTennis = new ChoiceBox<Integer>(FXCollections.observableArrayList(1,2));
	private ChoiceBox<String> allMoviesBox;
	private ObservableList<String> moviesCollection = FXCollections.observableArrayList();

	private Label movieLabel = new Label("Choose movie.");
	private Label theaterLabel = new Label("Choose theater");
	private Label hallLabel = new Label("Choose hall");
	private Label showLabel = new Label("Existing showtimes");
	private Label waitPleaseLabel = new Label("Adding shows might take a while, don't panic.");

	private Text showtimeLabel = new Text("Insert showtimes. When you are finished adding shows, press done.");

	private ArrayList<TextField> newShows = new ArrayList<TextField>();
	private ArrayList<String> oldShows = new ArrayList<String>();
	private ArrayList<Movie> allMoviesList = new ArrayList<Movie>();

	private TextField newShowTimeField = new TextField();
	private Button confirmModifyButton = new Button("Confirm modification");

	private Button addShowsButton = new Button("Add shows");
	private Button addButton = new Button("Add another text field");
	private Button removeButton = new Button("Remove shows");
	private Button modifyButton = new Button("Modify one show");
	private int HBoxIndex;
	private VBox oldShowsBox = new VBox();

	private DataBaseActions dataBaseActions;

	private Text warning = new Text("Provide a title of a movie!");
	private String movieName;
	private int chosenHallId = 0;
	private ArrayList<Hall> chosenHalls;

	public ShowModPane(final GridPane gridPane, DataBaseActions dataBaseActions2) {
		this.gridPane = gridPane;
		this.dataBaseActions = dataBaseActions2;

		initMovies();

		HBoxIndex = 9;

		createChooseWindow();

		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				if (e.getSource() == addShowsButton) {
					if (!newShows.get(0).getText().isEmpty()) {
						addInformationToDataBase();
					}

					gridPane.getChildren().clear();
					new MovieDisplayScreen(gridPane, dataBaseActions);
				}

				if (e.getSource() == modifyButton) {
					modifyShow();
				}

				if (e.getSource() == removeButton) {
					removeShows();
				}

				if (e.getSource() == addButton) {
					addHBox();
				}

				if (e.getSource() == confirmModifyButton) {
					updateShowInDatabase();
				}
			}		
		};

		allMoviesBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value) {
				movieName = allMoviesBox.getItems().get((Integer)new_value);

				Transitions.fadeIn(theaterLabel);
				Transitions.fadeIn(theaterBox);
			}
		});

		theaterBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value) {


				if (theaterBox.getItems().get((Integer)new_value).equals("Kinopalatsi")) {

					chosenHalls = dataBaseActions.getTheaterInfo("Kinopalatsi").getHalls();

					Transitions.fadeIn(hallLabel);
					try {
						gridPane.getChildren().remove(hallsTennis);
					} catch(Exception e) {

					}
					gridPane.add(hallsKino,0,7);
					hallsKino.setVisible(false);
					Transitions.fadeIn(hallsKino);

				} else {

					chosenHalls = dataBaseActions.getTheaterInfo("Tennispalatsi").getHalls();


					Transitions.fadeIn(hallLabel);
					try {
						gridPane.getChildren().remove(hallsKino);
					} catch(Exception e){

					}
					gridPane.add(hallsTennis,0,7);
					hallsTennis.setVisible(false);
					Transitions.fadeIn(hallsTennis);
				}
			}
		});

		hallsKino.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value) {
				/*if(!oldShowsBox.getChildren().isEmpty()) {
					oldShowsBox.getChildren().clear();
				}*/
				oldShowsBox.getChildren().clear();
				chosenHallId = hallsKino.getItems().get((Integer)new_value) - 1;
				Transitions.fadeIn(showtimeLabel);
				Transitions.fadeIn(addButton);
				Transitions.fadeIn(addShowsButton);
				Transitions.fadeIn(showLabel);
				oldShowsBox = showBox();
				oldShowsBox.setVisible(false);
				gridPane.add(oldShowsBox,1,11,2,1);
				Transitions.fadeIn(oldShowsBox);

				Transitions.fadeIn(modifyButton);
				Transitions.fadeIn(removeButton);
				if (newShows.isEmpty()) {
					addHBox();
				} 		
				Transitions.fadeIn(waitPleaseLabel);

			}
		});
		hallsTennis.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value) {

				oldShowsBox.getChildren().clear();
				chosenHallId = hallsTennis.getItems().get((Integer)new_value) + 2;
				Transitions.fadeIn(showtimeLabel);
				Transitions.fadeIn(addButton);
				Transitions.fadeIn(addShowsButton);
				Transitions.fadeIn(showLabel);
				oldShowsBox = showBox();  //Made oldShows an attribute
				oldShowsBox.setVisible(false);
				gridPane.add(oldShowsBox,1,11,2,1);
				Transitions.fadeIn(oldShowsBox);

				Transitions.fadeIn(modifyButton);
				Transitions.fadeIn(removeButton);
				if (newShows.isEmpty()) {
					addHBox();
				}			
				Transitions.fadeIn(waitPleaseLabel);

			}
		});
		addButton.setOnAction(eventHandler);
		addShowsButton.setOnAction(eventHandler);
		modifyButton.setOnAction(eventHandler);
		removeButton.setOnAction(eventHandler);
		confirmModifyButton.setOnAction(eventHandler);
	}

	private void addHBox() {

		HBox showtimeBox = new HBox(10); // New HBox
		TextField showtimeField = new TextField(); // new TextField. should not be an attribute
		newShows.add(showtimeField); // an arrayList consisting of TextField Objects
		showtimeBox.getChildren().add(showtimeField); // textField To HBox
		gridPane.add(showtimeBox, 0, HBoxIndex); // we add the hbox to gridpanel
		showtimeBox.setVisible(false);
		Transitions.fadeIn(showtimeBox);
		HBoxIndex++; // 
	}

	private VBox showBox() {
		ResultSet showset = null;
		VBox oldShowsBox = new VBox(10);
		oldShows.clear();

		if (theaterBox.getValue().equals("Kinopalatsi")) {
			showset = dataBaseActions.existingShows(chosenHallId);
			try {
				while(showset.next()){
					String mname = showset.getString("moviename");
					String time = showset.getString("time");
					int hallid = showset.getInt("hallid");
					if(hallid == chosenHallId ){
						oldShows.add(mname +" : "+ time);
					}
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if(theaterBox.getValue().equals("Tennispalatsi")){
			showset = dataBaseActions.existingShows(chosenHallId);
			try {
				while(showset.next()){
					String mname = showset.getString("moviename");
					String time = showset.getString("time");
					int hallid = showset.getInt("hallid");
					if(hallid == chosenHallId ){
						oldShows.add(mname + " : "+ time);
					}
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		for(int i = 0;i < oldShows.size();i++){

			oldShowsBox.getChildren().add(new Label(oldShows.get(i)));
		}
		return oldShowsBox;
	}

	/**
	 * Adds a new Show to the database. Problem is, we can't identify a hall based on it's hallid
	 * as duplicates exist.
	 */
	private void addInformationToDataBase() {
		for (int i = 0; i < newShows.size(); i++) {
			dataBaseActions.addShow(movieName, chosenHallId, newShows.get(i).getText());
			try {
				ResultSet idSet = dataBaseActions.askShowId(newShows.get(i).getText(), chosenHallId);
				int showId = idSet.getInt("id");

				for (Hall hall : chosenHalls) {
					if (hall.getHallNumber() == chosenHallId) {
						System.out.println("Iterating through seats now");
						for (Seat seat : hall.getSeats()) {
							dataBaseActions.addConnection(seat.getId(), showId, 0);
						}
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void modifyShow() {
		Transitions.fadeIn(newShowTimeField);
		Transitions.fadeIn(confirmModifyButton);

	}

	private void removeShows() {

		for (TextField textField : newShows) {
			String text = allMoviesBox.getValue() + " : " + textField.getText();
			if (oldShows.contains(text)) {

				dataBaseActions.updateQuery("DELETE FROM connections WHERE showid = " + 
						"(SELECT id FROM shows WHERE hallid = " + chosenHallId +
						" AND time = '" + textField.getText() + "');");
				dataBaseActions.updateQuery("DELETE FROM reservations WHERE showid = " + 
						"(SELECT id FROM shows WHERE hallid = " + chosenHallId +
						" AND time = '" + textField.getText() + "');");
				dataBaseActions.updateQuery("DELETE FROM shows WHERE hallid = " + chosenHallId +
						" AND time = '" + textField.getText() + "';");


			}
		}
		gridPane.getChildren().clear();
		new ShowModPane(gridPane, dataBaseActions);
	}

	public void initMovies() {

		try {
			ResultSet movieSet = dataBaseActions.selectQuery("SELECT * FROM movies;");

			while (movieSet.next()) {
				String name = movieSet.getString("name");
				int length = movieSet.getInt("length");
				allMoviesList.add(new Movie(name, length));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Movie movie : allMoviesList) {
			moviesCollection.add(movie.getName());
		}
		allMoviesBox = new ChoiceBox<String>(moviesCollection);
	}

	private void updateShowInDatabase() {
		String showTime = newShowTimeField.getText();
		String oldTime = newShows.get(0).getText();
		String text = allMoviesBox.getValue() + " : " + oldTime;

		if (oldShows.contains(text)) {
			dataBaseActions.updateQuery("UPDATE shows SET time = '" + showTime +
					"' WHERE hallid = " + chosenHallId +
					" AND time = '" + oldTime + "';");
			gridPane.getChildren().clear();
			new ShowModPane(gridPane, dataBaseActions);
		} else {
			newShowTimeField.clear();
			newShowTimeField.setPromptText("Show doesn't exist!");

		}
	}


	private void createChooseWindow() {
		gridPane.add(movieLabel, 0, 2);
		gridPane.add(allMoviesBox, 0, 3);
		gridPane.add(theaterLabel, 0, 4);
		gridPane.add(theaterBox, 0, 5); 
		gridPane.add(hallLabel, 0, 6);
		gridPane.add(showLabel,1,10,2,1);
		gridPane.add(warning,4,2);
		gridPane.add(showtimeLabel, 0, 8,3,1);
		gridPane.add(waitPleaseLabel, 4, 2);

		gridPane.add(addButton, 1, 9);
		gridPane.add(addShowsButton, 2, 9);
		gridPane.add(modifyButton, 3, 9);
		gridPane.add(removeButton, 4, 9);

		gridPane.add(newShowTimeField, 3, 10);
		gridPane.add(confirmModifyButton, 3, 11);

		
		waitPleaseLabel.setVisible(false);
		warning.setVisible(false);
		movieLabel.setVisible(false);
		allMoviesBox.setVisible(false);
		showtimeLabel.setVisible(false);
		hallLabel.setVisible(false);
		theaterLabel.setVisible(false);
		theaterBox.setVisible(false);
		showLabel.setVisible(false);

		addButton.setVisible(false);
		addShowsButton.setVisible(false);
		modifyButton.setVisible(false);
		removeButton.setVisible(false);

		newShowTimeField.setVisible(false);
		confirmModifyButton.setVisible(false);

		Transitions.fadeIn(movieLabel);
		Transitions.fadeIn(allMoviesBox);
	}


}