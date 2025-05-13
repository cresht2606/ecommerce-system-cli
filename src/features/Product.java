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

    public String getProId() { return proId; }
    public String getProName() { return proName; }
    public String getProCategory() { return proCategory; }
    public String getProModel() { return proModel; }
    public double getProCurrentPrice() { return proCurrentPrice; }

    @Override
    public String toString() {
        return String.format("{\"proId\":\"%s\", \"name\":\"%s\", \"price\":%.2f, \"like\":%d}",
                proId, proName, proCurrentPrice, proLikeCount);
    }
}
