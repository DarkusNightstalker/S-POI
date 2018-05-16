/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.dao.util;

import com.fiis.sppp.util.Faces;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Jdbc post gres sql.
 *
 * @author Danny
 */
public class JDBCPostGresSQL {

    /**
     * The constant DRIVER.
     */
    public static final String DRIVER = "org.postgresql.Driver";
    /**
     * The constant URL.
     */
//    public static final String URL = "jdbc:postgresql://192.168.10.4:5432/spoi";
//    public static final String USER = "appsiisplan";
//    public static final String PASSWORD = "App$PLAN7102";
    public static final String URL = "jdbc:postgresql://localhost:5432/spoi";
    /**
     * The constant USER.
     */
    public static final String USER = "postgres";
    /**
     * The constant PASSWORD.
     */
    public static final String PASSWORD = "admin";

    private Connection connection = null;

    /**
     * Instantiates a new Jdbc post gres sql.
     */
    public JDBCPostGresSQL() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } else if (connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return connection;
    }

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
