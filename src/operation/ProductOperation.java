package operation;

import java.util.List;

import features.Product;

public class ProductOperation{
    private static ProductOperation instance;

    public static ProductOperation getInstance(){
        if(instance == null){
            instance = new ProductOperation();
        }
        return instance;
    }

    /**
    * Extracts product information from the given product data files.
    * The data is saved into the data/products.txt file.
    */
    public void extractProductsFromFiles() {
    // Implementation
    }

    /**
    * Retrieves one page of products from the database.
    * One page contains a maximum of 10 items.
    * @param pageNumber The page number to retrieve
    * @return A list of Product objects, current page number, and total pages
    */
    public ProductListResult getProductList(int pageNumber) {
    // Implementation
    }

    /**
    * Deletes the product from the system based on the provided product_id.* @param productId The ID of the product to delete
    * @return true if successful, false otherwise
    */
    public boolean deleteProduct(String productId) {
    // Implementation
    }

    /**
    * Retrieves all products whose name contains the keyword (case insensitive).
    * @param keyword The search keyword
    * @return A list of Product objects matching the keyword
    */
    public List<Product> getProductListByKeyword(String keyword) {
    // Implementation
    }


    /**
    * Returns one product object based on the given product_id.
    * @param productId The ID of the product to retrieve
    * @return A Product object or null if not found
    */
    public Product getProductById(String productId) {
    // Implementation
    }

    /**
    * Generates a bar chart showing the total number of products
    * for each category in descending order.
    * Saves the figure into the data/figure folder.
    */
    public void generateCategoryFigure() {
    // Implementation using Java charting library
    }

    /**
    * Generates a pie chart showing the proportion of products that have
    * a discount value less than 30, between 30 and 60 inclusive,
    * and greater than 60.
    * Saves the figure into the data/figure folder.
    */
    public void generateDiscountFigure() {
    // Implementation using Java charting library
    }

    /**
    * Generates a chart displaying the sum of products' likes_count
    * for each category in ascending order.
    * Saves the figure into the data/figure folder.
    */
    public void generateLikesCountFigure() {
    // Implementation using Java charting library
    }

    /**
    * Generates a scatter chart showing the relationship between
    * likes_count and discount for all products.
    * Saves the figure into the data/figure folder.
    */
    public void generateDiscountLikesCountFigure() {
    // Implementation using Java charting library
    }

    /**
    * Removes all product data in the data/products.txt file.
    */
    public void deleteAllProducts() {
    // Implementation
    }


}

/*
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
*/