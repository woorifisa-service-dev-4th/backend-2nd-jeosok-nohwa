package jeosok_nowha.backend.domain.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import jeosok_nowha.backend.domain.member.repository.MemberRepository;
import jeosok_nowha.backend.domain.member.service.MemberService;

class MemberControllerTest {

	private MemberController memberController;
	private MemberService memberService;
	private MemberRepository memberRepositoryMock;

	@BeforeEach
	void setUp() {
		// Mockito를 사용하여 가짜 MemberRepository 생성
		memberRepositoryMock = mock(MemberRepository.class);
		memberService = new MemberService(memberRepositoryMock);
		memberController = new MemberController(memberService);
	}

	@Test
	@DisplayName("이메일 형식이 잘못되었을 때 예외가 발생해야 한다.")
	void signUp_Fails_When_Email_Invalid() {
		// When & Then
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "ss", 175.0, 70.0, "password123")
		);
		assertEquals("❌ 유효한 이메일 형식을 입력하세요 (예: user@example.com)", exception.getMessage());
	}

	@Test
	@DisplayName("닉네임이 비어 있으면 예외가 발생해야 한다.")
	void signUp_Fails_When_Nickname_Empty() {
		// When & Then
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("", "test@example.com", 175.0, 70.0, "password123")
		);
		assertEquals("닉네임은 필수 입력값입니다.", exception.getMessage());
	}

	@Test
	@DisplayName("키가 0 이하이거나 300 초과일 경우 예외가 발생해야 한다.")
	void signUp_Fails_When_Height_Invalid() {
		// When & Then
		Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@example.com", 0, 70.0, "password123")
		);
		assertEquals("❌ 키는 0보다 크고 300cm 이하이어야 합니다.", exception1.getMessage());

		Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@example.com", 305, 70.0, "password123")
		);
		assertEquals("❌ 키는 0보다 크고 300cm 이하이어야 합니다.", exception2.getMessage());
	}

	@Test
	@DisplayName("몸무게가 0 이하이거나 300 초과일 경우 예외가 발생해야 한다.")
	void signUp_Fails_When_Weight_Invalid() {
		// When & Then
		Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@example.com", 175.0, 0, "password123")
		);
		assertEquals("❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.", exception1.getMessage());

		Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@example.com", 175.0, 305, "password123")
		);
		assertEquals("❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.", exception2.getMessage());
	}

	@Test
	@DisplayName("비밀번호가 비어 있으면 예외가 발생해야 한다.")
	void signUp_Fails_When_Password_Empty() {
		// When & Then
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@example.com", 175.0, 70.0, "")
		);
		assertEquals("❌ 비밀번호는 필수 입력값입니다.", exception.getMessage());
	}
}
