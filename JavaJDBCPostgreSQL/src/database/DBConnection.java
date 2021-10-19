package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static void main(String[] args) {
		DBConnection obj_connection=new DBConnection();
		obj_connection.getConnection();
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "zoho");
			if (connection != null) {
				System.out.println("Connected");
			} else {
				System.out.println("Not Connected");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

}
