package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBRead {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		DBConnection obj_connection = new DBConnection();
		connection = obj_connection.getConnection();
		try {
			String query = "select *from employee";
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			System.out.println("Value Inserted");
			while (result.next()) {
				System.out.print(result.getString(1)+" ");
				System.out.print(result.getString(2)+" ");
				System.out.println(result.getString(3));
			}
			

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
