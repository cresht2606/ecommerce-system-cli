import features.*;
import operation.*;
import io.IOInterface;
import java.util.List;

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
                        int product_page = 1;
                        while(true){
                            ProductListResult result = ProductOperation.getInstance().getProductList(1);
                            if(result.getProducts().isEmpty()){
                                io.printMessage("No products found!\n");
                                break;
                            }
                            io.showList("admin", "Product", result.getProducts(), result.getCurrentPage(), result.getTotalPages());
                            
                            String nav = io.getUserInput("Enter 'n' for next, 'p' for previous, or 'b' to go back: ", 1)[0];

                            if (nav.equalsIgnoreCase("n") && product_page < result.getTotalPages()) {
                                product_page++;
                            } else if (nav.equalsIgnoreCase("p") && product_page > 1) {
                                product_page--;
                            } else if (nav.equalsIgnoreCase("b")) {
                                break;
                            } else {
                                io.printErrorMessage("Admin Navigation", "Invalid navigation input.");
                            }
                        }
                        break;
                    case "2": 
                        String fileName = io.getUserInput("Enter the filename to import from (e.g., other_users.txt): ", 1)[0];
                        boolean success = CustomerOperation.getInstance().importCustomersFromFile(fileName);
                        if (success) {
                            io.printMessage("Customers successfully imported into users.txt!\n");
                        } else {
                            io.printErrorMessage("Import", "Failed to import customers. Check file path or format.");
                        }
                        break;
                    
                    case "3": 
                        int customer_page = 1;
                        while (true) {
                            CustomerListResult custResult = CustomerOperation.getInstance().getCustomerList(customer_page);

                            if (custResult.getCustomers().isEmpty()) {
                                io.printMessage("No customers found!\n");
                                break;
                            }

                            io.showList("admin", "Customer", custResult.getCustomers(), custResult.getCurrentPage(), custResult.getTotalPages());

                            String nav = io.getUserInput("Enter 'n' for next, 'p' for previous, or 'b' to go back:", 1)[0];

                            if (nav.equalsIgnoreCase("n") && customer_page < custResult.getTotalPages()) {
                                customer_page++;
                            } else if (nav.equalsIgnoreCase("p") && customer_page > 1) {
                                customer_page--;
                            } else if (nav.equalsIgnoreCase("b")) {
                                break;
                            } else {
                                io.printErrorMessage("Customer Navigation", "Invalid input.");
                            }
                        }
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
                                io.printErrorMessage("Order Navigation", "Invalid navigation input.");
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
                        String confirmation = io.getUserInput("Are you sure you want to delete all data? This action cannot be undone. (Y/N):", 1)[0];
                        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                            ProductOperation.getInstance().deleteAllProducts();
                            CustomerOperation.getInstance().deleteAllCustomers();
                            OrderOperation.getInstance().deleteAllOrders();
                            io.printMessage("All data has been deleted successfully.\n");
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
                        // Ask for the attribute to update
                        String attribute = io.getUserInput(
                            "Enter the desired attribute you want to update (userName, userPassword, userEmail, userMobile):",
                            1
                        )[0];

                        // Ask for the new value
                        String newValue = io.getUserInput("Enter new information value:", 1)[0];

                        boolean updated = CustomerOperation.getInstance().updateProfile(attribute, newValue, (Customer) customer);
                        if (updated) {
                            io.printMessage("Update profile successfully!\n");
                        } else {
                            io.printErrorMessage("Profile Update", "Failed to update attribute. Check again format or validity!");
                        }
                        break;
                    case "3":
                        io.printMessage("Enter '3' to view all products or '3 keyword' to search: ");
                        String[] keyword_input = io.getUserInput("", 1);

                        if(keyword_input.length == 1 && keyword_input[0].equals("3")){
                            ProductListResult result = ProductOperation.getInstance().getProductList(1);
                            io.showList("customer", "Product", result.getProducts(), result.getCurrentPage(), result.getTotalPages());
                        } else if(keyword_input.length == 2 && keyword_input[0].equals("3")){
                            List<Product> filtered_product = ProductOperation.getInstance().getProductListByKeyword(keyword_input[1]);
                            io.showList("customer", "Product (Filtered)", filtered_product, 1, 1);
                        } else {
                            io.printErrorMessage("Product View", "Invalid product input format!");
                        }
                        break;
                    case "4":
                        int orderPage = 1;
                        while(true){
                            OrderListResult orders = OrderOperation.getInstance().getOrderList(customer.getUserId(), orderPage);
                            //Check if the order is empty
                            if(orders.getOrders().isEmpty()){
                                io.printMessage("No order history before, please buy something to fill in the cart!");
                                break;
                            }
                            io.showList("customer", "Order", orders.getOrders(), orders.getCurrentPage(), orders.getTotalPages());
                            io.printMessage("Enter 'n' (next), 'p' (previous), 'b' (back)\n");
                            String navigation = io.getUserInput("", 1)[0];
                            if(navigation.equalsIgnoreCase("n") && orderPage < orders.getTotalPages()){
                                orderPage++;
                            } else if (navigation.equalsIgnoreCase("p") && orderPage > 1){
                                orderPage--;
                            } else if(navigation.equalsIgnoreCase("b")) {
                                break;
                            } else {
                                io.printErrorMessage("Order Navigation", "Invalid input!");
                            }
                        }
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