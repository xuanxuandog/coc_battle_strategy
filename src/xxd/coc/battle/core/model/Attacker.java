package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attacker implements Comparable {
	
	private static final int DEFAULT_ATTACT_CHANCE = 2;
	
	private String id;
	
	private int attackChance = 2;
	
	private int leftAttackChance;
	
	//total stars that this attacker could get from all defenders regardless of attack chance,
	//this is just to relect how strong this attacker is
	private int totalStars = 0;
	
	
	private List<AttackableGroup> attackableGroups = null;
	
	/*
	 * key is the id of the defender, value is the number of star
	 * that this attacker think he can get from the defender.
	 */
	private Map<String, Integer> starConfidence = null;
	
	/*
	 * key is the id of the defender that this attacker has attacked,
	 * value is the number of stars that this attacker get from that defender
	 */
	private Map<String, Integer> attacked = null;
	
	public Map<String, Integer> getAttacked() {
		return attacked;
	}

	public Attacker(String id, int attackChance, Map<String, Integer> starConfidence) {
		
		if (id == null) {
			throw new IllegalArgumentException("id can't be null");
		}
		
		this.id = id;
		
		this.attackChance = attackChance;
		this.starConfidence = starConfidence;
		this.attacked = new HashMap<String, Integer>();
		this.leftAttackChance = this.attackChance;
		
		if (this.starConfidence != null) {
			for (int star : this.starConfidence.values()) {
				this.totalStars += star;
			}
		}
	}
	
	public Attacker(String id, Map<String, Integer> starConfidence) {
		this(id, DEFAULT_ATTACT_CHANCE, starConfidence);
	}
	
	/**
	 * 
	 * @param defenderId
	 * @return number of stars that this attacker get from the given defender id
	 */
	public void attack(Defender defender) {
		if (defender == null) {
			return;
		}

		this.attacked.put(defender.getId(), this.starConfidence.get(defender.getId()));
		this.leftAttackChance--;

	}
	
	public boolean canAttack(Defender defender) {
		return this.leftAttackChance > 0 && !this.attacked.containsKey(defender.getId())
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
	
	public void calculateAttackableGroups(Collection<Defender> defenders) {
		if (defenders == null) {
			return;
		}
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
		
		//get the attackable groups from the attackable defenders based on left attack chances
		this.attackableGroups = new ArrayList<AttackableGroup>();
		
		
		if (attackableDefenderIds.size() <= this.leftAttackChance) {
			//number of attackable defenders is less than left attack chance, so only has one possible group
			this.attackableGroups.add(new AttackableGroup(attackableDefenderIds.toArray(new String[attackableDefenderIds.size()])));
		} else {
			this.calculateAttackableGroups(attackableDefenderIds, 0, new HashSet<String>());	
		}
		
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

	@Override
	public int compareTo(Object o) {
		return this.totalStars - ((Attacker)o).totalStars;
	}
}
