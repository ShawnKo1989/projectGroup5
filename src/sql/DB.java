package sql;

public class DB {
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static String id = "temp";
	public static String pw = "1234";
	
	public DB(){}
	public DB(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	public DB(String driver, String url, String id, String pw) {
		this.driver = driver;
		this.url = url;	
		this.id = id;
		this.pw = pw;
	}
}