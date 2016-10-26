package xxd.coc.battle.core.strategy;

import java.util.List;
import java.util.Map;

import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.Defender;

public class StrategyInput {

	//the target number of stars that customer want to get,
	//if not set, it will be equal to the max number of stars
	//which is the number of defenders * the max stars(by default is 3) of a single defender
	private int targetStars = 0;
	
	private List<Attacker> attackers;
	
	private Map<String, Defender> defenders;

	public List<Attacker> getAttackers() {
		return attackers;
	}

	public void setAttackers(List<Attacker> attackers) {
		this.attackers = attackers;
	}

	public Map<String, Defender> getDefenders() {
		return defenders;
	}

	public void setDefenders(Map<String, Defender> defenders) {
		this.defenders = defenders;
	}
	
	public int getTargetStars() {
		if (this.targetStars == 0) {
			return this.defenders.size() * Defender.MAX_STARS;
		} else {
			return this.targetStars;
		}
	}
	
	public void setTargetStars(int targetStars) {
		this.targetStars = targetStars;
	}
	
}
