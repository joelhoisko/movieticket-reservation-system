package ticketReservationSystem;

/**
 * Admin is a special user with the power to alter shows, movies, mind and matter.
 * @author joel
 *
 */
public class Admin extends User {
	
	private static final boolean permission = true;

	public Admin(String userName, String name, String password) {
		super(userName, name, password, permission);
	}
	
	/* Nothing to get, nothing to lose */
	
}
