import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MysqlConnection {

	public static Connection mysqlDbConnection (String user, String pwd)  {
		user = "root";
		pwd = "";
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb", user, pwd);
			System.out.println("Connected to MySQL database");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
