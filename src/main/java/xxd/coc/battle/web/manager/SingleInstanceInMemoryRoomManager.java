package xxd.coc.battle.web.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	//private static final long ROOM_TIME_OUT = 1000 * 3600 * 6; //six hours
	
	private static final long ROOM_TIME_OUT = 1000 * 10;
	
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
		return this.threadSafeJoin(roomId, attackerId, starConfidence);
	}
	
	

	@Override
	public Room applyStrategy(String roomId) {
		return this.threadSafeApplyStrategy(roomId);
	}

	@Override
	public void cleanRooms() {
		this.threadSafeCleanRooms();
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
	
	private volatile Map<String, RoomInstance> rooms = null;
	
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
	
	private Room threadSafeCreateRoom(Room room) {
		if (this.rooms == null) {
			synchronized (this) {
				if (this.rooms == null) {
					this.rooms = new HashMap<String, RoomInstance>(100);
				}
			}	
		}
		synchronized(this.rooms) {
			String roomId = this.generateRoomId();
			room.setId(roomId);
			RoomInstance roomIns = new RoomInstance(room);
			rooms.put(roomId, roomIns);
			return room;
		}
	}
	
	private Room threadSafeJoin(String roomId, String attackerId, int[] starConfidence) {
		if (this.rooms.containsKey(roomId)) {
			synchronized(this.rooms.get(roomId)) {
				Room room = this.rooms.get(roomId).room;
				room.joinAttacker(attackerId, starConfidence);
				return room;
			}
		}
		return null;
	}

	private Room threadSafeApplyStrategy(String roomId) {
		if (this.rooms.containsKey(roomId)) {
			synchronized(this.rooms.get(roomId)) {
				Room room = this.rooms.get(roomId).room;
				room.applyStrategy();
				return room;
			}
		}
		return null;
	}
	
	private void threadSafeCleanRooms() {
		if (this.rooms != null) {
			synchronized(this.rooms) {
				List<String> toBeRemoved = new ArrayList<String>();
				long currenttime = System.currentTimeMillis();
				for (RoomInstance roomInstance : rooms.values()) {
					if (currenttime - roomInstance.createdTime > ROOM_TIME_OUT) {
						toBeRemoved.add(roomInstance.room.getId());
					}
				}
				for (String id : toBeRemoved) {
					this.rooms.remove(id);
				}
			}
		}
	}
	
}
