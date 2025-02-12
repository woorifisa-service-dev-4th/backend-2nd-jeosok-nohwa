package jeosok_nowha.backend;

import jeosok_nowha.backend.domain.chat.config.ChatConfig;
import jeosok_nowha.backend.domain.member.controller.MemberController;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.service.MemberService;
import jeosok_nowha.backend.domain.news.controller.NewsController;
import jeosok_nowha.backend.domain.news.repository.NewsRepository;
import jeosok_nowha.backend.domain.news.service.NewsService;
import jeosok_nowha.backend.domain.score.controller.ScoreController;

public class Main {
	public static void main(String[] args) {
		// 회원 관리 시스템 실행
		ChatConfig config = new ChatConfig();
		String host = config.getHost();
		int port = config.getPort();

		System.out.println(host);
		System.out.println(port);
		MemberRepository memberRepository = MemberRepository.getInstance();
		MemberService memberService = new MemberService(memberRepository);
		MemberController memberController = new MemberController(memberService);
		memberController.run();
		// 뉴스 관리 시스템 실행



	}
}
