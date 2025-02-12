package jeosok_nowha.backend.domain.score.entity;

import java.util.Date;

/**
 * 하루 저속노화 점수를 저장하는 클래스
 */

public class Score {
	/** 기록된 날짜 */
	private Date recordDate;
	/** 사용자 ID */
	private Long userId;
	/** 식단 점수 */
	private double dietScore;
	/** 운동 점수 */
	private double exerciseScore;
	/** 연관된 요약 ID */
	private Long relatedChatSummaryId;

	public Date getRecordDate() {
		return recordDate;
	}

	public Long getUserId() {
		return userId;
	}

	public double getDietScore() {
		return dietScore;
	}

	public double getExerciseScore() {
		return exerciseScore;
	}

	public Long getRelatedChatSummaryId() {
		return relatedChatSummaryId;
	}


	/**
	 * DailyScore 생성자
	 * @param recordDate 기록된 날짜
	 * @param userId 사용자 ID
	 * @param dietScore 식단 점수
	 * @param exerciseScore 운동 점수
	 */
	public Score(Date recordDate, Long userId, double dietScore, double exerciseScore) {
		this.recordDate = recordDate;
		this.userId = userId;
		this.dietScore = dietScore;
		this.exerciseScore = exerciseScore;

	}

	/**
	 * DailyScore 생성자
	 * @param recordDate 기록된 날짜
	 * @param userId 사용자 ID
	 * @param dietScore 식단 점수
	 * @param exerciseScore 운동 점수
	 * @param relatedChatSummaryId 연관된 요약 ID
	 */
	public Score(Date recordDate, Long userId, double dietScore, double exerciseScore, Long relatedChatSummaryId) {
		this.recordDate = recordDate;
		this.userId = userId;
		this.dietScore = dietScore;
		this.exerciseScore = exerciseScore;
		this.relatedChatSummaryId = relatedChatSummaryId;
	}



	/**
	 * 하루 총 점수 반환
	 * @return 총 점수 (식단 + 운동)
	 */
	public double getTotalScore() {
		return dietScore + exerciseScore;
	}
}
