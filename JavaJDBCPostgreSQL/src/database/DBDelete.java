package database;

import java.sql.Connection;
import java.sql.Statement;

public class DBDelete {
	public static void main(String[] args) {
		Connection connection =null;
		Statement statement=null;
		DBConnection obj_connection = new DBConnection();
		connection=obj_connection.getConnection();
		try {
			String query="delete from employee where id='2'";
			statement=connection.createStatement();
			statement.executeQuery(query);
			System.out.println("Value Deleted");
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}


}
