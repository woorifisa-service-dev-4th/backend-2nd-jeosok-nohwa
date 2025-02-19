package jeosok_nowha.backend.domain.chat.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import jeosok_nowha.backend.domain.chat.ChatServer;
import jeosok_nowha.backend.domain.chat.ChatClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

	private ChatController chatController;

	@Mock
	private ByteArrayOutputStream outputStream;

	@Mock
	private ChatServer mockChatServer;

	@Mock
	private ServerSocket mockServerSocket;

	@BeforeEach
	void setUp() throws Exception {
		// 테스트 실행 전에 ChatController 객체 생성 및 System.out 변경
		chatController = new ChatController(mockServerSocket);
		outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void should_StartServer_When_ModeIs1() throws Exception {
		// Given: 사용자 입력을 "1"으로 설정하여 서버 실행 모드를 테스트
		String input = "1\n";
		String testUserNickname = "testUser";
		String outputMessage = "✅ 서버 시작...";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		// ChatServer의 startServer()를 실제 실행하지 않도록 모킹
		willDoNothing().given(mockChatServer).startServer();

		// When: 서버 실행 모드 실행
		chatController.run(testUserNickname);

		// Then: 서버 시작 메시지가 출력되었는지 확인
		String output = outputStream.toString();
		assertThat(output).contains(outputMessage);
	}

	@Test
	void should_StartClient_When_ModeIs2() throws Exception {
		// Given: 사용자 입력을 "2"로 설정하여 클라이언트 실행 모드를 테스트
		String input = "2\n";
		String outputMessage = "💬 채팅 실행 모드를 선택하세요:";
		String testUserNickname = "testUser";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		// ChatClient 객체를 생성하지 않고 모킹하여 실행 검증
		ChatClient mockChatClient = mock(ChatClient.class);

		// When: 클라이언트 실행 모드 실행
		chatController.run(testUserNickname);

		// Then: 클라이언트 실행 메시지가 출력되었는지 확인
		String output = outputStream.toString();
		assertThat(output).contains(outputMessage);
	}

	@Test
	void should_PrintErrorMessage_When_InvalidInput() {
		// Given: 잘못된 입력 "3"을 설정하여 예외 처리를 테스트
		String input = "3\n";
		String testUserNickname = "testUser";
		String outputMessage = "❌ 잘못된 입력입니다. 다시 실행하세요.";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		// When: 잘못된 입력 실행
		chatController.run(testUserNickname);

		// Then: 오류 메시지가 출력되었는지 확인
		String output = outputStream.toString();
		assertThat(output).contains(outputMessage);
	}
}
