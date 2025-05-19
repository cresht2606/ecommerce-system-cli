package operation;

//File Reader / Writer
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import features.Order;

import java.util.Date;

public class OrderOperation{

    private static OrderOperation instance;

    /**
    * Returns the single instance of OrderOperation.
    * @return OrderOperation instance
    **/

    public static OrderOperation getInstance(){
        if(instance == null){
            instance = new OrderOperation();
        }
        return instance;
    }

    /**
    * Generates and returns a 5-digit unique order id starting with "o_".* @return A string such as o_12345
    */

    private static final long LIMIT = 100000;
    private static long last = 0;

    public String generateUniqueOrderId() {
        long ID = System.currentTimeMillis() % LIMIT;
        if(ID <= last){
            ID = (last + 1) % LIMIT; 
        }

        last = ID;

        String orderID = String.format("o_%05d", ID);
        return orderID;
    }

    /**
    * Creates a new order with a unique order id and saves it to the
    * data/orders.txt file.
    * @param customerId The ID of the customer making the order
    * @param productId The ID of the product being ordered
    * @param createTime The time of order creation (null for current time)
    * @return true if successful, false otherwise
    */
    public boolean createAnOrder(String customerId, String productId, String createTime) {

        if(customerId == null || productId == null){
            return false;
        }

        String orderId = generateUniqueOrderId();

        if(createTime == null || createTime.isEmpty()){
            createTime = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date()); 
        }

        String order = String.format("{\"order_id\":\"%s\", \"user_id\":\"%s\", \"pro_id\":\"%s\", \"order_time\":\"%s\"}",
                orderId, customerId, productId, createTime);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("ecommerce-system-cli/database/orders.txt", true))){
            writer.write(order);
            writer.newLine();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
    * Deletes the order from the data/orders.txt file based on order_id.
    * @param orderId The ID of the order to delete
    * @return true if successful, false otherwise
    */

    public boolean deleteOrder(String orderId) {
        File inputFile = new File("ecommerce-system-cli/database/orders.txt");
        File tempFile = new File("ecommerce-system-cli/database/backup_orders.txt");
        boolean deleted = false;

        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String extractedId = extractValue(currentLine, "order_id");
                if (orderId.equals(extractedId)) {
                    deleted = true;
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (deleted) {
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                return false;
            }
        } else {
            tempFile.delete();
        }

        return deleted;
    }

    public String extractValue(String dataline, String key) {
        String searchKey = "\"" + key + "\":\"";
        int start = dataline.indexOf(searchKey);
        if (start == -1) {
            return null;
        }
        start += searchKey.length();
        int end = dataline.indexOf("\"", start);
        if (end == -1) {
            return null;
        }
        return dataline.substring(start, end);
    }

    /**
    * Retrieves one page of orders from the database belonging to the
    * given customer. One page contains a maximum of 10 items.
    * @param customerId The ID of the customer
    * @param pageNumber The page number to retrieve
    * @return A list of Order objects, current page number, and total pages
    */
    public OrderListResult getOrderList(String customerId, int pageNumber) {
        List<Order> allOrders = new ArrayList<>();
        File inputFile = new File("database\\orders.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("\"user_id\":\"" + customerId + "\"")) {
                    // Inline parsing of order fields
                    String orderId = extractValue(line, "order_id");
                    String userId = extractValue(line, "user_id");
                    String proId = extractValue(line, "pro_id");
                    String orderTime = extractValue(line, "order_time");

                    if (orderId != null && userId != null && proId != null && orderTime != null) {
                        allOrders.add(new Order(orderId, userId, proId, orderTime));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderListResult(new ArrayList<>(), pageNumber, 0);
        }

        int totalOrders = allOrders.size();
        int pageSize = 10;
        int totalPages = (int) Math.ceil(totalOrders / 10.0);

        if (pageNumber < 1 || pageNumber > totalPages) {
            return new OrderListResult(new ArrayList<>(), pageNumber, totalPages);
        }

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalOrders);
        List<Order> pageOrders = allOrders.subList(start, end);

        return new OrderListResult(pageOrders, pageNumber, totalPages);
    }

    /**
    * Automatically generates test data including customers and orders.
    * Creates 10 customers and randomly generates 50-200 orders for each.
    * Order times should be scattered across different months of the year.*/
    public void generateTestOrderData() {
    // Implementation
    }

    /**
    * Generates a chart showing the consumption (sum of order prices)
    * across 12 different months for the given customer.
    * @param customerId The ID of the customer
    */
    public void generateSingleCustomerConsumptionFigure(String customerId) {
    // Implementation using Java charting library
    }

    /**
    * Generates a chart showing the consumption (sum of order prices)
    * across 12 different months for all customers.
    */
    public void generateAllCustomersConsumptionFigure() {
    // Implementation using Java charting library
    }

    /**
    * Generates a graph showing the top 10 best-selling products
    * sorted in descending order.
    */
    public void generateAllTop10BestSellersFigure() {
    // Implementation using Java charting library
    }


    /**
    * Removes all data in the data/orders.txt file.
    */
    public void deleteAllOrders() {
        File orderFile = new File("ecommerce-system-cli/database/orders.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile))){
            //Clear the file
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}

