package jeosok_nowha.backend.domain.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jeosok_nowha.backend.global.common.config.ChatConfig;

public class ChatServer {
	private final List<ChatThread> clients;
	private final ServerSocket serverSocket;

	public ChatServer(ServerSocket serverSocket) {
		this.clients = Collections.synchronizedList(new ArrayList<>());
		this.serverSocket = serverSocket;
	}



	public void startServer() {
		String host = "192.168.0.28";
		int port = 8888;

		try {
			System.out.println("✅ 채팅 서버가 시작되었습니다.");

			while (!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println("🔗 새로운 클라이언트 연결됨: " + socket.getInetAddress());

				ChatThread chatThread = new ChatThread(socket, clients);
				chatThread.start();
			}
		} catch (Exception e) {
			System.out.println("❌ 서버 오류 발생: " + e.getMessage());
		} finally {
			closeServer();
		}
	}

	public void closeServer() {
		try {
			if (!serverSocket.isClosed()) {
				serverSocket.close();
			}
			clients.clear();
			System.out.println("🔻 채팅 서버가 정상적으로 종료되었습니다.");
		} catch (Exception e) {
			System.out.println("❌ 서버 종료 중 오류 발생: " + e.getMessage());
		}
	}
}
