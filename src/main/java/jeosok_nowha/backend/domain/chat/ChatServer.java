package jeosok_nowha.backend.domain.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jeosok_nowha.backend.global.common.config.ChatConfig;

public class ChatServer {
	private final List<ChatThread> clients;


	public ChatServer() {
		this.clients = Collections.synchronizedList(new ArrayList<>());
	}

	public void startServer() {
		/*
		ChatConfig config = new ChatConfig();
		String host = config.getHost();
		int port = config.getPort();

		 */
		String host = "192.168.0.28";
		int port = 8888;
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("✅ 채팅 서버가 시작되었습니다.");

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
