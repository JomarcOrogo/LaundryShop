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

    public ReadyForPickup() {
        frame = new JFrame("Ready for Pickup");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        String[] columnNames = {"Select", "Name", "Package", "Weight", "Price", "Picked Up At"}; // Ensure 6 columns
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

        JButton pickUpButton = new JButton("Picked Up");
        pickUpButton.addActionListener(e -> markAsPickedUp());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sortButton);
        bottomPanel.add(pickUpButton);

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

        String pickedUpTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tableModel.setValueAt(pickedUpTime, rowIndex, 5); // Update the "Picked Up At" column (index 5)

        JOptionPane.showMessageDialog(frame, "Order marked as picked up.", "Order Picked Up", JOptionPane.INFORMATION_MESSAGE);

        tableModel.removeRow(rowIndex);
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
