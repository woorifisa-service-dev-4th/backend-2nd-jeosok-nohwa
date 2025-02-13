package jeosok_nowha.backend.domain.score.controller;

import java.util.Date;
import java.util.List;

import jeosok_nowha.backend.domain.score.entity.Score;
import jeosok_nowha.backend.domain.score.repository.ScoreRepository;
import jeosok_nowha.backend.domain.score.service.ScoreService;
import jeosok_nowha.backend.utils.PrintUtils;

public class ScoreController {

	ScoreRepository scoreRepository = new ScoreRepository();
	// ScoreService scoreService = nw ScoreService(scoreRepository);

	ScoreService scoreService = new ScoreService(scoreRepository);


	public void getScore() {
		List<Score> scores = scoreService.getScore(); // ✅ 정상 작동!

		if (scores.isEmpty()) {
			System.out.println("No scores available.");
			return;
		}

		// 헤더 출력
		System.out.println("-------------------------------------------------------------");
		System.out.printf("| %-20s | %-8s | %-10s | %-10s | %-10s |\n",
			"Record Date", "User ID", "Diet Score", "Exercise Score", "Total Score");
		System.out.println("-------------------------------------------------------------");

		// 각 Score 데이터 출력
		for (Score score : scores) {
			System.out.printf("| %-20s | %-8d | %-10.1f | %-10.1f | %-10.1f |\n",
				score.getRecordDate(), score.getUserId(),
				score.getDietScore(), score.getExerciseScore(), score.getTotalScore());
		}

		System.out.println("-------------------------------------------------------------");
	}


	// public void getScore(){
	//
	// 	PrintUtils.printGetRanking();
	// }
}
