import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteOrderListener implements ActionListener {
    private LaundryShopSystem system;

    public CompleteOrderListener(LaundryShopSystem system) {
        this.system = system;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (system.getLaundryQueue().isEmpty()) {
            JOptionPane.showMessageDialog(system.getFrame(), "No orders in the queue.", "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
        } else {
            LaundryOrder completedOrder = system.getLaundryQueue().poll();
            system.getReadyForPickupQueue().add(completedOrder);
            system.updateQueueDisplay();
            JOptionPane.showMessageDialog(system.getFrame(), "Order completed for: " + completedOrder.getCustomerName(), "Order Completed", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
