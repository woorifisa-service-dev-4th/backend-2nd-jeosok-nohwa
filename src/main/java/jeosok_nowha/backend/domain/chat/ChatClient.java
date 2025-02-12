package jeosok_nowha.backend.domain.chat;

import jeosok_nowha.backend.global.common.config.ChatConfig;
import java.io.*;
import java.net.Socket;

public class ChatClient {
	private final String name;
	private final Socket socket;
	private final BufferedReader serverInput;
	private final PrintWriter serverOutput;
	private final BufferedReader keyboardInput;

	public ChatClient(String name) throws Exception {
		this.name = name;

		// 🔥 ChatConfig 사용해서 YML에서 설정 가져오기
		ChatConfig config = new ChatConfig();
		String host = config.getHost();
		int port = config.getPort();

		System.out.println("✅ 서버에 연결 중... " + host + ":" + port);
		this.socket = new Socket(host, port);

		this.serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		this.serverOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
		this.keyboardInput = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		startChat();
	}

	private void startChat() {
		try {
			// 닉네임 전송
			serverOutput.println(name);
			serverOutput.flush();

			// 서버 메시지를 백그라운드에서 수신
			new InputThread(serverInput).start();

			// 사용자 입력을 서버로 전송
			sendMessageLoop();

		} catch (Exception ex) {
			System.out.println("❌ 채팅 중 오류 발생: " + ex.getMessage());
		} finally {
			closeResources();
		}
	}

	private void sendMessageLoop() throws IOException {
		String message;
		while (true) {
			System.out.print(name + ": "); // 🚀 사용자 이름 포함해서 출력
			System.out.flush(); // 🚀 버퍼 강제 출력

			message = keyboardInput.readLine(); // 🚀 사용자 입력 받기
			if (message == null || message.trim().isEmpty()) continue; // 🚀 빈 메시지 방지

			// ✅ 사용자가 `/quit`를 입력하면 종료
			if ("/quit".equalsIgnoreCase(message)) {
				serverOutput.println("quit");
				serverOutput.flush();
				break;
			}

			serverOutput.println(name + ": " + message);
			serverOutput.flush(); // 🚀 메시지 즉시 전송
		}
	}

	private void closeResources() {
		try { if (serverInput != null) serverInput.close(); } catch (IOException ex) { System.out.println("❌ BufferedReader 종료 오류"); }
		try { if (serverOutput != null) serverOutput.close(); } catch (Exception ex) { System.out.println("❌ PrintWriter 종료 오류"); }
		try {
			if (socket != null) {
				System.out.println("✅ 소켓 종료...");
				socket.close();
			}
		} catch (IOException ex) {
			System.out.println("❌ 소켓 종료 오류");
		}
	}


}
