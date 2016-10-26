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
		
		if (id == null || starConfidence == null) {
			return null;
		}
		
		Map<String, Integer> m = new HashMap<String, Integer>();
		
		for (int i = 0; i < starConfidence.length; i++) {
			m.put(String.valueOf(i+1), starConfidence[i]);
		}
		
		return new Attacker(id, m);
	}
	
	public List<Attacker> getAttackers(int[][] starConfidence) {
		if (starConfidence == null) {
			return null;
		}
		List<Attacker> ret = new ArrayList<Attacker>();
		for (int i = 0; i < starConfidence.length; i++) {
			ret.add(this.getAttacker(String.valueOf(i+1), starConfidence[i]));
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
}
