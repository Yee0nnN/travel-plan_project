
package travelplan.user;

import java.util.Scanner;

public class UserView {
    private static final Scanner sc = new Scanner(System.in);

    public static void menuDisplay() {
    	System.out.println();
    	System.out.println(" ======================================");
    	System.out.println("             [ '사용자'메뉴 ]");
    	System.out.println(" ──────────────────────────────────────");
        System.out.println(" 1. 로그인");
        System.out.println(" 2. 회원가입");
        System.out.println(" 3. 종료");
        System.out.println(" ──────────────────────────────────────");
        System.out.print(" [ 작업 번호 ]를 입력하세요: ");
    }

    public static void display(String message) {
        System.out.println(" [*] " + message);
    }

    public static String input(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }
}
