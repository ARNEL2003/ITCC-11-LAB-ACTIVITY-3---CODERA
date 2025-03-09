package src;

public class Customer {
    private int customerID;
    private String fullName;

    public Customer(int customerID, String fullName) {
        this.customerID = customerID;
        this.fullName = fullName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getFullName() {
        return fullName;
    }
}
