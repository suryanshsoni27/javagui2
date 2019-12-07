import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

public class ManageUserUI {

    public JFrame view;

    public JButton btnLoad = new JButton( "Load User" );
    public JButton btnSave = new JButton( "Save User" );

    public JTextField txtUsernameID = new JTextField( 20 );
    public JTextField txtPasswordID = new JTextField( 20 );
    public JTextField txtFullnameID = new JTextField( 20 );
    public JTextField txtUsertypeID = new JTextField( 20 );
    public JTextField txtCustomerID = new JTextField( 20 );

    public ManageUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        view.setTitle( "Manage User Information" );
        view.setSize( 600, 400 );
        view.getContentPane().setLayout( new BoxLayout( view.getContentPane(), BoxLayout.PAGE_AXIS ) );

        JPanel panelButtons = new JPanel( new FlowLayout() );
        panelButtons.add( btnLoad );
        panelButtons.add( btnSave );
        view.getContentPane().add( panelButtons );



        JPanel line1 = new JPanel( new FlowLayout() );
        line1.add( new JLabel( "User name" ) );
        line1.add( txtUsernameID );
        view.getContentPane().add( line1 );

        JPanel line2 = new JPanel( new FlowLayout() );
        line2.add( new JLabel( "Password" ) );
        line2.add( txtPasswordID );
        view.getContentPane().add( line2 );

        JPanel line3 = new JPanel( new FlowLayout() );
        line3.add( new JLabel( "Full name" ) );
        line3.add( txtFullnameID );
        view.getContentPane().add( line3 );

        JPanel line4 = new JPanel( new FlowLayout() );
        line4.add( new JLabel( "User type" ) );
        line4.add( txtUsertypeID );
        view.getContentPane().add( line4 );

        JPanel line5 = new JPanel( new FlowLayout() );
        line5.add( new JLabel( "CustomerID" ) );
        line5.add( txtCustomerID);
        view.getContentPane().add( line5 );

        btnLoad.addActionListener( new LoadButtonListerner() );

        btnSave.addActionListener( new SaveButtonListener() );

    }

    public void run() {
        view.setVisible( true );
    }

    class LoadButtonListerner implements ActionListener {


        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String id = txtUsernameID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog( null, "User name cannot be null" );
                return;
            }

            try {
                user.mUsername = id;
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog( null, "username  is invalid!" );
                return;
            }

            // call data access!

            user = StoreManager.getInstance().getDataAdapter().loadUser( user.mUsername );

            if (user == null) {
                JOptionPane.showMessageDialog( null, "user NOT exists!" );
            } else {
                txtUsernameID.setText( user.mUsername );
                txtPasswordID.setText( user.mPassword );
                txtFullnameID.setText( user.mFullname );
                txtUsertypeID.setText( Integer.toString(user.mUserType));
                txtCustomerID.setText( Integer.toString(user.mCustomerID) );
            }
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String username = txtUsernameID.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog( null, "username cannot be null" );
                return;
            }

            try {
                user.mUsername = username;
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog( null, "user is invalid!" );
                return;
            }


            String name = txtFullnameID.getText();
            try {
                user.mFullname = name;
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog( null, "name is not present!" );
                return;
            }

            String password = txtPasswordID.getText();
            try {
                user.mPassword = password;
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog( null, "password is invalid!" );
                return;
            }

            String type = txtUsertypeID.getText();
            try {
                user.mUserType = Integer.parseInt(type );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog( null, "invalid!" );
                return;
            }

            String cus = txtCustomerID.getText();
            try {
                user.mCustomerID = Integer.parseInt( cus );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog( null, "invalid!" );
                return;
            }


            int res = StoreManager.getInstance().getDataAdapter().saveUser( user );

            if (res == IDataAdapter.USER_DUPLICATE_ERROR)
                JOptionPane.showMessageDialog( null, "User is SAVED successfully!" );
        }
    }
}
