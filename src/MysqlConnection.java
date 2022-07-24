import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MysqlConnection {

	public static Connection mysqlDbConnection (String user, String pwd)  {
		user = "root";
		pwd = "root";
		try {
			Class.forName("org.mariadb.jdbc.Driver"); 
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/empdb?user=root&password=root");
			System.out.println("Connected to MariaDB database");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
