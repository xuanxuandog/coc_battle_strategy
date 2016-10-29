package xxd.coc.battle.web.input;

import java.io.Serializable;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoomInput{
	
	public RoomInput(String input) {
		if (input == null) return;
		
		try {
		    input = URLDecoder.decode(input, "UTF-8");
		    JSONObject json = new JSONObject(input);
		    this.target = json.optInt("target");
		    if (json.has("defenders")) {
		    	JSONArray array = json.optJSONArray("defenders");
		    	if (array == null) return;
		    	this.defenders = new int[array.length()];
		    	for (int i = 0; i < array.length(); i++) {
		    		this.defenders[i] = array.optInt(i);
		    	}
		    }
		} catch (Exception e) {
			
		}
	}
	
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
