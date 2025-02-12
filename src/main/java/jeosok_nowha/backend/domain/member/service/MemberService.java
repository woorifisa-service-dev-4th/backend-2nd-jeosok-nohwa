package jeosok_nowha.backend.domain.member.service;

import jeosok_nowha.backend.domain.member.entity.Member;
import jeosok_nowha.backend.domain.member.entity.MemberRole;
import jeosok_nowha.backend.domain.member.entity.MemberStatus;
import jeosok_nowha.backend.domain.member.payload.request.SignInRequest;
import jeosok_nowha.backend.domain.member.payload.request.SignUpRequest;
import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.util.MemberPrintUtil;
import jeosok_nowha.backend.domain.member.util.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MemberService {
	private final MemberRepository userRepository;
	public Member currentUser;

	public MemberService(MemberRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 회원가입 (DB에 저장)
	public void signUp(SignUpRequest request) {
		Optional<Member> existingUser = userRepository.findByEmail(request.getEmail());
		if (existingUser.isPresent()) {
			MemberPrintUtil.printError("회원가입 실패: 이미 사용 중인 이메일입니다.");
			return;
		}

		String hashedPassword = PasswordEncoder.hashPassword(request.getPassword());
		Member user = new Member(
			null,
			request.getNickname(),
			request.getEmail(),
			request.getHeight(),
			request.getWeight(),
			MemberStatus.ACTIVE,
			MemberRole.USER,
			hashedPassword
		);

		userRepository.save(user);
		MemberPrintUtil.print("✅ 회원가입 성공! 이메일: " + request.getEmail());
	}

	// 로그인 기능 (DB에서 정보 조회)
	public void signIn(SignInRequest request) {
		Optional<Member> userOpt = userRepository.findByEmail(request.getEmail());

		if (userOpt.isPresent()) {
			Member user = userOpt.get();
			if (PasswordEncoder.hashPassword(request.getPassword()).equals(user.getPassword())) {
				currentUser = user;
				MemberPrintUtil.print("✅ 로그인 성공! 환영합니다, " + user.getNickname() + "님!");
			} else {
				MemberPrintUtil.printError("로그인 실패: 비밀번호가 일치하지 않습니다.");
			}
		} else {
			MemberPrintUtil.printError("로그인 실패: 등록되지 않은 이메일입니다.");
		}
	}

	// 현재 로그인된 사용자 정보 조회
	public void getCurrentUserInfo() {
		if (currentUser == null) {
			MemberPrintUtil.printUserNotLoggedIn();
			return;
		}
		MemberPrintUtil.printCurrentUserInfo(
			currentUser.getId(),
			currentUser.getNickname(),
			currentUser.getEmail(),
			currentUser.getHeight(),
			currentUser.getWeight(),
			currentUser.getBmi(),
			currentUser.getRole().toString()
		);
	}

	// 모든 회원 조회 기능
	public void getAllMembers() {
		List<Member> members = userRepository.findAll();
		if (members.isEmpty()) {
			MemberPrintUtil.print("📌 저장된 사용자가 없습니다.");
			return;
		}

		MemberPrintUtil.print("\n--- 전체 회원 목록 ---");
		for (Member user : members) {
			MemberPrintUtil.printCurrentUserInfo(
				user.getId(),
				user.getNickname(),
				user.getEmail(),
				user.getHeight(),
				user.getWeight(),
				user.getBmi(),
				user.getRole().toString()
			);
			MemberPrintUtil.print("-----------------------------");
		}
	}

	// ✅ 현재 로그인된 사용자의 정보 수정 (닉네임, 키, 몸무게 변경)
	public void updateCurrentUserInfo() throws IOException {
		if (currentUser == null) {
			MemberPrintUtil.printError("❌ 로그인된 사용자가 없습니다. 먼저 로그인하세요.");
			return;
		}

		// 닉네임 수정
		String newNickname;
		while (true) {
			newNickname = MemberPrintUtil.readInput("새 닉네임 (현재: " + currentUser.getNickname() + "): ");
			if (!newNickname.isEmpty()) break;
			MemberPrintUtil.printError("❌ 닉네임은 비워둘 수 없습니다.");
		}

		// 키 수정
		double newHeight;
		while (true) {
			try {
				newHeight = Double.parseDouble(MemberPrintUtil.readInput("새 키 (현재: " + currentUser.getHeight() + "cm): "));
				if (newHeight > 0 && newHeight <= 300) break;
				MemberPrintUtil.printError("❌ 키는 0보다 크고 300cm 이하이어야 합니다.");
			} catch (NumberFormatException e) {
				MemberPrintUtil.printError("❌ 숫자 형식으로 입력하세요.");
			}
		}

		// 몸무게 수정
		double newWeight;
		while (true) {
			try {
				newWeight = Double.parseDouble(MemberPrintUtil.readInput("새 몸무게 (현재: " + currentUser.getWeight() + "kg): "));
				if (newWeight > 0 && newWeight <= 300) break;
				MemberPrintUtil.printError("❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.");
			} catch (NumberFormatException e) {
				MemberPrintUtil.printError("❌ 숫자 형식으로 입력하세요.");
			}
		}

		// DB 업데이트 수행
		userRepository.updateUserInfo(currentUser.getEmail(), newNickname, newHeight, newWeight);

		// 현재 로그인된 사용자 정보 업데이트
		currentUser = new Member(
			currentUser.getId(),
			newNickname,
			currentUser.getEmail(),
			newHeight,
			newWeight,
			currentUser.getStatus(),
			currentUser.getRole(),
			currentUser.getPassword()
		);

		MemberPrintUtil.print("✅ 회원 정보가 성공적으로 수정되었습니다.");
	}
}
