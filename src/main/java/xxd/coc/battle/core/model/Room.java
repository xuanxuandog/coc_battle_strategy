package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.AttackerDefenderFactory;
import xxd.coc.battle.core.strategy.MaxStarStrategy;
import xxd.coc.battle.core.strategy.Strategy;
import xxd.coc.battle.core.strategy.StrategyInput;
import xxd.coc.battle.core.strategy.StrategyOutput;

public class Room {

	private String id;
	
	private StrategyInput input;
	
	private StrategyOutput output;
	
	public Room(String id) {
		this.id = id;
		input = new StrategyInput();
		input.setAttackers(new ArrayList<Attacker>());
	}
	
	public void joinAttacker(String id, int[] starConfidence) {
		this.join(AttackerDefenderFactory.getInstance().getAttacker(id, starConfidence));
	}
	
	public void joinDefenders(int[] initialStars) {
		this.input.setDefenders(AttackerDefenderFactory.getInstance().getDefenders(initialStars));
	}
	
	public void join(Attacker attacker) {
		input.getAttackers().add(attacker);
	}
	
	public int getInitialStars() {
	    if (this.output != null) {
	    	return this.output.getInitialStars();
	    } else {
	    	return 0;
	    }
	}
	
	public void setTargetStars(int targetStars) {
		if (this.input != null) {
			this.input.setTargetStars(targetStars);
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public Map<String, Set<String>> getBattleMap() {
		if (this.output != null) {
			return this.output.getBattleMap();
		} else {
			return null;
		}
	}
	
	public int getCompletedStars() {
		if (this.output != null) {
			return this.output.getStarNumber();
		} else {
			return 0;
		}
	}
	
	public synchronized void applyStrategy() {
		Strategy s = new MaxStarStrategy();
		this.output = s.apply(input);
	}
	
}
