package xxd.coc.battle.core.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import xxd.coc.battle.core.model.AttackableGroup;
import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.AttackerDefenderFactory;
import xxd.coc.battle.core.model.Defender;

public class MaxStarStrategy implements Strategy {

	private StrategyOutput output = null;
	
	private int targetStars = 0;
	
	//set of defenders that has already been get max number of stars
	//so that in one try round, later attackers don't need to try 
	//attackable groups in which all defenders have been got max number of stars,
	//this is for improving performance.
	private Set<String> maxStarDefenders;
	
	public StrategyOutput apply(StrategyInput input) {
		List<Attacker> attackers = input.getAttackers();
		Map<String, Defender> defenders = input.getDefenders();
		
		this.targetStars = input.getTargetStars();
		
		//attackers that will join the battle(attackers that has at least one attackable defender)
		List<Attacker> attackersOnBattle = new ArrayList<Attacker>();
		for (Attacker attacker : attackers) {
			//System.out.println("calculate attackable groups for attacker:" + attacker.toString());
			attacker.calculateAttackableGroups(defenders.values());
			if (attacker.getAttackableGroups() != null && attacker.getAttackableGroups().size() > 0) {
				attackersOnBattle.add(attacker);
			}
		}
		
		//put weak attacker before strong attacker for better performance
		Collections.sort(attackersOnBattle);
		
		maxStarDefenders = new HashSet<String>();
		
		//recursively list all the situations and return if the one try round meet the target number of stars
		oneRound(0, attackersOnBattle, defenders);
		
		return this.output;
	}
	
	
	/**
	 * 
	 * @param index the index of attackers list, from 0 to the size of attacker list minus 1
	 * @param attackers
	 * @param defenders
	 */
	private void oneRound(int index, List<Attacker> attackers, Map<String, Defender> defenders) {
		
		//target meet, return the output
		if (this.output != null && this.output.getStarNumber() >= this.targetStars) {
			return;
		}
		
		//get current attacker in this try round
		Attacker attacker = attackers.get(index);
		List<AttackableGroup> attackableGroups = attacker.getAttackableGroups();
		
		//iterator all attackable groups of this attacker
		for (AttackableGroup attackableGroup : attackableGroups) {
			
			//check whether all defenders in this group has been got max stars,
			//if so, skip current group to improve performance
			boolean allMaxStar = true;
			for (String defenderId : attackableGroup.getDefenderIds()) {
				if (!maxStarDefenders.contains(defenderId)) {
					allMaxStar = false;
					break;
				}
			}
			if (allMaxStar) {
				continue;
			}
			
			//attack each defender in this attackable group
			for (String defenderId : attackableGroup.getDefenderIds()) {
				if (attacker.attack(defenders.get(defenderId)) >= Defender.MAX_STARS) {
					//record defender when it has been got max stars
					maxStarDefenders.add(defenderId);
				}
			}
			
			if (index < attackers.size() - 1) { //not the last attacker, recursively let the later attackers to continue attack their attackable groups
				oneRound(index + 1, attackers, defenders);
			} else { //this is the last attacker, need calculate the final result and compare to current max stars
				this.calculateResult(attackers, defenders);
				//meet the target, return the output
				if (this.output != null && this.output.getStarNumber() >= this.targetStars) {
					return;
				}
			}
			//backtracking and reset the attacker for next try round 
			attacker.reset();
		}
		
		
	}
	
	private void calculateResult(List<Attacker> attackers, Map<String, Defender> defenders) {
		
		//need clear the set for next new try round
		maxStarDefenders.clear();
		
		Map<String, Set<String>> battleMap = new HashMap<String, Set<String>>();
		
		//reset all defenders for next new try round
		for (Defender def : defenders.values()) {
			def.reset();
		}
		
		//building the battle map according to the attack history of each attacker
		for (Attacker attacker : attackers) {
			Map<String, Integer> attacked = attacker.getAttacked();
			
			if (attacked != null) {
				Set<String> defenderIds = new HashSet<String>();
				for (Entry<String, Integer> entry : attacked.entrySet()) {
					if (defenders.containsKey(entry.getKey())) {
						defenders.get(entry.getKey()).attacked(entry.getValue());
					}
					defenderIds.add(entry.getKey());
				}
				battleMap.put(attacker.getId(), defenderIds);
			}
		}
		
		//calculate the total number of stars and the increased stars based on initial number of stars(if it's not 0)
		int sum = 0;
		int initialStars = 0;
		for (Defender def : defenders.values()) {
			sum += def.getCompletedStars();
			initialStars += def.getInitialStars();
		}
		if (this.output == null) {
			this.output = new StrategyOutput();
		} 
		
		//if current round's result is better than the max result so far, replace it
		//otherwise, do nothing and try next round
		if (output.getStarNumber() < sum) {
			output.setStarNumber(sum);
			output.setBattleMap(battleMap);
			output.setInitialStars(initialStars);
		}
	}
	
	public static void main(String[] args) {
		int [][] i = new int[10][10];
		i[0] = new int[]{0,2,3,3,3,3,3,3,3,3};
		i[1] = new int[]{0,1,2,2,3,3,3,3,3,3};
		i[2] = new int[]{0,0,1,1,2,3,3,3,3,3};
		i[3] = new int[]{0,0,0,2,3,3,3,3,3,3};
		i[4] = new int[]{0,0,0,0,1,2,2,3,3,3};
		i[5] = new int[]{0,0,0,3,3,3,3,3,3,3};
		i[6] = new int[]{0,0,0,0,0,1,2,2,3,3};
		i[7] = new int[]{0,0,0,0,0,0,1,2,2,3};
		i[8] = new int[]{0,0,0,0,0,0,0,0,1,2};
		i[9] = new int[]{0,0,0,0,0,0,0,0,1,1};
		

		
		StrategyInput input = new StrategyInput();
		input.setTargetStars(26);
		input.setAttackers(AttackerDefenderFactory.getInstance().getAttackers(i, new int[]{1,2,2,2,2,2,2,2,2,2}));
		input.setDefenders(AttackerDefenderFactory.getInstance().getDefenders(new int[]{1,0,0,0,0,0,0,0,0,0}));
		
		Strategy strategy = new MaxStarStrategy();
		
		StrategyOutput output = strategy.apply(input);
		
		System.out.println(output);
		
	}
}
