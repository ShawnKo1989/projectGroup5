package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import sql.DB;

class BoardDelete{
	static void deleteWriting(String loginUser) throws Exception{
		Scanner sc = new Scanner(System.in);
		Class.forName(DB.driver);
		Connection conn = DriverManager.getConnection(DB.url,DB.id,DB.pw);
		while(true) {
			System.out.print("삭제할 게시글 번호 입력 :");
			int bno = sc.nextInt();
			// 게시글 번호에 맞게 삭제되는 sql 문장
			String sql = "DELETE FROM board WHERE bno=" + bno;
			String sql2 = "SELECT*FROM board WHERE bno=" + bno;
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			if(rs.next()) {
				String writer = rs.getString("writer");
				if(!writer.equals(loginUser)) {
					System.out.println("작성자만 삭제 가능합니다.");
					continue;
				}else {
					stmt.executeUpdate(sql);
					System.out.println("게시글이 삭제되었습니다.");
					break;
				}
			}
			rs.close();
			stmt.close();
			stmt2.close();
			conn.close();
		}
	}
}
public class Deleteboard {
	static boolean loginCheck = false;
	static String loginUser = "";
	static void loginCheck() throws Exception{
		Class.forName(DB.driver);
		Connection conn = DriverManager.getConnection(DB.url, DB.id, DB.pw);
		String sql = "SELECT*FROM idconn";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String id = rs.getString("id");
			String status = rs.getString("status");
			if(status.equals("login")) {
				loginCheck = true;
				loginUser = id;
				break;
			}
		}
		stmt.close();
		conn.close();
	}
	public static void main(String[] args)throws Exception {
		loginCheck();
		if(loginCheck) {
			ShowAll sa = new ShowAll();
			sa.showAlldata();
			BoardDelete.deleteWriting(loginUser);
		}else {
			System.out.println("로그인이 필요한서비스입니다.");
		}

	}

}
