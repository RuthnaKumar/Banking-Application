package database;

import java.sql.Connection;
import java.sql.Statement;

public class DBInsert {
	public static void main(String[] args) {
		Connection connection =null;
		Statement statement=null;
		DBConnection obj_connection = new DBConnection();
		connection=obj_connection.getConnection();
		try {
			String query="INSERT INTO employee (name, age) VALUES ('kk','30')";
			statement=connection.createStatement();
			statement.executeQuery(query);
			System.out.println("Value Inserted");
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
