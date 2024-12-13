public class LaundryOrder {
    private String customerName;
    private String packageType;
    private double weight;

    public LaundryOrder(String customerName, String packageType, double weight) {
        this.customerName = customerName;
        this.packageType = packageType;
        this.weight = weight;
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

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Package: " + packageType + ", Weight: " + weight + " kg";
    }
}
