package operation;
import features.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

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
    * The data is saved into the database/products.txt file.
    */
    public void extractProductsFromFiles() {
        File sourceFile = new File("ecommerce-system-cli/raw_data/raw_products.txt");
        File targetFile = new File("ecommerce-system-cli/database/products.txt");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false)) // overwrite
        ) {
            String line;
            Pattern pattern = Pattern.compile("\"(.*?)\":(?:\"(.*?)\"|(\\d+(?:\\.\\d+)?))");

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                Product product = new Product();
                Matcher matcher = pattern.matcher(line);
                boolean valid = true;

                while (matcher.find()) {
                    String key = matcher.group(1);
                    String value = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);

                    try {
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
                            default:
                                break;
                        }
                    } catch (NumberFormatException e) {
                        valid = false;
                        break;
                    }
                }

                // Check for completeness of required fields before writing
                if (valid &&
                    product.getProId() != null && product.getProModel() != null && product.getProCategory() != null && product.getProName() != null && product.getProCurrentPrice() >= 0 && product.getProRawPrice() >= 0 && product.getProDiscount() >= 0 && product.getProLikeCount() >= 0) {
                    writer.write(product.toString());
                    writer.newLine();
                }
            }

        } catch (IOException ignored) {
            // silently ignore as required
        }
    }


    //Helper extract of order_id, user_id, pro_id, order_time
    public String extractValue(String dataline, String key) {
        String searchKey = "\"" + key + "\":";
        int start = dataline.indexOf(searchKey);
        if (start == -1) return null;

        start += searchKey.length();
        char firstChar = dataline.charAt(start);

        // Handle quoted string value
        if (firstChar == '"') {
            start++;
            int end = dataline.indexOf('"', start);
            if (end == -1) return null;
            return dataline.substring(start, end);
        } else {
            // Handle unquoted numeric value
            int end = start;
            while (end < dataline.length() && (Character.isDigit(dataline.charAt(end)) || dataline.charAt(end) == '.')) {
                end++;
            }
            return dataline.substring(start, end);
        }
    }


    /**
    * Retrieves one page of products from the database.
    * One page contains a maximum of 10 items.
    * @param pageNumber The page number to retrieve
    * @return A list of Product objects, current page number, and total pages
    */

    public ProductListResult getProductList(int pageNumber) {
        List<Product> allProductList = new ArrayList<>();
        File inputFile = new File("database\\products.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String proId = extractValue(line, "pro_id");
                String proModel = extractValue(line, "pro_model");
                String proCategory = extractValue(line, "pro_category");
                String proName = extractValue(line, "pro_name");
                String proCurrentPriceStr = extractValue(line, "pro_current_price");
                String proRawPriceStr = extractValue(line, "pro_raw_price");
                String proDiscountStr = extractValue(line, "pro_discount");
                String proLikeCountStr = extractValue(line, "pro_likes_count");

                // Block execution if any field is missing
                if (proId == null || proModel == null || proCategory == null || proName == null ||
                    proCurrentPriceStr == null || proRawPriceStr == null ||
                    proDiscountStr == null || proLikeCountStr == null) {
                    throw new IllegalStateException("Missing required product field in line: " + line);
                }

                double proCurrentPrice = Double.parseDouble(proCurrentPriceStr);
                double proRawPrice = Double.parseDouble(proRawPriceStr);
                double proDiscount = Double.parseDouble(proDiscountStr);
                int proLikeCount = Integer.parseInt(proLikeCountStr);

                Product product = new Product(proId, proName, proCategory, proModel,
                                            proCurrentPrice, proRawPrice, proDiscount, proLikeCount);

                allProductList.add(product);
            }
        } catch (IOException e){
            e.printStackTrace();
            return new ProductListResult(new ArrayList<>(), pageNumber, 0);
        }

        int totalProducts = allProductList.size();
        int pageSize = 10;
        int totalPages = (int) Math.ceil(totalProducts / 10.0);

        if (pageNumber < 1 || pageNumber > totalPages) {
            return new ProductListResult(new ArrayList<>(), pageNumber, totalPages);
        }

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalProducts);
        List<Product> pageProducts = allProductList.subList(start, end);

        return new ProductListResult(pageProducts, pageNumber, totalPages);
    }   

    /**
    * Deletes the product from the system based on the provided product_id.* @param productId The ID of the product to delete
    * @return true if successful, false otherwise
    */
    public boolean deleteProduct(String productId) {
        File inputFile = new File("ecommerce-system-cli/database/products.txt");
        File tempFile = new File("ecommerce-system-cli/database/backup_products.txt");

        boolean deleted = false;

        try(
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ){
            String currentLine;

            while((currentLine = reader.readLine()) != null){
                String extractedId = extractProductId(currentLine);
                if(productId.equals(extractedId)){
                    deleted = true;
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
        if(deleted){
            if(!inputFile.delete() || !tempFile.renameTo(inputFile)){
                return false;
            }
        } else{
            tempFile.delete();
        }
        return deleted;

    }

    //Helper extract proId
    public String extractProductId(String dataline){
        String key = "\"pro_id\":\"";
        int start = dataline.indexOf(key);
        if(start == -1){
            return null;
        }
        start += key.length();
        int end = dataline.indexOf("\"", start);
        if(end == -1){
            return null;
        }
        return dataline.substring(start, end);
    }



    /**
    * Retrieves all products whose name contains the keyword (case insensitive).
    * @param keyword The search keyword
    * @return A list of Product objects matching the keyword
    */
    public List<Product> getProductListByKeyword(String keyword) {
        List<Product> matching_products = new ArrayList<>();
        File inputFile = new File("ecommerce-system-cli/database/products.txt");

        //trim() : remove all whitespaces
        if(keyword == null || keyword.trim().isEmpty()){
            return matching_products;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String currentLine;

            while((currentLine = reader.readLine()) != null){
                currentLine = currentLine.replace("\n", "").replaceAll("\r", "");

                String proId = extractValue(currentLine, "pro_id");
                String proModel = extractValue(currentLine, "pro_model");
                String proCategory = extractValue(currentLine, "pro_category");
                String proName = extractValue(currentLine, "pro_name");
                String proCurrentPriceStr = extractValue(currentLine, "pro_current_price");
                String proRawPriceStr = extractValue(currentLine, "pro_raw_price");
                String proDiscountStr = extractValue(currentLine, "pro_discount");
                String proLikeCountStr = extractValue(currentLine, "pro_likes_count");

                //Skip if any value is missing
                if (proId == null || proModel == null || proCategory == null || proName == null || proCurrentPriceStr == null || proRawPriceStr == null || proDiscountStr == null || proLikeCountStr == null) {
                    continue;
                }

                if(proName.toLowerCase().contains(keyword.toLowerCase())){
                    //Convert from string to appropriate numeric datatypes
                    double proCurrentPrice = Double.parseDouble(proCurrentPriceStr);
                    double proRawPrice = Double.parseDouble(proRawPriceStr);
                    double proDiscount = Double.parseDouble(proDiscountStr);
                    int proLikeCount = Integer.parseInt(proLikeCountStr);

                    Product product = new Product(proId, proName, proCategory, proModel, proCurrentPrice, proRawPrice, proDiscount, proLikeCount);
                    matching_products.add(product);

                }   

            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return matching_products;
    }   


    /**
    * Returns one product object based on the given product_id.
    * @param productId The ID of the product to retrieve
    * @return A Product object or null if not found
    */
    public Product getProductById(String productId) {

        File inputFile = new File("ecommerce-system-cli/database/products.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                currentLine = currentLine.replace("\n", "").replaceAll("\r", "");

                String proId = extractValue(currentLine, "pro_id");
                if (productId == null || !productId.equals(proId)) {
                    continue;
                }

                String proModel = extractValue(currentLine, "pro_model");
                String proCategory = extractValue(currentLine, "pro_category");
                String proName = extractValue(currentLine, "pro_name");
                String proCurrentPriceStr = extractValue(currentLine, "pro_current_price");
                String proRawPriceStr = extractValue(currentLine, "pro_raw_price");
                String proDiscountStr = extractValue(currentLine, "pro_discount");
                String proLikeCountStr = extractValue(currentLine, "pro_likes_count");

                //Skip if any value is missing
                if (proId == null || proModel == null || proCategory == null || proName == null || proCurrentPriceStr == null || proRawPriceStr == null || proDiscountStr == null || proLikeCountStr == null) {
                    continue;
                }
                
                //Convert from string to appropriate numeric datatypes
                double proCurrentPrice = Double.parseDouble(proCurrentPriceStr);
                double proRawPrice = Double.parseDouble(proRawPriceStr);
                double proDiscount = Double.parseDouble(proDiscountStr);
                int proLikeCount = Integer.parseInt(proLikeCountStr);

                return new Product(proId, proName, proCategory, proModel, proCurrentPrice, proRawPrice, proDiscount, proLikeCount);

                }

            }
         catch(IOException e){
            e.printStackTrace();
        }
        return null;
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
        File productFile = new File("database\\products.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(productFile))){
            //Clear the file
        } catch(IOException e){
            e.printStackTrace();
        }    
    }


}
