import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.Statement;

public class NetworkDataAdapter implements IDataAdapter {

    String host = "localhost";
    int port = 1000;

    Gson gson = new Gson();
    SocketNetworkAdapter adapter = new SocketNetworkAdapter();
    MessageModel msg = new MessageModel();


    public int connect(String dbfile) {
        int pos = dbfile.indexOf( ":" );
        host = dbfile.substring( 0, pos );
        port = Integer.parseInt( dbfile.substring( pos + 1, dbfile.length() ) );
        return 0;
    }


    public int disconnect() {
        return 0;
    }


    public ProductModel loadProduct(int id) {
        msg.code = MessageModel.GET_PRODUCT;
        msg.data = Integer.toString( id );
        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, ProductModel.class );
        }
    }


    public int saveProduct(ProductModel model) {
        msg.code = MessageModel.PUT_PRODUCT;
        msg.data = gson.toJson( model );

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.PRODUCT_DUPLICATE_ERROR;
        else {
            return IDataAdapter.PRODUCT_SAVED_OK;
        }
    }


    public CustomerModel loadCustomer(int id) {
        msg.code = MessageModel.GET_CUSTOMER;
        msg.data = Integer.toString( id );
        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, CustomerModel.class );
        }
    }


    public int saveCustomer(CustomerModel model) {
        msg.code = MessageModel.PUT_CUSTOMER;
        msg.data = gson.toJson( model );

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.CUSTOMER_DUPLICATE_ERROR;
        else {
            return IDataAdapter.CUSTOMER_SAVED_OK;
        }
    }


    public PurchaseModel loadPurchase(int id) {
        msg.code = MessageModel.GET_PURCHASE;
        msg.data = Integer.toString( id );
        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, PurchaseModel.class );
        }
    }


    public int savePurchase(PurchaseModel model) {
        msg.code = MessageModel.PUT_PURCHASE;
        msg.data = gson.toJson( model );

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.PURCHASE_DUPLICATE_ERROR;
        else {
            return IDataAdapter.PURCHASE_SAVED_OK;
        }
    }


    public UserModel loadUser(String id) {
        msg.code = MessageModel.GET_USER;
        msg.data = id;
        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, UserModel.class );
        }

    }


    public int saveUser(UserModel model) {
        msg.code = MessageModel.PUT_USER;
        msg.data = gson.toJson( model );

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.USER_DUPLICATE_ERROR;
        else {
            return IDataAdapter.USER_SAVED_OK;
        }

    }


    public PurchaseListModel loadPurchaseHistory(int customerID) {
        msg.code = MessageModel.GET_PURCHASE_LIST;
        msg.data = Integer.toString( customerID );

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, PurchaseListModel.class );
        }
    }


    public SummaryListModel loadSummaryProduct() {
        msg.code = MessageModel.GET_PRODUCT_LIST;

        try {
            msg = adapter.exchange( msg, host, port );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson( msg.data, SummaryListModel.class );
        }


    }
}
