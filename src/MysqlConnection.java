import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MysqlConnection {

	public static Connection mysqlDbConnection (String user, String pwd)  {
		try {
			// Class.forName("com.mysql.jdbc.Driver");	//	MYSQL server
			Class.forName("org.mariadb.jdbc.Driver");	//	MARIADB server
			// Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB?user=USER&password=PASSWORD"); //	MYSQL server
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/empdb?user=root&password=root");	//	MARIADB server
			System.out.println("Connected to MariaDB database");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
