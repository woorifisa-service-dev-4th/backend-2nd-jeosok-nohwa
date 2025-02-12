package jeosok_nowha.backend.domain.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ChatThread extends Thread {
	private Socket socket;
	private List<ChatThread> clients;
	private PrintWriter pw;
	private String name;

	public ChatThread(Socket socket, List<ChatThread> clients) {
		this.socket = socket;
		this.clients = clients;
		this.clients.add(this);
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			// 클라이언트가 닉네임을 보냄
			this.name = br.readLine();
			broadcast(name + "님이 입장하셨습니다.");

			String message;
			while ((message = br.readLine()) != null) {
				if ("/quit".equalsIgnoreCase(message)) {
					break;
				}
				broadcast(name + ": " + message);
			}
		} catch (Exception e) {
			System.out.println("클라이언트 연결 종료: " + e.getMessage());
		} finally {
			try {
				clients.remove(this);
				socket.close();
				broadcast(name + "님이 퇴장하셨습니다.");
			} catch (Exception e) {
				System.out.println("소켓 닫기 오류: " + e.getMessage());
			}
		}
	}

	private void broadcast(String message) {
		synchronized (clients) {
			for (ChatThread client : clients) {
				client.sendMessage(message);
			}
		}
	}

	private void sendMessage(String message) {
		if (pw != null) {
			pw.println(message);
		}
	}
}
