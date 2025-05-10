package operation;

import features.Order;
import java.util.*;

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


