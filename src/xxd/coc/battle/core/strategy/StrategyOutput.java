package xxd.coc.battle.core.strategy;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class StrategyOutput {

	private int starNumber = 0;
	
	private int initialStars = 0;
	
	private Map<String, Set<String>> battleMap;
	
	public Map<String, Set<String>> getBattleMap() {
		return battleMap;
	}

	

	public int getInitialStars() {
		return initialStars;
	}



	public void setInitialStars(int initialStars) {
		this.initialStars = initialStars;
	}



	public int getStarNumber() {
		return starNumber;
	}


	public void setStarNumber(int starNumber) {
		this.starNumber = starNumber;
	}


	public void setBattleMap(Map<String, Set<String>> battleMap) {
		this.battleMap = battleMap;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\ninitial stars:").append(this.initialStars);
		sb.append(", total stars:").append(this.starNumber);
		sb.append(", increased ").append(this.starNumber - this.initialStars);
		sb.append("\n");
		for (Entry<String, Set<String>> entry : this.battleMap.entrySet()) {
			sb.append(entry.getKey()).append("->");
			boolean isFirst = true;
			for (String v : entry.getValue()) {
				if (isFirst) {
			        isFirst = false;
				} else {
				    sb.append(",");
				}
				sb.append(v);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
