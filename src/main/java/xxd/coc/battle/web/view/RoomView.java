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
		JSONObject json = new JSONObject();
		return json;
	}

}
