package ticketReservationSystem;

/**
 * Generic user with just a name, userName and password.
 * @author joel
 *
 */
public abstract class User {
	
	private String name;
	private String userName;
	private String password;
	private boolean permission;
	
	public User(String userName, String name, String password, boolean permission) {
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.permission = permission;
	}
	
	/* Only Get */
	
	public boolean getPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
}
