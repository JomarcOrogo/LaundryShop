import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class ReadyForPickupPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public ReadyForPickupPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "Order No.", "Name", "Package", "Weight", "Price", "Picked Up At"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Select column will use Boolean type
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only the Select column is editable (checkboxes)
            }
        };

        table = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        // Set the "Select" column as checkboxes
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addOrder(LaundryOrder order, int orderNumber) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(false);  // Default value for checkbox (unchecked)
        row.add(orderNumber);  // Order number column
        row.add(order.getCustomerName());
        row.add(order.getPackageType());
        row.add(order.getWeight());
        row.add(String.format("₱%.2f", order.getPrice()));
        row.add("");  // Column for Picked Up At time (initially empty)
        tableModel.addRow(row.toArray());  // Use toArray() to convert ArrayList to Object[]
    }

    public void sortTableByColumn(int columnIndex) {
        rowSorter.toggleSortOrder(columnIndex);
    }

    public JTable getTable() {
        return table;
    }
}
