package jeosok_nowha.backend.domain.chat.controller;

import jeosok_nowha.backend.domain.chat.ChatServer;
import jeosok_nowha.backend.domain.chat.ChatClient;
import jeosok_nowha.backend.domain.chat.util.ChatPrintUtil;

public class ChatController {
	private static final String USER = "user";
	private ChatServer chatServer;

	public void run() {
		try {
			// 채팅 시작 메시지 출력
			ChatPrintUtil.printChatStart();
			ChatPrintUtil.printChatEndQuestion();

			// 서버가 실행되지 않았다면 시작
			if (chatServer == null) {
				chatServer = new ChatServer();
			}

			// 클라이언트 실행
			new ChatClient(USER);

		} catch (Exception e) {
			System.out.println("❌ 채팅 오류 발생: " + e.getMessage());
		} finally {
			System.out.println("✅ 채팅 종료");
		}
	}
}
