package ticketReservationSystem;

import java.util.ArrayList;


/**
 * Models a single hall. A hall is identified by its iD or hallNumber. A hall contains
 * a list of Shows which are shown there and the Seats which can be reserved.
 * @author joel
 *
 */



public class Hall{
	
	private int hallNumber;
	
	private ArrayList<Show> ownShows;
	private ArrayList<Seat> seats;
	
	public Hall(int hallNumber, ArrayList<Show> ownShows, ArrayList<Seat> seats) {
		this.hallNumber = hallNumber;
		this.ownShows = ownShows;
		this.seats = seats;
	}
	
	/* get set */

	public int getHallNumber() {
		return hallNumber;
	}

	public void setHallNumber(int hallNumber) {
		this.hallNumber = hallNumber;
	}

	public ArrayList<Show> getOwnShows() {
		return ownShows;
	}

	public void setOwnShows(ArrayList<Show> ownShows) {
		this.ownShows = ownShows;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	
}
