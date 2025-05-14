package io;

import java.util.List;
import java.util.Scanner;

public class IOInterface {
    private static IOInterface instance;
    private Scanner scanner;

    private IOInterface() {
        scanner = new Scanner(System.in);
    }

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Read an integer with input validation
    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    
    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    
    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    
    public void printError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    
    public void printObject(Object obj) {
        System.out.println(obj.toString());
    }

    public void printList(List<?> list, int currentPage, int totalPages) {
        System.out.println("\n List (Page " + currentPage + " of " + totalPages + "):");
        for (Object item : list) {
            System.out.println(" - " + item.toString());
        }
        System.out.println("──────────────────────────────────");
    }

    
    public void showMainMenu() {
        System.out.println("\n==== E-COMMERCE SYSTEM ====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
    }

    
    public void showAdminMenu() {
        System.out.println("\n==== ADMIN MENU ====");
        System.out.println("1. Show products");
        System.out.println("2. Add customers");
        System.out.println("3. Show customers");
        System.out.println("4. Show orders");
        System.out.println("5. Generate test data");
        System.out.println("6. Generate all statistical figures");
        System.out.println("7. Delete all data");
        System.out.println("8. Logout");        
    }

    
    public void showCustomerMenu() {
        System.out.println("\n==== CUSTOMER MENU ====");
        System.out.println("1. Show profile");
        System.out.println("2. Update profile");
        System.out.println("3. Show products");
        System.out.println("4. Show history orders");
        System.out.println("5. Logout");
    }
}
