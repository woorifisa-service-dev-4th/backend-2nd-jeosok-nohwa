package jeosok_nowha.backend.utils;

public class PrintUtils {
	private static final String PRINT_GET_RANKING = "=== 랭킹 ===";

	public static void print(String message) {
		System.out.println(message);
	}

	public static void printGetRanking(){
		print(PRINT_GET_RANKING);
	}
}
