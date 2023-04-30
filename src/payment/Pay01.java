package payment;

import java.util.ArrayList;
import java.util.Scanner;

class Paymethod{
	String cardNum;
	int expireDateMon;
	int expireDateYear;
	int cvvNum;
	String nameOncard;
	Paymethod(String cardNum, int expireDateMon,int expireDateYear,int cvvNum, String nameOncard){
		this.cardNum = cardNum;
		this.expireDateMon = expireDateMon;
		this.expireDateYear = expireDateYear;
		this.cvvNum = cvvNum;
		this.nameOncard = nameOncard;
	}
}
class Testlist{
	String title;
	int price;
	Testlist(String title, int price){
		this.title = title;
		this.price = price;
	}
}
public class Pay01 {
	static ArrayList<Testlist> gameList = new ArrayList<Testlist>();
	static ArrayList<Paymethod> payList = new ArrayList<Paymethod>();
	static Scanner sc = new Scanner(System.in);
	static void showAllgame(){
		gameList.add(new Testlist("Alba - A Wildlife Adventure",17500));
		gameList.add(new Testlist("Alien: Isolation",43000));
		gameList.add(new Testlist("Deponia: The Complete Journey",37000));
		gameList.add(new Testlist("Dishonored - Definitive Edition",22000));
		gameList.add(new Testlist("Frontier Hunter: Erza's Wheel of Fortune",20000));

		for(int i = 0; i<=gameList.size()-1; i++) {
			Testlist gl = gameList.get(i);
			System.out.println((i+1)+"번."+gl.title);
			System.out.println("\t\t"+ gl.price + "원");
		}
		
	}
	static void selectGame() {
		System.out.print("구매할 게임을 선택: ");
		int input = sc.nextInt();
		for(int i= 0; i<=gameList.size()-1; i++) {
			if(input-1 == i) {
				System.out.println(gameList.get(i).title + "을 선택하셨습니다.");
				System.out.println(gameList.get(i).price + "원을 결제합니다.");
			}
		}
		
	}
	static String payinfoNum() {
		String cardNumber = "";
		while(true) {
			System.out.print("카드번호 16자리 입력:");
			String cardNum = sc.next();
			if(cardNum.length()>16&&cardNum.length()<16) {
				System.out.println("잘못입력하였습니다. 16자리의 카드번호를 입력하세요");
				continue;
			}else {
				cardNumber = cardNum;
				break;
			}
		}
		return cardNumber;
	}
	static int payinfoDateMon() {
		System.out.print("카드유효기간(DD): ");
		int expireDateMon = sc.nextInt();
		return expireDateMon;
		
	}
	static int payinfoDateYear() {
		System.out.print("카드유효기간(YY): ");
		int expireDateYear = sc.nextInt();
		return expireDateYear;
		
	}
	static int payinfoCvv() {
		System.out.print("CVV: ");
		int cvvNum = sc.nextInt();
		return cvvNum;
		
	}
	static String payinfoName() {
		System.out.print("카드상의 이름:");
		String nameOncard = sc.next();
		return nameOncard;
	}
	static void showCard() {
		for(int i=0;i<=payList.size()-1; i++) {
			Paymethod pay = payList.get(i);
			System.out.println(pay.cardNum+" "+pay.expireDateMon+"/"+pay.expireDateYear+" "+
			pay.cvvNum+" "+pay.nameOncard);
		}
	}
	public static void main(String[] args) {
		// 카드결제 시스템 테스트파일
		showAllgame();
		selectGame();
		String cardNum = payinfoNum();
		int expireMon = payinfoDateMon();
		int expireYear = payinfoDateYear();
		int cvv = payinfoCvv();
		String name = payinfoName(); 
		
		payList.add(new Paymethod(cardNum,expireMon,expireYear,cvv,name));
		showCard();
	}

}
