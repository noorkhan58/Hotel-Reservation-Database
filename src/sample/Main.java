package sample;

import java.sql.SQLException;
import input.InputHelper;
import manager.UserManager;
import sampleUser.User;

public class Main {



    public static void main(String[] args) throws SQLException {

       UserManager.displayAllRows();
       User newUser = new User();
       int uID = InputHelper.getIntegerInput("Enter the row you want to update:");
       newUser.setuID(uID);
//       newUser.setuName(InputHelper.getInput("Enter User Name: "));
//       newUser.setuStars(InputHelper.getIntegerInput("Enter user rating: "));
//       newUser.setMemberSince(InputHelper.getTimeStamp());
//       newUser.setBanned(InputHelper.getBooleanInput("Is the user banned? Yes/No: "));
//       boolean result = UserManager.insertUser(newUser);
//       
//       if(result) {
//    	   System.out.println("New user has been added with user id: " + newUser.getuID());
//       }
       String uName = InputHelper.getInput("Enter new user name: ");
       newUser.setuName(uName);
       int uStars = InputHelper.getIntegerInput("Enter new rating: ");
       newUser.setuStars(uStars);
       boolean result = UserManager.update(newUser);
       if(result) {
    	   System.out.println("User updated");
       }else {
    	   System.err.println("Whoops, something wrong");
       }
       
    }
}
