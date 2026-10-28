package com.nextgen.week4.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Configuration and Connection Provider.
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Supports:
 * - Standard MySQL JDBC connectivity
 * - Diagnostic checks
 */
public class DatabaseConfig {

    private static String mysqlUrl = "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String mysqlUser = "root";
    private static String mysqlPass = "password";

    public static void setCredentials(String url, String user, String pass) {
        mysqlUrl = url;
        mysqlUser = user;
        mysqlPass = pass;
    }

    public static Connection getMySQLConnection() throws SQLException {
        try {
            // Load MySQL Connector Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Driver may not be present in standalone classpath
        }
        return DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
    }

    public static boolean testMySQLConnection() {
        try (Connection conn = getMySQLConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getMysqlUrl() { return mysqlUrl; }
    public static String getMysqlUser() { return mysqlUser; }
}
