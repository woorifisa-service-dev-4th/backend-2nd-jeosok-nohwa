package jeosok_nowha.backend.domain.news.service;

import java.util.List;
import java.util.Optional;

import jeosok_nowha.backend.domain.news.NewsCrawler;
import jeosok_nowha.backend.domain.news.entity.News;
import jeosok_nowha.backend.domain.news.repository.NewsRepository;

public class NewsService {
	private final NewsRepository newsRepository;

	public NewsService(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	public void createNews( String title,String link,String press){
		News news = new News(title,link, press);
		News savedNews = newsRepository.save(news);
		System.out.println(" 뉴스 추가 완료: " +  savedNews);
	}

	public void newsAlllist(){
		List<News> newsList = newsRepository.findAll();
		if (newsList.isEmpty()) {
			System.out.println(" 뉴스 데이터가 없습니다!");
		} else {
			for (News news : newsList) {
				System.out.println("📰 " + news);
			}
		}
		System.out.println(newsList);
	}

	public void updateNews(int id, String newTitle, String newLink, String newPress) {
		boolean isUpdated = newsRepository.update(id, newTitle, newLink, newPress);

		if (isUpdated) {
			Optional<News> optionalNews = newsRepository.findById(id);
			if (optionalNews.isPresent()) {
				News news = optionalNews.get();
				System.out.println(" 뉴스 수정 완료: " + news);
			}
		} else {
			System.out.println(" 해당 ID의 뉴스를 찾을 수 없습니다.");
		}
	}



	public void deleteNews(int id){
		boolean isDeleted = newsRepository.deleteById(id);
		if (isDeleted) {
			System.out.println(" 뉴스 삭제 완료!");
		} else {
			System.out.println(" 해당 ID의 뉴스를 찾을 수 없습니다.");
		}
	}

	public void fetchAndSaveNews() {
		List<News> newsList = NewsCrawler.fetchNews();
		if (newsList.isEmpty()) {
			System.out.println(" 크롤링된 뉴스가 없습니다.");
			return;
		}

		System.out.println(" 크롤링한 뉴스 데이터를 DB에 저장 중...");
		for (News news : newsList) {
			newsRepository.save(news);
		}
		System.out.println("✅ 모든 뉴스 저장 완료!");
	}
}
