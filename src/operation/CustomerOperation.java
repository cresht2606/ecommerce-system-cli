package operation;

import features.Customer;

public class CustomerOperation {

    /**
    * Returns the single instance of CustomerOperation.
    * @return CustomerOperation instance
    */

    public static CustomerOperation getInstance(){
        return null;

    }

    /**
    * Validate the provided email address format. An email address
    * consists of username@domain.extension format.
    * @param userEmail The email to validate
    * @return true if valid, false otherwise
    */

    public boolean validateEmail(String userEmail){
        return false;

    }

    /**
    * Validate the provided mobile number format. The mobile number
    * should be exactly 10 digits long, consisting only of numbers,
    * and starting with either '04' or '03'.
    * @param userMobile The mobile number to validate
    * @return true if valid, false otherwise
    */

    public boolean validateMobile(String userMobile){
        return false;

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
        return false;

    }

    /**
    * Update the given customer object's attribute value. According to
    * different attributes, perform appropriate validations.
    * @param attributeName The attribute to update
    * @param value The new value
    * @param customerObject The customer object to update
    * @return true if updated, false if failed
    */
    public boolean updateProfile(String attributeName, String value, Customer customerObject){
        return false;
    }

    /**
    * Delete the customer from the data/users.txt file based on the
    * provided customer_id.
    * @param customerId The ID of the customer to delete
    * @return true if deleted, false if failed
    */
    public boolean deleteCustomer(String customerId){
        return false;
    }

    /**
    * Retrieve one page of customers from the data/users.txt.
    * One page contains a maximum of 10 customers.
    * @param pageNumber The page number to retrieve
    * @return A List of Customer objects, the current page number, and total
    pages
    */
    public CustomerListResult getCustomerList(int pageNumber){
        return null;
    }

    /**
    * Removes all the customers from the data/users.txt file.
    */
    public void deleteAllCustomers() {
    }


}
