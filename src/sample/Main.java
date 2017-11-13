package sample;

import java.sql.SQLException;
import input.InputHelper;
import manager.ReservationManager;
import manager.RoomManager;
import manager.UserManager;
import sampleRoom.Room;
import sampleUser.User;

public class Main {

    public static void main(String[] args) throws Exception {

       //UserManager.displayAllRows();
       //RoomManager.displayRooms();
       Room room = new Room();
       //deleteRoom();
       
       User newUser = new User();
       //System.out.println(newUser.getuStars());
       //insertUser(newUser);
       //updateUser(newUser);
       //deleteUser(newUser);
       
       ReservationManager.dispalyReservation();
       
    }
    /**
     * 
     * @param newUser
     * @throws SQLException
     */
    private static void insertUser(User newUser) throws SQLException {
    	System.out.println("Enter following, press enter if answer is none\n");
    	newUser.setuName(InputHelper.getInput("Enter User Name: "));
    	newUser.setuStars(5);
    	newUser.setMemberSince(InputHelper.getTimeStamp());
    	newUser.setBanned(false);
    	newUser.setDays(InputHelper.getIntegerInput("Enter the number of days: "));
    	newUser.setReferrals(InputHelper.getIntegerInput("Enter referrals code number: "));
    	newUser.setRefrence(InputHelper.getIntegerInput("Enter reference number: "));
    	boolean result = UserManager.insertUser(newUser);
    	if(result) {
     	   System.out.println("New user has been added with user id: " + newUser.getuID());
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
    	int uID = InputHelper.getIntegerInput("Enter the row/uID you want to update:");
    	newUser.setuID(uID);
    	String uName = InputHelper.getInput("Enter new user name: ");
        newUser.setuName(uName);
        int uStars = InputHelper.getIntegerInput("Enter new rating: ");
        newUser.setuStars(uStars);
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
    	int uID = InputHelper.getIntegerInput("Enter the row/uID you want to delete: ");
    	boolean result = UserManager.deleteUser(uID);
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


}
