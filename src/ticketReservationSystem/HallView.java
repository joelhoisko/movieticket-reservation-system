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
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class HallView {

	private GridPane pane;

	private ArrayList<Show> choiceShows = new ArrayList<Show>();
	private Show chosenShow;
	private DataBaseActions dataBaseActions;
	private ChoiceBox<String> theatersChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Kinopalatsi","Tennispalatsi"));
	private ChoiceBox<Integer> hallsK = new ChoiceBox<Integer>(FXCollections.observableArrayList(1,2,3));
	private ChoiceBox<Integer> hallsT = new ChoiceBox<Integer>(FXCollections.observableArrayList(1,2));
	private ChoiceBox<String> showsBox;
	private ObservableList<String> showlist = FXCollections.observableArrayList();
	private ArrayList<ToggleButton> seatButtonList = new ArrayList<ToggleButton>();	
	private ArrayList<Seat> ownSeats = new ArrayList<Seat>();
	private Button showSeats;
	private Button makeAReservation = new Button("Reserve!");
	private String selectedMovie;
	private int hallId = 0;

	public HallView(final GridPane pane, final String movie, DataBaseActions dataBaseActions2){
		this.dataBaseActions = dataBaseActions2;
		this.pane = pane;

		this.selectedMovie = movie;

		showSeats = new Button("Choose seats");
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		pane.add(separator, 1, 0);
		pane.add(new Text(selectedMovie),0,0);
		pane.add(new Text("Choose Theater."), 0, 1);
		theatersChoice.setVisible(false);
		pane.add(theatersChoice,0,2);
		Transitions.fadeIn(theatersChoice);

		theatersChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value){
				if(theatersChoice.getItems().get((Integer)new_value).equals("Kinopalatsi")){
					Text t = new Text("Choose hall.");

					pane.add(t, 0, 3);
					pane.add(hallsK,0,4);

					t.setVisible(false);
					Transitions.fadeIn(t);
					hallsK.setVisible(false);
					Transitions.fadeIn(hallsK);
				}else{
					Text t = new Text("Choose hall.");

					pane.add(t, 0, 3);
					pane.add(hallsT,0,4);

					t.setVisible(false);
					Transitions.fadeIn(t);
					hallsT.setVisible(false);
					Transitions.fadeIn(hallsT);
				}


			}
		});

		hallsT.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue v, Number value, Number new_value){
				int hall = hallsT.getItems().get((Integer)new_value) + 2; //Changed hallsK to hallsT
				hallId = hall;

				Theater Tennispalatsi = dataBaseActions.getTheaterInfo((String) theatersChoice.getValue());
				ArrayList<Show> shows = Tennispalatsi.getShows();


				for(int i = 0; i< shows.size();i++){
					if(shows.get(i).getHall().getHallNumber() == hall && shows.get(i).getMovie().getName().equals(selectedMovie)){
						showlist.add(shows.get(i).getTime());
						choiceShows.add(shows.get(i));
					}
				}
				if(showlist.size()==0){
					//showlist.add("No shows yet!");
				}
				showsBox = new ChoiceBox<String>(showlist);
				Text t = new Text("Choose show");
				t.setVisible(false);
				showsBox.setVisible(false);
				pane.add(t,0,5);
				pane.add(showsBox,0,6);
				Transitions.fadeIn(showsBox);
				Transitions.fadeIn(t);
				// We handle showBox events here. Now the box exists in the gridpanel and the compiler will not be whining about a nullpointer...
				showsBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
					public void changed(ObservableValue v, Number value, Number new_value){
						pane.add(showSeats,0,7);
						showSeats.setVisible(false);
						Transitions.fadeIn(showSeats);

						String selectedTime = showsBox.getItems().get((Integer)new_value);

						for (int i = 0; i < showlist.size(); i++) {
							if (showlist.get(i).equals(selectedTime)) {
								chosenShow = choiceShows.get(i);
							}
						}
					}
				});

			}
		});

		hallsK.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			public void changed(@SuppressWarnings("rawtypes") ObservableValue v, Number value, Number new_value){
				int hall = hallsK.getItems().get((Integer)new_value) - 1; //Changed hallsT to hallsK
				hallId = hall;

				Theater Kinopalatsi = dataBaseActions.getTheaterInfo((String) theatersChoice.getValue());

				ArrayList<Show> shows = Kinopalatsi.getShows();
				for (int i = 0; i< shows.size();i++) {
					if (shows.get(i).getHall().getHallNumber() == hall && shows.get(i).getMovie().getName().equals(selectedMovie)){
						showlist.add(shows.get(i).getTime());
						choiceShows.add(shows.get(i));
					}
				}
				if (showlist.size()==0) {
					//showlist.add("No shows yet!");
				}
				showsBox = new ChoiceBox<String>(showlist);
				Text t = new Text("Choose show");
				t.setVisible(false);
				pane.add(t,0,5);
				pane.add(showsBox,0,6);
				showsBox.setVisible(false);
				Transitions.fadeIn(showsBox);
				Transitions.fadeIn(t);

				// We handle showBox events here. Now the box 
				//exists in the gridpanel and the compiler will not be whining about a nullpointer...
				showsBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
					@SuppressWarnings("rawtypes")
					public void changed(ObservableValue v, Number value, Number new_value){
						pane.add(showSeats,0,7);
						showSeats.setVisible(false);
						Transitions.fadeIn(showSeats);

						String selectedTime = showsBox.getItems().get((Integer)new_value);

						for (int i = 0; i < showlist.size(); i++) {
							if (showlist.get(i).equals(selectedTime)) {
								chosenShow = choiceShows.get(i);
							}
						}
					}
				});

			}
		});

		showSeats.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){

				createHall();
			}
		});

		makeAReservation.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				makeReservation();
			}
		});

	}

	public void createHall() {
		ArrayList<Seat> seatList = new ArrayList<Seat>();

		try {

			String query = "SELECT seats.id, seats.seatnumber FROM seats, connections WHERE "+
							"connections.showid = " + chosenShow.getId() + 
							" AND connections.seatid = seats.id;"; ;
			ResultSet seatSet = dataBaseActions.selectQuery(query);

			while (seatSet.next()) {

				int seatId = seatSet.getInt("id");
				int seatNumber = seatSet.getInt("seatnumber");

				int rowIndex;
				int colIndex;
				String seatString = String.valueOf(seatNumber);

				String[] table = seatString.split("");
				if (seatString.length() > 2) {
					colIndex = Integer.parseInt(table[2]);
					rowIndex = Integer.parseInt(table[0] + table[1]) + 1;
					if (colIndex == 0) {
						rowIndex--;
					}
				} else if (seatString.length() == 2) {
					colIndex = Integer.parseInt(table[1]);
					rowIndex = Integer.parseInt(table[0]) + 1;
					if (colIndex == 0) {
						rowIndex--;
					}
				} else {
					colIndex = Integer.parseInt(seatString);
					rowIndex = 1;
				}
				seatNumber = Integer.parseInt(seatString);

				seatList.add(new Seat(seatNumber, rowIndex, colIndex, seatId));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		pane.add(makeAReservation, 12, 1);
		makeAReservation.setVisible(false);
		Transitions.fadeIn(makeAReservation);

		ImageView imageView = new ImageView();
		Image reservedSeat = new Image("/res/Varattupenkki.png",20,20,false,false);

		Image freeSeat = new Image("/res/Vapaapenkki.png",20,20,false,false);

		try {
			for (Seat seat : seatList) {

				String query = "SELECT state FROM connections WHERE seatid = " + seat.getId() + 
								" AND showid = " + chosenShow.getId() + ";";
				ResultSet stateSet = dataBaseActions.selectQuery(query);

				int state = stateSet.getInt("state");

				if (state == 0) {
					imageView.setImage(freeSeat);
					seatButtonList.add(new ToggleButton(String.valueOf(seat.getSeatNumber()), new ImageView(freeSeat)));
				} else {
					imageView.setImage(reservedSeat);
					ToggleButton toggle = new ToggleButton(String.valueOf(seat.getSeatNumber()), new ImageView(reservedSeat));
					toggle.setDisable(true);
					seatButtonList.add(toggle);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int columnIndex = 2;
		int rowIndex = 0;

		for (int i = 0; i < seatButtonList.size(); i++) {
			pane.add(seatButtonList.get(i), columnIndex, rowIndex);
			if (columnIndex > 10) {
				columnIndex = 1;
				rowIndex++;
			}
			columnIndex++;
		}

	}


	private void makeReservation() {

		for (ToggleButton toggle : seatButtonList) {
			int rowIndex;
			int colIndex;

			if (toggle.isSelected()) {
				String toggleText = toggle.getText();
				String[] table = toggleText.split("");
				if (toggleText.length() > 2) {
					colIndex = Integer.parseInt(table[2]);
					rowIndex = Integer.parseInt(table[0] + table[1]) + 1;
					if (colIndex == 0) {
						rowIndex--;
					}
				} else if (toggleText.length() == 2) {
					colIndex = Integer.parseInt(table[1]);
					rowIndex = Integer.parseInt(table[0]) + 1;
					if (colIndex == 0) {
						rowIndex--;
					}
				} else {
					colIndex = Integer.parseInt(toggleText);
					rowIndex = 1;

				}
				int seatNumber = Integer.parseInt(toggle.getText());


				try {
					ResultSet seatSeat = dataBaseActions.selectQuery("SELECT * FROM seats" +
							" WHERE hallid = " + hallId +
							" AND seatnumber = " + seatNumber + ";");
					int seatId = seatSeat.getInt("id");
					ownSeats.add(new Seat(seatNumber, rowIndex, colIndex, seatId));
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

		for (Seat seat : ownSeats) {
			dataBaseActions.addReservation(chosenShow.getId(), seat.getId(), LoginScene.GLOBAL_USER.getUserName());
			dataBaseActions.updateSeatState(1, seat.getId(), chosenShow.getId());
		}

		pane.getChildren().clear();
		new MyReservations(dataBaseActions, pane);
	}

}
