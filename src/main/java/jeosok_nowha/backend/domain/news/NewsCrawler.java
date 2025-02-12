package jeosok_nowha.backend.domain.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsCrawler {
	public static void main(String[] args) {
		String keyword = "저속노화";  // 원하는 검색어
		String NewsSearchUrl =
			"https://search.naver.com/search.naver?ssc=tab.news.all&where=news&sm=tab_jum&query=" + keyword;

		try {
			// 네이버 뉴스 검색 페이지 HTML 가져오기
			Document doc = Jsoup.connect(NewsSearchUrl).get();

			// 뉴스 기사 목록 가져오기
			Elements newsElements = doc.select(".news_wrap");

			System.out.println("📢 네이버 뉴스 검색 결과: " + keyword);
			System.out.println("-------------------------------------------------");

			for (Element news : newsElements) {
				Element titleElement = news.selectFirst(".news_tit");  // 뉴스 제목
				Element imageElement = news.selectFirst(".dsc_thumb img");  // 뉴스 이미지
				Element linkElement = news.selectFirst(".api_txt_lines.dsc_txt_wrap"); // 뉴스 링크
				Element pressElement = news.selectFirst(".info.press"); // 언론사명

				if (titleElement != null && linkElement != null && pressElement != null) {
					String title = titleElement.text();  // 제목
					String contents = linkElement.text();
					String image = imageElement.attr("data-src");
					String link = linkElement.attr("href");  // 뉴스 링크
					String press = pressElement.text();  // 언론사명

					System.out.println("📰 제목: " + title);
					System.out.println("\uD83D\uDCC4 내용: " + contents);
					System.out.println("\uD83D\uDCF8 이미지: " + image);
					System.out.println("🔗 링크: " + link);
					System.out.println("🏢 언론사: " + press);
					System.out.println("-------------------------------------------------");
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
