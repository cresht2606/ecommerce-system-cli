package features;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    private String orderId;
    private String userId;
    private String proId;
    private String orderTime;

    //Constructor
    public Order(String orderId, String userId, String proId, String orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    //Default constructor
    public Order(){
        this.orderId = "";
        this.userId = "";
        this.proId = "";
        this.orderTime = "";
    }

    //Getters
    public String getOrderId() {
        return orderId;
    }
    public String getUserId() {
        return userId;
    }
    public String getProId() {
        return proId;
    }
    public String getOrderTime() {
        return orderTime;
    }

    
    //Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setProId(String proId) {
        this.proId = proId;
    }
    public void setOrderTime(String orderTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        sdf.setLenient(false);

        try{
            Date date = sdf.parse(orderTime);
            this.orderTime = sdf.format(date);
        } catch (ParseException e){
            throw new IllegalArgumentException("Invalid date format. Expected format: DD-MM-YYYY_HH:MM:SS");
        }

    }

    @Override
    public String toString() {
        return String.format("{\"order_id\":\"%s\", \"user_id\":\"%s\", \"pro_id\":\"%s\", \"order_time\":\"%s\"}",
                orderId, userId, proId, orderTime);
    }
}
