package features;

public class Product {
    private String proId;
    private String proName;
    private String proCategory;
    private String proModel;
    private double proCurrentPrice;
    private double proRawPrice;
    private double proDiscount;
    private int proLikeCount;

    //Constructor
    public Product(String proId, String proName, String proCategory, String proModel,
                   double proCurrentPrice, double proRawPrice, double proDiscount, int proLikeCount) {
        this.proId = proId;
        this.proName = proName;
        this.proCategory = proCategory;
        this.proModel = proModel;
        this.proCurrentPrice = proCurrentPrice;
        this.proRawPrice = proRawPrice;
        this.proDiscount = proDiscount;
        this.proLikeCount = proLikeCount;
    }

    //Default Constructor
    public Product(){
        this.proId = "";
        this.proName = "";
        this.proCategory = "";
        this.proModel = "";
        this.proCurrentPrice = 0.0;
        this.proRawPrice = 0.0;
        this.proDiscount = 0.0;
        this.proLikeCount = 0;

    }


    //Setters
    public void setProId(String proId) {
        this.proId = proId;
    }
    public void setProName(String proName) {
        this.proName = proName;
    }
    public void setProCategory(String proCategory) {
        this.proCategory = proCategory;
    }
    public void setProModel(String proModel) {
        this.proModel = proModel;
    }
    public void setProCurrentPrice(double proCurrentPrice) {
        this.proCurrentPrice = proCurrentPrice;
    }
    public void setProRawPrice(double proRawPrice) {
        this.proRawPrice = proRawPrice;
    }
    public void setProDiscount(double proDiscount) {
        this.proDiscount = proDiscount;
    }
    public void setProLikeCount(int proLikeCount) {
        this.proLikeCount = proLikeCount;
    }

    
    //Getters
    public String getProId() {
        return proId;
    }
    public String getProName() {
        return proName;
    }
    public String getProCategory() {
        return proCategory;
    }
    public String getProModel() {
        return proModel;
    }
    public double getProCurrentPrice() {
        return proCurrentPrice;
    }
    public double getProRawPrice() {
        return proRawPrice;
    }
    public double getProDiscount() {
        return proDiscount;
    }
    public int getProLikeCount() {
        return proLikeCount;
    }

    @Override
    public String toString() {
        return String.format(
            "{\"pro_id\":\"%s\", \"pro_model\":\"%s\", \"pro_category\":\"%s\", " +
            "\"pro_name\":\"%s\", \"pro_current_price\":\"%.2f\", \"pro_raw_price\":\"%.2f\", " +
            "\"pro_discount\":\"%.2f\", \"pro_likes_count\":\"%d\"}",
            proId, proModel, proCategory, proName,
            proCurrentPrice, proRawPrice, proDiscount, proLikeCount
        );
    }
}
