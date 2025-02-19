package jeosok_nowha.backend.domain.news.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jeosok_nowha.backend.domain.news.entity.News;
import jeosok_nowha.backend.domain.news.repository.NewsRepository;

class NewsServiceTest {

	@InjectMocks
	private NewsService newsService;

	@Mock
	private NewsRepository newsRepository;

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	@DisplayName("뉴스 추가 테스트")
	void testCreateNews() {
		News news = new News("테스트 뉴스", "https://test.com", "테스트 언론사");

		when(newsRepository.save(any(News.class))).thenReturn(news);

		newsService.createNews("테스트 뉴스", "https://test.com", "테스트 언론사");

		verify(newsRepository, times(1)).save(any(News.class));

		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("뉴스 추가 완료"), "출력 메시지가 예상과 다름");
	}

	@Test
	@DisplayName("뉴스 목록 조회")
	void testNewsAlllist_Data() {
		when(newsRepository.findAll()).thenReturn(Collections.emptyList());

		newsService.newsAlllist();

		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("뉴스 데이터가 없습니다!"), "출력 메시지가 예상과 다름");
	}

	@Test
	@DisplayName("뉴스 수정")
	void testUpdateNews_Success() {
		when(newsRepository.update(anyInt(), anyString(), anyString(), anyString())).thenReturn(true);
		when(newsRepository.findById(anyInt())).thenReturn(Optional.of(new News("기존 뉴스", "https://old.com", "구언론사")));

		newsService.updateNews(1, "새로운 제목", "https://new.com", "새로운 언론사");

		verify(newsRepository, times(1)).update(anyInt(), anyString(), anyString(), anyString());
		verify(newsRepository, times(1)).findById(anyInt());

		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("뉴스 수정 완료"), "출력 메시지가 예상과 다름");
	}

	@Test
	@DisplayName("뉴스 삭제")
	void testDeleteNews() {
		// Mock 설정 (newsRepository.deleteById(1) 호출 시 성공)
		when(newsRepository.deleteById(1)).thenReturn(true);

		// 뉴스 삭제 실행
		newsService.deleteNews(1);

		// 검증: `newsRepository.deleteById(1)`가 한 번 호출되었는지 확인
		verify(newsRepository, times(1)).deleteById(1);

		// 출력 검증
		String output = outputStreamCaptor.toString();
		assertTrue(output.contains("뉴스 삭제 완료!"), "출력 메시지가 예상과 다름");
	}
}

