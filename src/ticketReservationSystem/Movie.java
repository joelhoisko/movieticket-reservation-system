package ticketReservationSystem;


public class Movie {

	private String name;
	private int length;
	

	
	public Movie(String name, int length) {
		this.name = name;
		this.length = length;
	}
	
	
	/* GETTER-SETTER HELL */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
