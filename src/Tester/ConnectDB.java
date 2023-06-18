package Tester;
import org.testng.annotations.Test;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.jcraft.jsch.JSch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDB {
    // Connection object
    static Connection con = null;
    // Statement object
    private static Statement stmt;
    // Constant for SSH properties
    String driverName = "org.postgresql.Driver";
    public static String SSH_HOST = "3.217.55.206";
    public static int SSH_PORT = 22;
    public static String SSH_USER = "developer";
    public static String SSH_PASSWORD_FILE = "C:\\Users\\TruongMai\\OneDrive\\Documents\\ACHViewer1.0a\\Dang Private pem key (1)";
    // Constant for Database properties
    public static String DB_HOST = "acb-qa-business.cluster-cirbh61no0du.us-east-1.rds.amazonaws.com";
    public static int DB_PORT = 5432;
    public static String DB_USER = "master";
    public static String DB_PASSWORD = "4JR7YS3gilF82xyjMMUb";
    public static String DB_NAME = "account";

	@SuppressWarnings("resource")
	@BeforeTest
    
    public void setUp() throws Exception {
    	try {
            JSch jsch = new JSch();
            jsch.addIdentity(SSH_PASSWORD_FILE);
            com.jcraft.jsch.Session 
            session = jsch.getSession(SSH_USER, SSH_HOST);
            session.setConfig("StrictHostKeyChecking", "no");

            // Establish SSH connection using the PEM file
            session.connect();
        	if(session !=null && session.isConnected()){
               System.out.println("Connected to SSH server");
        	}

            // Connect to the database using JDBC
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME; 
            Socket socket = new Socket(SSH_HOST, SSH_PORT);
            socket.setSoTimeout(500000);
            con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASSWORD); 
            System.out.println(con);

            // Statement object to send the SQL statement to the Database
            stmt = con.createStatement();
            System.out.println(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(con != null && !con.isClosed()){
        		System.out.println("Closing Database Connection");
        		con.close();
        	}
        	}
    }

	@Test
    public void test() {
        try {
            String query = "SELECT account_number FROM loan_account LIMIT 3";
            // Get the contents of the loan_account table from the DB
            ResultSet res = stmt.executeQuery(query);
            // Print the result until all the records are printed
            while (res.next()) {
                System.out.print(res.getString(1));
                System.out.print(" " + res.getString(2));
                System.out.print(" " + res.getString(3));
                System.out.println(" " + res.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() throws Exception {
        // Close DB connection
        if (con != null) {
            con.close();
        }
    }
 }