package jeosok_nowha.backend.global.common.utils;

public class PrintUtils {
	private static final String Banner = ""
		+ "     ██╗███████╗ ██████╗ ███████╗ ██████╗ ██╗  ██╗     ███╗   ██╗ ██████╗ ██╗    ██╗██╗  ██╗ █████╗ \n"
		+ "     ██║██╔════╝██╔═══██╗██╔════╝██╔═══██╗██║ ██╔╝     ████╗  ██║██╔═══██╗██║    ██║██║  ██║██╔══██╗\n"
		+ "     ██║█████╗  ██║   ██║███████╗██║   ██║█████╔╝█████╗██╔██╗ ██║██║   ██║██║ █╗ ██║███████║███████║\n"
		+ "██   ██║██╔══╝  ██║   ██║╚════██║██║   ██║██╔═██╗╚════╝██║╚██╗██║██║   ██║██║███╗██║██╔══██║██╔══██║\n"
		+ "╚█████╔╝███████╗╚██████╔╝███████║╚██████╔╝██║  ██╗     ██║ ╚████║╚██████╔╝╚███╔███╔╝██║  ██║██║  ██║\n"
		+ " ╚════╝ ╚══════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚═╝  ╚═╝     ╚═╝  ╚═══╝ ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝\n"
		+ "                                                                                                    ";
	public static void print(String message) {
		System.out.println(message);
	}
	public static void printBanner() {
		System.out.println(Banner);
	}

	public static void printError(String message) {
		System.err.println(message);
	}
}
