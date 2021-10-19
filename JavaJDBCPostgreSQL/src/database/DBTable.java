package database;

import java.sql.Connection;
import java.sql.Statement;

public class DBTable {
	public static void main(String[] args) {
		Connection connection =null;
		Statement statement=null;
		DBConnection obj_connection = new DBConnection();
		connection=obj_connection.getConnection();
		try {
			String query="create table employee(id SERIAL  primary key,name varchar(100) not null,age int )";
			statement=connection.createStatement();
			statement.execute(query);
			System.out.println("Table Created");
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
