package features;

//Default Time Format
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Temporary list for storing existed ID users
import java.util.Set;
import java.util.HashSet;


/**
* Constructs a user object.
* @param userId Must be unique, format: u_10 digits, such as u_1234567890
* @param userName The user's name
* @param userPassword The user's password
* @param userRegisterTime Format: "DD-MM-YYYY_HH:MM:SS"
* @param userRole Default value: "customer"
*
**/

public abstract class User {
    private String userId;
    private String userName;
    private String userPassword;
    private String userRegisterTime;
    private String userRole;
    
    //Constructor with parameters
    public User(String userId, String userName, String userPassword, String userRegisterTime, String userRole){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRegisterTime = userRegisterTime;
        this.userRole = userRole;
    }

    //Default constructor
    public User(){

    }

    //Getters and Setters
    public String getUserId() {
        return userId;
    }

    /**
     * GENERAL IDEA: Since the requirements involving uniqueness and 10-digit format. For the former one, we could keep track of identical ID by storing it in HashSet
     * , The latter one will be using RegEx Pattern
     * Using regular expression for 10-digit ID: 
     * u_ : match the prefix 
     * \\d : match any digit (0 -> 9) 
     * {10} : only 10 digits are allowed
    **/

    private static final Set<String> assignedIDUsers = new HashSet<>();
    public void setUserId(String userId) {
        if(userId  == null || !userId.matches("u_\\d{10}")){
            System.out.println("Invalid ID input format, please try again!");
            return;

        }
        if(!assignedIDUsers.contains(userId)){
            System.out.println("The ID user " + userId + " has already existed, please try again!");
            return;
        }
        else{
            this.userId = userId;
            assignedIDUsers.add(userId);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRegisterTime() {
        return userRegisterTime;
    }

    /**
    * void setUserRegisterTime: Using SimpleDateFormat and Date library to set the format time, while Parse is used to extract string time components as input 
    **/

    public void setUserRegisterTime(String userRegisterTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");

        //Set the leniency of the interpretation of date and time, "false" means it must follow the given pattern
        sdf.setLenient(false);

        //Insert the time with appropriate format, with exception handling
        try{
            Date date = sdf.parse(userRegisterTime);
            this.userRegisterTime = sdf.format(date);
        } 
        catch (ParseException e){
            System.out.println("Invalid date format, please try again!");
        }
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    //Default information display format
    @Override
    public String toString(){
        return "{ " + "user_id" + ":" + getUserId() + ", " + "user_name" + ":" + getUserName() + ", " + "user_password" + ":" + getUserPassword() + ", " + "user_register_time" + ":" + getUserRegisterTime() + ", " + "user_role" + ":" + getUserRole() + " }";
    }

    
}
