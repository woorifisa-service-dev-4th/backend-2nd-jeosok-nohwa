package jeosok_nowha.backend;

import jeosok_nowha.backend.domain.news.controller.NewsController;
import jeosok_nowha.backend.domain.news.repository.NewsRepository;
import jeosok_nowha.backend.domain.news.service.NewsService;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		NewsRepository newsRepository = new NewsRepository();
		NewsService newsService = new NewsService(newsRepository);
		NewsController newsController = new NewsController(newsService);
		newsController.run();
	}
}
