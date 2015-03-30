package ticketReservationSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MyReservations {

	private GridPane gridPane;
	private Customer customer = LoginScene.GLOBAL_USER;
	private ArrayList<Reservation> reservationsList = new ArrayList<Reservation>();

	private ArrayList<Button> modifyButtonList = new ArrayList<Button>();
	private ArrayList<Button> removeButtonList = new ArrayList<Button>();
	private ArrayList<ToggleButton> toggleList;
	private int rowNumber;

	private DataBaseActions dataBaseActions;

	public MyReservations(DataBaseActions datBase, GridPane gridPane) {
		this.dataBaseActions = datBase;
		this.gridPane = gridPane;

		parseReservations();

		createReservationsView();

	}

	private void createReservationsView() {

		rowNumber = 2;
		for (Reservation r : customer.getReservations()) {
			HBox hBox = new HBox(10);
			Group group = new Group();

			Label movieLabel = new Label(r.getShow().getMovie().getName());
			Label theaterLabel = new Label(r.getTheater().getName());
			Label hallLabel;
			if (r.getTheater().getName().equals("Kinopalatsi")) {
				hallLabel = new Label(String.valueOf(r.getShow().getHall().getHallNumber() + 1));
			} else {
				hallLabel = new Label(String.valueOf(r.getShow().getHall().getHallNumber() - 2));
			}
			Label timeLabel = new Label(" Aika: " + r.getShow().getTime());
			Label seatLabel = new Label("Penkki: " + r.getSeat().getSeatNumber());

			Button removeButton = new Button("Remove reservation");
			Button modifyButton = new Button("Modify!");
			modifyButtonList.add(modifyButton);
			removeButtonList.add(removeButton);
			setModifyButtonActions(modifyButton, rowNumber - 2);
			setRemoveButtonActions(removeButton, rowNumber - 2);

			group.getChildren().addAll(movieLabel, theaterLabel, hallLabel, seatLabel, timeLabel, modifyButton, removeButton);
			hBox.getChildren().addAll(movieLabel, theaterLabel, hallLabel, seatLabel, timeLabel, modifyButton, removeButton);

			group.setVisible(false);
			gridPane.add(hBox, 0, rowNumber++);
			Transitions.fadeIn(group);
		}

	}


	private void setModifyButtonActions(Button button, final int row) {

		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				
				ArrayList<Seat> seatList = new ArrayList<Seat>();

				try {

					String query = "SELECT seats.id, seats.seatnumber FROM seats, connections WHERE "+
									"connections.showid = " + customer.getReservations().get(row).getShow().getId() + 
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
				
				ImageView imageView = new ImageView();
				Image reservedSeat = new Image("/res/Varattupenkki.png",20,20,false,false);
				Image freeSeat = new Image("/res/Vapaapenkki.png",20,20,false,false);
				gridPane.getChildren().clear();

				toggleList = new ArrayList<ToggleButton>();

				try {
					for (Seat seat : seatList) {

						String query = "SELECT state FROM connections WHERE seatid = " + seat.getId() + 
								" AND showid = " + customer.getReservations().get(row).getShow().getId() + ";";
						ResultSet stateSet = dataBaseActions.selectQuery(query);

						int state = stateSet.getInt("state");

						if (state == 0) {
							imageView.setImage(freeSeat);
							toggleList.add(new ToggleButton(String.valueOf(seat.getSeatNumber()), new ImageView(freeSeat)));
						} else {
							imageView.setImage(reservedSeat);
							ToggleButton toggle = new ToggleButton(String.valueOf(seat.getSeatNumber()), new ImageView(reservedSeat));
							toggle.setDisable(true);
							if (seat.getId() == customer.getReservations().get(row).getSeat().getId()) {
								toggle.setDisable(false);
							}
							toggleList.add(toggle);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				
				String theaterName = customer.getReservations().get(row).getTheater().getName();
				int hallId = customer.getReservations().get(row).getShow().getHall().getHallNumber();
				
	
				if (theaterName.equals("Tennispalatsi")) {
					hallId = hallId - 3;
					System.out.println("Ollaan iffisssä myres ja hallid: " + hallId);
				}

				setToggleAction(toggleList);


				int columnIndex = 2;
				int rowIndex = 0;

				for (int i = 0; i < toggleList.size(); i++) {
					gridPane.add(toggleList.get(i), columnIndex, rowIndex);
					if (columnIndex > 10) {
						columnIndex = 1;
						rowIndex++;
					}
					columnIndex++;
				}
				
				rowNumber = row;
				Button confirmButton = new Button("Confirm");
				Button cancelButton = new Button("Cancel");
				setConfirmAction(confirmButton);
				setCancelAction(cancelButton);
				gridPane.add(confirmButton, 12, 2);
				gridPane.add(cancelButton, 13, 2);
				confirmButton.setVisible(false);
				cancelButton.setVisible(false);
				Transitions.fadeIn(confirmButton);
				Transitions.fadeIn(cancelButton);
			}
		});

	}

	private void setConfirmAction(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				for (ToggleButton toggle : toggleList) {
					if (toggle.isSelected()) {

						int reservationId = customer.getReservations().get(rowNumber).getiD();
						int oldSeatId = customer.getReservations().get(rowNumber).getSeat().getId();
						int hallId = customer.getReservations().get(rowNumber).getShow().getHall().getHallNumber();
						int toggleNumber = Integer.parseInt(toggle.getText());

						try {
							ResultSet seatSeat = dataBaseActions.selectQuery("SELECT * FROM seats" +
									" WHERE hallid = " + hallId +
									" AND seatnumber = " + toggleNumber + ";");
							int newSeatId = seatSeat.getInt("id");

							dataBaseActions.updateReservation(reservationId, oldSeatId, newSeatId, customer.getReservations().get(rowNumber).getShow().getId());
						} catch (SQLException e) {
							e.printStackTrace();
						}

						gridPane.getChildren().clear();
						new MyReservations(dataBaseActions, gridPane);
					}
				}
			}
		});

	}

	private void setCancelAction(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gridPane.getChildren().clear();

				new MyReservations(dataBaseActions, gridPane);
			}
		});
	}

	private void setToggleAction(final ArrayList<ToggleButton> toggleList) {

		for (final ToggleButton toggle : toggleList) {

			toggle.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {

					for (ToggleButton newToggle : toggleList) {
						if (newToggle.isSelected()) {
							newToggle.setSelected(false);
						}
					}
					toggle.setSelected(true);
				}
			});

		}
	}

	private void setRemoveButtonActions(Button button, final int row) {

		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				dataBaseActions.updateSeatState(0, customer.getReservations().get(row).getSeat().getId(), customer.getReservations().get(row).getShow().getId());
				dataBaseActions.removeReservation(customer.getReservations().get(row).getiD());
				customer.getReservations().remove(row);
				gridPane.getChildren().clear();
				new MyReservations(dataBaseActions, gridPane);
			}
		});

	}


	private void parseReservations() {
		String query = "SELECT * FROM reservations WHERE customername = '" + customer.getUserName() + "';";
		ResultSet reservationsSet = dataBaseActions.selectQuery(query);
		ArrayList<Integer> showIdList = new ArrayList<Integer>();
		ArrayList<Integer> reservationIdList = new ArrayList<Integer>();
		ArrayList<Integer> seatIdList = new ArrayList<Integer>();
		ArrayList<String> theaterNameList = new ArrayList<String>();

		try {
			while (reservationsSet.next()) {
				reservationIdList.add(reservationsSet.getInt("id"));
				showIdList.add(reservationsSet.getInt("showid"));
				seatIdList.add(reservationsSet.getInt("seatid"));

			}

			for (int showId : showIdList) {
				String  blah = "SELECT theaters.name FROM theaters,halls,shows WHERE" +
						" shows.id = " + showId + " AND" +
						" shows.hallid = halls.id AND halls.theaterid = theaters.id;";
				ResultSet theaterSet = dataBaseActions.selectQuery(blah);
				while (theaterSet.next()) {
					theaterNameList.add(theaterSet.getString("name"));
				}
			}

			for (int i = 0; i < reservationIdList.size(); i++) {

				Seat seat = createNewSeat(seatIdList.get(i));


				Theater theater = dataBaseActions.getTheaterInfo(theaterNameList.get(i));

				Show show = createNewShow(showIdList.get(i));

				Reservation reservation = new Reservation(reservationIdList.get(i), theater, show, seat, customer);

				reservationsList.add(reservation);

				customer.setReservations(reservationsList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Show createNewShow(int showId) {
		String query = "SELECT * FROM shows WHERE id = " + showId + ";";
		ResultSet showSet = dataBaseActions.selectQuery(query);
		String movieName = "";
		int hallId = 0;
		String time = "";

		try {
			movieName = showSet.getString("moviename");
			hallId = showSet.getInt("hallid");
			time = showSet.getString("time");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {

			String movieQuery = "SELECT * FROM movies WHERE name = '" + movieName + "';";
			ResultSet movieSet = dataBaseActions.selectQuery(movieQuery);
			int movieLength = movieSet.getInt("length");
			Movie movie = new Movie(movieName, movieLength);

			return new Show(movie, new Hall(hallId), time, showId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	private Seat createNewSeat(int seatId) {

		ResultSet numberSet = dataBaseActions.selectQuery("SELECT * FROM seats WHERE id = " + seatId + ";");
		String seatString;

		int seatNumber;
		try {
			seatNumber = numberSet.getInt("seatnumber");
			seatString = String.valueOf(seatNumber);
			String[] table = seatString.split("");

			int colIndex;
			int rowIndex;
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
			return new Seat(seatNumber, rowIndex, colIndex, seatId);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
