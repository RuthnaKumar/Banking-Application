package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Bank {
	static Scanner sc = new Scanner(System.in);
	Connection connection = null;
	Statement statement = null;
	ResultSet result = null;
	int id;
	String name, Account_type, Address, password, Contact_number;
	static boolean loop = true;

	public static void main(String[] args) throws SQLException {
		Bank con = new Bank();
		con.getConnection();
		// con.tableCreation();
		while (loop = true) {
			System.out.println("Banking Application");
			System.out.println(" 1,Create Account\n 2,View Account\n 3,Update Account\n 4,Delete Account\n 5,Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Create Bank Account");
				con.createAccount();
				break;
			}
			case 2: {
				System.out.println("Account Datails");
				System.out.println("---------------");
				con.viewAccount();
				break;
			}
			case 3: {
				System.out.println("Update Account");
				con.updateAccount();
				break;
			}
			case 4: {
				System.out.println("Delete Account");
				con.deleteAccount();
				break;
			}
			case 5: {
				loop = false;
				break;
			}
			}
		}
	}

	public Connection getConnection() {
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

	public void tableCreation() {
		try {
			String table = "create table bank(id SERIAL  primary key,name varchar(100) not null,"
					+ "Account_type varchar(100) not null,Address varchar(150) not null ,Contact_number varchar(150) not null,password varchar(150) not null)";
			statement = connection.createStatement();
			statement.execute(table);
			System.out.println("Table Created");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void createAccount() throws SQLException {
		System.out.println("Enter the Name");
		name = sc.next();
		System.out.println("Enter the Account Type SAVING OR CURRENT");
		Account_type = sc.next();
		System.out.println("Enter the Address");
		Address = sc.next();
		System.out.println("Enter the Contact Number");
		Contact_number = sc.next();
		System.out.println("Enter the Password");
		password = sc.next();
		if (Contact_number.length() == 10) {
			String check = "select * from bank where contact_number='"+Contact_number+"'";
			statement = connection.createStatement();
			result = statement.executeQuery(check);
			 if (!result.next()) {
			try {
				String query = "INSERT INTO bank (name, Account_type,Address,Contact_number,password) VALUES " + "('"
						+ name + "','" + Account_type + "','" + Address + "','" + Contact_number + "','" + password
						+ "')";
				statement = connection.createStatement();
				statement.executeUpdate(query);
				System.out.println("Account Created");
			} catch (Exception e) {
				System.out.println(e);
			}
			}
			 else {
				 System.out.println("Contact Number Already Exists");
			 }
		}
		else {
			System.out.println("Contact Number Invalid Enter Valid Contact Number");
		}
	}

	public void viewAccount() {
		System.out.println("1,All Account\n2,Specific Account");
		int choice = sc.nextInt();
		switch (choice) {
		case 1: {
			try {
				String query = "select *  from bank order by id asc";
				statement = connection.createStatement();
				result = statement.executeQuery(query);
				while (result.next()) {
					System.out.print(result.getString(1) + " ");
					System.out.print(result.getString(2) + " ");
					System.out.print(result.getString(3) + " ");
					System.out.println(result.getString(4));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			break;
		}
		case 2: {
			System.out.println("Enter the ID");
			int id = sc.nextInt();
			try {
				// String check = "select exists(select * from bank where id='"+id+"')";
				String query = "select * from bank where id='" + id + "' ";
				statement = connection.createStatement();
				result = statement.executeQuery(query);
				if (result.next()) {
					System.out.print(result.getString(1) + " ");
					System.out.print(result.getString(2) + " ");
					System.out.print(result.getString(3) + " ");
					System.out.println(result.getString(4));
				} else {
					System.out.println("Enter the Correct ID");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			break;
		}
		}
	}

	public void updateAccount() throws SQLException {
		System.out.println("Enter the ID to Update");
		id = sc.nextInt();
		String check = "select * from bank where id='" + id + "' ";
		statement = connection.createStatement();
		result = statement.executeQuery(check);
		if (result.next()) {
			System.out.println("1,Name\n2,Account Type\n3,Address\n4,Contact Number");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				try {
					System.out.println("Enter the Name");
					String name = sc.next();
					String query = "update bank set name='" + name + "' where id='" + id + "'";
					statement = connection.createStatement();
					statement.execute(query);
					System.out.println("Name Updated");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			}
			case 2: {
				try {
					System.out.println("Enter the Account Type");
					String Account_type = sc.next();
					String query = "update bank set Account_type='" + Account_type + "' where id='" + id + "'";
					statement = connection.createStatement();
					statement.execute(query);
					System.out.println("Account Type Updated");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			}
			case 3: {
				try {
					System.out.println("Enter the Address Type");
					String Address = sc.next();
					String query = "update bank set Address='" + Address + "' where id='" + id + "'";
					statement = connection.createStatement();
					statement.execute(query);
					System.out.println("Address Updated");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			}
			case 4: {
				try {
					System.out.println("Enter the Contact_number Type");
					long Contact_number = sc.nextLong();
					String query = "update bank set Contact_number='" + Contact_number + "' where id='" + id + "'";
					statement = connection.createStatement();
					statement.execute(query);
					System.out.println("Contact Number Updated");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			}
			}
		} else {
			System.out.println("ID not Existed");
		}

	}

	public void deleteAccount() throws SQLException {
		System.out.println("Enter the ID to Delete");
		id = sc.nextInt();
		String check = "select * from bank where id='" + id + "' ";
		statement = connection.createStatement();
		result = statement.executeQuery(check);
		if (result.next()) {
			try {
				String query = "delete from bank where id='" + id + "' ";
				statement = connection.createStatement();
				statement.execute(query);
				System.out.println("Value Deleted");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("ID not Existed");
		}
	}
}
