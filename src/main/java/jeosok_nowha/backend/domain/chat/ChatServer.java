package jeosok_nowha.backend.domain.chat;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jeosok_nowha.backend.domain.chat.config.ChatConfig;



public class ChatServer {
	private List<ChatThread> clients;
	private int port;

	public ChatServer() {
		// 🔥 YAML 설정에서 포트 번호 가져오기
		ChatConfig config = new ChatConfig();
		this.port = config.getPort();
		this.clients = Collections.synchronizedList(new ArrayList<>());

		startServer();
	}

	private void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("✅ 채팅 서버 시작! (포트: " + port + ")");

			while (true) {
				Socket socket = serverSocket.accept();
				ChatThread chatThread = new ChatThread(socket, clients);
				chatThread.start();
			}
		} catch (Exception e) {
			System.out.println("❌ 서버 오류 발생: " + e.getMessage());
		}
	}

}

