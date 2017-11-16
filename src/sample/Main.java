package sample;

import java.sql.SQLException;
import input.InputHelper;
import manager.AdminManager;
import manager.ReservationManager;
import manager.RoomManager;
import manager.UserManager;
import sampleAdmin.Admin;
import sampleRoom.Room;
import sampleUser.User;

public class Main {

    public static void main(String[] args) throws Exception {
  
    	int firstInput = InputHelper.getIntegerInput("Are You Admin/User?\nPress\n1 - Admin\n2- User\n");
    	if(firstInput ==1) {
    		boolean trueAdmin = loginAdmin();
    		if(trueAdmin) {
    			adminInput();
    		}else{
    			System.out.println("username/password combination wrong. try again");
    		}
    	}else if(firstInput == 2) {
    		userInput();
    	}else{
    		System.out.println("Invalid input, try again later");
    	}
       
    }
    
    /**
     * insert admin to the database
     * @param admin
     * @throws SQLException
     */
    private static void addAdmin(Admin admin) throws SQLException {
    	admin.setUsername(InputHelper.getInput("Enter username"));
    	admin.setPassword(InputHelper.getInput("Enter password"));
    	boolean result = AdminManager.insertAdmin(admin);
    	if(result) {
    		System.out.println("New admin added to the database");
    	}else {
    		System.out.println("Whoops, Something wrong. Admin not added");
    	}
    }
    /**
     *  Admin log in
     * @return true if username and password valid
     * @throws SQLException
     */
    private static boolean loginAdmin() throws SQLException {
    	String username = InputHelper.getInput("Enter your username: ");
		String password = InputHelper.getInput("Enter your password: ");
		boolean foundAdmin = AdminManager.login(username, password);
		if(foundAdmin){
			System.out.println("Admin successfully logged in");
			return true;
		}else{
			return false;
		}
    }
    
    /**
     * delete admin from database
     */
    private static void deleteAdmin() {
    	String username = InputHelper.getInput("Enter the admin name: ");
    	boolean result = AdminManager.deleteAdmin(username);
    	if(result) {
      	   System.out.println("Admin has been deleted from database");
         }else {
         	System.out.println("Whoops, Something wrong. User not deleted");
         }	
    }
    /**
     * method inserts user to the user table
     * @param newUser
     * @throws SQLException
     */
    private static void insertUser(User newUser) throws SQLException {
    	System.out.println("Enter following, press just enter if answer is none\n");
    	newUser.setuName(InputHelper.getInput("Enter User Name: "));
    	newUser.setuStars(5);
    	newUser.setMemberSince(InputHelper.getTimeStamp());
    	newUser.setBanned(false);
    	newUser.setDays(0);
    	newUser.setReferrals(0);
    	String refer = InputHelper.getInput("Enter reference user or 0 if no reference: ");
    	if(refer.isEmpty() || refer == "0") {
    		newUser.setReference(null);
    	}else {
    	newUser.setReference(refer);
    	}
    	boolean result = UserManager.insertUser(newUser);
    	if(result) {
     	   System.out.println("New user has been added with username: " + newUser.getuName());
        }else {
        	System.out.println("Whoops, Something wrong. User not added");
        }
    }
    /**
     * 
     * @param newUser
     * @throws SQLException
     */
    private static void updateUser(User newUser) throws SQLException {
    	System.out.println("Enter following, press enter if answer is none\n");
    	String uname = InputHelper.getInput("Enter the row/uName you want to update:");
    	newUser.setuName(uname);
        int uStars = InputHelper.getIntegerInput("Enter new rating: ");
        newUser.setuStars(uStars);
        int days = InputHelper.getIntegerInput("Enter new number of days: ");
        newUser.setDays(days);
        int referals = InputHelper.getIntegerInput("Enter new referals number: ");
        newUser.setReferrals(referals);
        String reference = InputHelper.getInput("Enter new reference name: ");
        if(reference.isEmpty()) {
        	newUser.setReference(null);
        }else {
        newUser.setReference(reference);
        }
        
        
        boolean result = UserManager.update(newUser);
        if(result) {
     	   System.out.println("User updated");
        }else {
     	   System.err.println("Whoops, something wrong. User not updated");
        }
    }
    /**
     * 
     * @throws Exception
     */
    private static void deleteUser() throws Exception {
    	String uName = InputHelper.getInput("Enter the user name: ");
    	boolean result = UserManager.deleteUser(uName);
    	if(result) {
      	   System.out.println("User has been deleted");
         }else {
         	System.out.println("Whoops, Something wrong. User not deleted");
         }
    }
    /**
     * 
     * @param newRoom
     * @throws SQLException
     */
    private static void insertRoom(Room newRoom) throws SQLException {
    	newRoom.setrNumber(InputHelper.getIntegerInput("Enter new room number: "));
    	newRoom.setrStatus(newRoom.getrStatus());
    	newRoom.setPrice(InputHelper.getIntegerInput("Enter the cost of the room: "));
    	newRoom.setrType(InputHelper.getInput("Enter the type of the room"));
    	newRoom.setHandicap(newRoom.isHandicap());
    	boolean result = RoomManager.insertRoom(newRoom);
    	if(result) {
    		System.out.println("Room inserted into your database");
    	}else{
    		System.err.println("Whoops, Something wrong. Room not inserted");
    	}
    }
    /**
     * 
     * @param newRoom
     */
    private static void updateRoom(Room newRoom) {
    	int rNumber  = InputHelper.getIntegerInput("Enter the room number you want to update: ");
    	newRoom.setrNumber(rNumber);
    	String rStatus = InputHelper.getInput("Enter room's current status: ");
    	newRoom.setrStatus(rStatus);
    	int price = InputHelper.getIntegerInput("Enter the room's price: ");
    	newRoom.setPrice(price);
    	boolean result = RoomManager.updateRoom(newRoom);
    	if(result) {
      	   System.out.println("Room updated");
         }else {
      	   System.err.println("Whoops, something wrong. Room not updated");
         }
    	
    }
    /**
     * 
     */
    private static void deleteRoom() {
    	int rNumber = InputHelper.getIntegerInput("Enter the row/uID you want to delete: ");
    	boolean result = RoomManager.deleteRoom(rNumber);
    	if(result) {
      	   System.out.println("Room has been deleted");
         }else {
         	System.out.println("Whoops, Something wrong. Room not deleted");
         }	
    }


    private static void banCustomer(User banUser) throws SQLException {
    	String user = InputHelper.getInput("Enter the customer name you want to ban: ");
    	banUser.setuName(user);
    	banUser.setBanned(true);
    	boolean result = UserManager.update(banUser);
    	if(result) {
    		System.out.println(banUser.getuName() + " has been banned");
    	}else{
    		System.out.println("Whoops, something wrong. Banned not successful");
    	}
    	
    }
    private static void makeReservation() {
    	
    }
    /**
     * This is the options for admin. when admin log in, they can do follwoing.
     * @throws Exception
     */
    private static void adminInput() throws Exception {
    	int answer = InputHelper.getIntegerInput("Please press number from the following: \n"
    			+ "1 - Add new admin\n"
    			+ "2 - Add new customer informations\n"
    			+ "3 - Delete customer informations\n"
    			+ "4 - Update customer informations\n"
    			+ "5 - Ban existing customer\n"
    			+ "6 - Make reservation\n"
    			+ "7 - Cancel reservation\n"
    			+ "8 - Delete existing admin\n"
    			+ "0 - quit\n");
    	switch (answer) {
		case 1:
			addAdmin(new Admin());
			adminInput();
			break;
		case 2:
			insertUser(new User());
			adminInput();
			break;
		case 3:
			deleteUser();
			adminInput();
			break;
		case 4:
			updateUser(new User());
			adminInput();
			break;
		case 5:
			banCustomer(new User());
			adminInput();
			break;
		case 8:
			deleteAdmin();
			adminInput();
			break;
		case 0:
			break;

		default:
			break;
		}
    }
    
    /**
     * This is the options for customer. they can do follwoing.
     * @throws Exception
     */
    
    private static void userInput() throws Exception {
    	int answer = InputHelper.getIntegerInput("Please press number from the following: \n"
    			+ "1 - Create an account\n"
    			+ "2 - Delete account"
    			+ "3 - Make reservation\n"
    			+ "4 - Cancel reservation\n"
    			+ "0 - quit\n");
    	switch (answer) {
		case 1:
			insertUser(new User());
			userInput();
			break;
		case 2:
			deleteUser();
			userInput();
			break;
		case 3:
			break;
		case 0:
			break;

		default:
			break;
		}
    }
}
