package operation;

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
    public String generateUniqueOrderId() {
    // Implementation
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
    // Implementation
    }

    /**
    * Deletes the order from the data/orders.txt file based on order_id.
    * @param orderId The ID of the order to delete
    * @return true if successful, false otherwise
    */
    public boolean deleteOrder(String orderId) {
    // Implementation
    }

    /**
    * Retrieves one page of orders from the database belonging to the
    * given customer. One page contains a maximum of 10 items.
    * @param customerId The ID of the customer
    * @param pageNumber The page number to retrieve
    * @return A list of Order objects, current page number, and total pages
    */
    public OrderListResult getOrderList(String customerId, int pageNumber) {
    // Implementation
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
    // Implementation
    }


}

/*
public class OrderOperation {
    private static OrderOperation instance;
    private List<Order> orders;

    private OrderOperation() {
        orders = new ArrayList<>();
    }

    public static OrderOperation getInstance() {
        if (instance == null) {
            instance = new OrderOperation();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrdersByUser(String userId) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.toString().contains(userId)) {
                result.add(o);
            }
        }
        return result;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
*/

