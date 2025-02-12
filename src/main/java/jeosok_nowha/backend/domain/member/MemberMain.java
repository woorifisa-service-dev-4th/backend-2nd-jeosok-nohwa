package jeosok_nowha.backend.domain.member;

import jeosok_nowha.backend.domain.member.controller.MemberController;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.service.MemberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberMain {
	public static void main(String[] args) {
		MemberRepository userRepository = MemberRepository.getInstance();
		MemberService userService = new MemberService(userRepository);
		MemberController userController = new MemberController(userService);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				// ✅ 메뉴 출력
				System.out.println("\n---------------------- [User Management System] ----------------------");
				System.out.println("  1. 회원가입                  2. 저장된 사용자 목록 조회");
				System.out.println("  99. 종료");
				System.out.println("---------------------------------------------------------------------");
				System.out.print("메뉴 선택: ");

				String input = reader.readLine();
				if (input == null) continue;

				switch (input.trim()) {
					case "1":
						System.out.println("\n[회원가입]");
						userController.signUp();
						break;
					case "2":
						System.out.println("\n[📌 저장된 사용자 목록]");
						userRepository.printUsers();
						break;
					case "99":
						System.out.println("\n✅ 프로그램 종료.");
						return;
					default:
						System.out.println("❌ 잘못된 입력입니다. 다시 입력하세요.");
				}
			} catch (IOException e) {
				System.out.println("🚨 입력 오류: " + e.getMessage());
			}
		}
	}
}
