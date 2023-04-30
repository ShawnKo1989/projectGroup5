package board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;
class BoardWrite extends sql.DB{
	String title;
	String content;
	String writer;
	String bdate;
	BoardWrite(String title, String content, String writer, String bdate){
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.bdate = bdate;
	}
	static Scanner sc = new Scanner(System.in);
	void boardUpdate() throws Exception{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		String sql = "INSERT INTO board(bno,title,content,writer,bdate,viewer)"
				+" VALUES(seq_board.nextval,'"+title+"','"+content+"','"+writer+"','"+bdate+"',"+0+")";
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate(sql);
		
		stmt.close();
		conn.close();
		System.out.println("게시글이 작성되었습니다.");
	}
}
public class BoardWriting extends sql.DB{
	static String loginUser = "";
	static boolean loginCheck = false;
	static Scanner sc = new Scanner(System.in);
	static String titleWrite() {
		System.out.print("제목을 입력 : ");
		String title = sc.nextLine();
		return title;
	}
	static String contentWrite() {
		System.out.print("내용을 입력 : ");
		String content = sc.nextLine();
		return content;
	}
	static void loginCheck() throws Exception{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
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
		if(loginCheck) {
		String bdate ="";
		String title = titleWrite();
		String content = contentWrite();
		Date date = new Date();
		bdate +=date;
		BoardWrite bw = new BoardWrite(title,content,loginUser,bdate);
		bw.boardUpdate();
		}else {
			System.out.println("로그인이 필요한 서비스입니다.");
			System.out.println("로그인 후에 이용해주세요 >_<");
		}
		
	}

}
