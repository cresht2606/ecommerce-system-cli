package operation;

public class AdminOperation {

    private static AdminOperation instance;

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
    }

}
