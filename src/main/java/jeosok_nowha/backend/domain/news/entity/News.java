package jeosok_nowha.backend.domain.news.entity;

public class News {
	private int id;
	private String title;
	private String link;
	private String press;


	public News(int id,String title,String link,String press){
		this.id = id;
		this.title = title;
		this.link = link;
		this.press = press;
	}

	public News(String title,String link,String press){
		this.id = id;
		this.title = title;
		this.link = link;
		this.press = press;
	}

	public int getId() {
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getLink(){
		return link;
	}

	public String getPress(){
		return press;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPress(String press) {
		this.press = press;
	}
}

