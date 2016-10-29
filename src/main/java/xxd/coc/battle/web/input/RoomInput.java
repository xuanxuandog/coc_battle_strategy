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
		    	if (array != null) {
			    	this.defenders = new int[array.length()];
			    	for (int i = 0; i < array.length(); i++) {
			    		this.defenders[i] = array.optInt(i);
			    	}
		    	}
		    }
		    if (json.has("starConfidence")) {
		    	JSONArray array = json.optJSONArray("starConfidence");
		    	if (array != null) {
		    		this.starConfidence = new int[array.length()];
		    		for (int i = 0; i < array.length(); i++) {
		    			this.starConfidence[i] = array.optInt(i);
		    		}
		    	}
		    }
		} catch (Exception e) {
			
		}
	}
	
	private int target = 0;
	
	private int[] defenders = null;

	private int[] starConfidence = null;
	
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

	public int[] getStarConfidence() {
		return starConfidence;
	}

	public void setStarConfidence(int[] starConfidence) {
		this.starConfidence = starConfidence;
	}
	
	
	
}
