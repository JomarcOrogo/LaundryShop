import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadyForPickup {
    private JFrame frame;
    private ReadyForPickupPanel panel;
    private int orderNumber;

    public ReadyForPickup() {
        frame = new JFrame("Ready for Pickup");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(650, 650);
        frame.setLocationRelativeTo(null);

        panel = new ReadyForPickupPanel();
        frame.add(panel, BorderLayout.CENTER);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> showSortOptions());

        JButton pickUpButton = new JButton("Picked Up");
        pickUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsPickedUp();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sortButton);
        bottomPanel.add(pickUpButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        orderNumber = 1; // Initialize order number
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void addOrder(LaundryOrder order) {
        panel.addOrder(order, orderNumber++);
    }

    private void markAsPickedUp() {
        int rowIndex = panel.getTable().getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to mark as picked up.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current time and format it
        String pickedUpTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        panel.getTable().setValueAt(pickedUpTime, rowIndex, 6); // Update the "Picked Up At" column

        // Get the order details
        int orderNumber = (int) panel.getTable().getValueAt(rowIndex, 1);  // Order No.
        String customerName = (String) panel.getTable().getValueAt(rowIndex, 2);  // Customer Name
        String packageType = (String) panel.getTable().getValueAt(rowIndex, 3);  // Package Type
        double weight = (double) panel.getTable().getValueAt(rowIndex, 4);  // Weight
        String price = (String) panel.getTable().getValueAt(rowIndex, 5);  // Price
        String pickedUpAt = (String) panel.getTable().getValueAt(rowIndex, 6);  // Picked Up At

        // Write the order details to the file
        OrderFileWriter.writeOrderDetails(orderNumber, customerName, packageType, weight, price, pickedUpAt);

        JOptionPane.showMessageDialog(frame, "Order marked as picked up and saved to file.", "Order Picked Up", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSortOptions() {
        String[] sortOptions = {"Sort by Package Type", "Sort by Client Name", "Sort by Price", "Sort by Order Number"};
        String selectedOption = (String) JOptionPane.showInputDialog(
                frame,
                "Choose sorting option:",
                "Sort Orders",
                JOptionPane.QUESTION_MESSAGE,
                null,
                sortOptions,
                sortOptions[0]
        );

        if (selectedOption == null) return;

        switch (selectedOption) {
            case "Sort by Package Type":
                panel.sortTableByColumn(3); // Package Type column
                break;
            case "Sort by Client Name":
                panel.sortTableByColumn(2); // Client Name column
                break;
            case "Sort by Price":
                panel.sortTableByColumn(5); // Price column
                break;
            case "Sort by Order Number":
                panel.sortTableByColumn(1); // Order Number column
                break;
        }
    }
}
