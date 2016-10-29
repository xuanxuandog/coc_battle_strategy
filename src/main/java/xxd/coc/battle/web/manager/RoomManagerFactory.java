package xxd.coc.battle.web.manager;

import org.springframework.aop.target.ThreadLocalTargetSource;

/**
 * This class is an single instance to return 
 * the global instance of RommManager implementation
 * based on configuration(in future).
 * @author xualu
 *
 */
public class RoomManagerFactory {

	private static final long CLEAN_ROOMS_INTERVAL = 1000 * 3600; //one hour
	
	/*
	 * FIXME: need load room manager based on configuration in future
	 */
	private static RoomManager roomManager = new SingleInstanceInMemoryRoomManager();
	
	static {
		new CleanRoomsThread().start();
	}
	
	public static RoomManager getRoomManager() {
		return roomManager;
	}
	
	static class CleanRoomsThread extends Thread {
		
		public void run() {
			while (true) {
				if (roomManager != null) {
					roomManager.cleanRooms();
				}
				try {
					Thread.currentThread().sleep(CLEAN_ROOMS_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
