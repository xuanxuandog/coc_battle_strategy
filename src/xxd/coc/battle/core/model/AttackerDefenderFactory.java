package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackerDefenderFactory {

	private static final AttackerDefenderFactory _instance = new AttackerDefenderFactory();
	
	private AttackerDefenderFactory() {
		
	}
	
	public static AttackerDefenderFactory getInstance() {
		return _instance;
	}
	
	public Attacker getAttacker(String id, int[] starConfidence) {
		
		return this.getAttacker(id, starConfidence, Attacker.DEFAULT_ATTACT_CHANCE);
	}
	
	public Attacker getAttacker(String id, int[] starConfidence, int attackChance) {
		
		if (id == null || starConfidence == null) {
			return null;
		}
		
		Map<String, Integer> m = new HashMap<String, Integer>();
		
		for (int i = 0; i < starConfidence.length; i++) {
			m.put(String.valueOf(i+1), starConfidence[i]);
		}
		
		return new Attacker(id, attackChance, m);
	}
	
	public List<Attacker> getAttackers(int[][] starConfidence) {
		if (starConfidence == null) {
			throw new IllegalArgumentException("wrong input for starConfidence");
		}
		int[] attackChance = new int[starConfidence.length];
		for (int i = 0; i < attackChance.length; i++) {
			attackChance[i] = Attacker.DEFAULT_ATTACT_CHANCE;
		}
		return this.getAttackers(starConfidence, attackChance);
	}
	
	public List<Attacker> getAttackers(int[][] starConfidence, int[] attackChance) {
		if (starConfidence == null || attackChance == null || starConfidence.length != attackChance.length) {
			throw new IllegalArgumentException("wrong input for starConfidence or attackChance");
		}
		List<Attacker> ret = new ArrayList<Attacker>();
		for (int i = 0; i < starConfidence.length; i++) {
			ret.add(this.getAttacker(String.valueOf(i+1), starConfidence[i], attackChance[i]));
		}
		
		return ret;
	}
	
	public Map<String, Defender> getDefenders(int count) {
		
		Map<String, Defender> m = new HashMap<String, Defender>();
		
		for (int i = 0; i < count; i++) {
			String id = String.valueOf(i + 1);
			m.put(id, new Defender(id));
		}
		
		return m;
	}
	
	public Map<String, Defender> getDefenders(int[] initialStars) {
		Map<String, Defender> m = new HashMap<String, Defender>();
		
		for (int i = 0; i < initialStars.length; i++) {
			String id = String.valueOf(i + 1);
			m.put(id, new Defender(id, initialStars[i]));
		}
		
		return m;
	}
}
