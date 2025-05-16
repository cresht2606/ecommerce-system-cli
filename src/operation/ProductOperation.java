package operation;
import features.Product;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BufferedReader;
import java.util.BufferedWriter;

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
        File sourceFile = new File("ecommerce-system-cli/raw_data/raw_products.txt");  // arbitrary file name
        File targetFile = new File("ecommerce-system-cli/database/products.txt");  // where we store products

        try (
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))  // overwrite mode
        ) {
            String line;
            
            // Regex pattern to extract key-value pairs from the JSON-like format
            Pattern pattern = Pattern.compile("\"(.*?)\":\"(.*?)\"");

            while ((line = reader.readLine()) != null) {
                line = line.trim();  // Remove any unnecessary spaces or newline characters
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Initialize a temporary product object
                Product product = new Product();

                // Match all key-value pairs in the line
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String key = matcher.group(1);
                    String value = matcher.group(2);

                    // Populate the Product object based on the key-value pairs
                    switch (key) {
                        case "pro_id":
                            product.setProId(value);
                            break;
                        case "pro_model":
                            product.setProModel(value);
                            break;
                        case "pro_category":
                            product.setProCategory(value);
                            break;
                        case "pro_name":
                            product.setProName(value);
                            break;
                        case "pro_current_price":
                            product.setProCurrentPrice(Double.parseDouble(value));
                            break;
                        case "pro_raw_price":
                            product.setProRawPrice(Double.parseDouble(value));
                            break;
                        case "pro_discount":
                            product.setProDiscount(Double.parseDouble(value));
                            break;
                        case "pro_likes_count":
                            product.setProLikeCount(Integer.parseInt(value));
                            break;
                    }
                }

                // Write the Product's string representation to the target file
                writer.write(product.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
    }
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
