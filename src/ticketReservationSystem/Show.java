package ticketReservationSystem;


/**
 * A show models a single viewing of a movie in a single hall.
 * @author joel
 *
 */
public class Show {

	private Movie movie;
	private Hall hall;
	private String time;
	
	public Show(Movie movie, Hall hall, String time) {
		this.movie = movie;
		this.hall = hall;
		this.time = time;
	}
	
	/* GetSet */

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
