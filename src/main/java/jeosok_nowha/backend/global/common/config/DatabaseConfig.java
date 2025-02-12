package jeosok_nowha.backend.global.common.config;

import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	private static Map<String, String> dbConfig;

	static {
		try {
			// 1️⃣ config.yml 파일을 읽어옴
			InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("config.yml");
			if (inputStream == null) {
				throw new RuntimeException("❌ config.yml 파일을 찾을 수 없습니다!");
			}

			// 2️⃣ YAML 파싱
			Yaml yaml = new Yaml();
			Map<String, Object> yamlConfig = yaml.load(inputStream);

			// 3️⃣ "database" 섹션을 읽어서 dbConfig에 저장
			dbConfig = (Map<String, String>) yamlConfig.get("database");
			if (dbConfig == null) {
				throw new RuntimeException("❌ config.yml에서 'database' 설정을 찾을 수 없습니다!");
			}
		} catch (Exception e) {
			throw new RuntimeException("❌ YAML 설정 로드 중 오류 발생", e);
		}
	}

	public static Connection getConnection() {
		try {
			// 4️⃣ config.yml에서 DB 설정을 가져와서 연결
			String url = dbConfig.get("url");
			String username = dbConfig.get("username");
			String password = dbConfig.get("password");
			String driver = dbConfig.get("driver");

			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("❌ 데이터베이스 연결 오류 발생", e);
		}
	}
}
