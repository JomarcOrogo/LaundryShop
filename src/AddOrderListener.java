import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddOrderListener implements ActionListener {
    private LaundryShopSystem system;

    public AddOrderListener(LaundryShopSystem system) {
        this.system = system;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String customerName = system.getCustomerNameField().getText().trim();
        String contactNumber = system.getContactField().getText().trim(); // Get the contact number
        String packageType = (String) system.getPackageTypeDropdown().getSelectedItem();
        String weightText = system.getLaundryWeightField().getText().trim();

        if (customerName.isEmpty() || contactNumber.isEmpty() || weightText.isEmpty()) {
            JOptionPane.showMessageDialog(system.getFrame(), "Please fill out all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!LaundryShopSystem.isValidPhoneNumber(contactNumber)) {
            JOptionPane.showMessageDialog(system.getFrame(), "Please enter a valid Philippine contact number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            double price = PriceCalculator.calculatePrice(packageType, weight);
            LaundryOrder order = new LaundryOrder(customerName, contactNumber, packageType, weight, price); // Pass contactNumber here
            system.getLaundryQueue().add(order);
            system.updateQueueDisplay();
            system.getCustomerNameField().setText("");
            system.getLaundryWeightField().setText("");
            system.getContactField().setText(""); // Clear contact field
            JOptionPane.showMessageDialog(system.getFrame(), "Order added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(system.getFrame(), "Please enter a valid number for the weight.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


