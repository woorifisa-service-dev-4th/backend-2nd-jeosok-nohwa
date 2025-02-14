package jeosok_nowha.backend.domain.member.controller;

import static jeosok_nowha.backend.domain.member.util.MemberPrintUtil.*;

import jeosok_nowha.backend.domain.chat.controller.ChatController;
import jeosok_nowha.backend.domain.member.payload.request.SignUpRequest;
import jeosok_nowha.backend.domain.member.payload.request.SignInRequest;
import jeosok_nowha.backend.domain.member.service.MemberService;
import jeosok_nowha.backend.domain.member.util.MemberPrintUtil;
import jeosok_nowha.backend.domain.news.controller.NewsController;
import jeosok_nowha.backend.domain.news.repository.NewsRepository;
import jeosok_nowha.backend.domain.news.service.NewsService;
import jeosok_nowha.backend.domain.score.controller.ScoreController;
import jeosok_nowha.backend.global.common.utils.PrintUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class MemberController {
	private final MemberService userService;
	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public MemberController(MemberService userService) {
		this.userService = userService;
	}

	public void run() {
		PrintUtils.printBanner();
		while (true) {
			try {
				printMenu();
				String input = reader.readLine();
				if (input == null) continue;

				switch (input.trim()) {
					case "1":
						signIn();
						break;
					case "2":
						signUp();
						break;
					case "3":
						userService.getCurrentUserInfo();
						break;
					case "4":
						// 채팅 메뉴
						if(userService.currentUser == null) {
							MemberPrintUtil.printError("로그인 후 이용 가능합니다.");
							break;
						}
						String currentUser = userService.currentUser.getNickname();
						ChatController chatController = new ChatController();
						chatController.run(currentUser);
						continue; // 채팅 종료 후 메인 메뉴로 돌아가기
					case "5":
						NewsRepository newsRepository = new NewsRepository();
						NewsService newsService = new NewsService(newsRepository);
						NewsController newsController = new NewsController(newsService);
						newsController.run();
						break;
					case "6":
						ScoreController scoreController = new ScoreController();
						scoreController.getScore();
						break;
					case "99":
						System.exit(0);
						break; // 메인으로 돌아가기
					default:
						MemberPrintUtil.printError("올바른 번호를 입력하세요.");
				}
			} catch (IOException e) {
				MemberPrintUtil.printError("입력 오류 발생: " + e.getMessage());
			}
		}
	}

	public void signUp() throws IOException {
		MemberPrintUtil.printSignUpPrompt();

		String nickname;
		while (true) {
			nickname = MemberPrintUtil.readInput("닉네임: ");
			if (!nickname.isEmpty()) break;
			MemberPrintUtil.printError("닉네임은 필수 입력값입니다.");
		}

		String email;
		while (true) {
			email = MemberPrintUtil.readInput("이메일: ");
			if (isValidEmail(email)) break;
			MemberPrintUtil.printError("❌ 유효한 이메일 형식을 입력하세요 (예: user@example.com)");
		}

		double height;
		while (true) {
			try {
				height = Double.parseDouble(MemberPrintUtil.readInput("키 (cm): "));
				if (height > 0 && height <= 300) break;
				MemberPrintUtil.printError("❌ 키는 0보다 크고 300cm 이하이어야 합니다.");
			} catch (NumberFormatException e) {
				MemberPrintUtil.printError("❌ 숫자 형식으로 입력하세요.");
			}
		}

		double weight;
		while (true) {
			try {
				weight = Double.parseDouble(MemberPrintUtil.readInput("몸무게 (kg): "));
				if (weight > 0 && weight <= 300) break;
				MemberPrintUtil.printError("❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.");
			} catch (NumberFormatException e) {
				MemberPrintUtil.printError("❌ 숫자 형식으로 입력하세요.");
			}
		}

		String password;
		while (true) {
			password = MemberPrintUtil.readInput("비밀번호: ");
			if (!password.isEmpty()) break;
			MemberPrintUtil.printError("❌ 비밀번호는 필수 입력값입니다.");
		}

		SignUpRequest request = new SignUpRequest(email, password, nickname, height, weight);
		userService.signUp(request);
	}

	public void signIn() throws IOException {
		MemberPrintUtil.printSignInPrompt();
		String email = MemberPrintUtil.readInput("이메일: ");
		String password = MemberPrintUtil.readInput("비밀번호: ");

		SignInRequest request = new SignInRequest(email, password);
		userService.signIn(request);
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if (!Pattern.matches(emailRegex, email)) {
			return false;
		}

		String[] validDomains = {
			"gmail.com", "naver.com", "daum.net", "yahoo.com", "outlook.com", "hotmail.com", "kakao.com"
		};

		String domain = email.substring(email.lastIndexOf("@") + 1);

		for (String validDomain : validDomains) {
			if (domain.equalsIgnoreCase(validDomain)) {
				return true;
			}
		}
		MemberPrintUtil.printError("❌ 허용되지 않은 이메일 도메인입니다. (예: @gmail.com, @naver.com 등)");
		return false;
	}

	// 테스트를 위한 오버로딩 추가
	public void signUp(String nickname, String email, double height, double weight, String password) {
		if (nickname == null || nickname.isEmpty()) {
			throw new IllegalArgumentException("닉네임은 필수 입력값입니다.");
		}
		if (!isValidEmail(email)) {
			throw new IllegalArgumentException("❌ 유효한 이메일 형식을 입력하세요 (예: user@example.com)");
		}
		if (height <= 0 || height > 300) {
			throw new IllegalArgumentException("❌ 키는 0보다 크고 300cm 이하이어야 합니다.");
		}
		if (weight <= 0 || weight > 300) {
			throw new IllegalArgumentException("❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("❌ 비밀번호는 필수 입력값입니다.");
		}

		// 정상적으로 회원가입 요청 객체 생성 후 가입
		SignUpRequest request = new SignUpRequest(email, password, nickname, height, weight);
		userService.signUp(request);
	}


}
