package jeosok_nowha.backend.domain.chat.util;

public class ChatPrintUtil {

	static final String CHAT_START = "채팅을 시작합니다.";
	static final String CHAT_END_QUESTION = "채팅을 종료하고 싶으시면 '/Q'를 입력해주세요.";
	public static void print(String message) {
		System.out.println(message);
	}
	public static void printChatStart(){
		System.out.println(CHAT_START);
	}

	public static void printChatEndQuestion(){
		System.out.println(CHAT_END_QUESTION);
	}
}
