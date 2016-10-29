package xxd.coc.battle.web.manager;

import xxd.coc.battle.core.model.Room;

/**
 * an interface to manage rooms, the subclasses need
 * take care of thread safety based on their own scenarios
 * @author xualu
 *
 */

public interface RoomManager {
	
	/**
	 * create a new room with some given data in the room object,
	 * for example: target stars, initial defenders, initial attackers
	 * @param numberOfDefenders
	 * @return
	 */
	Room createRoom(Room room);
	
	/**
	 * return a room by given room id
	 * @param roomId
	 * @return
	 */
	Room getRoom(String roomId);
	
	/**
	 * add an attacker to a room by given room id and attacker id and his/her star confidence against all defenders
	 * @param roomId
	 * @param attackerId
	 * @param starConfidence
	 * @return
	 */
	Room join(String roomId, String attackerId, int[] starConfidence);
	
	/**
	 * apply strategy on this room based on current target stars, defenders and attackers
	 * @param roomId
	 */
	Room applyStrategy(String roomId);
	
	/**
	 * a callback function which will be called by a centralized RoomCleanManager periodically to
	 * clean up rooms that are no longer in use for saving resource
	 */
	void cleanRooms();
	
	/**
	 * get the total number of active rooms
	 * @return
	 */
	int getRoomCount();
}
