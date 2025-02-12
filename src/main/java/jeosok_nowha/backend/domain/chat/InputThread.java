// package jeosok_nowha.backend.domain.chat;
//
// import java.io.BufferedReader;
//
// public class InputThread extends Thread{
// 	BufferedReader br;
// 	public InputThread(BufferedReader br){
// 		this.br = br;
// 	}
//
// 	@Override
// 	public void run() {
// 		try{
// 			String line = null;
// 			while ((line = br.readLine()) != null) {
// 				System.out.println(line);
// 			}
// 		}catch (Exception ex){
// 			System.out.println("....");
// 		}
// 	}
// }
