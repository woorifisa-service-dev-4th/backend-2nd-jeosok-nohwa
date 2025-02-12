package jeosok_nowha.backend.domain.chat.config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ChatConfig {
	private final Map<String, Object> config;

	public ChatConfig() {
		try {
			// ✅ YAML 파일 로드
			InputStream inputStream = ChatConfig.class.getClassLoader().getResourceAsStream("config.yml");
			if (inputStream == null) {
				throw new RuntimeException("❌ config.yml 파일을 찾을 수 없습니다!");
			}
			Yaml yaml = new Yaml();
			config = yaml.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException("설정 파일을 읽는 중 오류 발생", e);
		}
	}

	public String getHost() {
		try {
			// ✅ `chat` & `server` 존재 여부 확인 후 안전하게 가져오기
			Map<String, Object> chatConfig = (Map<String, Object>) config.get("chat");
			if (chatConfig == null) throw new RuntimeException("❌ 'chat' 설정을 찾을 수 없습니다!");

			Map<String, Object> serverConfig = (Map<String, Object>) chatConfig.get("server");
			if (serverConfig == null) throw new RuntimeException("❌ 'chat.server' 설정을 찾을 수 없습니다!");

			return (String) serverConfig.getOrDefault("host", "127.0.0.1");
		} catch (Exception e) {
			System.out.println("⚠️ 설정을 찾을 수 없어 기본값(127.0.0.1) 사용");
			return "127.0.0.1";
		}
	}

	public int getPort() {
		try {
			// ✅ `chat` & `server` 존재 여부 확인 후 안전하게 가져오기
			Map<String, Object> chatConfig = (Map<String, Object>) config.get("chat");
			if (chatConfig == null) throw new RuntimeException("❌ 'chat' 설정을 찾을 수 없습니다!");

			Map<String, Object> serverConfig = (Map<String, Object>) chatConfig.get("server");
			if (serverConfig == null) throw new RuntimeException("❌ 'chat.server' 설정을 찾을 수 없습니다!");

			Object portValue = serverConfig.get("port");
			if (portValue == null) throw new RuntimeException("❌ 'chat.server.port' 설정을 찾을 수 없습니다!");

			return Integer.parseInt(portValue.toString());
		} catch (Exception e) {
			System.out.println("⚠️ 설정을 찾을 수 없어 기본 포트(8080) 사용");
			return 8080;
		}
	}
}
