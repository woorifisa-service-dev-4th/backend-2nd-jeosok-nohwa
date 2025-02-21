package jeosok_nowha.backend.domain.news.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jeosok_nowha.backend.domain.news.entity.News;
import jeosok_nowha.backend.global.common.config.DatabaseConfig;

public class NewsRepository {
	private final List<News> newsList = new ArrayList<>();
	private int newsIdCount=1;

	// 뉴스 저장
	public News save(News news) {
		String query = "INSERT INTO news (title, link, press) VALUES (?, ?, ?)";

		try (Connection conn = DatabaseConfig.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getLink());
			pstmt.setString(3, news.getPress());
			pstmt.executeUpdate();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					news.setId(rs.getInt(1));
					return news;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	// 뉴스 조회
	public List<News> findAll() {
		List<News> newsList = new ArrayList<>();
		String query = "SELECT id, title, link, press FROM news";

		try (Connection conn = DatabaseConfig.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String link = rs.getString("link");
				String press = rs.getString("press");

				System.out.println("ID: " + id);
				System.out.println("제목: " + title);
				System.out.println("링크: " + link);
				System.out.println("언론사: " + press);

				// NULL 값이 있으면 기본값 설정
				title = (title != null) ? title : "제목 없음";
				link = (link != null) ? link : "https://no-link.com";
				press = (press != null) ? press : "알 수 없는 언론사";

				newsList.add(new News(id, title, link, press));
			}
		} catch (SQLException e) {
			System.out.println("❌ SQL 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return newsList;
	}


	// 뉴스 검색 (뉴스 id)
	public Optional<News> findById(int id) {
		String query = "SELECT id, title, link, press FROM news WHERE id = ?";

		try (Connection conn = DatabaseConfig.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return Optional.of(new News(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getString("link"),
						rs.getString("press")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}


	// 뉴스 수정
	public boolean update(int id, String newTitle,String newLink,String newPress) {
		String query = "UPDATE news SET title = ?, link = ?,  press = ?, WHERE id = ?";

		try (Connection conn = DatabaseConfig.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, newTitle);
			pstmt.setString(2, newLink);
			pstmt.setString(3, newPress);
			pstmt.setInt(4, id);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 뉴스 삭제
	public boolean deleteById(int id) {
		String query = "DELETE FROM news WHERE id = ?";

		try (Connection conn = DatabaseConfig.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
