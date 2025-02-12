package jeosok_nowha.backend.domain.member.service;

import jeosok_nowha.backend.domain.member.entity.Member;
import jeosok_nowha.backend.domain.member.entity.MemberStatus;
import jeosok_nowha.backend.domain.member.entity.MemberRole;
import jeosok_nowha.backend.domain.member.payload.request.SignUpRequest;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.util.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class MemberService {
	private final MemberRepository userRepository;

	public MemberService(MemberRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void signUp(SignUpRequest request) {

		Optional<Member> existingUser = userRepository.findByEmail(request.getEmail());
		if (existingUser.isPresent()) {
			System.out.println("❌ 회원가입 실패: 이미 사용 중인 이메일입니다.");
			return; // 중복되면 회원가입 실패
		}


		String hashedPassword = PasswordEncoder.hashPassword(request.getPassword());


		Member user = new Member(
			UUID.randomUUID().toString(),
			request.getNickname(),
			request.getEmail(),
			request.getHeight(),
			request.getWeight(),
			null,  // 프로필 사진 기본값: null
			MemberStatus.ACTIVE,
			MemberRole.USER,
			hashedPassword
		);


		userRepository.save(user);
		System.out.println("✅ 회원가입 성공! 이메일: " );
	}
}
