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

import xxd.coc.battle.core.model.Attacker;
import xxd.coc.battle.core.model.AttackerDefenderFactory;
import xxd.coc.battle.core.model.Room;
import xxd.coc.battle.web.input.InputWrapper;
import xxd.coc.battle.web.manager.RoomManagerFactory;
import xxd.coc.battle.web.view.RoomCountView;
import xxd.coc.battle.web.view.RoomView;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	private static final Logger log = Logger.getLogger(RoomController.class);
	
	@RequestMapping(method = RequestMethod.POST)
    public RoomView createRoom(@RequestBody String body) {
		
		InputWrapper InputWrapper = new InputWrapper(body);
		Room room = new Room();
		if (InputWrapper.getTarget() > 0) {
			room.setTargetStars(InputWrapper.getTarget());
		}
		room.joinDefenders(InputWrapper.getDefenders());
		room = RoomManagerFactory.getRoomManager().createRoom(room);
		return new RoomView(room);
	}
	
	@RequestMapping(path="count", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getRoomCount() {
		return new ResponseEntity<String>(new RoomCountView(
				RoomManagerFactory.getRoomManager().getRoomCount()).toString(), HttpStatus.OK);
	}
	
	@RequestMapping(path="{id}", method = RequestMethod.GET)
	public RoomView getRoom(@PathVariable String id) {
		return new RoomView(RoomManagerFactory.getRoomManager().getRoom(id));
	}
	
	@RequestMapping(path="join/{roomId}/{attackerId}", method = RequestMethod.POST) 
	public RoomView joinRoom(@PathVariable String roomId, @PathVariable String attackerId, @RequestBody String body) {
		InputWrapper inputWrapper = new InputWrapper(body);
		Attacker attacker = AttackerDefenderFactory.getInstance().getAttacker(attackerId, inputWrapper.getStarConfidence(), inputWrapper.getAttackedDefenders());
		return new RoomView(RoomManagerFactory.getRoomManager().join(roomId, attacker));
	}
	
	@RequestMapping(path="apply/{roomId}", method = RequestMethod.GET) 
	public RoomView applyStrategy(@PathVariable String roomId) {
		return new RoomView(RoomManagerFactory.getRoomManager().applyStrategy(roomId));
	}
	
	@RequestMapping(path="clean", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> cleanRooms() {
		RoomManagerFactory.getRoomManager().cleanRooms();
		return new ResponseEntity<String>(new RoomCountView(
				RoomManagerFactory.getRoomManager().getRoomCount()).toString(), HttpStatus.OK);
	}
	
	@RequestMapping(path="target/{roomId}/{targetStars}", method = RequestMethod.GET)
	public RoomView setTargetStars(@PathVariable String roomId, @PathVariable int targetStars) {
		return new RoomView(RoomManagerFactory.getRoomManager().setTargetStars(roomId, targetStars));
	}
	
}
