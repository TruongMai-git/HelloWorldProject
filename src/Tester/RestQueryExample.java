package Tester;

import java.sql.*;

public class RestQueryExample {
    //DriverManagerGetConnectionByUrl
    private static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5433/customer";
        return DriverManager.getConnection(jdbcUrl);
    }

    //Statement
    private static void queryData() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SQL");
                processResult(rs);

            }
        }
    }

    //Call statement
    public static void main(String[] args) throws SQLException {
        queryData();
    }

    //Print result
    private static void printRow(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String value = rs.getString(i);
            System.out.print(value);
            System.out.print("\t");
        }
        System.out.println();
    }

    //
    private static void printColName  (ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String colLabel = meta.getColumnLabel(i);
            System.out.print(colLabel);
            System.out.print("\t");
        }
        System.out.println();
    }

    //Result
    private static void processResult(ResultSet rs) throws SQLException {
        printColName(rs);
        while (rs.next()) {
            printRow(rs);
        }
    }

}