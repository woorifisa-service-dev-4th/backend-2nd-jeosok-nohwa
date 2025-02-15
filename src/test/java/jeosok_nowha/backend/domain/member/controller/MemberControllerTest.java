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
		// 메시지를 바로 기대값에 하지말고
		String expectedMessage="❌ 유효한 이메일 형식을 입력하세요 (예: user@example.com)";

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "sss", 175.0, 70.0, "password123")
		);
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	@DisplayName("닉네임이 비어 있으면 예외가 발생해야 한다.")
	void signUp_Fails_When_Nickname_Empty() {
		String expectedMessage="닉네임은 필수 입력값입니다.";
		// When & Then
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("", "test@naver.com", 175.0, 70.0, "password123")
		);
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	@DisplayName("키가 0 이하이거나 300 초과일 경우 예외가 발생해야 한다.")
	void signUp_Fails_When_Height_Invalid() {
		String expectedMessage="❌ 키는 0보다 크고 300cm 이하이어야 합니다.";
		// When & Then
		Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@naver.com", 0, 70.0, "password123")
		);
		assertEquals(expectedMessage, exception1.getMessage());

		Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@naver.com", 305, 70.0, "password123")
		);
		assertEquals(expectedMessage, exception2.getMessage());
	}

	@Test
	@DisplayName("몸무게가 0 이하이거나 300 초과일 경우 예외가 발생해야 한다.")
	void signUp_Fails_When_Weight_Invalid() {
		String expectedMessage="❌ 몸무게는 0보다 크고 300kg 이하이어야 합니다.";
		// When & Then
		Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@naver.com", 175.0, 0, "password123")
		);
		assertEquals(expectedMessage, exception1.getMessage());

		Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@naver.com", 175.0, 305, "password123")
		);
		assertEquals(expectedMessage, exception2.getMessage());
	}

	@Test
	@DisplayName("비밀번호가 비어 있으면 예외가 발생해야 한다.")
	void signUp_Fails_When_Password_Empty() {
		String expectedMessage="❌ 비밀번호는 필수 입력값입니다.";
		// When & Then
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			memberController.signUp("testUser", "test@naver.com", 175.0, 70.0, "")
		);
		assertEquals(expectedMessage, exception.getMessage());
	}










}
