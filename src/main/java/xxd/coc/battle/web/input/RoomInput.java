package xxd.coc.battle.web.input;

import java.io.Serializable;

public class RoomInput implements Serializable{
	
	private int target = 0;
	
	private int[] defenders = null;

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int[] getDefenders() {
		return defenders;
	}

	public void setDefenders(int[] defenders) {
		this.defenders = defenders;
	}
	
	
	
}
