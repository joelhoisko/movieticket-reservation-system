package ticketReservationSystem;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MovieDisplayScreen {
	private GridPane gridPane;
	private DataBaseActions database;

	private ArrayList<Movietile> movieTileList = new ArrayList<Movietile>();
	private ArrayList<Movie> movieList = new ArrayList<Movie>();

	private Theater Kinopalatsi;
	private Theater Tennispalatsi;

	public MovieDisplayScreen(GridPane panel, DataBaseActions dataBaseActions2) {
		this.gridPane = panel;
		this.database = dataBaseActions2;


		this.Kinopalatsi = database.getTheaterInfo("Kinopalatsi");
		this.Tennispalatsi = database.getTheaterInfo("Tennispalatsi");

		ArrayList<Movie> kinoMovies = Kinopalatsi.getMovies();
		ArrayList<Movie> tennisMovies = Tennispalatsi.getMovies();

		if (!kinoMovies.isEmpty()) {
			// add all the movies from kinopalatsi to movieTileList and movieList

			for (int i = 0; i < kinoMovies.size();i++) {

				movieTileList.add(new Movietile(kinoMovies.get(i).getName(),gridPane, database));
				movieList.add(kinoMovies.get(i));
			}
		}
		// Now we add all the movies from tennispalatsi that do not already exist in the movieList and movieTileList. 
		if (!tennisMovies.isEmpty()) {
			for(int j = 0; j < tennisMovies.size();j++){
				if(!movieList.contains(tennisMovies.get(j))){
					movieTileList.add(new Movietile(tennisMovies.get(j).getName(),gridPane, database));
					movieList.add(tennisMovies.get(j));
				}
			}

		}

		// lets add the movies to the panel
		int row = 0;
		int column = 0;

		for (int g = 0; g < movieTileList.size();g++) {
			
			VBox tile = movieTileList.get(g).getTile();
			tile.setVisible(false);
			panel.add(tile,column,row);
			Transitions.fadeIn(tile);
			column++;
			if (column == 5) {
				row++;
				column =0;
			}
		}

		if (kinoMovies.isEmpty() && tennisMovies.isEmpty()) {
			Text notif = new Text("No movies yet!");
			panel.add(notif, 0, 0);

			notif.setVisible(false);
			Transitions.fadeIn(notif);
		}

	}
}