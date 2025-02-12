package jeosok_nowha.backend.domain.member.payload.request;

public class SignUpRequest {
	private final String email;
	private final String password;
	private final String nickname;
	private final double height;
	private final double weight;

	public SignUpRequest(String email, String password, String nickname, double height, double weight) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.height = height;
		this.weight = weight;
	}

	public String getEmail() { return email; }
	public String getPassword() { return password; }
	public String getNickname() { return nickname; }
	public double getHeight() { return height; }
	public double getWeight() { return weight; }
}
