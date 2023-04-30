package board;

import java.sql.ResultSet;

import example.Dbfortest;


class ShowAll {
	void showAlldata() throws Exception {
		ResultSet rs =Dbfortest.m("SELECT*FROM board ORDER BY bno DESC");
		System.out.println("----<<<< 게시판 >>>>----");
		while(rs.next()) {
			int bno = rs.getInt("bno");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String writer = rs.getString("writer");
			String bdate = rs.getString("bdate");
			int viewer = rs.getInt("viewer");
			
			System.out.println("\s"+bno+".\s"+title+" / "+content+""
					+ " / "+writer+" / "+bdate+" / 조회수:"+viewer);
		}
	}
}
public class Showboard {

	public static void main(String[] args) throws Exception {
		ShowAll sa = new ShowAll();
		sa.showAlldata();

	}

}
