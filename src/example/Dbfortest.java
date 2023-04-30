package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Dbfortest {
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static String id = "temp";
	public static String pw = "1234";
	public static ResultSet m(String sql) throws Exception{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		Statement stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
//		stmt.close();
//		conn.close();
		return rs;
	}
}
