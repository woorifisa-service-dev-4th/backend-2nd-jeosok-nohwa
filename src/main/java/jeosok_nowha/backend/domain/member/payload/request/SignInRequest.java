package jeosok_nowha.backend.domain.member.payload.request;

public class SignInRequest {
	private final String email;
	private final String password;

	public SignInRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() { return email; }
	public String getPassword() { return password; }
}
