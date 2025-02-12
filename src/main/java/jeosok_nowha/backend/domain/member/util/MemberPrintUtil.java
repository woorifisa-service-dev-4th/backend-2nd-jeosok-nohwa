package jeosok_nowha.backend.domain.member.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberPrintUtil {
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final String LINE = "======================================================";

	public static void print(String message) {
		System.out.println(message);
	}

	public static void printLine() {
		print(LINE);
	}

	public static void printMenu() {
		print("\n---------------------- [Jesoknowha System] ----------------------");
		print("  1. 로그인                      2. 회원가입");
		print("  3. 현재 로그인된 사용자 정보 조회  4. 채팅");
		print("  5. 뉴스                        99. 종료"  );
		print("---------------------------------------------------------------------");
		print("기능 선택: ");
	}

	public static void printSignUpPrompt() {
		print("\n[회원가입] 정보를 입력하세요.");
	}

	public static void printSignInPrompt() {
		print("\n[로그인] 정보를 입력하세요.");
	}

	public static void printUserNotLoggedIn() {
		print("❌ 로그인된 사용자가 없습니다. 먼저 로그인하세요.");
	}

	public static void printCurrentUserInfo(String id, String nickname, String email, double height, double weight, double bmi, String role) {
		print("\n--- 현재 로그인된 사용자 정보 ---");
		print("📌 ID: " + id);
		print("📌 닉네임: " + nickname);
		print("📌 이메일: " + email);
		print("📌 키: " + height + "cm");
		print("📌 몸무게: " + weight + "kg");
		print("📌 BMI: " + bmi);
		print("📌 역할: " + role);
		print("-----------------------------");
	}

	// ✅ 입력을 통합하여 처리하는 유틸 메서드 추가
	public static String readInput(String prompt) throws IOException {
		print(prompt);
		return reader.readLine().trim();
	}

	public static void printError(String message) {
		print("🚨 " + message);
	}
}
