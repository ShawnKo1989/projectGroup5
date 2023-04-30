package register;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import example.Dbfortest;


class Idcheck{
	static Scanner sc = new Scanner(System.in);
	static String identification() throws Exception{
		String trueId="";
		while(true) {
			boolean check = false;
			ResultSet rs = Dbfortest.m("SELECT id FROM register");
			System.out.print("ID를 입력해주세요 : ");
			String userID = sc.next();
			
			while(rs.next()) {
				String id = rs.getString("id");
				if(id.equals(userID)) {
					System.out.println("이미 사용중인 ID 입니다. 다시 입력해 주세요.");
					check = true;
					break;
				}
			}
			if(check) {
				continue;
			}else {
				trueId=userID;
			}
			rs.close();
			return trueId;
		}
	
	}
}
class EmailCheck{
	static Scanner sc = new Scanner(System.in);
	static String emailCheck() throws Exception{
		String trueEmail = "";
		while(true) {
			boolean check = false;
			System.out.print("EMAIL을 입력해주세요 : ");
			String userEmail = sc.next();
			ResultSet rs = Dbfortest.m("SELECT email FROM register");
			while(rs.next()) {
				String email = rs.getString("email");
				if(email.equals(userEmail)) {
					System.out.println("이미 사용중인 E-mail 입니다. 다시 입력해 주세요.");
					check = true;
					break;
				}
			}
			if(check) {
				continue;
			}else {
				trueEmail = userEmail;
			}
			
			rs.close();
			return trueEmail;
		}
	}
}
public class Resgis01 {
	static Scanner sc = new Scanner(System.in);
	static String name() {		//중복값 허용됨
		System.out.print("가입자 이름 :");
		String name = sc.next();
		return name;
	}
	static String pw() {		// 대소문자 숫자가 최소 1개 들어가야됨
		String rpw = "";
		while(true) {
			ArrayList<Character>passArr = new ArrayList<Character>();
			boolean a = false;
			boolean b = false;
			boolean c = false;
			System.out.print("PASSWORD : ");
			String pw = sc.next();
			for(int i = 0; i<=pw.length()-1;i++) {
				passArr.add(pw.charAt(i));
				if(passArr.get(i)>='a'&&passArr.get(i)<='z') {
					a = true;
				}
				else if(passArr.get(i)>='A'&&passArr.get(i)<='Z') {
					b = true;
				}
				else if(passArr.get(i)>='0'&&passArr.get(i)<='9') {
					c = true;
				}
			}
			
			if(a&&b&&c) {
				conpass(pw);
				rpw = pw;
				break;
			}else {
				System.out.println("최소 1개의 대소문(영)문자와 숫자를 포함해야 합니다.");
				continue;
			}
		}
		return rpw;
	}
	static void conpass(String pw) {
		while(true) {
			System.out.print("비밀번호 확인 : ");
			String conpass = sc.next();
			if(pw.equals(conpass)) {
				System.out.println("비밀번호 확인이 완료되었습니다.");
				break;
			}else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요");
				continue;
			}
		}
	}
	static void showAll() throws Exception{
		System.out.println("\t----회원목록----");
		ResultSet rs = Dbfortest.m("SELECT*FROM register");
		while(rs.next()) {
			String name = rs.getString("name");
			String id = rs.getString("id");
			String email = rs.getString("email");
			String pw = rs.getString("pw");
			
			System.out.println(name+" / "+id+" / "+email+" / "+pw);
		}	
		
		rs.close();
		System.out.println();
	}
	static String mathRandom() {
		String rannum = "";
		for(int i = 1; i<=6; i++) {
			int ran = (int)(Math.random()*9);
			rannum += ran;
		}
		return rannum;
	}
	public static void naverMailSend(String name, String id, String email) {
        String host = "smtp.naver.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String user = "hotdog0818@naver.com"; // 패스워드
        String password = "abc1234!";       
        String confirmNum =mathRandom();

        // SMTP 서버 정보를 설정한다.
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // 메일 제목
            message.setSubject("5조 프로젝트를 위한 테스트 발송");

            // 메일 내용
            message.setText(confirmNum);

            // send the message
            Transport.send(message);
            System.out.println("본인인증을 위한 메일이 발송되었습니다.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        checkEmail(confirmNum);
    }
	static void checkEmail(String confirmNum) {
		while(true) {
			System.out.print("메일로 받은 인증번호를 입력:");
			String checkNum = sc.next();
			if(confirmNum.equals(checkNum)) {
				System.out.println("인증이 완료되었습니다!!");
				break;
			}else {
				System.out.println("잘못된 인증번호 입니다. 확인후 다시 입력해 주세요.");
				continue;
			}
		}
			
	}
	public static void databaseRG(String name, String id,String email,String pw)throws Exception{
		Dbfortest.m("INSERT INTO register(name,id,email,pw) VALUES('"+name+"','"+id+"','"+email+"','"+pw+"')");
		Dbfortest.m("INSERT INTO idconn(id,status) VALUES('"+id+"','logout')"); // 주의 세미콜론(;)이 없어야 된다.
	}
	public static void main(String[] args) throws Exception {
		// 회원가입 테스트 파일
		while(true) {
			System.out.println("종료 : 0");
			String name = name();
			if(name.equals("0")) break;
			String id = Idcheck.identification();
			String email = EmailCheck.emailCheck();
			String pw = pw();
//			naverMailSend(name, id, email);
			System.out.println("회원가입이 완료되었습니다!");
			databaseRG(name,id,email,pw);
			showAll();
			
			
		}
	}
}

