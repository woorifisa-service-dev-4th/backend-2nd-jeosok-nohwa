package jeosok_nowha.backend.domain.news.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jeosok_nowha.backend.domain.news.service.NewsService;

class NewsControllerTest {

	@InjectMocks
	private NewsController newsController;
	@Mock
	private NewsService newsService;

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	@DisplayName("뉴스 생성 테스트")
	void testCreateNews()  throws InterruptedException{
		// 입력값 시뮬레이션
		String input =
			"새로운 제목" + System.lineSeparator() +
			"https://new.com" + System.lineSeparator() +
			"새로운 언론사" + System.lineSeparator();

		System.setIn(new ByteArrayInputStream(input.getBytes()));

		newsController = new NewsController(newsService);

		// Mock 설정
		doNothing().when(newsService).createNews(anyString(), anyString(), anyString());

		// 뉴스 수정 실행
		newsController.createNews();

		// `newsService.updateNews()`가 호출되었는지 검증
		verify(newsService, times(1)).createNews("새로운 제목", "https://new.com", "새로운 언론사");


		// 출력 검증
		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("📝 뉴스 제목:"), "출력 메시지가 예상과 다름");
	}


	@Test
	@DisplayName("뉴스 전체 조회 테스트")
	void testNewsAlllist() {
		// Mock 설정
		doNothing().when(newsService).newsAlllist();

		// 뉴스 조회 실행
		newsController.newsAlllist();

		// newsService.newsAlllist()가 호출되었는지 검증
		verify(newsService, times(1)).newsAlllist();
	}

	@Test
	@DisplayName("뉴스 수정 테스트")
	void testUpdateNews()  throws InterruptedException{
		// 입력값 시뮬레이션
		String input = "1" + System.lineSeparator() +
			"새로운 제목" + System.lineSeparator() +
			"https://new.com" + System.lineSeparator() +
			"새로운 언론사" + System.lineSeparator();

		System.setIn(new ByteArrayInputStream(input.getBytes()));

		newsController = new NewsController(newsService);

		// Mock 설정
		doNothing().when(newsService).updateNews(anyInt(),anyString(), anyString(), anyString());

		// 뉴스 수정 실행
		newsController.updateNews();

		// `newsService.updateNews()`가 호출되었는지 검증
		verify(newsService, times(1)).updateNews(1, "새로운 제목", "https://new.com", "새로운 언론사");


		// 출력 검증
		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("✏ 수정할 뉴스 ID:"), "출력 메시지가 예상과 다름");
	}



	@Test
	@DisplayName("뉴스 삭제 테스트")
	void testDeleteNews() throws InterruptedException {
		// 입력값 시뮬레이션
		String input = "1\n";
		System.setIn(new ByteArrayInputStream(input.getBytes())); // 가짜 입력값 설정


		newsController = new NewsController(newsService);

		// Mock 설정
		doNothing().when(newsService).deleteNews(anyInt());

		// 뉴스 삭제 실행
		newsController.deleteNews();

		// `newsService.deleteNews()`가 호출되었는지 검증
		verify(newsService, times(1)).deleteNews(1);

		// 출력 검증
		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("🗑 삭제할 뉴스 ID:"), "출력 메시지가 예상과 다름");
	}

}