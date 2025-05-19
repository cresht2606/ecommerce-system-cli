package operation;

import features.Customer;

import java.util.regex.Pattern;
import java.io.BufferedReader;

//Save .txt files
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

//Set current time of shopping
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
//Set Customer list
import java.util.List;
import java.util.Set;


public class CustomerOperation {

    /**
    * Returns the single instance of CustomerOperation.
    * @return CustomerOperation instance
    */

    private static CustomerOperation instance;
    public static CustomerOperation getInstance(){
        if(instance == null){
            instance = new CustomerOperation();
        }
        return instance;
    }

    /**
    * Validate the provided email address format. An email address
    * consists of username@domain.extension format.
    * @param userEmail The email to validate
    * @return true if valid, false otherwise
    * Since using charsAt() can be time-consuming and not suitable for long-character-suffix name emails, instead we use RegEx
    */

    public boolean validateEmail(String userEmail){

        //All valid character types: a-z, A-Z, 0-9, _+&*-, optionally there is a seperated dot is made: E.g: john.cloe . 
        //(?:[a-zA-Z0-9-]+\\.) → Domain: must have atleast one "." (e.g: .gmail). 
        //[a-zA-Z]{2,7}$: Top-domain value (.com, .org, .vn, .us) 

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
        Pattern p = Pattern.compile(emailRegex);

        return userEmail != null && p.matcher(userEmail).matches();
    }

    /**
    * Validate the provided mobile number format. The mobile number
    * should be exactly 10 digits long, consisting only of numbers,
    * and starting with either '04' or '03'.
    * @param userMobile The mobile number to validate
    * @return true if valid, false otherwise
    */

    public boolean validateMobile(String userMobile){
        if(userMobile.length() != 10){
            return false;
        }
        if(!userMobile.startsWith("03") && !userMobile.startsWith("04")){
            return false;
        }
        for(char c : userMobile.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }  

    /**
    * Save the information of the new customer into the data/users.txt file.
    * @param userName Customer's username
    * @param userPassword Customer's password
    * @param userEmail Customer's email
    * @param userMobile Customer's mobile number
    * @return true if success, false if failure
    */

    public boolean registerCustomer(String userName, String userPassword, String userEmail, String userMobile){

        //Validate userName, userPassword
        UserOperation userOp = UserOperation.getInstance();
        if(!userOp.validateUsername(userName) || !userOp.validatePassword(userPassword)){
            return false;
        }
        if(userOp.checkUsernameExist(userName)){
            return false;
        }

        //Validate email and mobile
        if(!validateEmail(userEmail) || !validateMobile(userMobile)){
            return false;
        }

        //Generate unique UserId, since UserOperation is a singleton and only one instance of the class exist during runtime
        String userId = userOp.generateUniqueUserId();
        String encrypted_pass = userOp.encryptPassword(userPassword);

        //Get current time
        String registerTime = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date());

        //Create new Customer object by using getters, setters
        Customer customer = new Customer();
        try{
            customer.setUserId(userId);
            customer.setUserName(userName);
            customer.setUserPassword(encrypted_pass);
            customer.setUserRegisterTime(registerTime);
            customer.setUserRole("customer");
            customer.setUserEmail(userEmail);
            customer.setUserMobile(userMobile);
        }catch (IllegalArgumentException e){
            return false;
        }

        String userData = "{"
        + "\"user_id\":\"" + customer.getUserId() + "\","
        + "\"user_name\":\"" + customer.getUserName() + "\","
        + "\"user_password\":\"" + customer.getUserPassword() + "\","
        + "\"user_register_time\":\"" + customer.getUserRegisterTime() + "\","
        + "\"user_role\":\"" + customer.getUserRole() + "\","
        + "\"user_email\":\"" + customer.getUserEmail() + "\","
        + "\"user_mobile\":\"" + customer.getUserMobile() + "\""
        + "}";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("database\\users.txt", true))){
            writer.write(userData);
            writer.newLine();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    /**
    * Update the given customer object's attribute value. According to
    * different attributes, perform appropriate validations.
    * @param attributeName The attribute to update
    * @param value The new value
    * @param customerObject The customer object to update
    * @return true if updated, false if failed
    */

    public boolean updateProfile(String attributeName, String value, Customer customerObject) {
        UserOperation userOp = UserOperation.getInstance();

        // Validate and update in-memory object
        if ("userName".equalsIgnoreCase(attributeName)) {
            if (!userOp.validateUsername(value)) return false;
            customerObject.setUserName(value);
        } else if ("userPassword".equalsIgnoreCase(attributeName)) {
            if (!userOp.validatePassword(value)) return false;
            customerObject.setUserPassword(userOp.encryptPassword(value)); // Encrypt before saving
        } else if ("userEmail".equalsIgnoreCase(attributeName)) {
            if (!validateEmail(value)) return false;
            customerObject.setUserEmail(value);
        } else if ("userMobile".equalsIgnoreCase(attributeName)) {
            if (!validateMobile(value)) return false;
            customerObject.setUserMobile(value);
        } else {
            return false; // Unknown attribute
        }

        // Read all lines and update the matching customer
        File inputFile = new File("database\\users.txt");
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String userId = extractUserId(line);
                if (userId != null && userId.equals(customerObject.getUserId())) {
                    // Found the user, replace with updated data
                    String updatedLine = "{"
                        + "\"user_id\":\"" + customerObject.getUserId() + "\","
                        + "\"user_name\":\"" + customerObject.getUserName() + "\","
                        + "\"user_password\":\"" + customerObject.getUserPassword() + "\","
                        + "\"user_register_time\":\"" + customerObject.getUserRegisterTime() + "\","
                        + "\"user_role\":\"" + customerObject.getUserRole() + "\","
                        + "\"user_email\":\"" + customerObject.getUserEmail() + "\","
                        + "\"user_mobile\":\"" + customerObject.getUserMobile() + "\""
                        + "}";
                    updatedLines.add(updatedLine);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!found) return false;

        // Overwrite the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
    * Delete the customer from the data/users.txt file based on the
    * provided customer_id.
    * @param customerId The ID of the customer to delete
    * @return true if deleted, false if failed
    */
    public boolean deleteCustomer(String customerId){
        File inputFile = new File("ecommerce-system-cli/database/users.txt");
        File tempFile = new File("ecommerce-system-cli/database/backup_users.txt");
        boolean deleted = false;

        try(
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ){
            String currentLine;

            while((currentLine = reader.readLine()) != null){
                String extractedId = extractUserId(currentLine);
                if(customerId.equals(extractedId)){
                    deleted = true;
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
        if(deleted){
            if(!inputFile.delete() || !tempFile.renameTo(inputFile)){
                return false;
            }
        } else{
            tempFile.delete();
        }
        return deleted;
    }

    //Helper extract customerID
    public String extractUserId(String dataline){
        String key = "\"user_id\":\"";
        int start = dataline.indexOf(key);
        if(start == -1){
            return null;
        }
        start += key.length();
        int end = dataline.indexOf("\"", start);
        if(end == -1){
            return null;
        }
        return dataline.substring(start, end);
    }

    //Helper extract userName
    public String extractUserName(String dataline) {
        String key = "\"user_name\":\"";
        int start = dataline.indexOf(key);
        if (start == -1) {
            return null;
        }
        start += key.length();
        int end = dataline.indexOf("\"", start);
        if (end == -1) {
            return null;
        }
        return dataline.substring(start, end);
    }

    /**
    * Retrieve one page of customers from the data/users.txt.
    * One page contains a maximum of 10 customers.
    * @param pageNumber The page number to retrieve
    * @return A List of Customer objects, the current page number, and total
    pages
    */
    public CustomerListResult getCustomerList(int pageNumber){

        //Create a list to store Customers' information
        List<String> allCustomerLines = new ArrayList<>();

        //Read the users.txt by storing it as filepath
        File inputFile = new File("database\\users.txt");


        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            //Read the file line by line
            while((line = reader.readLine()) != null){
                //Check to see whether the lines contains appropriate format below
                if(line.contains("\"user_role\":\"customer\"")){
                    //Add customer to the list
                    allCustomerLines.add(line);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            //If raising error, return an empty customer list with 0 total pages
            return new CustomerListResult(new ArrayList<>(), pageNumber, 0);
        }
        
        //Calculate total number of customers, pages and maximum allocation for customers storage
        int totalCustomers = allCustomerLines.size();
        int pageSize = 10;
        int totalPages = (int) Math.ceil(totalCustomers / 10.0); //Type-casting

        //Validate the requested page number
        if(pageNumber < 1 || pageNumber > totalPages){
            //If the pageNumber does not exist or exceeds the limit page → Return empty Customer list
            return new CustomerListResult(new ArrayList<>(), pageNumber, totalPages);
        }

        //Determine the start and end indices for the current page
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalCustomers);

        //Extract the sublist of customers for the page
        List<String> pageCustomers = allCustomerLines.subList(start, end);

        //Return the Customer list
        return new CustomerListResult(pageCustomers, pageNumber, totalPages);

    }

    /**
    * Removes all the customers from the data/users.txt file.
    * But not remove all other admins so we need to filter only keyword "customer"
    */

    public void deleteAllCustomers() {
        File inputFile = new File("database\\users.txt");
        List<String> preservedLines = new ArrayList<>();

        // Step 1: Read and filter lines
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Keep all lines except those of customers
                if (!line.contains("\"user_role\":\"customer\"")) {
                    preservedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Overwrite the file with preserved (non-customer) lines
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            for (String line : preservedLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Read other customer files .txt and copy those customers (if not presented) into users.txt
    */

    public boolean importCustomersFromFile(String sourceFileName) {
        File sourceFile = new File("database\\" + sourceFileName);
        File targetFile = new File("database\\users.txt");

        if (!sourceFile.exists()) {
            System.err.println("Source file not found: " + sourceFileName);
            return false;
        }

        // Step 1: Read all existing usernames from users.txt
        Set<String> existingUsernames = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(targetFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String username = extractUserName(line);
                if (username != null) {
                    existingUsernames.add(username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Step 2: Read from source file and append non-duplicates
        int importedCount = 0;

        try (
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, true)) // Append mode
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.contains("\"user_role\":\"customer\"")) {
                    continue;
                }

                String username = extractUserName(line);
                if (username == null || existingUsernames.contains(username)) {
                    continue; // Skip duplicates
                }

                writer.write(line);
                writer.newLine();
                existingUsernames.add(username); // Add to avoid future duplicates in this run
                importedCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (importedCount == 0) {
            System.out.println("No new customers were imported (all may already exist).");
            return false;
        }

        return true;
    }


}
