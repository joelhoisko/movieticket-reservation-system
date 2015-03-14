package ticketReservationSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manages all the users in the system and keeps them in their respective lists.
 * @author joel
 *
 */
public class UserList {

	private ArrayList<User> userList;
	private ArrayList<Admin> adminList;
	private ArrayList<Customer> customerList;

	/**
	 * Initializes all the ArrayLists.
	 */
	public UserList() {}
	
	public void initializeLists() {
		this.userList = new ArrayList<User>();
		this.adminList = new ArrayList<Admin>();
		this.customerList = new ArrayList<Customer>();
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	/**
	 * Goes through the userList and finds the user with the respective userName.
	 * @param userName
	 * @return
	 * @throws UserNotFoundException
	 */
	public User getUser(String userName) throws UserNotFoundException {

		for (int i = 0; i < userList.size(); i++) {
			if (userName.equals(userList.get(i).getUserName())) {
				return userList.get(i);
			}
		}
		throw new UserNotFoundException("That username doesn't exist.");
	}

	public ArrayList<Admin> getAdminList() {
		return adminList;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	/**
	 * Adds a new Customer to the customerList and userList.
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		customerList.add(customer);
		userList.add(customer);
	}

	/**
	 * Adds a new Admin to the adminList and userList.
	 * @param admin
	 */
	public void addAdmin(Admin admin) {
		adminList.add(admin);
		userList.add(admin);
	}

	/**
	 * Clears the Lists and parses through the given ResultSet for Users.
	 * It adds every user found into userList and into their respective list based
	 * on their permission.
	 * @param resultSet
	 * @throws SQLException
	 */
	public void parseUserList(ResultSet resultSet) {
		userList.clear();
		adminList.clear();
		customerList.clear();

		// We get the information from the respective columns, one row at a time.
		try {
			while (resultSet.next()) {
				String userName = resultSet.getString("username");
				String name = resultSet.getString("name");
				String password = resultSet.getString("password");
				int permission = resultSet.getInt("permission");

				/* If "permission == 1" that user is an Admin.
				 * addAdmin/Customer also adds them to the userList.
				 * We can't use boolean values because SQLite doesn't support it.
				 */
				if (permission == 1) {
					addAdmin(new Admin(userName, name, password));
				} else {
					addCustomer(new Customer(userName, name, password));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while parsing UserList!");
		}
	}
	
	/**
	 * For testing purposes.
	 */
	public void printuserList() {
		for (int i = 0; i < userList.size(); i++) {
			System.out.println("Username: " + userList.get(i).getUserName());
			System.out.println("Name: " + userList.get(i).getName());
			System.out.println("Password: " + userList.get(i).getPassword());
			System.out.println();
		}
	}
	public void printAdminList(){
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getPermission()){
			System.out.println("Username: " + userList.get(i).getUserName());
			System.out.println("Name: " + userList.get(i).getName());
			System.out.println("Password: " + userList.get(i).getPassword());
			System.out.println();
			}
		}
	}
}
