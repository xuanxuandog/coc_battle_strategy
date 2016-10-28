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
	 * create a new room with given number of defenders
	 * @param numberOfDefenders
	 * @return
	 */
	Room createRoom(int numberOfDefenders);
	
	/**
	 * create a new room with given defenders and their initial stars
	 * @param defendersWithInitialStars
	 * @return
	 */
	Room createRoom(int[] defendersWithInitialStars);
	
	/**
	 * return a room by given room id
	 * @param id
	 * @return
	 */
	Room getRoom(String id);
	
	/**
	 * add an attacker to a room by given room id and attacker id and his/her star confidence against all defenders
	 * @param roomId
	 * @param attackerId
	 * @param starConfidence
	 * @return
	 */
	Room join(String roomId, String attackerId, int[] starConfidence);
	
	/**
	 * a callback function which will be called by a centralized RoomCleanManager periodically to
	 * clean up rooms that are no longer in use for saving resource
	 */
	void cleanRooms();
}
