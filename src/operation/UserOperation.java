//Singleton pattern: Ensure that only one instance of a class exists in the application.

package operation;

import java.util.List;
import java.util.ArrayList;

import java.util.Random;

import features.User;

public class UserOperation {
    private List<User> userList = new ArrayList<>();
    private static UserOperation instance;

    /**
    * Returns the single instance of UserOperation.
    * @return UserOperation instance
    **/

    public static UserOperation getInstance(){
        if(instance == null){
            instance = new UserOperation();
        }
        return instance;
    }

    /**
    * Generates and returns a 10-digit unique user id starting with 'u_'
    * every time when a new user is registered.
    * @return A string value in the format 'u_10digits', e.g., 'u_1234567890'
    **/

    /** 
    The idea here is to use time format (but instead convert to miliseconds) since it increases monotonically, thus less repetitive ID.
    However, milisecond times can be numerously huge so we use % last and LIMIT = 10^10, by which if the newly generated ID is less than or equal to the last one, increment it by 1 to maintain uniqueness
    Finally, assign it to the String userID with String.format()
    **/

    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    public String generateUniqueUserId(){
        long ID = System.currentTimeMillis() % LIMIT;
        if(ID <= last){
            ID = (last + 1) % LIMIT; 
        }

        last = ID;

        String userID = String.format("u_%010d", ID);
        return userID;
    }

    /**
    * Encode a user-provided password.
    * Encryption steps:
    * 1. Defining a String variable consisting of all alphabets (CAPSLOCK & Non-capslock)
    * 2. Generate a random String with the length twice the length of user password (must include String alphabetic variable and numbers from Random(), as well as defining)
    * 3. Following the principle: for each two characters in random string, insert a character from user password (Using for loop)
    * 4. Add "^^" at the beginning and "$$" at the end
    * Conclusion: Use StringBuilder as a String storage with functionality of appending, indexing and deleting (this will be used for rand_string, encryptedPassword and originalPassword)
    * @param userPassword The password to encrypt
    * @return Encrypted password
    **/

    public String encryptPassword(String userPassword){
        String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder rand_string = new StringBuilder();
        Random rng = new Random();
        
        //Generate random string with doubled length of password
        while(rand_string.length() < userPassword.length() * 2){
            //Generate random character in alphabets with sufficient length
            int index = (int) (rng.nextInt(alphabets.length()));
            rand_string.append(alphabets.charAt(index));
        }

        StringBuilder encryptedPassword = new StringBuilder();
        for(int i = 0; i < userPassword.length(); i++){
            //Multiplying by 2 index will ensure there isn't any repetitive characters (1 - 2, 3 - 4 but not 0 - 1, 1 - 2, 2 - 3) 
            encryptedPassword.append(rand_string.charAt(i * 2));
            encryptedPassword.append(rand_string.charAt(i * 2 + 1));
            encryptedPassword.append(userPassword.charAt(i));
        }

        encryptedPassword.insert(0, "^^");
        encryptedPassword.append("$$");

        return encryptedPassword.toString();
    }

    /**
    * Decode the encrypted password with a similar rule as the encryption
    method.
    * @param encryptedPassword The encrypted password to decrypt
    * @return Original user-provided password
    * Essentially reverse the progress but with a check at both open ends for "^^" and "$$", then adding back original characters with sequence 2 - 1 - 2 - 1 (2 random, 1 character)
    * Starting at index = 2 (1), end at the length subtracted by 2 
    */
    public String decryptPassword(String encryptedPassword){
        if(encryptedPassword.contains("$$") && encryptedPassword.contains("^^")){
            encryptedPassword = encryptedPassword.substring(2, encryptedPassword.length() - 2);
        }
        else{
            throw new IllegalArgumentException("Invalid encrypted password, expect to go through encryption before decryption!");
        }

        StringBuilder originalPassword = new StringBuilder();
        for(int i = 2; i < encryptedPassword.length(); i+= 3){
            originalPassword.append(encryptedPassword.charAt(i));
        }
        
        return originalPassword.toString();
    }

    /**
    * Verify whether a user is already registered or exists in the system.
    * @param userName The username to check
    * @return true if exists, false otherwise
    */

    public boolean checkUsernameExist(String userName){
        for(User user : userList){
            if(user.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }   

    /**
    * Validate the user's name. The name should only contain letters or
    * underscores, and its length should be at least 5 characters.
    * @param userName The username to validate
    * @return true if valid, false otherwise
    */

    public boolean validateUsername(String userName){
        if(userName.length() <= 4){
            return false;
        }
        for(char c : userName.toCharArray()){
            if(!Character.isLetter(c) && c != '_'){
                return false;
            }
        }
        return true;
    }

    /**
    * Validate the user's password. The password should contain at least
    * one letter (uppercase or lowercase) and one number. The length
    * must be greater than or equal to 5 characters.
    * @param userPassword The password to validate
    * @return true if valid, false otherwise
    */
    public boolean validatePassword(String userPassword){
        boolean hasLetter = false;
        boolean hasDigit = false;
        if(userPassword.length() <= 4){
            return false;
        }
        for(char c : userPassword.toCharArray()){
            if(Character.isLetter(c)){
                hasLetter = true;
            }
            if(Character.isDigit(c)){
                hasDigit = true;
            }
            if(hasDigit == true && hasLetter == true){
                return true;
            }
        }
        return false;
    }

    /**
    * Verify the provided user's name and password combination against
    * stored user data to determine the authorization status.
    * @param userName The username for login
    * @param userPassword The password for login
    * @return A User object (Customer or Admin) if successful, null otherwise
    * By raising user's functions getUserName() and getUserPassword(), if both are valid → return user, for loop as usual
    */

    public User login(String userName, String userPassword){
        for(User user : userList){
            if(user.getUserName().equals(userName) && user.getUserPassword().equals(userPassword)){
                return user;
            }
        }
        return null;
    }

    //Constructor 
    public UserOperation(){
        //Load users from file or prepared data (JSON)
    }

}
