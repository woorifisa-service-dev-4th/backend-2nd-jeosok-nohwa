package jeosok_nowha.backend.domain.news.controller;

import jeosok_nowha.backend.domain.member.controller.MemberController;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.service.MemberService;
import jeosok_nowha.backend.domain.news.service.NewsService;

import java.util.Scanner;

public class NewsController {
	private final NewsService newsService;
	private final Scanner scanner;

	public NewsController(NewsService newsService) {
		this.newsService = newsService;
		this.scanner = new Scanner(System.in);
	}

	// 뉴스 추가
	public void createNews() {
		System.out.print("📝 뉴스 제목: ");
		String title = scanner.nextLine();
		System.out.print("🔗 뉴스 링크: ");
		String link = scanner.nextLine();
		System.out.print("🏢 언론사 이름: ");
		String press = scanner.nextLine();

		newsService.createNews(title,link,press);
	}

	// 뉴스 조회
	public void newsAlllist() {
		newsService.newsAlllist();
	}

	// 뉴스 수정
	public void updateNews() {
		System.out.print("✏ 수정할 뉴스 ID: ");
		int id = scanner.nextInt();
		scanner.nextLine();
		System.out.print("🆕 새로운 제목: ");
		String newTitle = scanner.nextLine();
		System.out.print("🆕 새로운 링크: ");
		String newLink = scanner.nextLine();
		System.out.print("🏢 새로운 언론사 이름: ");
		String newPress = scanner.nextLine();

		newsService.updateNews(id, newTitle, newLink, newPress);
	}

	// ✅ 뉴스 삭제
	public void deleteNews() {
		System.out.print("🗑 삭제할 뉴스 ID: ");
		int id = scanner.nextInt();
		scanner.nextLine();
		newsService.deleteNews(id);
	}

	public void run() {
		while (true) {
			System.out.println("\n===== 뉴스 관리 시스템 =====");
			System.out.println("1. 뉴스 추가");
			System.out.println("2. 뉴스 전체 조회");
			System.out.println("3. 뉴스 수정");
			System.out.println("4. 뉴스 삭제");
			System.out.println("5. 메인 화면");
			System.out.print("선택: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 클리어

			switch (choice) {
				case 1:
					createNews();
					break;
				case 2:
					newsAlllist();
					break;
				case 3:
					updateNews();
					break;
				case 4:
					deleteNews();
					break;
				case 5:
					MemberRepository memberRepository = MemberRepository.getInstance();
					MemberService memberService = new MemberService(memberRepository);
					MemberController memberController = new MemberController(memberService);
					memberController.run();
				default:
					System.out.println("올바른 번호를 입력하세요.");
			}
		}
	}
}
