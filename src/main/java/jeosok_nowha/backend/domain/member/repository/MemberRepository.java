package jeosok_nowha.backend.domain.member.repository;

import jeosok_nowha.backend.domain.member.entity.Member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberRepository {
	private static final MemberRepository instance = new MemberRepository();
	private final Map<String, Member> users = new HashMap<>();

	private MemberRepository() {}

	public static MemberRepository getInstance() {
		return instance;
	}

	public synchronized void save(Member user) {
		users.put(user.getEmail(), user);
	}

	public synchronized Optional<Member> findByEmail(String email) {
		return Optional.ofNullable(users.get(email));
	}

	// ✅ 저장된 사용자 수 반환 (에러 해결)
	public synchronized int getSize() {
		return users.size();
	}

	public synchronized void printUsers() {
		if (users.isEmpty()) {
			System.out.println("📌 저장된 사용자가 없습니다.");
			return;
		}

		System.out.println("\n--- 저장된 사용자 목록 ---");
		for (Member user : users.values()) {
			System.out.println("📌 ID: " + user.getId());
			System.out.println("📌 닉네임: " + user.getNickname());
			System.out.println("📌 이메일: " + user.getEmail());
			System.out.println("📌 키: " + user.getHeight() + "cm");
			System.out.println("📌 몸무게: " + user.getWeight() + "kg");
			System.out.println("📌 BMI: " + user.getBmi());
			System.out.println("📌 상태: " + user.getStatus());
			System.out.println("📌 역할: " + user.getRole());
			System.out.println("📌 암호화된 비밀번호: " + user.getPassword());
			System.out.println("-----------------------------");
		}
	}
}
