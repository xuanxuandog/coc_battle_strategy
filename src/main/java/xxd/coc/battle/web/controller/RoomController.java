package xxd.coc.battle.web.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xxd.coc.battle.core.model.Room;
import xxd.coc.battle.web.input.RoomInput;
import xxd.coc.battle.web.manager.RoomManagerFactory;
import xxd.coc.battle.web.view.RoomCountView;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	private static final Logger log = Logger.getLogger(RoomController.class);
	
	@RequestMapping(method = RequestMethod.POST)
    public Room createRoom(@RequestBody String body) {
		
		RoomInput roomInput = new RoomInput(body);
		Room room = new Room();
		room.setTargetStars(roomInput.getTarget());
		room.joinDefenders(roomInput.getDefenders());
		room = RoomManagerFactory.getRoomManager().createRoom(room);
		return room;
	}
	
	@RequestMapping(path="_count", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getRoomCount() {
		return new ResponseEntity<String>(new RoomCountView(
				RoomManagerFactory.getRoomManager().getRoomCount()).toString(), HttpStatus.OK);
	}
	
	@RequestMapping(path="{id}", method = RequestMethod.GET)
	public Room getRoom(@PathVariable String id) {
		return RoomManagerFactory.getRoomManager().getRoom(id);
	}
	
	@RequestMapping(path="join/{roomId}/{attackerId}", method = RequestMethod.POST) 
	public Room joinRoom(@PathVariable String roomId, @PathVariable String attackerId, @RequestBody String body) {
		RoomInput roomInput = new RoomInput(body);
		return RoomManagerFactory.getRoomManager().join(roomId, attackerId, roomInput.getStarConfidence());
	}
	
	@RequestMapping(path="apply/{roomId}", method = RequestMethod.GET) 
	public Room applyStrategy(@PathVariable String roomId) {
		return RoomManagerFactory.getRoomManager().applyStrategy(roomId);
	}
	
	@RequestMapping(path="clean", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> cleanRooms() {
		RoomManagerFactory.getRoomManager().cleanRooms();
		return new ResponseEntity<String>(new RoomCountView(
				RoomManagerFactory.getRoomManager().getRoomCount()).toString(), HttpStatus.OK);
	}
	
}
