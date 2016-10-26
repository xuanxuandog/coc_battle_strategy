package xxd.coc.battle.core.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import xxd.coc.battle.core.model.AttackableGroup;
import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.AttackerDefenderFactory;
import xxd.coc.battle.core.model.Defender;

public class MaxStarStrategy implements Strategy {

	private StrategyOutput output = null;
	
	private int maxStars = 0;
	
	private int tryCount = 0;
	
	private int maxTryCount = 1;
	
	
	public StrategyOutput apply(StrategyInput input) {
		List<Attacker> attackers = input.getAttackers();
		Map<String, Defender> defenders = input.getDefenders();
		
		this.maxStars = input.getTargetStars();
		
		//attackers that will join the battle(attackers that has at least one attackable defender)
		List<Attacker> attackersOnBattle = new ArrayList<Attacker>();
		for (Attacker attacker : attackers) {
			//System.out.println("calculate attackable groups for attacker:" + attacker.toString());
			attacker.calculateAttackableGroups(defenders.values());
			if (attacker.getAttackableGroups() != null && attacker.getAttackableGroups().size() > 0) {
				attackersOnBattle.add(attacker);
			}
			//System.out.println("done:" + attacker.getAttackableGroups().size() + ", details:" + attacker.getAttackableGroups().toString());
			if (attacker.getAttackableGroups().size() > 0) {
			    this.maxTryCount *= attacker.getAttackableGroups().size();
			}
		}
		
		//put weak attacker before strong attacker for better performance
		Collections.sort(attackersOnBattle);
		
//		for (Attacker a : attackersOnBattle) {
//			System.out.println(a);
//		}
		
		oneRound(0, attackersOnBattle, defenders);
		
		return this.output;
	}
	
	

	private void oneRound(int index, List<Attacker> attackers, Map<String, Defender> defenders) {
		
		if (this.output != null && this.output.getStarNumber() >= this.maxStars) {
			return;
		}
		
		Attacker attacker = attackers.get(index);
		List<AttackableGroup> attackableGroups = attacker.getAttackableGroups();
		
		//iterator all attackable groups
		for (AttackableGroup attackableGroup : attackableGroups) {
			//System.out.println(attacker.getId() + "->" + attackableGroup.toString());
			for (String defenderId : attackableGroup.getDefenderIds()) {
				attacker.attack(defenders.get(defenderId));
			}
			if (index < attackers.size() - 1) { //not the last attacker, let the left attackers to continue attack
				oneRound(index + 1, attackers, defenders);
			} else { //this is the last attacker, need calculate the final result and compare to current max stars
				this.calculateResult(attackers, defenders);
				if (this.output != null && this.output.getStarNumber() >= this.maxStars) {
					return;
				}
			}
			attacker.reset();
		}
		
		
	}
	
	private void calculateResult(List<Attacker> attackers, Map<String, Defender> defenders) {
		
		
		this.tryCount++;
		
		
		
		Map<String, Set<String>> battleMap = new HashMap<String, Set<String>>();
		
		for (Defender def : defenders.values()) {
			def.reset();
		}
		
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
		int sum = 0;
		int initialStars = 0;
		for (Defender def : defenders.values()) {
			sum += def.getCompletedStars();
			initialStars += def.getInitialStars();
		}
		if (this.output == null) {
			this.output = new StrategyOutput();
		} 
		
		System.out.println("try " + tryCount + "/" + this.maxTryCount + "(" + tryCount * 100 / maxTryCount + "%), result:" + sum + "/" + this.maxStars);
		
		if (output.getStarNumber() < sum) {
			output.setStarNumber(sum);
			output.setBattleMap(battleMap);
		}
	}
	
	public static void main(String[] args) {
		int [][] i = new int[10][10];
		i[0] = new int[]{1,2,3,3,3,3,3,3,3,3};
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
		input.setAttackers(AttackerDefenderFactory.getInstance()
				.getAttackers(i));
		input.setDefenders(AttackerDefenderFactory.getInstance().getDefenders(i[0].length));
		
		Strategy strategy = new MaxStarStrategy();
		
		StrategyOutput output = strategy.apply(input);
		
		System.out.println(output);
		
	}
}
