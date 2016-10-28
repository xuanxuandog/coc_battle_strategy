package xxd.coc.battle.web.view;

import org.json.JSONObject;

public class RoomCountView implements View {

	private int roomCount = 0;
	
	public RoomCountView(int roomCount) {
		this.roomCount = roomCount;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
		json.put("count", this.roomCount);
		} catch (Exception e){}
		return json;
	}

}
