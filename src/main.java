import java.io.IOException;

import features.*;
import operation.*;
import io.IOInterface;

public class main{
    public static void main(String[] args) {
        AdminOperation.getInstance().registerAdmin(); // Step 1: Register admin at startup

        IOInterface io = IOInterface.getInstance();
        UserOperation userOp = UserOperation.getInstance();

        while (true) {
            try {
                io.mainMenu(); // Step 2: Show main menu
                String[] input = io.getUserInput("", 1);
                String choice = input[0];

                switch (choice) {
                    case "1": // Login
                        handleLogin(io, userOp);
                        break;
                    case "2": // Register as customer
                        handleCustomerRegistration();
                        break;
                    case "3": // Quit
                        io.printMessage("Exiting system. Goodbye!\n");
                        return;
                    default:
                        io.printErrorMessage("Main Menu", "Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                io.printErrorMessage("Main", e.getMessage());
            }
        }
    }

    private static void handleLogin(IOInterface io, UserOperation userOp) {
        String[] credentials = io.getUserInput("Enter username and password:", 2);
        String username = credentials[0];
        String password = credentials[1];

        User loggedInUser = userOp.login(username, password);
        if (loggedInUser != null) {
            io.printMessage("\nLogin successful. Welcome, " + loggedInUser.getUserName() + "!\n");
            if ("admin".equalsIgnoreCase(loggedInUser.getUserRole())) {
                runAdminMenu();
            } else {
                runCustomerMenu(loggedInUser);
            }
        } else {
            io.printErrorMessage("Login", "Invalid username or password.");
        }
    }

    private static void handleCustomerRegistration() {
        IOInterface io = IOInterface.getInstance();
        CustomerOperation custOp = CustomerOperation.getInstance();

        try {
            String[] input = io.getUserInput("Enter username, password, email, mobile (space-separated):", 4);
            String username = input[0];
            String password = input[1];
            String email = input[2];
            String mobile = input[3];

            boolean success = custOp.registerCustomer(username, password, email, mobile);
            if (success) {
                io.printMessage("Customer registered successfully!\n");
            } else {
                io.printErrorMessage("Registration", "Failed to register customer (Either invalid format or username already exists)");
            }
        } catch (Exception e) {
            io.printErrorMessage("Registration", "Unexpected error: " + e.getMessage());
        }
    }


    private static void runAdminMenu() {
        IOInterface io = IOInterface.getInstance();
        boolean isRunning = true;

        while (isRunning) {
            io.adminMenu();
            try {
                String[] input = io.getUserInput("", 1);
                String choice = input[0];

                switch (choice) {
                    case "1":
                        // Show products
                        ProductListResult result = ProductOperation.getInstance().getProductList(1);
                        io.showList("admin", "Product", result.getProducts(), result.getCurrentPage(), result.getTotalPages());
                        break;
                    case "2":
                        // Add customers (call registration logic)
                        handleCustomerRegistration(); 
                        break;
                    case "3":
                        // Show customers
                        CustomerListResult custResult = CustomerOperation.getInstance().getCustomerList(1);
                        io.showList("admin", "Customer", custResult.getCustomers(), custResult.getCurrentPage(), custResult.getTotalPages());
                        break;
                    case "4":
                        String[] inputOrder = io.getUserInput("Enter the customer ID to view orders: ", 1);
                        String customerId = inputOrder[0];
                        int page = 1;

                        while (true) {
                            OrderListResult orderResult = OrderOperation.getInstance().getOrderList(customerId, page);

                            if (orderResult.getOrders().isEmpty()) {
                                io.printMessage("No orders found for customer ID: " + customerId + "\n");
                                break;
                            }

                            io.showList("admin", "Order", orderResult.getOrders(), orderResult.getCurrentPage(), orderResult.getTotalPages());

                            String[] navInput = io.getUserInput("Enter 'n' for next, 'p' for previous, or 'b' to go back: ", 1);
                            String nav = navInput[0];

                            if (nav.equalsIgnoreCase("n") && page < orderResult.getTotalPages()) {
                                page++;
                            } else if (nav.equalsIgnoreCase("p") && page > 1) {
                                page--;
                            } else if (nav.equalsIgnoreCase("b")) {
                                break;
                            } else {
                                io.printErrorMessage("Admin Order Menu", "Invalid navigation input.");
                            }
                        }
                        break;

                    case "5":
                        // Generate test data
                        break;
                    case "6":
                        // Generate figures
                        break;
                    case "7":
                        io.printMessage("Are you sure you want to delete all data? This action cannot be undone. (Y/N): ");
                        String confirmation = io.getUserInput("", 1)[0];

                        if (confirmation.equalsIgnoreCase("yes")) {
                            ProductOperation.getInstance().deleteAllProducts();
                            CustomerOperation.getInstance().deleteAllCustomers();
                            OrderOperation.getInstance().deleteAllOrders();

                            io.printMessage("All data has been deleted successfully.\n");

                            // Optional: re-register admin account if needed
                            AdminOperation.getInstance().registerAdmin();
                        } else {
                            io.printMessage("Deletion cancelled.\n");
                        }
                        break;
                    case "8":
                        isRunning = false; // Logout
                        break;
                    default:
                        io.printErrorMessage("Admin Menu", "Invalid choice. Please select from 1 to 8.");
                }
            } catch (Exception e) {
                io.printErrorMessage("Admin Menu", e.getMessage());
            }
        }
    }

    private static void runCustomerMenu(User customer) {
        IOInterface io = IOInterface.getInstance();
        boolean isRunning = true;

        while (isRunning) {
            io.customerMenu();
            try {
                String[] input = io.getUserInput("", 1);
                String choice = input[0];

                switch (choice) {
                    case "1":
                        // Show profile
                        io.printObject(customer);
                        break;
                    case "2":
                        // Update profile (you can request attribute name and value interactively)
                        break;
                    case "3":
                        // Show product list or by keyword
                        break;
                    case "4":
                        // Show order history
                        break;
                    case "5":
                        // Generate consumption figures
                        break;
                    case "6":
                        isRunning = false; // Logout
                        break;
                    default:
                        io.printErrorMessage("Customer Menu", "Invalid choice. Please select from 1 to 6.");
                }
            } catch (Exception e) {
                io.printErrorMessage("Customer Menu", e.getMessage());
            }
        }
    }

}