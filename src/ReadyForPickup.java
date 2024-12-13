import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
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

        String[] columnNames = {"Select", "Name", "Package", "Weight", "Price"};
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

        JButton sortButton = new JButton("Sort by Price");
        sortButton.addActionListener(e -> sortTableByColumn(4));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sortButton);

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
        tableModel.addRow(row);
    }

    private void sortTableByColumn(int columnIndex) {
        rowSorter.toggleSortOrder(columnIndex);
    }
}
