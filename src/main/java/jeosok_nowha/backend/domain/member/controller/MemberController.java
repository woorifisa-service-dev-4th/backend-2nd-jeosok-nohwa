package jeosok_nowha.backend.domain.member.controller;

import jeosok_nowha.backend.domain.member.payload.request.SignUpRequest;
import jeosok_nowha.backend.domain.member.payload.request.SignInRequest;
import jeosok_nowha.backend.domain.member.service.MemberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberController {
	private final MemberService userService;
	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public MemberController(MemberService userService) {
		this.userService = userService;
	}

	public void signUp() throws IOException {
		System.out.print("닉네임: ");
		String nickname = reader.readLine();
		System.out.print("이메일: ");
		String email = reader.readLine();
		System.out.print("키 (cm): ");
		double height = Double.parseDouble(reader.readLine());
		System.out.print("몸무게 (kg): ");
		double weight = Double.parseDouble(reader.readLine());
		System.out.print("비밀번호: ");
		String password = reader.readLine();

		SignUpRequest request = new SignUpRequest(email, password, nickname, height, weight);
		userService.signUp(request);
	}

	public void signIn() throws IOException {
		System.out.print("이메일: ");
		String email = reader.readLine();
		System.out.print("비밀번호: ");
		String password = reader.readLine();

		SignInRequest request = new SignInRequest(email, password);
		userService.signIn(request);
	}
}
