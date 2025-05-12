package operation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import features.Admin;

public class AdminOperation {

    private static AdminOperation instance;
    private static boolean isRegisteredOnce = false;

    /**
    * Returns the single instance of AdminOperation.
    * @return AdminOperation instance
    **/

    public static AdminOperation getInstance(){
        if(instance == null){
            instance = new AdminOperation();
        }
        return instance;
    }

    /**
    * Creates an admin account. This function should be called when
    * the system starts. The same admin account should not be
    * registered multiple times.
    * The registration is based on the assignment of Admin class (which is also inherites from User class)
    **/
    
    public void registerAdmin(){
        if(isRegisteredOnce){
            throw new IllegalArgumentException("The Admin account has been registered before! Expected to come in User registration phase!");
        }
        Scanner sc = new Scanner(System.in);
        UserOperation userOperation = UserOperation.getInstance();

        String userName;
        String userPassword;

        //Obtaining username
        while(true){
            System.out.println("Enter Admin name (5 characters at minimum, only letters and underscores)");
            userName = sc.nextLine();

            if(userOperation.validateUsername(userName) && !userOperation.checkUsernameExist(userName)){
                break;
            }
            else{
                throw new IllegalArgumentException("Invalid or existing username! Expected to be a human!");
            }
        }

        //Obtaining password
        while(true){
            System.out.println("Enter Admin password (5 characters at minimum, must contain both letters and numbers)");
            userPassword = sc.nextLine();

            if(userOperation.validatePassword(userPassword)){
                break;
            }
            else{
                throw new IllegalArgumentException("Invalid password input! Expected to follow the principles!");
            }
        }

        //Set Admin ID
        String userId = userOperation.generateUniqueUserId();
        
        //Set format date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentTime = sdf.format(new Date());

        //Assign Admin information
        Admin admin = new Admin(userId, userName, userPassword, currentTime, "admin");

        //Add admin to the userlist
        userOperation.addUser(admin);

        isRegisteredOnce = true;
        System.out.println("Admin account successfully created!");
    }

}
