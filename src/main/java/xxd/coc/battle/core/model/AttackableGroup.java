package xxd.coc.battle.core.model;

import java.util.Map;

/**
 * A group of defenders that certain attacker can get star(s) from
 * @author xualu
 *
 */
public class AttackableGroup implements Comparable{

	//total stars that attacker can get from this attackable group
	private int stars = 0;
	
	//all the defender ids in this attackable group
	private String[] defenderIds;
	
	public AttackableGroup(int stars, String[] defenderIds) {
		this.stars = stars;
		this.defenderIds = defenderIds;
	}
	
	
	public AttackableGroup(String[] defenderIds) {
		this(0, defenderIds);
	}
	
	/**
	 * calculate the total stars that certain attacker get from this group of defenders
	 * @param starConfidence the mapping of defender->star that certain attacker can get
	 */
	public void calculateStars(Map<String, Integer> starConfidence) {
	    if (starConfidence == null || this.defenderIds == null) {
	    	return;
	    }
	    
	    for (String id : this.defenderIds) {
	    	if (starConfidence.containsKey(id)) {
	    		this.stars += starConfidence.get(id);
	    	}
	    }
	}
	
	


	public String[] getDefenderIds() {
		return defenderIds;
	}


	@Override
	public int compareTo(Object o) {
		return ((AttackableGroup)o).stars - this.stars;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("stars:").append(this.stars).append(", defenders:");
		for (int i = 0; i < this.defenderIds.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(this.defenderIds[i]);
		}
		return sb.toString();
	}
}
