package xxd.coc.battle.core.model;

public class Defender {
	
	/*
	 * default max stars that attacker can get from each defender
	 */
	public static int MAX_STARS = 3;
	
	//id of the defender
	private String id;
	
	//the initial stars that this defender lost from certain attacker(s)
	//by default, it should be 0
	private int initialStars;
	
	//number of stars that certain attacker(s) has got from this defender
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
	
	/**
	 * 
	 * @param numberOfStars actual number of stars that some attacker get from this defender
	 */
	public void attacked(int numberOfStars) {
		//only need update when the actual number of stars is larger than previous completed stars
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
		//if number of completed stars is less than initial number of stars, return the initial value.
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
