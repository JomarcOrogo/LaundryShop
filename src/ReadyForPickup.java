import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

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
        Vector<Object> row = new Vector<>();
        row.add(false);
        row.add(order.getCustomerName());
        row.add(order.getPackageType());
        row.add(order.getWeight());
        row.add(String.format("₱%.2f", order.getPrice()));
        row.add("");  // Column for Picked Up At time (initially empty)
        tableModel.addRow(row);
    }

    private void markAsPickedUp() {
        int rowIndex = table.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to mark as picked up.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm with the user before marking the order as picked up
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure this order has been picked up?", "Confirm Pickup", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Get the current time and format it
        String pickedUpTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tableModel.setValueAt(pickedUpTime, rowIndex, 5); // Update the "Picked Up At" column

        // Notify the user
        JOptionPane.showMessageDialog(frame, "Order marked as picked up.", "Order Picked Up", JOptionPane.INFORMATION_MESSAGE);

        // Remove the order from the list
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
