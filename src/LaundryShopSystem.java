import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class LaundryShopSystem {
    private JFrame frame;
    private JTextField customerNameField;
    private JTextField laundryWeightField;
    private JComboBox<String> packageTypeDropdown;
    private JTextArea queueDisplay;
    private Queue<LaundryOrder> laundryQueue;

    public LaundryShopSystem() {
        laundryQueue = new LinkedList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Laundry Shop System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Center GUI on the screen
        frame.setLocationRelativeTo(null);

        // Top panel for adding orders
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Add Laundry Order"));
        JLabel nameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField();
        JLabel packageLabel = new JLabel("Package Type:");
        packageTypeDropdown = new JComboBox<>(new String[]{"Basic Package", "Premium Package"});
        JLabel weightLabel = new JLabel("Laundry Weight (kg):");
        laundryWeightField = new JTextField();

        JButton addOrderButton = new JButton("Add Order");
        addOrderButton.addActionListener(new AddOrderListener(this));

        topPanel.add(nameLabel);
        topPanel.add(customerNameField);
        topPanel.add(packageLabel);
        topPanel.add(packageTypeDropdown);
        topPanel.add(weightLabel);
        topPanel.add(laundryWeightField);
        topPanel.add(addOrderButton);

        // Center panel for displaying queue
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Order Queue"));
        queueDisplay = new JTextArea();
        queueDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(queueDisplay);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for dequeue action and legend
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        // Legend for package types
        JPanel legendPanel = new JPanel(new GridLayout(2, 1));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Package Legend"));
        JLabel basicPackageLabel = new JLabel("Basic Package: Wash, Hang Dry, Fabric Softener");
        JLabel premiumPackageLabel = new JLabel("Premium Package: Wash, Air Dry, Fabric Softener");
        legendPanel.add(basicPackageLabel);
        legendPanel.add(premiumPackageLabel);

        // Dequeue action
        JPanel actionPanel = new JPanel();
        JButton completeOrderButton = new JButton("Complete Order");
        completeOrderButton.addActionListener(new CompleteOrderListener(this));
        actionPanel.add(completeOrderButton);

        bottomPanel.add(legendPanel);
        bottomPanel.add(actionPanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
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

    public static void main(String[] args) {
        new LaundryShopSystem();
    }
}
