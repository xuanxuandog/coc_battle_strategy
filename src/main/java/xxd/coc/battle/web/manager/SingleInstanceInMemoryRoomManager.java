package xxd.coc.battle.web.manager;

import java.util.HashMap;
import java.util.Map;

import xxd.coc.battle.core.model.Room;

/**
 * this is a simple version of RoomManager implementation,
 * it can only support one instance web service and manage all the 
 * rooms data in memory. However, it guarantees room data consistent
 * and thread safety  
 * @author xualu
 *
 */
public class SingleInstanceInMemoryRoomManager implements RoomManager {
	
	@Override
	public Room createRoom(Room room) {
		return this.threadSafeCreateRoom(room);
	}

	@Override
	public Room getRoom(String id) {
		// TODO Auto-generated method stub
		if (this.rooms != null && this.rooms.containsKey(id)) {
			return this.rooms.get(id).room;
		} else {
			return null;
		}
	}

	@Override
	public Room join(String roomId, String attackerId, int[] starConfidence) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void applyStrategy(String roomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanRooms() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getRoomCount() {
		// TODO Auto-generated method stub
		if (this.rooms == null) {
			return 0;
		} else {
			return this.rooms.size();
		}
	}

	private static int ROOM_ID = 1;
	
	private Map<String, RoomInstance> rooms = null;
	
	private synchronized String generateRoomId() {
		return String.valueOf(ROOM_ID++);
	}
	
	private class RoomInstance {
		Room room = null;
		long createdTime = System.currentTimeMillis();
		
		RoomInstance(Room room) {
			this.room = room;
		}
	}
	
	private synchronized Room threadSafeCreateRoom(Room room) {
		String roomId = this.generateRoomId();
		room.setId(roomId);
		if (this.rooms == null) {
			this.rooms = new HashMap<String, RoomInstance>(100);
		}
		RoomInstance roomIns = new RoomInstance(room);
		rooms.put(roomId, roomIns);
		return room;
	}

	

	
}
