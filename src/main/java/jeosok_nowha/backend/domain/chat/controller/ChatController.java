package jeosok_nowha.backend.domain.chat.controller;

import jeosok_nowha.backend.domain.chat.ChatServer;
import jeosok_nowha.backend.domain.chat.ChatClient;
import jeosok_nowha.backend.domain.chat.util.ChatPrintUtil;

import java.util.Scanner;

public class ChatController {
	private static ChatServer chatServer;

	public void run(String nickname) {
		Scanner scanner = new Scanner(System.in);

		try {
			ChatPrintUtil.printChatStart();
			System.out.println("💬 채팅 실행 모드를 선택하세요:");
			System.out.println("1. 서버 실행");
			System.out.println("2. 클라이언트만 실행");
			System.out.print("> ");

			String mode = scanner.nextLine().trim();

			if ("1".equals(mode)) {
				// ✅ 서버 실행 (백그라운드 스레드에서 실행)
				if (chatServer == null) {
					System.out.println("✅ 서버 시작...");
					chatServer = new ChatServer();
					new Thread(() -> chatServer.startServer()).start();
				}

				// ✅ 서버가 준비될 시간을 주기 위해 대기 (필요할 경우)
				Thread.sleep(1000);

				/*// ✅ 클라이언트 실행
				System.out.println("✅ 클라이언트 실행");
				new ChatClient(nickname);*/

			} else if ("2".equals(mode)) {


				new ChatClient(nickname);

			} else {
				System.out.println("❌ 잘못된 입력입니다. 다시 실행하세요.");
			}
		} catch (Exception e) {
			System.out.println("❌ 채팅 오류 발생: " + e.getMessage());
		} finally {
			System.out.println("✅ 채팅 종료");
			scanner.close();
		}
	}
}
