package jeosok_nowha.backend.domain.score.service;

import java.util.List;

import jeosok_nowha.backend.domain.score.entity.Score;
import jeosok_nowha.backend.domain.score.repository.ScoreRepository;

public class ScoreService {
	private final ScoreRepository scoreRepository;
	private ScoreService scoreService;

	public ScoreService(ScoreRepository scoreRepository) {
		this.scoreRepository = scoreRepository;
	}

	public void uploadScore(Score score){
		scoreRepository.saveScore(score);
	}

	public List<Score> getScore() {
		return scoreRepository.getScores(); // ✅ 올바르게 반환
	}
}