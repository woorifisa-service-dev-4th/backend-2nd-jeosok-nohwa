package jeosok_nowha.backend.domain.member.entity;

public class Member {
	private final String id;
	private String nickname;
	private String email;
	private double height;
	private double weight;
	private double bmi;
	private String profileImage;
	private MemberStatus status;
	private MemberRole role;
	private final String password;

	public Member(String id, String nickname, String email, double height, double weight,
		MemberStatus status, MemberRole role, String password) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.height = height;
		this.weight = weight;
		this.bmi = calculateBMI(); // BMI 자동 계산
		this.status = status;
		this.role = role;
		this.password = password;
	}

	private double calculateBMI() {
		return weight / ((height / 100) * (height / 100));  // BMI 계산
	}

	public double getHeight() { return height; }
	public double getWeight() { return weight; }
	public String getId() { return id; }
	public String getNickname() { return nickname; }
	public String getEmail() { return email; }
	public double getBmi() { return bmi; }
	public MemberStatus getStatus() { return status; }
	public MemberRole getRole() { return role; }
	public String getPassword() { return password; }
}
