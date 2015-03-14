package ticketReservationSystem;

import java.awt.TextField;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

public class ShowModPane {
	private final GridPane gridPane = new GridPane();
	private Label theater = new Label("Theater");
	private Label hall = new Label("Hall");
	private Label showtimes = new Label("Showtimes");
	private final TextField theaterField;
	private final TextField hallField = new TextField();
	private Button okButton = new Button("OK");
	private int HBoxIndex;
	
	public ShowModPane(final GridPane gridPane) {
		this.gridPane = gridPane;
		HBoxIndex = 1;
		
		gridPane.add(theater, 0, 2);
		gridPane.add(hall, 1, 2);
		gridPane.add(showtimes, 2, 2);
		
		theaterField = new TextField();
		gridPane.add(theaterField, 0, 3);
		gridPane.add(hallField, 1, 3);
	}
}
