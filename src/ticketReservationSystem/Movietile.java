package ticketReservationSystem;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Movietile {
private VBox b;
private Image V;
private ChoiceBox<String> bx;

public Movietile(){
	b = new VBox(10);
	V = new Image(getClass().getResourceAsStream("Test.jpg"),100,100,true,true); 
	bx = new ChoiceBox<String>(FXCollections.observableArrayList("Terminator","Matrix","Starwars","X-men"));
	ImageView vw = new ImageView(V);
	
	b.getChildren().add(vw);
	b.getChildren().add(bx);
}
public VBox getTile(){
	return b;
}
}
