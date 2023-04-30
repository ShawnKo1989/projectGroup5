package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import sql.DB;
import sql.DB;
class BoardCorrecter{
	static Scanner sc = new Scanner(System.in);
	String loginUser;
	int bno;
	BoardCorrecter(String loginUser){
		this.loginUser = loginUser;
	}
	void reWriteBoard(String loginUser) throws Exception{
			Class.forName(DB.driver);
			Connection conn = DriverManager.getConnection(DB.url,DB.id,DB.pw);
			while(true) {
				System.out.print("수정할 게시글 번호 입력 :");
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
					if(!writer.equals(loginUser)) {
						System.out.println("작성자만 수정가능합니다");
						continue;
					}else {
					//조회한 글을 출력하는 문장
						System.out.print("게시글 제목 : "+title);
						System.out.println("\t게시글 번호 : "+bno);
						System.out.print("게시글 작성자 : "+writer);
						System.out.print("\t작성날짜 : "+bdate);
						System.out.println("\t조회수 : "+ viewer);
						System.out.println("게시글 내용 : "+content);
						this.bno = bno;
						break;
					}
				}
				rs.close();
				stmt.close();
			}
		Statement stmt2 = conn.createStatement();
		Statement stmt3 = conn.createStatement();
		System.out.println("어떤내용을 수정? \n 1.제목 \n 2.내용");
		int selectNum = sc.nextInt();
		switch(selectNum) {
		case 1:
			System.out.println("제목을 수정합니다.");
			System.out.print("입력:");
			sc.nextLine();
			String newTitle = sc.nextLine();
			String sql2 = "UPDATE board SET title= '"+newTitle+"' WHERE bno=" + bno;
			stmt2.executeUpdate(sql2);
			System.out.println("글제목이 수정되었습니다.");
			break;
		case 2:
			System.out.print("내용을 수정합니다.");
			System.out.print("입력:");
			sc.nextLine();
			String newContent = sc.nextLine();
			String sql3 = "UPDATE board SET content= '"+newContent+"' WHERE bno=" + bno;
			stmt3.executeUpdate(sql3);
			System.out.println("글내용이 수정되었습니다.");
			break;
		default:
			break;
		}
		sc.close();
		stmt3.close();
		stmt2.close();
		conn.close();
	}
}
public class Boardcorrect {
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
	public static void main(String[] args) throws Exception{
		loginCheck();
		BoardCorrecter bc = new BoardCorrecter(loginUser);
		if(loginCheck) {
			bc.reWriteBoard(loginUser);
		}else {
			System.out.println("로그인이 필요한 서비스입니다.");
		}
		
	}
}

