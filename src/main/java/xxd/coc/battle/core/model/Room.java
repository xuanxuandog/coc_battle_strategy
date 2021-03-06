package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	private int[] initialStars;
	
	private int totalInitialStars;
	
	public Room() {
		this(null);
	}
	
	public Room(String id) {
		this.id = id;
		input = new StrategyInput();
		input.setAttackers(new HashMap<String, Attacker>());
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void joinDefenders(int[] initialStars) {
		this.initialStars = initialStars;
		if (this.initialStars != null) {
			for (int i : this.initialStars) {
				this.totalInitialStars += i;
			}
		}
		this.input.setDefenders(AttackerDefenderFactory.getInstance().getDefenders(initialStars));
	}
	
	public void join(Attacker attacker) {
		input.getAttackers().put(attacker.getId(), attacker);
	}
	
	public int[] getInitialStars() {
	    return this.initialStars;
	}
	
	public int getTotalInitialStars() {
		return this.totalInitialStars;
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
	
	public int getTotalCompletedStars() {
		if (this.output != null) {
			return this.output.getStarNumber();
		} else {
			return 0;
		}
	}
	
	public Map<String, Integer> getCompletedStars() {
		if (this.output != null) {
			return this.output.getCompletedStars();
		} else {
			return null;
		}
	}
	
	public void applyStrategy() {
		Strategy s = new MaxStarStrategy();
		this.output = s.apply(input);
	}
	
	public Map<String, Attacker> getAttackers() {
		if (this.input != null) {
			return this.input.getAttackers();
		} else {
			return null;
		}
	}
	
	public int getTargetStars() {
		if (this.input != null) {
			return this.input.getTargetStars();
		} else {
			return 0;
		}
	}
	
	public int getTryCount() {
		if (this.output != null) {
			return this.output.getTryCount();
		} else {
			return 0;
		}
	}
}
