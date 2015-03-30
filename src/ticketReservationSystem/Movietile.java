package ticketReservationSystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Movietile {
	private VBox vBox;
	private Image image;
	private GridPane gridPane;
	private Text title;
	private Button reserveButton;
	private String movieName;
	private DataBaseActions dataBaseActions;

	public Movietile(String name, GridPane panel, final DataBaseActions database){
		this.vBox = new VBox(10);
		this.gridPane = panel;
		this.movieName = name;
		this.dataBaseActions = database;
		
		reserveButton = new Button("Make a reservation!");
		title = new Text(name);
		// On Linux the file "Test.JPG" shows up as "Test.jpg". @joel
		image = new Image(getClass().getResourceAsStream("Test.JPG"),100,100,true,true); 
	
		ImageView imageView = new ImageView(image);
		reserveButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				gridPane.getChildren().clear();
				new HallView(gridPane, movieName, dataBaseActions);
			}
		});

		vBox.getChildren().add(imageView);
		vBox.getChildren().add(title);
		vBox.getChildren().add(reserveButton);
	}
	
	public VBox getTile(){
		return vBox;
	}

}
