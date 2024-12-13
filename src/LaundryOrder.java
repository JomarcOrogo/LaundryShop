public class LaundryOrder {
    private String customerName;
    private String packageType;
    private double weight;
    private double price;

    public LaundryOrder(String customerName, String packageType, double weight, double price) {
        this.customerName = customerName;
        this.packageType = packageType;
        this.weight = weight;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
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
        return "Customer: " + customerName + ", Package: " + packageType + ", Weight: " + weight + " kg, Price: ₱" + price;
    }
}
