package jeosok_nowha.backend.domain.member.service;

import jeosok_nowha.backend.domain.member.entity.Member;
import jeosok_nowha.backend.domain.member.entity.MemberRole;
import jeosok_nowha.backend.domain.member.entity.MemberStatus;
import jeosok_nowha.backend.domain.member.payload.request.SignUpRequest;
import jeosok_nowha.backend.domain.member.payload.request.SignInRequest;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.util.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberService {
	private final MemberRepository userRepository;
	private final Map<String, Member> loggedInUsers = new HashMap<>(); // ✅ 로그인된 사용자 세션

	public MemberService(MemberRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void signUp(SignUpRequest request) {
		Optional<Member> existingUser = userRepository.findByEmail(request.getEmail());
		if (existingUser.isPresent()) {
			System.out.println("❌ 회원가입 실패: 이미 사용 중인 이메일입니다.");
			return;
		}

		String hashedPassword = PasswordEncoder.hashPassword(request.getPassword());
		Member user = new Member(
			String.valueOf(userRepository.getSize() + 1), // ✅ ID를 순차적인 숫자로 변경
			request.getNickname(),
			request.getEmail(),
			request.getHeight(),
			request.getWeight(),
			null,
			MemberStatus.ACTIVE,
			MemberRole.USER,
			hashedPassword
		);

		userRepository.save(user);
		System.out.println("✅ 회원가입 성공! 이메일: " + request.getEmail());
	}


	public void signIn(SignInRequest request) {
		Optional<Member> userOpt = userRepository.findByEmail(request.getEmail());

		if (userOpt.isPresent()) {
			Member user = userOpt.get();
			if (PasswordEncoder.hashPassword(request.getPassword()).equals(user.getPassword())) {
				loggedInUsers.put(user.getEmail(), user); // ✅ 로그인 성공 시 세션에 저장
				System.out.println("✅ 로그인 성공! 환영합니다, " + user.getNickname() + "님!");
			} else {
				System.out.println("❌ 로그인 실패: 비밀번호가 일치하지 않습니다.");
			}
		} else {
			System.out.println("❌ 로그인 실패: 등록되지 않은 이메일입니다.");
		}
	}
}
