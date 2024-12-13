public class LaundryOrder {
    private String customerName;
    private String contactNumber;  // Add a field for contact number
    private String packageType;
    private double weight;
    private double price;

    public LaundryOrder(String customerName, String contactNumber, String packageType, double weight, double price) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;  // Store the contact number
        this.packageType = packageType;
        this.weight = weight;
        this.price = price;
    }

    // Add getters and setters if needed
    public String getCustomerName() {
        return customerName;
    }

    public String getContactNumber() {
        return contactNumber;  // Getter for contact number
    }

    public String getPackageType() {
        return packageType;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Contact: " + contactNumber + ", Package: " + packageType +
                ", Weight: " + weight + "kg, Price: ₱" + price;
    }
}
