package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CRUD {
	public static void main(String[] args) {
		CRUD obj_connection = new CRUD();
		obj_connection.getConnection();
		obj_connection.crud();
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
	public void crud() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		//DBConnection obj_connection = new DBConnection();
		CRUD obj_connection = new CRUD();
		connection = obj_connection.getConnection();
		 String name;
		int age;
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter the Name");
		name=sc.next();
		System.out.println("Enter the Age");
		age=sc.nextInt();
		try {
//			String table = "create table crud(id SERIAL  primary key,name varchar(100) not null,age int )";
//			statement = connection.createStatement();
//			statement.execute(table);
			//INSERT VALUE
			String insert="INSERT INTO crud (name, age) VALUES ("+name+","+age+")";
			statement = connection.createStatement();
			statement.execute(insert);
			System.out.println("Value Inserted");
			//READ VALUE
			String read = "select *from crud";
			statement = connection.createStatement();
			result = statement.executeQuery(read);
			System.out.println("Value Read");
			while (result.next()) {
				System.out.print(result.getString(1)+" ");
				System.out.print(result.getString(2)+" ");
				System.out.println(result.getString(3));
			}
			//UPDATE VALUE		
			String update = "update crud set name='ak' where id='2'";
			statement = connection.createStatement();
			statement.execute(update);
			System.out.println("Update Value");
			//DELETE VALUE		
			String delete = "delete from crud where id='3'";
			statement = connection.createStatement();
			statement.execute(delete);
			System.out.println("Value deleted");
		} catch (Exception e) {
			System.out.println(e);
		}
		

	}

}

