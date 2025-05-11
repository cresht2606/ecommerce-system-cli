//Singleton pattern: Ensure that only one instance of a class exists in the application.

package operation;

import features.User;

public class UserOperation {

    /**
    * Returns the single instance of UserOperation.
    * @return UserOperation instance
    **/

    public static UserOperation getInstance(){
        return null;
    }

    /**
    * Generates and returns a 10-digit unique user id starting with 'u_'
    * every time when a new user is registered.
    * @return A string value in the format 'u_10digits', e.g., 'u_1234567890'
    **/

    public String generateUniqueUserId(){
        return null;

    }

    /**
    * Encode a user-provided password.
    * Encryption steps:
    * 1. Generate a random string with a length equal to two times
    * the length of the user-provided password. The random string
    * should consist of characters chosen from a-zA-Z0-9.
    * 2. Combine the random string and the input password text to
    * create an encrypted password, following the rule of selecting
    * two letters sequentially from the random string and
    * appending one letter from the input password. Repeat until all
    * characters in the password are encrypted. Finally, add "^^" at
    * the beginning and "$$" at the end of the encrypted password.
    * *
    @param userPassword The password to encrypt
    * @return Encrypted password
    */

    public String encryptPassword(String userPassword){
        return null;
    }

    /**
    * Decode the encrypted password with a similar rule as the encryption
    method.
    * @param encryptedPassword The encrypted password to decrypt
    * @return Original user-provided password
    */
    public String decryptPassword(String encryptedPassword){
        return null;
    }

    /**
    * Verify whether a user is already registered or exists in the system.
    * @param userName The username to check
    * @return true if exists, false otherwise
    */

    public boolean checkUsernameExist(String userName){
        return false;
    }   

    /**
    * Validate the user's name. The name should only contain letters or
    * underscores, and its length should be at least 5 characters.
    * @param userName The username to validate
    * @return true if valid, false otherwise
    */

    public boolean validateUsername(String userName){
        return false;
    }

    /**
    * Validate the user's password. The password should contain at least
    * one letter (uppercase or lowercase) and one number. The length
    * must be greater than or equal to 5 characters.
    * @param userPassword The password to validate
    * @return true if valid, false otherwise
    */
    public boolean validatePassword(String userPassword){
        return false;
    }

    /**
    * Verify the provided user's name and password combination against
    * stored user data to determine the authorization status.
    * @param userName The username for login
    * @param userPassword The password for login
    * @return A User object (Customer or Admin) if successful, null otherwise
    */

    public User login(String userName, String userPassword){
        return null;
    }

}
