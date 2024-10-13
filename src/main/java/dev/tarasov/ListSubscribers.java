package dev.tarasov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListSubscribers {

    private final static String RDS_URL = "jdbc:postgresql://database-1.cbais8yksen2.eu-north-1.rds.amazonaws.com:5432/mydb";
    private final static String RDS_USER_NAME = "root";
    private final static String RDS_USER_PASSWD = "13032001";
    public static final String SQL_QUERY = "select * from Subscribers;";

    public List<String> handleRequest() {
        try (var connection = DriverManager.getConnection(RDS_URL, RDS_USER_NAME, RDS_USER_PASSWD)) {
            if (!connection.isValid(0))
                throw new RuntimeException("Unable to connect to: " + RDS_URL);
            return getSubscribers(connection);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        new ListSubscribers().handleRequest().forEach(System.out::println);
    }

    private ArrayList<String> getSubscribers(Connection connection) throws SQLException {
        var subscribers = new ArrayList<String>();
        var statement = connection.prepareStatement(SQL_QUERY);
        var rs = statement.executeQuery();
        while (rs.next()) {
            var mail = rs.getString("email");
            subscribers.add(mail);
        }
        return subscribers;
    }
}
