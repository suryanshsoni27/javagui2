import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI {

    public UserModel user = null;

    public JFrame view;

    public JButton btnMakePurchase = new JButton("Make a Purchase");
    public JButton btnProduct = new JButton("Product history");
    public JButton btnViewPurchases = new JButton("View Purchase History");

    public CustomerUI(final UserModel user) {

        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Customer");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Fullname: " + user.mFullname));
        panelUser.add(new JLabel("CustomerID: " + user.mCustomerID));

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnMakePurchase);
        panelButtons.add(btnViewPurchases);
        panelButtons.add(btnProduct);

        view.getContentPane().add(panelButtons);


        btnViewPurchases.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                PurchaseHistoryUI ui = new PurchaseHistoryUI(user);
                ui.view.setVisible(true);
            }
        });

        btnProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                SummaryUI ui = new SummaryUI();
                ui.view.setVisible(true);
            }
        });

        btnMakePurchase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ui = new AddPurchaseUI();
                ui.run();
            }
        });

        //design ui

    }
}