package operation;

import java.util.List;

public class CustomerListResult {
    private List<String> customers;
    private int currentPage;
    private int totalPages;

    public CustomerListResult(List<String> customers, int currentPage, int totalPages){
        this.customers = customers;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<String> getCustomers(){
        return customers;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public int getTotalPages(){
        return totalPages;
    }   
}
