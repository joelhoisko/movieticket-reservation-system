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
	private final Button b = new Button("Ok");
	private final Button b1 = new Button("Cancel");
	private Text text;
	private GridPane panel;
	/**
	 * Constructor. In the future this might also take some information about what it does when the user presses buttons
	 * Now pressing either of the defined buttons just closes the window.
	 * @param p GridPane
	 * @param message String message
	 */
public CommonAlertDialogs(GridPane p, String message){
	text = new Text(message);
	panel = p;
	text.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
	final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent e) {
			if(e.getSource()== b){
				close();
			}
			if(e.getSource()==b1){
				close();
			}
			
		}
	
	};
	b.setOnAction(eventHandler);
	b1.setOnAction(eventHandler);
	panel.add(text,0,0,2,1);
	panel.add(b,0,1);
	panel.add(b1,1,1);
	panel.setPrefSize(200, 200);
	Scene s = new Scene(panel);
	s.getStylesheets().add("res/stylesheet.css");
	this.setScene(s);
	this.show();
	
}
}
