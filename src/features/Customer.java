package features;

/**
* Constructs a customer object.
* @param userId Must be unique, format: u_10 digits, such as u_1234567890
* @param userName The user's name
* @param userPassword The user's password
* @param userRegisterTime Format: "DD-MM-YYYY_HH:MM:SS"
* @param userRole Default value: "customer"
* @param userEmail The customer's email address
* @param userMobile The customer's mobile number
**/


public class Customer extends User{
    private String userEmail;
    private String userMobile;

    //Constructor with parameters
    public Customer(String userId, String userName, String userPassword, String userRegisterTime, String userRole, String userEmail, String userMobile){
        super(userId, userName, userPassword, userRegisterTime, userRole);
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        setUserRole("customer");
    }

    //Default constructor (Do nothing but still assign following roles)
    public Customer(){
        super();
        setUserRole("customer");

    }  

    //Getters & Setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    //Default information display format
    @Override
    public String toString(){
        return "{ " + "user_id" + ":" + getUserId() + ", " + "user_name" + ":" + getUserName() + ", " + "user_password" + ":" + getUserPassword() + ", " + "user_register_time" + ":" + getUserRegisterTime() + ", " + "user_role" + ":" + getUserRole() + ", " + "user_email" + ":" + getUserEmail() + ", " + "user_mobile" + ":" + getUserMobile() + " }";
    }

}
