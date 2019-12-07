import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {
    public JFrame view;

    public JButton btnAddPurchase = new JButton("Add New Purchase");
    public JButton btnupPurchase = new JButton("update Purchase");
    public JButton btnAddCustomer = new JButton("Add New Customer");
    public JButton btnupCustomer = new JButton("update Customer");
    public CashierUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(400, 300);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("CASHIER WORK");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddPurchase);
        panelButtons.add(btnupPurchase);
        panelButtons.add(btnAddCustomer);
        panelButtons.add(btnupCustomer);

        view.getContentPane().add(panelButtons);


        btnAddPurchase.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ap = new AddPurchaseUI();
                ap.run();
            }
        });

        btnupPurchase.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI ap = new ManagePurchaseUI();
                ap.run();
            }
        });

        btnAddCustomer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                AddCustomerUI ap = new AddCustomerUI();
                ap.run();
            }
        });

        btnupCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ap = new ManageCustomerUI();
                ap.run();
            }
        });

    }
}