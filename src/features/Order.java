package features;

public class Order {
    private String orderId;
    private String userId;
    private String proId;
    private String orderTime;

    public Order(String orderId, String userId, String proId, String orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    public Order() {
        this("o_00000", "", "", "01-01-1970_00:00:00");
    }

    @Override
    public String toString() {
        return String.format(
            "{\"order_id\":\"%s\", \"user_id\":\"%s\", \"pro_id\":\"%s\", \"order_time\":\"%s\"}",
            orderId, userId, proId, orderTime
        );
    }
}

