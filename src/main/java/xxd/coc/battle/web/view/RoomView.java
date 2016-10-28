package xxd.coc.battle.web.view;

import org.json.JSONObject;

import xxd.coc.battle.core.model.Room;

public class RoomView implements View {

	private Room room;
	
	public RoomView(Room room) {
		this.room = room;
	}
	
	@Override
	public JSONObject toJSON() {
		if (this.room == null) {
			return new JSONObject();
		}
		JSONObject json = new JSONObject();
		try {
			json.put("id", room.getId());
		} catch (Exception e) {
			
		}
		return json;
	}
	
	public String toString() {
		return this.toJSON().toString();
	}

}
