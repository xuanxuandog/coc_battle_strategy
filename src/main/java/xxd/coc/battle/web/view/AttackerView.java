package xxd.coc.battle.web.view;

import java.util.Map;
import java.util.Set;

import xxd.coc.battle.core.model.Attacker;

public class AttackerView {
	private Attacker attacker;
	
	public AttackerView(Attacker attacker) {
		this.attacker = attacker;
	}
	
	public String getId() {
		return this.attacker.getId();
	}
	
	public Map<String, Integer> getStarConfidence() {
		return this.attacker.getStarConfidence();
	}
	
	public int getAttackChance() {
		return this.attacker.getLeftAttackChance();
	}
	
	public Set<String> getAttacked() {
		return this.attacker.getAttackedDefenders();
	}
}
