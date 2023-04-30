package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import sql.DB;


public class Logout01 {
	static boolean loginCheck = false;
	static boolean logoutSuccess = false;
	static ArrayList<Log> log = new ArrayList<Log>();
	static Scanner sc = new Scanner(System.in);
	public static void databaseRG(String id, String pw)throws Exception{
		String driver ="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "temp";
		String dbPw = "1234";
		
//		System.out.println("<< JDBC 오라클 접속 테스트>>");
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbId, dbPw);
		String sql = "SELECT*FROM register"; // 주의 세미콜론(;)이 없어야 된다.
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) { // rs.next() : 다음 row가 있으면 손가락으로 이동. true리턴
			String regisId = rs.getString("id");
			String regisPw = rs.getString("pw");
			String regisName = rs.getString("name");
			log.add(new Log(regisId,regisPw,regisName));
		}
		checkLogout(id,pw);
		
		rs.close();
		stmt.close();
		conn.close();
	}
	static void checkLogout(String id,String pw) throws Exception{
		boolean check = false;
		String name = "";
		for(int i =0; i<=log.size()-1; i++) {
			if(id.equals(log.get(i).id) && pw.equals(log.get(i).pw)) {
				name = log.get(i).name; 
				check = true;
				break;
			}
		}
		if(check) {
			System.out.println(name+"님 좋은하루되세요!");
			System.out.println("로그아웃되었습니다.");
			String driver ="oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbId = "temp";
			String dbPw = "1234";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, dbId, dbPw);
			String sql = "UPDATE idconn SET status= 'logout' WHERE id= '" + id+"'";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			logoutSuccess = true;
		}else {
			System.out.println("ID또는 PW가 잘못되었습니다. 다시입력해주세요.");
		}
		
	}
	static String id() {
		System.out.print("ID : ");
		String id = sc.next();
		return id;
	}
	static String pw() {
		System.out.print("PW : ");
		String pw = sc.next();
		return pw;
	}
	static void loginCheck() throws Exception{
		Class.forName(DB.driver);
		Connection conn = DriverManager.getConnection(DB.url, DB.id, DB.pw);
		String sql = "SELECT r.name,i.status"
				+ " FROM idconn i, register r"
				+ " WHERE i.id = r.id";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String status = rs.getString("status");
			if(status.equals("login")) {
				loginCheck = true;
				break;
			}
		}
		
		stmt.close();
		conn.close();
	}
	public static void main(String[] args) throws Exception {
		loginCheck();
		if(!loginCheck) {
			System.out.println("로그인되어있지않습니다.");
		}else {
			while(true) {
				String id = id();
				String pw = pw();
				databaseRG(id,pw);
				if(logoutSuccess) {
					break;
				}
			}
		}

	}

}
