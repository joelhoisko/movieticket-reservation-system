package ticketReservationSystem;

import java.util.ArrayList;

/**
 * Handles all the information regarding a single theater and the halls and shows there.
 * @author joel
 *
 */
public class Theater {
	
	private String name;
	
	private ArrayList<Hall> halls;
	private ArrayList<Movie> movies;
	private ArrayList<Show> shows;
	
	
	public Theater(ArrayList<Hall> halls, String name, ArrayList<Movie> movies, ArrayList<Show> shows) {
		this.halls = halls;
		this.name = name;
		this.movies = movies;
		this.shows = shows;
	}
	
	/* GETTERU-SETERU */

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Hall> getHalls() {
		return halls;
	}


	public void setHalls(ArrayList<Hall> halls) {
		this.halls = halls;
	}


	public ArrayList<Movie> getMovies() {
		return movies;
	}


	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}


	public ArrayList<Show> getShows() {
		return shows;
	}


	public void setShows(ArrayList<Show> shows) {
		this.shows = shows;
	}
	
}
