package jeosok_nowha.backend.domain.chat.config;


import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ChatConfig {
	private Map<String, Object> config;

	public ChatConfig() {
		try  {
			// 1️⃣ config.yml 파일을 읽어옴
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
		return (String) ((Map<String, Object>) config.get("chat")).get("server.host");
	}

	public int getPort() {
		return (Integer) ((Map<String, Object>) config.get("chat")).get("server.port");
	}
}

