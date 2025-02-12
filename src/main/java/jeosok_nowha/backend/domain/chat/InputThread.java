
package jeosok_nowha.backend.domain.chat;
import java.io.BufferedReader;

public class InputThread extends Thread {
	BufferedReader br;
	public InputThread(BufferedReader br){
		this.br = br;
	}

	@Override
	public void run() {
		try {
			String message;
			while ((message = br.readLine()) != null) {
				System.out.println(message);
			}
		} catch (Exception e) {
			System.out.println("서버 연결 종료");
		}
	}
}