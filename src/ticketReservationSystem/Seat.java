package ticketReservationSystem;

/**
 * Seat models the placement and availability of a single Hall's seats.
 * @author joel
 *
 */
public class Seat {
	
	private Hall hall;
	
	private int seatNumber;
	private int state;
	private int[][] seatLocation;
	
	public Seat(Hall hall, int seatNumber, int state, int[][] seatLocation) {
		this.hall = hall;
		this.seatNumber = seatNumber;
		this.state = state;
		this.seatLocation = seatLocation;
	}
	
	/* GETTERSETTER */

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int[][] getSeatLocation() {
		return seatLocation;
	}

	public void setSeatLocation(int[][] seatLocation) {
		this.seatLocation = seatLocation;
	}
	
}
