package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *  This is a class that means to mimic the swing JOptionPanel.
 * @author Markus
 *
 */
public class CommonAlertDialogs extends Stage {
	private final Button okButton = new Button("Ok");
	private final Button cancelButton = new Button("Cancel");
	private Text text;
	private GridPane gridPane;
	/**
	 * Constructor. In the future this might also take some information about what it does when the user presses buttons
	 * Now pressing either of the defined buttons just closes the window.
	 * @param newGridPane GridPane
	 * @param message String message
	 */
	public CommonAlertDialogs(GridPane newGridPane, String message){
		this.text = new Text(message);
		this.gridPane = newGridPane;
		text.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));

		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				if(e.getSource()== okButton){
					close();
				}
				if(e.getSource()==cancelButton){
					close();
				}

			}

		};
		okButton.setOnAction(eventHandler);
		cancelButton.setOnAction(eventHandler);

		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.add(text,0,0,2,1);
		gridPane.add(okButton,0,1,1,1);
		gridPane.add(cancelButton,1,1,1,1);
		gridPane.setPrefSize(100, 100);

		Scene s = new Scene(gridPane);
		s.getStylesheets().add("res/stylesheet.css");
		this.setScene(s);
		this.show();

	}
}
