package com.example.projeto;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecta_bd {

    public static Connection getConnection() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection cn = null;
        try {
            String banco = "projeto20192s";
            String url = "jdbc:mysql://192.168.0.11:3306/" + banco;
            String usuario = "mobile";
            String senha = "mobile22";

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(url, usuario, senha);

            DatabaseMetaData dbmd = cn.getMetaData();

            String dbName = dbmd.getDatabaseProductName();

            String dbVersion = dbmd.getDatabaseProductVersion();

            String dbUrl = dbmd.getURL();

            String userName = dbmd.getUserName();

            String driverName = dbmd.getDriverName();

        } catch (SQLException e) {
            Log.e("BANCO", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("BANCO", e.getMessage());
        } catch (Exception e) {
            Log.e("BANCO", e.getMessage());
        }
        return cn;
    }


}
