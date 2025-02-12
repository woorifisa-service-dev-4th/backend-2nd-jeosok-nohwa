package jeosok_nowha.backend.domain.chat;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ChatThread extends Thread {
	private final Socket socket;
	private final List<ChatThread> clients;
	private PrintWriter output;
	private String name;

	public ChatThread(Socket socket, List<ChatThread> clients) {
		this.socket = socket;
		this.clients = clients;
		this.clients.add(this);
	}

	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			// ✅ 클라이언트의 닉네임을 첫 메시지로 받음
			this.name = input.readLine();
			broadcast("✅ " + name + " 님이 입장하셨습니다.");

			String message;
			while ((message = input.readLine()) != null) {
				if ("/quit".equalsIgnoreCase(message)) {
					break;
				}
				broadcast(name + ": " + message);
			}

		} catch (IOException e) {
			System.out.println("❌ 클라이언트 연결 오류: " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	private void broadcast(String message) {
		synchronized (clients) {
			for (ChatThread client : clients) {
				if (client != this) { // 자신에게는 전송하지 않음
					client.sendMessage(message);
				}
			}
		}
	}

	private void sendMessage(String message) {
		if (output != null) {
			output.println(message);
			output.flush();
		}
	}

	private void closeConnection() {
		try {
			clients.remove(this);
			socket.close();
			broadcast("❌ " + name + " 님이 퇴장하셨습니다.");
		} catch (IOException e) {
			System.out.println("❌ 소켓 종료 오류: " + e.getMessage());
		}
	}
}