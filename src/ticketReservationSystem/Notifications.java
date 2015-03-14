package ticketReservationSystem;

import javafx.scene.Node;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Notifications {
private int durationin = 3000;
private int durationout = 3000;
private String message="";
private double height;
private double width;
private final Rectangle r;
private FadeTransition frect;
private FadeTransition ftext;
private StackPane p;
private Node n;
private final Text t;
/*
 * A class for displaying short notifications similar to Android toasts
 */
/**
 * Constructor for Notification.
 * @param message
 * @param duration in milliseconds
 */
public Notifications(String message, Node notifSource){
	height = 50;
	n = notifSource;

	this.message = message;
	 t = new Text(message);
	t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	width = t.getWrappingWidth();
	r = new Rectangle(t.getLayoutX(),t.getLayoutY(),width,height);
	
	r.setFill(Color.DARKBLUE);
	r.setArcHeight(width);
	r.setArcWidth(height);
	
	
	 
	 frect = new FadeTransition(Duration.millis(durationin),r);
	 
	 ftext = new FadeTransition(Duration.millis(durationout),t);
  
	frect.setFromValue(1);
	frect.setToValue(0);
	ftext.setFromValue(1);
	ftext.setToValue(0);
	
	Scene s = n.getScene();
	final Parent p = s.getRoot();
	
	if(p instanceof Pane){
		Pane panel = (Pane) p;
		
		panel.getChildren().add(t);
		panel.getChildren().add(r);
		
	}
	if(p instanceof Group){
		Group g = (Group) p;
		g.getChildren().add(t);
		g.getChildren().add(r);
	}
}
	public void start(){
	frect.play();
	ftext.play();
	frect.setOnFinished(new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent arg0) {
		
			Scene s = n.getScene();
			final Parent p = s.getRoot();
			if(p instanceof Pane){
				Pane panel = (Pane) p;
				panel.getChildren().remove(t);
				panel.getChildren().remove(r);
			}
			if(p instanceof Group){
				Group g = (Group) p;
				g.getChildren().remove(t);
				g.getChildren().remove(r);
			}
			
		}
		
	});
	
	
	
}


}
