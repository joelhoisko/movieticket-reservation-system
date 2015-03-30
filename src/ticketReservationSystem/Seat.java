package ticketReservationSystem;

/**
 * Seat models the placement and availability of a single Hall's seats.
 * @author joel
 *
 */
public class Seat {
	
	private int iD;
	private int seatNumber;
	private int row;
	private int column;
	
	public Seat(int seatNumber, int row, int column, int seatId) {
		
		this.seatNumber = seatNumber;
		this.row = row;
		this.column = column;
		this.iD = seatId;
	}
	
	/* GETTERSETTER */

	public int getId() {
		return iD;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int[] getSeatLocation() {
		int[] location = new int[2];
		location[0] = row;
		location[1] = column;
		return location ;
	}

	public void setSeatLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
}
