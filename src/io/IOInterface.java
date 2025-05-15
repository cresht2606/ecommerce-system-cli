package io;

import java.util.List;
import java.util.Scanner;

public class IOInterface {
    private static IOInterface instance;
    private Scanner scanner;

    //Constructor
    private IOInterface() {
        scanner = new Scanner(System.in);
    }

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    /**
    * Accept user input.
    * @param message The message to display for input prompt
    * @param numOfArgs The number of arguments expected
    * @return An array of strings containing the arguments
    */

    public String[] getUserInput(String message, int numOfArgs) {
        
        //Display a message (e.g: instructions, table)
        System.out.println(message);

        System.out.println("Enter your choice: ");

        // Read input line, trim spaces, and split by whitespace
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        //Check if the correct argument was made within the number list
        if(parts.length != numOfArgs){
            throw new IllegalArgumentException("Invalid input. Expected \" + numOfArgs + \" arguments.");
        }
        return parts;
    }

    /**
    * Display the login menu with options: (1) Login, (2) Register, (3) Quit.
    * The admin account cannot be registered.
    */

    public void mainMenu() {
        System.out.println("======= E-commerce System =======");
        System.out.println("(1) Login");
        System.out.println("(2) Register");
        System.out.println("(3) Quit");
    }

    /**
    * Display the admin menu with options:
    * (1) Show products
    * (2) Add customers
    * (3) Show customers
    * (4) Show orders
    * (5) Generate test data
    * (6) Generate all statistical figures
    * (7) Delete all data
    * (8) Logout
    */

    public void adminMenu() {
        System.out.println("======= Admin Menu =======");
        System.out.println("(1) Show products");
        System.out.println("(2) Add customers");
        System.out.println("(3) Show customers");
        System.out.println("(4) Show orders");
        System.out.println("(5) Generate test data");
        System.out.println("(6) Generate all statistical figures");
        System.out.println("(7) Delete all data");
        System.out.println("(8) Logout");
    }


    /**
    * Display the customer menu with options:
    * (1) Show profile
    * (2) Update profile
    * (3) Show products (user input could be "3 keyword" or "3")
    * (4) Show history orders
    * (5) Generate all consumption figures
    * (6) Logout
    */

    public void customerMenu() {
        System.out.println("======= Customer Menu =======");
        System.out.println("(1) Show profile");
        System.out.println("(2) Update profile");
        System.out.println("(3) Show products");
        System.out.println("(4) Show history orders");
        System.out.println("(5) Generate all consumption figures");
        System.out.println("(6) Logout");
    }


    /**
    * Prints out different types of lists (Customer, Product, Order).
    * Shows row number, page number, and total page number.
    * @param userRole The role of the current user
    * @param listType The type of list to display
    * @param objectList The list of objects to display
    * @param pageNumber The current page number
    * @param totalPages The total number of pages
    */

    public void showList(String userRole, String listType, List<?> objectList, int pageNumber, int totalPages) {
        System.out.println("======= " + listType + " List (Page " + pageNumber + " of " + totalPages + ") =======");

        int index = 1;
        for (Object obj : objectList) {
            // Example: only display full details for admin
            if ("admin".equalsIgnoreCase(userRole)) {
                System.out.println(index++ + ". " + obj.toString());
            } else if ("customer".equalsIgnoreCase(userRole)) {
                // For customers, hide sensitive fields like password or userId if possible
                String line = obj.toString();
                // Mask password (assuming JSON format with "user_password":"...")
                line = line.replaceAll("\"user_password\":\"[^\"]+\"", "\"user_password\":\"******\"");
                // Optionally mask user_id too
                line = line.replaceAll("\"user_id\":\"[^\"]+\"", "\"user_id\":\"****\"");
                System.out.println(index++ + ". " + line);
            } else {
                // Default fallback (in case role is unrecognized)
                System.out.println(index++ + ". " + obj.toString());
            }
        }
    }

    /**
    * Prints out an error message and shows where the error occurred.
    * @param errorSource The source of the error
    * @param errorMessage The error message
    */

    public void printErrorMessage(String errorSource, String errorMessage) {
        System.out.println("[Error] - Source: " + errorSource + " Message: " + errorMessage);
    }

    /**
    * Print out the given message.
    * @param message The message to print
    */

    public void printMessage(String message) {
        System.out.print(message);
    }


    /**
    * Print out the object using the toString() method.
    * @param targetObject The object to print
    */

    public void printObject(Object targetObject) {
        System.out.print(targetObject.toString());
    }

}
