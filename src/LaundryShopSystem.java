import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LaundryShopSystem {
    private JFrame frame;
    private JTextField customerNameField;
    private JTextField laundryWeightField;
    private JComboBox<String> packageTypeDropdown;
    private JTextArea queueDisplay;
    private Queue<LaundryOrder> laundryQueue;
    private Queue<LaundryOrder> readyForPickupQueue;
    private JTextField contactField;  // New field for contact number

    public LaundryShopSystem() {
        laundryQueue = new LinkedList<>();
        readyForPickupQueue = new LinkedList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Laundry Shop System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Adjusted the layout to accommodate the new row
        topPanel.setBorder(BorderFactory.createTitledBorder("Add Laundry Order"));

        JLabel nameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField();

        JLabel contactLabel = new JLabel("Contact Number:"); // New label for contact number
        contactField = new JTextField(); // New text field for contact number

        JLabel packageLabel = new JLabel("Package Type:");
        packageTypeDropdown = new JComboBox<>(new String[]{"Basic Package", "Premium Package"});

        JLabel weightLabel = new JLabel("Laundry Weight (kg):");
        laundryWeightField = new JTextField();

        JButton addOrderButton = new JButton("Add Order");
        addOrderButton.addActionListener(new AddOrderListener(this));

        topPanel.add(nameLabel);
        topPanel.add(customerNameField);
        topPanel.add(contactLabel); // Add contact label
        topPanel.add(contactField); // Add contact field
        topPanel.add(packageLabel);
        topPanel.add(packageTypeDropdown);
        topPanel.add(weightLabel);
        topPanel.add(laundryWeightField);
        topPanel.add(addOrderButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Order Queue"));
        queueDisplay = new JTextArea();
        queueDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(queueDisplay);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel legendPanel = new JPanel(new GridLayout(2, 1));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Package Legend"));
        JLabel basicPackageLabel = new JLabel("Basic Package: Wash, Hang Dry, Fabric Softener, 7kg base weight (₱150), ₱15/kg extra");
        JLabel premiumPackageLabel = new JLabel("Premium Package: Wash, Air Dry, Fabric Softener, 9kg base weight (₱200), ₱15/kg extra");
        legendPanel.add(basicPackageLabel);
        legendPanel.add(premiumPackageLabel);

        JPanel actionPanel = new JPanel();
        JButton completeOrderButton = new JButton("Complete Order");
        completeOrderButton.addActionListener(new CompleteOrderListener(this));
        JButton readyForPickupButton = new JButton("Ready for Pickup");
        readyForPickupButton.addActionListener(e -> {
            ReadyForPickup window = new ReadyForPickup();
            for (LaundryOrder order : readyForPickupQueue) {
                window.addOrder(order);
            }
            window.showWindow();
        });

        actionPanel.add(completeOrderButton);
        actionPanel.add(readyForPickupButton);

        bottomPanel.add(legendPanel);
        bottomPanel.add(actionPanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public JTextField getContactField() {
        return contactField; // Getter method for contact field
    }

    public void updateQueueDisplay() {
        StringBuilder display = new StringBuilder();
        for (LaundryOrder order : laundryQueue) {
            display.append(order).append("\n");
        }
        queueDisplay.setText(display.toString());
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getLaundryWeightField() {
        return laundryWeightField;
    }

    public JComboBox<String> getPackageTypeDropdown() {
        return packageTypeDropdown;
    }

    public Queue<LaundryOrder> getLaundryQueue() {
        return laundryQueue;
    }

    public Queue<LaundryOrder> getReadyForPickupQueue() {
        return readyForPickupQueue;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Regex for Philippine phone numbers (local or international)
        return phoneNumber.matches("^((\\+63)|(0))9[0-9]{9}$");
    }

    public static void main(String[] args) {
        new LaundryShopSystem();
    }
}

