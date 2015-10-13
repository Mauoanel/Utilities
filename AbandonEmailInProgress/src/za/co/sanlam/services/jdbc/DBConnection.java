package za.co.sanlam.services.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import za.co.sanlam.services.utils.ResourceBundle;

public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);
    static String url = null;
    static String db = null;
    static String driver = null;
    static String user = null;
    static String pass = null;

    static {

        url = ResourceBundle.getProperty("db.connection.url.ch");
        db = ResourceBundle.getProperty("db.connection.dbname.ch");
        driver = ResourceBundle.getProperty("db.connection.driver");
        user = ResourceBundle.getProperty("db.connection.userName");
        pass = ResourceBundle.getProperty("db.connection.password");

        LOGGER.info("----------------------------------------------");
        LOGGER.info("DB URL : " + url + " db : " + db);
        LOGGER.info("----------------------------------------------");
    }

    public static Connection getCHDBConnection() {

        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.close();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        }
        return con;
    }
}
