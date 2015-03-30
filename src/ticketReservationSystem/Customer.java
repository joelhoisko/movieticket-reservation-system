package ticketReservationSystem;

import java.util.ArrayList;

/**
 * Customer is a User with the ability to make and modify Reservations.
 * @author joel
 *
 */
public class Customer extends User {
	
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	
	private final static boolean permission = false;

	public Customer(String userName, String name, String password) {
		super(userName, name, password, permission);
	}
	
	/* Getting to setting */

	public ArrayList<Reservation> getReservations() {
		return reservationList;
	}
	
	public void setReservations(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	/**
	 * We make a new reservation for a certain show and add it to the Customers list of reservations.
	 * @param iD
	 * @param theater
	 * @param show
	 * @param seats
	 * @param customer
	 */
	public void makeReservation(int iD, Theater theater, Show show, ArrayList<Seat> seats, Customer customer) {
		//Reservation reservation = new Reservation(iD, theater, show, seats, this);
		//reservationList.add(reservation);
	}

}
