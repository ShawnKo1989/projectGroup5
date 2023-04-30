package example;

import java.util.ArrayList;
import java.util.Scanner;

public class Ex1903 {
	
	public static void main(String[] args) {
		while(true) {
			goHome = false;
			showHomeMenu();
			switch(selectMenu()) {
			case 1:  // 전체 회원 조회
				showMembers();
				break;
			case 2:  // 회원 등록
				signIn();
				break;
			case 3:  // 회원 검색
				searchMembers();
				break;
			case 4:  // 회원 삭제
				deleteMembers();
				break;
			case 0:  // 종료
				quit();
				break;
			}
			if(isQuit)
				break;
		}
		sc.close();
	}
	
	static ArrayList<Members> mbs = new ArrayList<Members>();
	static Scanner sc = new Scanner(System.in);
	static String id;
	static String name;
	static String pw;
	static boolean goHome;
	static boolean isQuit;
	
	static void inputId() {
		System.out.print("ID : ");
		id = sc.nextLine();
	}
	static void inputName() {
		System.out.print("이름 : ");
		name =  sc.nextLine();
	}
	static void inputPw() {
		System.out.print("PW : ");
		pw =  sc.nextLine();
	}
	static int selectMenu() {
		return (int)(sc.nextLine().charAt(0)-'0');
	}
	static boolean idOverlap(String id) {
		for(int i=0; i<mbs.size(); i++) {
			if(mbs.get(i).id.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	static void quit() {
		isQuit = true;
		System.out.println("프로그램을 종료합니다.");
	}
	static void sort(int i, int j) {
		Members tmp = mbs.get(i);
		mbs.set(i, mbs.get(j));
		mbs.set(j, tmp);
	}
	static void sortMembers() {
		for(int loop=1; loop<mbs.size(); loop++) {
			boolean isSortFin = true;
			for(int i=0; i<mbs.size()-1; i++) {
				Members[] m = {mbs.get(i), mbs.get(i+1)};
				for(int d=0; d<m[0].id.length(); d++) {
					if((m[0].id.length()>m[1].id.length() && d==m[1].id.length())
							|| (m[0].id.charAt(d)>m[1].id.charAt(d))) {
						sort(i, i+1);
						isSortFin = false;
						break;
					} else if(m[0].id.charAt(d)<m[1].id.charAt(d)) {
						break;
					}
				}
			}
			if(isSortFin)
				break;
		}
	}
	static void showHomeMenu() {
		System.out.println("------------------");
		System.out.println("1.전체회원조회");
		System.out.println("2.회원등록");
		System.out.println("3.회원검색");
		System.out.println("4.회원삭제");
		System.out.println("0.종료");
		System.out.println("------------------");
		System.out.print(">> ");
	}
	static void showSearchMenu() { 
		System.out.println("------------------");
		System.out.println("1.ID로 검색");
		System.out.println("2.이름으로 검색");
		System.out.println("0.처음으로");
		System.out.println("------------------");
		System.out.println(">> ");
	}
	static void doOrHomeMenu() {
		System.out.println("------------------");
		System.out.println("1.계속\t\t0.처음으로");
		System.out.println("------------------");
		System.out.print(">> ");
		switch(selectMenu()) {
		case 1:
			break;
		case 0:
			goHome = true;
			break;
		}
	}
	static void showMembers() {
		if(mbs.size()<1) {
			System.out.println("(등록된 회원이 없습니다)");
		} else {
			for(int i=0; i<mbs.size(); i++) {
				printMember(i);
			}
		}
	}
	static void printMember(int i) {
		System.out.println(mbs.get(i).id + " / " + mbs.get(i).name);
	}
	static void signIn() {
		while(true) {
			inputId();
			if(idOverlap(id)) {
				System.out.println("중복된 ID입니다. 다시 입력하세요.");
				continue;
			}
			inputName();
			inputPw();
			mbs.add(new Members(id, name, pw));
			break;
		}
		sortMembers();
	}
	static void searchId() {
		inputId();
		boolean chk = true;
		for(int i=0; i<mbs.size(); i++) {
			if(mbs.get(i).id.contains(id)) {
				printMember(i);
				chk = false;
			}
		}
		if(chk)
			System.out.println("미등록된 회원입니다.");
	}
	static void searchName() {
		inputName();
		boolean chk = true;
		for(int i=0; i<mbs.size(); i++) {
			if(mbs.get(i).name.contains(name)) {
				printMember(i);
				chk = false;
			}
		}
		if(chk)
			System.out.println("미등록된 회원입니다.");
	}
	static void searchMembers() {
		while(true) {
			showSearchMenu();
			switch((int)(sc.nextLine().charAt(0)-'0')) {
			case 1:
				searchId();
				break;
			case 2:
				searchName();
				break;
			case 0:
				goHome = true;
				break;
			}
			if(goHome)
				break;
		}
	}
	static void deleteMembers() {
		while(true) {
			inputId();
			inputName();
			inputPw();
			boolean doRmv = false;
			for(int i=0; i<mbs.size(); i++) {
				if(mbs.get(i).id.equals(id) 
						&& mbs.get(i).name.equals(name) 
						&& mbs.get(i).pw.equals(pw)) {
					doRmv = true;
					mbs.remove(i);
					System.out.println("회원을 삭제하였습니다.");
					break;
				}
			}
			if(!doRmv) {
				System.out.println("잘못된 입력입니다.");
			}
			doOrHomeMenu();
			if(goHome)
				break;
		}
	}
}

class Members {
	String id;
	String name;
	String pw;
	
	Members(String id, String name, String pw){
		this.id = id;
		this.name = name;
		this.pw = pw;
	}
}
