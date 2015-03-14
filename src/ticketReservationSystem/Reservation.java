package ticketReservationSystem;

import java.util.ArrayList;

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
	private ArrayList<Seat> seats;

	private Customer customer;
	
	public Reservation(int iD, Theater theater, Show show, ArrayList<Seat> seats, Customer customer) {
		super();
		this.iD = iD;
		this.theater = theater;
		this.show = show;
		this.seats = seats;
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

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
