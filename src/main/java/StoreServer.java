import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "C:\\Users\\hsoni\\Desktop\\comp3700\\Project1_data.db";

    public static void main(String[] args) {

        HashMap<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();

        int totalActiveUsers = 0;

        int port = 1000;

        if (args.length > 0) {
            System.out.println( "Running arguments: " );
            for (String arg : args)
                System.out.println( arg );
            port = Integer.parseInt( args[0] );
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect( dbfile );

            ServerSocket server = new ServerSocket(port);

            System.out.println( "Server is listening at port = " + port );

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter( pipe.getOutputStream(), true );
                Scanner in = new Scanner( pipe.getInputStream() );

                MessageModel msg = gson.fromJson( in.nextLine(), MessageModel.class );

                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println( "GET product with id = " + msg.data );
                    ProductModel p = adapter.loadProduct( Integer.parseInt( msg.data ) );
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson( p );
                    }
                    out.println( gson.toJson( msg ) );
                }

                if (msg.code == MessageModel.PUT_PRODUCT) {
                    ProductModel p = gson.fromJson( msg.data, ProductModel.class );
                    System.out.println( "PUT command with Product = " + p );
                    int res = adapter.saveProduct( p );
                    if (res == IDataAdapter.PRODUCT_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println( gson.toJson( msg ) );
                }

                if (msg.code == MessageModel.LOGIN) {
                    UserModel u = gson.fromJson( msg.data, UserModel.class );
                    System.out.println( "LOGIN command with User = " + u );
                    UserModel user = adapter.loadUser(u.mUsername);
                    if (user != null && user.mPassword.equals( u.mPassword )) {
                        msg.code = MessageModel.OPERATION_OK;
                        totalActiveUsers++;
                        int accessToken = totalActiveUsers;
                        msg.ssid = accessToken;
                        msg.data = gson.toJson( user, UserModel.class );
                        activeUsers.put( accessToken, user );
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println( gson.toJson( msg ) );  // answer login command!
                }





                if (msg.code == MessageModel.GET_CUSTOMER) {
                    System.out.println( "GET Customer with id = " + msg.data );
                    CustomerModel p = adapter.loadCustomer( Integer.parseInt( msg.data ) );
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson( p );
                    }
                    out.println( gson.toJson( msg ) );
                }

                if (msg.code == MessageModel.PUT_CUSTOMER) {
                    CustomerModel c = gson.fromJson( msg.data, CustomerModel.class );
                    System.out.println( "PUT command with Customer = " + c );
                    int res = adapter.saveCustomer( c );
                    if (res == IDataAdapter.CUSTOMER_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println( gson.toJson( msg ) );
                }


                if (msg.code == MessageModel.GET_USER) {
                    System.out.println( "GET User with id = " + msg.data );
                    UserModel u = adapter.loadUser( msg.data);
                    if (u == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson( u );
                    }
                    out.println( gson.toJson( msg ) );
                }


                // add for purchase
                if (msg.code == MessageModel.PUT_USER) {
                    UserModel u = gson.fromJson( msg.data, UserModel.class );
                    System.out.println( "PUT command with Customer = " + u );
                    int res = adapter.saveUser( u );
                    if (res == IDataAdapter.USER_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println( gson.toJson( msg ) );
                }

                if (msg.code == MessageModel.GET_PURCHASE) {
                    System.out.println( "GET User with id = " + msg.data );
                    PurchaseModel u = adapter.loadPurchase(Integer.parseInt( msg.data));
                    if (u == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    } else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson( u );
                    }
                    out.println( gson.toJson( msg ) );
                }


                // add for purchase
                if (msg.code == MessageModel.PUT_PURCHASE) {
                    PurchaseModel u = gson.fromJson( msg.data, PurchaseModel.class );
                    System.out.println( "PUT command with Purchase = " + u );
                    int res = adapter.savePurchase( u );
                    if (res == IDataAdapter.PURCHASE_SAVED_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    } else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println( gson.toJson( msg ) );
                }

                if (msg.code == MessageModel.GET_PURCHASE_LIST) {
                    int id = Integer.parseInt(msg.data);
                    PurchaseListModel res = adapter.loadPurchaseHistory(id);
                    msg.code = MessageModel.OPERATION_OK;
                    msg.data = gson.toJson(res);
                    out.println(gson.toJson(msg));  // answer get purchase history!!!
                }

                if (msg.code == MessageModel.GET_PRODUCT_LIST) {
                    System.out.println("GET_PRODUCT_LIST --- ");
                    SummaryListModel res = adapter.loadSummaryProduct();
                    msg.code = MessageModel.OPERATION_OK;
                    msg.data = gson.toJson(res);
                    out.println(gson.toJson(msg));  // answer get product sale summary!!!
                }





            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

