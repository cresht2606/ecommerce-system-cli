package features;

/**
* Constructs an admin object.
* @param userId Must be unique, format: u_10 digits, such as u_1234567890
* @param userName The user's name
* @param userPassword The user's password
* @param userRegisterTime Format: "DD-MM-YYYY_HH:MM:SS"
* @param userRole Default value: "admin"
*/

public class Admin extends User{

    //Constructor with parameters
    public Admin(String userId, String userName, String userPassword, String userRegisterTime, String userRole){
        super(userId, userName, userPassword, userRegisterTime, userRole);
        setUserRole("admin");
    }

    //Default Constructor (Do nothing but still assign following roles)
    public Admin(){
        super();
        setUserRole("admin");
    }

    @Override
    public String toString(){
        return "{ " + "user_id" + ":" + getUserId() + ", " + "user_name" + ":" + getUserName() + ", " + "user_password" + ":" + getUserPassword() + ", " + "user_register_time" + ":" + getUserRegisterTime() + ", " + "user_role" + ":" + getUserRole() + " }";
    }

}
