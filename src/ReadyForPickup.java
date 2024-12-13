import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadyForPickup {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ArrayList<LaundryOrder> orders;

    public ReadyForPickup() {
        orders = new ArrayList<>(); // Initialize orders list
        frame = new JFrame("Ready for Pickup");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        String[] columnNames = {"Select", "Name", "Package", "Weight", "Price", "Picked Up At"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> showSortOptions());

        JButton pickUpButton = new JButton("Mark as Picked Up");
        pickUpButton.addActionListener(e -> markAsPickedUp());

        JButton removeButton = new JButton("Remove Order");
        removeButton.addActionListener(e -> removeOrder());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sortButton);
        bottomPanel.add(pickUpButton);
        bottomPanel.add(removeButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void addOrder(LaundryOrder order) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(false);  // Select checkbox
        row.add(order.getCustomerName());  // Name
        row.add(order.getPackageType());  // Package Type
        row.add(order.getWeight());  // Weight
        row.add(String.format("₱%.2f", order.getPrice()));  // Price
        row.add("");  // Column for "Picked Up At" (initially empty)
        tableModel.addRow(row.toArray()); // Convert ArrayList to array and add to table model
        orders.add(order);  // Add the order to the list
    }

    private void markAsPickedUp() {
        int rowIndex = table.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to mark as picked up.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure this order has been picked up?", "Confirm Pickup", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Get the LaundryOrder from the table
        LaundryOrder completedOrder = orders.get(rowIndex);

        // Add current date and time to "Picked Up At" column
        String pickedUpAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tableModel.setValueAt(pickedUpAt, rowIndex, 5);  // Set the "Picked Up At" column value

        // Notify LaundryShopSystem to remove the order from the relevant queues
        LaundryShopSystem system = new LaundryShopSystem(); // Ensure you have access to the main system
        system.removeOrderFromQueues(completedOrder); // Custom method to remove the order from the queues

        JOptionPane.showMessageDialog(frame, "Order marked as picked up.", "Order Picked Up", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeOrder() {
        int rowIndex = table.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this order?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Get the LaundryOrder from the table
        LaundryOrder orderToRemove = orders.get(rowIndex);

        // Remove the order from the orders list and the table model
        orders.remove(orderToRemove);
        tableModel.removeRow(rowIndex);

        // Explicitly refresh the table
        ((DefaultTableModel) table.getModel()).fireTableDataChanged();

        JOptionPane.showMessageDialog(frame, "Order removed from the list.", "Order Removed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSortOptions() {
        String[] sortOptions = {"Sort by Package Type", "Sort by Client Name", "Sort by Price"};
        String selectedOption = (String) JOptionPane.showInputDialog(frame, "Choose sorting option:", "Sort Orders", JOptionPane.QUESTION_MESSAGE, null, sortOptions, sortOptions[0]);

        if (selectedOption == null) return;

        switch (selectedOption) {
            case "Sort by Package Type":
                sortTableByColumn(2); // Package Type column
                break;
            case "Sort by Client Name":
                sortTableByColumn(1); // Client Name column
                break;
            case "Sort by Price":
                sortTableByColumn(4); // Price column
                break;
        }
    }

    private void sortTableByColumn(int columnIndex) {
        rowSorter.toggleSortOrder(columnIndex);
    }
}
