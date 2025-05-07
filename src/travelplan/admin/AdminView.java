
package travelplan.admin;

import java.util.Scanner;

public class AdminView {
    private static final Scanner sc = new Scanner(System.in);

    public static void menuDisplay() {
    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '관리자'메뉴 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 사용자 여행 조회");
        System.out.println(" 2. 추천 여행 관리");
        System.out.println(" 3. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }

    public static void loginMenu() {
       	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '관리자'로그인 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 로그인");
        System.out.println(" 2. 회원가입");
        System.out.println(" 3. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }

    public static String input(String label) {
        System.out.print(label);
        return sc.nextLine();
    }

    public static void display(String msg) {
        System.out.println(" [*] " + msg);
    }
}
