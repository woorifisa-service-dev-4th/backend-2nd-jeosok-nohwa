package jeosok_nowha.backend.domain.score.controller;

import java.util.Date;

import jeosok_nowha.backend.domain.score.entity.Score;
import jeosok_nowha.backend.domain.score.repository.ScoreRepository;
import jeosok_nowha.backend.domain.score.service.ScoreService;
import jeosok_nowha.backend.utils.PrintUtils;

public class ScoreController {

	// ScoreRepository scoreRepository = new ScoreRepository();
	// ScoreService scoreService = new ScoreService(scoreRepository);

	ScoreService scoreService;

	Score score1 = new Score(new Date(), 1L, 1.0, 1.0,1L);
	Score score2 = new Score(new Date(), 2L, 2.0, 1.5,2L);
	Score score3 = new Score(new Date(), 3L, 1.5, 2.2,3L);


	public void uploadScore(){
		scoreService.uploadScore(score1);
		scoreService.uploadScore(score2);
		scoreService.uploadScore(score3);
	}

	// public void getScore(){
	//
	// 	PrintUtils.printGetRanking();
	// }
}
