package database;

import java.sql.Connection;
import java.sql.Statement;

public class DBUpdate {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		DBConnection obj_connection = new DBConnection();
		connection = obj_connection.getConnection();
		try {
			String query = "update employee set name='ak' where id='2'";
			statement = connection.createStatement();
			statement.executeQuery(query);
			System.out.println("Value Updated");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
