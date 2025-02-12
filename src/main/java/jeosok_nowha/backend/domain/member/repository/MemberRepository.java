package jeosok_nowha.backend.domain.member.repository;

import static jeosok_nowha.backend.global.common.config.DatabaseConfig.*;

import jeosok_nowha.backend.global.common.config.DatabaseConfig;
import jeosok_nowha.backend.domain.member.entity.Member;
import jeosok_nowha.backend.domain.member.entity.MemberRole;
import jeosok_nowha.backend.domain.member.entity.MemberStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepository {


	private static final MemberRepository instance = new MemberRepository();

	private MemberRepository() {}

	public static MemberRepository getInstance() {
		return instance;
	}

	public void save(Member member) {
		String sql = "INSERT INTO member (nickname, email, height, weight, status, role, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, member.getNickname());
			pstmt.setString(2, member.getEmail());
			pstmt.setDouble(3, member.getHeight());
			pstmt.setDouble(4, member.getWeight());
			pstmt.setString(5, member.getStatus().name());
			pstmt.setString(6, member.getRole().name());
			pstmt.setString(7, member.getPassword());

			pstmt.executeUpdate();
			System.out.println("✅ 회원 저장 완료: " + member.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Optional<Member> findByEmail(String email) {
		String sql = "SELECT * FROM member WHERE email = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Member member = new Member(
					rs.getString("id"),
					rs.getString("nickname"),
					rs.getString("email"),
					rs.getDouble("height"),
					rs.getDouble("weight"),
					MemberStatus.valueOf(rs.getString("status")),
					MemberRole.valueOf(rs.getString("role")),
					rs.getString("password")
				);
				return Optional.of(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	public void updateUserInfo(String email, String newNickname, double newHeight, double newWeight) {
		String sql = "UPDATE member SET nickname = ?, height = ?, weight = ? WHERE email = ?";

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, newNickname);
			pstmt.setDouble(2, newHeight);
			pstmt.setDouble(3, newWeight);
			pstmt.setString(4, email);

			pstmt.executeUpdate();
			System.out.println("✅ 회원 정보 업데이트 완료: " + email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		String sql = "SELECT * FROM member";

		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Member member = new Member(
					rs.getString("id"),
					rs.getString("nickname"),
					rs.getString("email"),
					rs.getDouble("height"),
					rs.getDouble("weight"),
					MemberStatus.valueOf(rs.getString("status")),
					MemberRole.valueOf(rs.getString("role")),
					rs.getString("password")
				);
				members.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return members;
	}
}
