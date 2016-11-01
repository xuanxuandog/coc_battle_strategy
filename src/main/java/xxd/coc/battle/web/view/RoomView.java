package xxd.coc.battle.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.Room;

public class RoomView {

	private Room room;
	
	public RoomView(Room room) {
		this.room = room;
	}
	
	public String getId() {
		return room.getId();
	}
	
	public int getTargetStars() {
		return room.getTargetStars();
	}
	
	public int getInitialStars() {
		return room.getInitialStars();
	}
	
	public Map<String,Set<String>> getBattleMap() {
		return room.getBattleMap();
	}
	
	public int getTotalCompletedStars() {
		return room.getTotalCompletedStars();
	}
	
	public List<AttackerView> getAttackers() {
		List<AttackerView> l = new ArrayList<AttackerView>();
		for (Attacker attacker : this.room.getAttackers().values()) {
			l.add(new AttackerView(attacker));
		}
		return l;
	}
	
	public Map<String, Integer> getCompletedStars() {
		return room.getCompletedStars();
	}
	
	public  int getTryCount() {
		return room.getTryCount();
	}
}
