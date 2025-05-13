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

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getProId() { return proId; }
    public String getOrderTime() { return orderTime; }

    @Override
    public String toString() {
        return String.format("{\"orderId\":\"%s\", \"userId\":\"%s\", \"proId\":\"%s\", \"orderTime\":\"%s\"}",
                orderId, userId, proId, orderTime);
    }
}
