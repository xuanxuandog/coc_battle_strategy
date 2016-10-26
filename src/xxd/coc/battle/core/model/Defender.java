package xxd.coc.battle.core.model;

public class Defender {
	
	public static final int MAX_STARS = 3;
	
	private String id;
	
	private int initialStars;
	
	private int completedStars;
	
	public Defender(String id) {
		this(id, 0);
	}
	
	public Defender(String id, int initialStars) {
		if (id == null) {
			throw new IllegalArgumentException("id can't be null");
		}
		this.id = id;
		this.initialStars = initialStars;
	}
	
	
	public void attacked(int numberOfStars) {
		if (this.completedStars < numberOfStars) {
			this.completedStars = numberOfStars;
		}
	}
	
	public void reset() {
		this.completedStars = this.initialStars;
	}

	public String getId() {
		return id;
	}

	public int getCompletedStars() {
		return this.completedStars > this.initialStars ? this.completedStars : this.initialStars;
	}
	
	public int getInitialStars() {
		return this.initialStars;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("defender:").append(this.id)
		.append(", initial stars:").append(this.initialStars)
		.append(", completed stars:").append(this.completedStars);
		return sb.toString();
	}
	
}
