package jeosok_nowha.backend.domain.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		// 동시성 문제를 해결하기 위해서..!
		List<ChatThread> list = Collections.synchronizedList(new ArrayList<>());

		while(true) {
			Socket socket = serverSocket.accept();
			ChatThread chatThread = new ChatThread(socket, list);
			chatThread.start();
		}
	}
}
