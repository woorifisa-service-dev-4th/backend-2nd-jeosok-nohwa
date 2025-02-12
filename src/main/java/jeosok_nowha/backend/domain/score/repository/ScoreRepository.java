package jeosok_nowha.backend.domain.score.repository;

import static jeosok_nowha.backend.global.common.config.DatabaseConfig.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jeosok_nowha.backend.domain.score.entity.Score;

public class ScoreRepository {
	public static void saveScore(Score score) {
		String query = "INSERT INTO scores (record_date, user_id, diet_score, exercise_score, total_score) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setTimestamp(1, new java.sql.Timestamp(score.getRecordDate().getTime())); // recordDate
			stmt.setLong(2, score.getUserId()); // userId
			stmt.setDouble(3, score.getDietScore()); // dietScore
			stmt.setDouble(4, score.getExerciseScore()); // exerciseScore
			stmt.setDouble(5, score.getRelatedChatSummaryId()); // totalScore

			stmt.executeUpdate(); // SQL 실행
			System.out.println("Score saved successfully!");
		} catch (SQLException e) {
			System.out.println("Error while saving score: " + e.getMessage());
		}
	}


	public static List<Score> getScores() {
		List<Score> scores = new ArrayList<>();
		String query = "SELECT * FROM scores";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery(); // SQL 실행
				while (rs.next()) {// ResultSet에서 값 추출하여 Score 객체 생성
					Date recordDate = rs.getTimestamp("record_date");
					Long userId = rs.getLong("user_id");
					double dietScore = rs.getDouble("diet_score");
					double exerciseScore = rs.getDouble("exercise_score");
					double totalScore = rs.getDouble("total_score");

					// Score 객체 생성 후 리스트에 추가
					Score score = new Score(recordDate, userId, dietScore, exerciseScore);
					scores.add(score);
				}
		} catch (SQLException e) {
			System.out.println("Error while retrieving scores: " + e.getMessage());
		}
		return scores;
	}
}
