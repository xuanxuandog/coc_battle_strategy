package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Attacker implements Comparable {
	
	//the default attack chance that every attacker has in the begining
	public static int DEFAULT_ATTACT_CHANCE = 2;
	
	//attacker id
	private String id;
	
	//initial attack chance of this attacker
	private int attackChance = DEFAULT_ATTACT_CHANCE;
	
	//left attack chance of this attacker
	private int leftAttackChance;
	
	//total stars that this attacker could get from all defenders regardless of attack chance,
	//this is just to tell how strong this attacker is
	private int totalStars = 0;
	
	
	//list of all groups of defenders that this attacker can get star from,
	//it's a list of combination of defenders among all defenders,
	//in each group, the number of defenders equals the left attack chance of this attacker
	private List<AttackableGroup> attackableGroups = null;
	
	/*
	 * key is the id of the defender, value is the number of star
	 * that this attacker think he can get from the defender.
	 */
	private Map<String, Integer> starConfidence = null;
	
	/*
	 * key is the id of the defender that this attacker has attacked during calculating best strategy
	 * value is the number of stars that this attacker get from that defender
	 */
	private Map<String, Integer> attacked = null;
	
	/*
	 * the ids of defenders that this attacker has already attacked before calculating best strategy
	 */
	private Set<String> attackedDefenders = null;
	
	public Map<String, Integer> getAttacked() {
		return attacked;
	}

	public Attacker(String id, Map<String, Integer> starConfidence, Set<String> attackedDefenders) {
		if (id == null) {
			throw new IllegalArgumentException("id can't be null");
		}
		
		this.id = id;
		
		this.attackedDefenders = attackedDefenders;
		
		if (this.attackedDefenders != null) {
			this.attackChance = Attacker.DEFAULT_ATTACT_CHANCE - this.attackedDefenders.size();
		} 
		this.starConfidence = starConfidence;
		this.attacked = new HashMap<String, Integer>();
		this.leftAttackChance = this.attackChance;
		
		//total stars can be calculated at the beginning according to attacker's confidence
		if (this.starConfidence != null) {
			for (Entry<String, Integer> entry : starConfidence.entrySet()) {
				if (entry.getValue() > Defender.MAX_STARS) {
					//attacker's confidence can not exceed the max number of star that each defender has
					this.starConfidence.put(entry.getKey(), Defender.MAX_STARS);
				}
				//calculate total stars
				this.totalStars += entry.getValue();
			}
		}
	}
	
	public Attacker(String id, Map<String, Integer> starConfidence) {
		this(id, starConfidence, null);
	}
	
	/**
	 * 
	 * @param defenderId
	 * @return number of stars that this attacker get from the given defender id
	 */
	public int attack(Defender defender) {
		if (defender == null) {
			return 0;
		}

		this.attacked.put(defender.getId(), this.starConfidence.get(defender.getId()));
		this.leftAttackChance--;

		return this.starConfidence.get(defender.getId());
	}
	
	/*
	 * check whether this attacker can attack given defender
	 */
	public boolean canAttack(Defender defender) {
		/*
		 * need match 3 condition:
		 * 1. left attack chance > 0
		 * 2. the given defender has not been attacked by this attacker already
		 * 3. this attacker can get at least 1 star from the given defender
		 */
		return this.leftAttackChance > 0 
				&& !this.attacked.containsKey(defender.getId())
				&& (this.attackedDefenders == null || !this.attackedDefenders.contains(defender.getId()))
				&& this.starConfidence.get(defender.getId()) > 0;
	}
	
	
	public void reset() {
		this.leftAttackChance = this.attackChance;
		this.attacked.clear();
	}
	
	public String getId() {
		return id;
	}

	public int getLeftAttackChance() {
		return leftAttackChance;
	}
	
	public List<AttackableGroup> getAttackableGroups() {
		return this.attackableGroups;
	}

	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("attacker: ").append(this.id)
		.append(", star confidence:").append(this.starConfidence.toString());
		if (this.attackableGroups != null) {
		    sb.append(", attackable groups:");
		    sb.append(this.attackableGroups.toString());
		}
		sb.append(", attacked:").append(this.attacked.toString());

		return sb.toString();
	}
	
	/**
	 * based on all given defenders and the confidence map of this attacker,
	 * also the left attack chance of this attacker
	 * calculate the list of combination of defenders that this attacker can attack
	 * @param defenders
	 */
	public void calculateAttackableGroups(Collection<Defender> defenders) {
		if (defenders == null) {
			return;
		}
		
		//first get all the defenders that this attacker can attack
		List<String> attackableDefenderIds = new ArrayList<String>();
		for (Defender defender : defenders) {
			if (this.canAttack(defender)) {
				attackableDefenderIds.add(defender.getId());
			}
		}
		
		//no attackable defender for this attacker
		if (attackableDefenderIds.size() == 0) {
			return;
		}
		
		this.attackableGroups = new ArrayList<AttackableGroup>();
		
		
		if (attackableDefenderIds.size() <= this.leftAttackChance) {
			//number of attackable defenders is less than left attack chance, so only has one possible combination
			this.attackableGroups.add(new AttackableGroup(attackableDefenderIds.toArray(new String[attackableDefenderIds.size()])));
		} else {
			//recursively calculate the combination
			this.calculateAttackableGroups(attackableDefenderIds, 0, new HashSet<String>());	
		}
		
		//sort the attackable group by total stars of each group, 
		//order the list by putting the group with most stars at head,
		//this is for performance in later strategy quickly find the battle mapping with target star number
		for (AttackableGroup attackableGroup : this.attackableGroups) {
			attackableGroup.calculateStars(this.starConfidence);
		}
		Collections.sort(this.attackableGroups);
	}
	
	private void calculateAttackableGroups(List<String> all, int index, Set<String> tmp) {
		
		for (int i = index; i < all.size(); i++) {
			tmp.add(all.get(i));
			if (tmp.size() == this.leftAttackChance) {
				this.attackableGroups.add(new AttackableGroup(tmp.toArray(new String[tmp.size()])));
			} else {
				calculateAttackableGroups(all, i + 1, tmp);
			}
			tmp.remove(all.get(i));
		}
	}
	
	

	public Map<String, Integer> getStarConfidence() {
		return starConfidence;
	}

	@Override
	public int compareTo(Object o) {
		return this.totalStars - ((Attacker)o).totalStars;
	}
	
	public Set<String> getAttackedDefenders() {
		return this.attackedDefenders;
	}
}
