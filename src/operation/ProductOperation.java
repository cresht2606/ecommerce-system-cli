package operation;

import features.Product;
import java.util.*;

public class ProductOperation {
    private static ProductOperation instance;
    private List<Product> products;

    private ProductOperation() {
        products = new ArrayList<>();
    }

    public static ProductOperation getInstance() {
        if (instance == null) {
            instance = new ProductOperation();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String proId) {
        for (Product p : products) {
            if (p.toString().contains(proId)) {
                return p;
            }
        }
        return null;
    }
}