package Diary;

public class Diary {
	private String weather;
	private String emotion;
	private String title;
	private String context;
	
	public Diary(String weather, String emotion, String title, String context) {
		this.weather = weather;
		this.emotion = emotion;
		this.title = title;
		this.context = context;
	}
	
	public String getWeather() {
		return weather;
	}

	public String getEmotion() {
		return emotion;
	}

	public String getTitle() {
		return title;
	}

	public String getContext() {
		return context;
	}
}
