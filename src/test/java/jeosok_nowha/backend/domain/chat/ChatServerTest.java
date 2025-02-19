package jeosok_nowha.backend.domain.chat;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class ChatServerTest {

	private ChatServer chatServer;
	private ServerSocket mockServerSocket;
	private List<ChatThread> clients;

	@BeforeEach
	void setUp() throws IOException {
		mockServerSocket = mock(ServerSocket.class); // Mock ServerSocket 생성
		clients = Collections.synchronizedList(new ArrayList<>());
		chatServer = new ChatServer(mockServerSocket); // Mock 주입
	}

	@Test
	@DisplayName("서버가 클라이언트 연결을 수락해야 함")
	void should_AcceptClientConnection() throws IOException {
		// Given: Mock 클라이언트 소켓 생성
		Socket mockSocket = mock(Socket.class);

		// Mock 서버가 accept() 호출될 때 가짜 클라이언트 소켓 반환
		when(mockServerSocket.accept()).thenReturn(mockSocket);

		// When: 서버 실행
		Thread serverThread = new Thread(() -> chatServer.startServer());
		serverThread.start();

		// Then: 서버가 최소 1번 이상 클라이언트를 수락했는지 확인
		verify(mockServerSocket, timeout(3000).atLeastOnce()).accept();

		// 서버 종료
		serverThread.interrupt();
	}

	/*@Test
	void should_HandleMultipleClients() throws IOException {
		// Given: 여러 개의 Mock 소켓 생성
		List<Socket> mockSockets = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			mockSockets.add(mock(Socket.class));
		}

		// accept() 호출될 때 여러 개의 클라이언트 소켓 반환
		when(mockServerSocket.accept()).thenReturn(
			mockSockets.get(0), mockSockets.get(1), mockSockets.get(2)
		);

		// When: 서버가 여러 클라이언트를 수락하는지 테스트
		Thread serverThread = new Thread(() -> chatServer.startServer());
		serverThread.start();

		// Then: accept()가 여러 번 호출되었는지 검증
		verify(mockServerSocket, timeout(5000).times(3)).accept();

		// 서버 종료
		serverThread.interrupt();
	}*/
}
