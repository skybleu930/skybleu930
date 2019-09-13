package jumprunning;

public class SaveScore {
	private String date;
	private String name;
	private int score;
	
	public SaveScore(String date, String name, int score) {
		this.date = date;
		this.name = name;
		this.score = score;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
