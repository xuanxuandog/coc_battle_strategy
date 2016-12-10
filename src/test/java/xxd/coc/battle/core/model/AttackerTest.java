package xxd.coc.battle.core.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class AttackerTest extends TestCase {
	
	
	public void testLeftAttackChance() {
		Map<String, Integer> confidence = new HashMap<String, Integer>();
		confidence.put("2", 2);
		confidence.put("1", 3);
		Set<String> attacked = new HashSet<String>();
		attacked.add("1");
		Attacker a = new Attacker("1", confidence, attacked);
		assertEquals(a.getLeftAttackChance(), 1);
	}

}
