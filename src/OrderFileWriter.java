import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderFileWriter {
    private static final String FOLDER_PATH = "PickUpOrders"; // Folder where the file will be saved
    private static final String FILE_PATH = FOLDER_PATH + "/orders.txt"; // File path within the folder

    public static void writeOrderDetails(int orderNumber, String customerName, String packageType, double weight, String price, String pickedUpAt) {
        // Ensure the folder exists, create it if it doesn't
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdir(); // Create the folder if it doesn't exist
        }

        String orderDetails = String.format("Order No: %d, Customer: %s, Package: %s, Weight: %.2fkg, Price: %s, Picked Up At: %s",
                orderNumber, customerName, packageType, weight, price, pickedUpAt);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(orderDetails);
            writer.newLine();  // Add a new line after each order
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
