

import jeosok_nowha.backend.domain.chat.InputThread;
import jeosok_nowha.backend.domain.chat.config.ChatConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
	private String name;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private BufferedReader keyboard;

	public ChatClient(String name) throws Exception {
		this.name = name;

		// 🔥 ChatConfig 사용해서 YML에서 설정 가져오기
		ChatConfig config = new ChatConfig();
		String host = config.getHost();
		int port = config.getPort();

		System.out.println("✅ 서버에 연결 중... " + host + ":" + port);
		this.socket = new Socket(host, port);

		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
		this.keyboard = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		startChat();
	}

	private void startChat() {
		try {
			// 닉네임 전송
			pw.println(name);
			pw.flush();

			// 서버 메시지를 백그라운드에서 수신
			InputThread inputThread = new InputThread(br);
			inputThread.start();

			// 사용자 입력을 서버로 전송
			String line;
			while ((line = keyboard.readLine()) != null) {
				if ("/quit".equalsIgnoreCase(line)) {
					pw.println("/quit");
					break;
				}
				pw.println(line);
			}
		} catch (Exception ex) {
			System.out.println("❌ 채팅 중 오류 발생: " + ex.getMessage());
		} finally {
			closeResources();
		}
	}

	private void closeResources() {
		try {
			if (br != null)
				br.close();
		} catch (Exception ex) {
			System.out.println("❌ BufferedReader 종료 오류");
		}
		try {
			if (pw != null)
				pw.close();
		} catch (Exception ex) {
			System.out.println("❌ PrintWriter 종료 오류");
		}
		try {
			if (socket != null) {
				System.out.println("✅ 소켓 종료...");
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("❌ 소켓 종료 오류");
		}
	}
}