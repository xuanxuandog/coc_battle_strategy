package xxd.coc.battle.web.manager;

/**
 * This class is an single instance to return 
 * the global instance of RommManager implementation
 * based on configuration(in future).
 * @author xualu
 *
 */
public class RoomManagerFactory {

	/*
	 * FIXME: need load room manager based on configuration in future
	 */
	private static RoomManager roomManager = new SingleInstanceInMemoryRoomManager();
	
	public static RoomManager getRoomManager() {
		return roomManager;
	}
}
