package ticketReservationSystem;


/**
 * A single Reservation contains a Show, the reserving Customer and the Seats 
 * which the Customer can reserve from that single Show.
 * @author joel
 *
 */
public class Reservation {
	
	private int iD;
	
	private Theater theater;
	private Show show;
	private Seat seat;

	private Customer customer;
	
	public Reservation(int iD, Theater theater, Show show, Seat  seat, Customer customer) {
		super();
		this.iD = iD;
		this.theater = theater;
		this.show = show;
		this.seat = seat;
		this.setCustomer(customer);
	}
	
	/* Get up, Set up */

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Seat getSeat() {
		return seat;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
