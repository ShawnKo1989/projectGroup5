package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import sql.DB;

class ShowSelected{
	static void showWriting() throws Exception{
		Scanner sc = new Scanner(System.in);
		Class.forName(DB.driver);
		Connection conn = DriverManager.getConnection(DB.url,DB.id,DB.pw);
		System.out.print("조회할 게시글 번호 입력 :");
		int bno = sc.nextInt();
		String sql = "SELECT*FROM board WHERE bno=" + bno;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			String title = rs.getString("title");
			String content = rs.getString("content");
			String writer = rs.getString("writer");
			String bdate = rs.getString("bdate");
			int viewer = rs.getInt("viewer");
			//조회한 글을 출력하는 문장
			System.out.print("게시글 제목 : "+title);
			System.out.println("\t게시글 번호 : "+bno);
			System.out.print("게시글 작성자 : "+writer);
			System.out.print("\t작성날짜 : "+bdate);
			System.out.println("\t조회수 : "+ viewer);
			System.out.println("게시글 내용 : "+content);
			// 열람한 후에 조회수가 1 올라가는 문장
			String sql2 = "UPDATE board SET viewer ="+(viewer+1)+" WHERE bno ="+bno ;
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate(sql2);
			stmt2.close();
		}
		rs.close();
		stmt.close();
		conn.close();

	}
}
public class Selectboard {

	public static void main(String[] args) throws Exception {
		ShowSelected.showWriting();
	}

}
