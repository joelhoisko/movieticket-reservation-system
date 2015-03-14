package ticketReservationSystem;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		// Just for an example, let's make 2 theaters.
		ArrayList<Movie> moviesKino = new ArrayList<Movie>();		
		ArrayList<Hall> hallsKino = new ArrayList<Hall>();
		ArrayList<Show> showsKino = new ArrayList<Show>();
		
		@SuppressWarnings("unused")
		Theater Turku = new Theater(hallsKino, "Kinopalatsi", moviesKino, showsKino);
			
		ArrayList<Movie> moviesTennis = new ArrayList<Movie>();
		ArrayList<Hall> hallsTennis = new ArrayList<Hall>();
		ArrayList<Show> showsTennis = new ArrayList<Show>();

		@SuppressWarnings("unused")
		Theater Helsinki = new Theater(hallsTennis, "Tennispalatsi", moviesTennis, showsTennis);
		
		System.out.println("I'm not broken!");
	}
}
