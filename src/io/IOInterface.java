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
        System.out.println("1. Log in");
        System.out.println("2. Register as Customer");
        System.out.println("3. Exit");
    }

    
    public void showAdminMenu() {
        System.out.println("\n==== ADMIN MENU ====");
        System.out.println("1. Manage Users");
        System.out.println("2. Manage Products");
        System.out.println("3. Manage Orders");
        System.out.println("4. View Statistics");
        System.out.println("5. Log out");
    }

    
    public void showCustomerMenu() {
        System.out.println("\n==== CUSTOMER MENU ====");
        System.out.println("1. View Products");
        System.out.println("2. Place an Order");
        System.out.println("3. View My Orders");
        System.out.println("4. Update Profile");
        System.out.println("5. Log out");
    }
}
