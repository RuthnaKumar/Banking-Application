package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Banking_Application {
	static Scanner sc = new Scanner(System.in);
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet result = null;
	static int id;
	String name, Account_type, Address;
	static String password;
	String Contact_number;
	static boolean innerloop = true;
	static boolean Outerloop = true;

	public static void main(String[] args) throws SQLException {
		Banking_Application con = new Banking_Application();
		con.getConnection();
		String tableCheck = "select exists(select from pg_tables where schemaName ='public' and tableName='bank')";
		statement = connection.createStatement();
		result = statement.executeQuery(tableCheck);
		if (result.next()) {
			if (result.getString(1).equals("f")) {
				System.out.println("Table");
				con.tableCreation();
			}
		}
		while (Outerloop) {
			System.out.println("Banking Application");
			System.out.println(" 1,Create Account\n 2,Login\n 3,Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Create Bank Account");
				con.createAccount();
				break;
			}
			case 2: {
				System.out.println("Account Login");
				System.out.print("Enter the ID : ");
				id = sc.nextInt();
				System.out.print("Enter the Password : ");
				password = sc.next();
				String encryptedPassword = "";
				try {
					SecretKeySpec skeyspec = new SecretKeySpec("zoho".getBytes(), "Blowfish");
					Cipher cipher = Cipher.getInstance("Blowfish");
					cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
					byte[] encrypted = cipher.doFinal(password.getBytes());
					encryptedPassword = new String(encrypted);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String check = "select * from bank where id ='" + id + "' and password='" + encryptedPassword + "'";
				statement = connection.createStatement();
				result = statement.executeQuery(check);
				if (result.next()) {
					System.out.println("Successfully Login");
					innerloop = true;
					while (innerloop) {
						System.out.println(" 1,View Account\n 2,Balance Check\n "
								+ "3,Update Account\n 4,Delete Account\n 5,Logout");
						int choice1 = sc.nextInt();
						switch (choice1) {
						case 1: {
							System.out.println("Account Datails");
							con.viewAccount(id);
							break;
						}
						case 2: {
							System.out.println("Account Balance");
							con.viewBalance(id);
							break;
						}
						case 3: {
							System.out.println("Update Account");
							con.updateAccount(id);
							break;
						}
						case 4: {
							System.out.println("Delete Account");
							con.deleteAccount(id);
						}
						case 5: {
							System.out.println("Account Logout");
							innerloop = false;
							break;
						}
						default: {
							System.out.println("Enter the Valid Input");
						}
						}
					}
				} else {
					System.out.println("Invalid ID or Password");
					boolean innermostloop = true;
					while (innermostloop) {
						System.out.println(" 1,Forgot password\n 2,Exit");
						int option = sc.nextInt();
						switch (option) {
						case 1: {
							System.out.println("Forgot Password");
							System.out.println("Enter the Mobile Number");
							String contact_number = sc.next();
							con.forgotPassword(id, contact_number);

						}
						case 2: {
							System.out.println("Exited");
							innermostloop = false;
							break;
						}
						}
					}
				}
				break;
			}
			case 3: {
				Outerloop = false;
				break;
			}
			default: {
				System.out.println("Enter the Valid Input");
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
		String encryptedPassword = null;
		try {
			SecretKeySpec skeyspec = new SecretKeySpec("zoho".getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted = cipher.doFinal(password.getBytes());
			encryptedPassword = new String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Contact_number.length() == 10) {
			String check = "select * from bank where contact_number='" + Contact_number + "'";
			statement = connection.createStatement();
			result = statement.executeQuery(check);
			if (!result.next()) {
				try {
					String query = "INSERT INTO bank (name, Account_type,Address,Contact_number,password) VALUES "
							+ "('" + name + "','" + Account_type + "','" + Address + "','" + Contact_number + "','"
							+ encryptedPassword + "')";
					statement = connection.createStatement();
					statement.executeUpdate(query);
					System.out.println("Account Created");
					try {
						String query1 = "select * from bank where Contact_number='" + Contact_number + "' ";
						statement = connection.createStatement();
						result = statement.executeQuery(query1);
						if (result.next()) {
							System.out.print("ID : " + result.getString(1) + " ");
							System.out.print("NAME : " + result.getString(2) + " ");
							System.out.print("ADDRESS : " + result.getString(3) + " ");
							System.out.println("CONTACT NUMBER : " + result.getString(4));
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				System.out.println("Contact Number Already Exists");
			}
		} else {
			System.out.println("Contact Number Invalid Enter Valid Contact Number");
		}
	}

	public void viewAccount(int id) {
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
	}

	public void viewBalance(int id) {
		try {
			String query = "select bank.id,bank.name,balance.balance from bank "
					+ "inner join balance on bank.id=balance.id where bank.id='" + id + "' ";
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			if (result.next()) {
				System.out.println("Account ID : " + result.getString(1) + " ");
				System.out.println("Account Holder Name : " + result.getString(2) + " ");
				System.out.println("Account Balance : " + result.getString(3));
			} else {
				System.out.println("Enter the Correct ID");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateAccount(int id) throws SQLException {
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

	public void deleteAccount(int id) throws SQLException {
		String check = "select * from bank where id='" + id + "' ";
		statement = connection.createStatement();
		result = statement.executeQuery(check);
		if (result.next()) {
			try {
				String query = "delete from bank where id='" + id + "' ";
				statement = connection.createStatement();
				statement.execute(query);
				System.out.println("Account Deleted Successfully");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("ID not Existed");
		}
	}

	public void forgotPassword(int id, String contact_number) throws SQLException {
		String check = "select * from bank where id='" + id + "' and contact_number='" + contact_number + "' ";
		statement = connection.createStatement();
		result = statement.executeQuery(check);
		if (result.next()) {

			try {
				System.out.println("Enter the New Password");
				String New_Password = sc.next();
				String newEncryptedPassword = null;
				try {
					SecretKeySpec skeyspec = new SecretKeySpec("zoho".getBytes(), "Blowfish");
					Cipher cipher = Cipher.getInstance("Blowfish");
					cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
					byte[] encrypted = cipher.doFinal(New_Password.getBytes());
					newEncryptedPassword = new String(encrypted);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String query = "update bank set password='" + newEncryptedPassword + "' where id='" + id + "'";
				statement = connection.createStatement();
				statement.execute(query);
				System.out.println("Password Changed");
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			System.out.println("Enter the valid ID or Mobile Number");
		}
	}
}
