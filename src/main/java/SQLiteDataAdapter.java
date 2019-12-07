import java.sql.*;

public class SQLiteDataAdapter implements IDataAdapter {

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }


    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;

        try {
            String sql = "SELECT* FROM Products WHERE Productid = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("Productid");
                product.mVendor = rs.getString( "supplier" );
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT* FROM Customer WHERE CustomerID = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerId = rs.getInt("CustomerID");
            customer.mPhone = rs.getString("Phone");
            customer.mAddress= rs.getString("Address");
            customer.mprice= rs.getDouble("Total");
            customer.mNCame = rs.getString("Name");
            customer.mPin = rs.getString("Pin");





        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }



    public PurchaseModel loadPurchase(int purchaseid) {
        PurchaseModel purchase = new PurchaseModel();

        try {
            String sql = "SELECT* FROM Purchase WHERE purchaseid = " + purchaseid;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                purchase = new PurchaseModel();
                purchase.mPurchaseID = rs.getInt("purchaseid");
                purchase.mProductID = rs.getInt("productid");
                purchase.mCustomerID = rs.getInt( "Customerid" );
                purchase.mPrice = rs.getDouble("price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mTax = rs.getDouble( "tax" );
                purchase.mCost = rs.getDouble( "Total");
                purchase.mDate = rs.getString( "Date" );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }




    public UserModel loadUser(String UserID) {
        UserModel user = new UserModel();

        try {
            String sql = "SELECT* FROM Users WHERE Username = \'" + UserID + "\'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            user.mUsername = rs.getString("Username");
            user.mPassword = rs.getString("Password");
            user.mFullname= rs.getString("Fullname");
            user.mUserType= rs.getInt("Usertype");
            user.mCustomerID= rs.getInt("CustomerID");





        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public int saveProduct(ProductModel product) {



        try {
            Statement stmt = conn.createStatement();
            ProductModel p = loadProduct(product.mProductID); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Products WHERE ProductID = " + product.mProductID);
            }

            String sql = "INSERT INTO Products VALUES " + product;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }



    public int saveCustomer(CustomerModel customer) {
        try {
            Statement stmt = conn.createStatement();
            CustomerModel p = loadCustomer(customer.mCustomerId); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Customer WHERE CustomerID = " + customer.mCustomerId);
            }

            String sql = "INSERT INTO Customer VALUES " + customer;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;
      /*  try {
            String sql = "INSERT INTO Customer VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;*/
    }

    public int savePurchase(PurchaseModel purchase) {
        /*
        try {
            String sql = "INSERT INTO Purchase VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;*/

        try {
            Statement stmt = conn.createStatement();
            PurchaseModel p = loadPurchase(purchase.mPurchaseID); // check if this purchase exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Purchase WHERE PurchaseID = " + purchase.mPurchaseID);
            }

            String sql = "INSERT INTO Purchase VALUES " + purchase;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;
    }


    public int saveUser(UserModel user) {

        try {
            Statement stmt = conn.createStatement();
            UserModel p = loadUser(user.mUsername); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Users WHERE CustomerID = " + user.mCustomerID);
            }

            String sql = "INSERT INTO Users VALUES " + user;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return USER_DUPLICATE_ERROR;
        }

        return USER_SAVED_OK;
        /*
        try {
            String sql = "INSERT or IGNORE into Users VALUES " + user;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return USER_DUPLICATE_ERROR;
        }

        return USER_SAVED_OK;*/
    }


    public PurchaseListModel loadPurchaseHistory(int id) {
        PurchaseListModel res = new PurchaseListModel();
        try {
            String sql = "SELECT* FROM Purchase WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("PurchaseID");
                purchase.mProductID = rs.getInt("ProductID");
                purchase.mPrice = rs.getDouble("Price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mCost = rs.getDouble("Cost");
                purchase.mTax = rs.getDouble("Tax");
                purchase.mTotal = rs.getDouble("Total");
                purchase.mDate = rs.getString("Date");

                res.purchases.add(purchase);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }


    public SummaryListModel loadSummaryProduct() {
        SummaryListModel res = new SummaryListModel();
        try {
            String sql = "SELECT Products.ProductID as ID, Name, sum(Purchase.Quantity) AS TotalUnit," +
                    "sum(Purchase.Total) as TotalMoney FROM Products JOIN Purchase ON " +
                    "Products.ProductID = Purchase.ProductId";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductSaleModel product = new ProductSaleModel();
                product.mProductID = rs.getInt("ID");
                product.mProductName = rs.getString( "Name" );
                product.mTotalUnitSold = rs.getDouble("TotalUnit");
                product.mTotalMoneySold = rs.getDouble("TotalMoney");

                res.products.add(product);
            }

            System.out.println( "Summary list size = " + res.products.size() );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }



}
