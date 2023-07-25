package com.example.sourthenlankacarrental.Connection;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    Connection connection;
    static String username, password, ip, port, database;

    public static Connection getConnection() {
        ip = "192.168.8.100";
        port = "1444";
        database = "slcrms";
        username = "sa";
        password = "1234";
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection=null;
        String ConnectionURL=null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+username+";password="+password+";";

            connection = DriverManager.getConnection(ConnectionURL);
        } catch (ClassNotFoundException e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }

        return connection;
    }
}
