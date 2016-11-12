package xxd.coc.battle.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttackerDefenderFactory {

	private static final AttackerDefenderFactory _instance = new AttackerDefenderFactory();
	
	private AttackerDefenderFactory() {
		
	}
	
	public static AttackerDefenderFactory getInstance() {
		return _instance;
	}
	
	public Attacker getAttacker(String id, int[] starConfidence) {
		
		return this.getAttacker(id, starConfidence, null);
	}
	
	public Attacker getAttacker(String id, int[] starConfidence, Set<String> attackedDefenders) {
		
		if (id == null || starConfidence == null) {
			return null;
		}
		
		Map<String, Integer> m = new HashMap<String, Integer>();
		
		for (int i = 0; i < starConfidence.length; i++) {
			m.put(String.valueOf(i+1), starConfidence[i]);
		}
		
		return new Attacker(id, m, attackedDefenders);
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
