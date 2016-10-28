package xxd.coc.battle.web.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xxd.coc.battle.core.model.Room;
import xxd.coc.battle.web.input.RoomInput;
import xxd.coc.battle.web.manager.RoomManagerFactory;
import xxd.coc.battle.web.view.RoomCountView;
import xxd.coc.battle.web.view.RoomView;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	private static final Logger log = Logger.getLogger(RoomController.class);
	
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> createRoom(@ModelAttribute RoomInput roomInput) {
		/*
		 * NOTE: the annotation ModelAttribute is important, which can't be replaced with RequestBody,
		 * Because RequestBody can only support header with content type application/json, and by default,
		 * the header content type is application/x-www-form-urlencoded. So if want to accept both two, need
		 * use ModelAttribute annotation
		 */
		
		Room room = new Room();
		room.setTargetStars(roomInput.getTarget());
		room.joinDefenders(new int[]{0,0,0});
		RoomView view = new RoomView(RoomManagerFactory.getRoomManager().createRoom(room));
		return new ResponseEntity<String>(view.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(path="_count", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getRoomCount() {
		return new ResponseEntity<String>(new RoomCountView(
				RoomManagerFactory.getRoomManager().getRoomCount()).toJSON().toString(), HttpStatus.OK);
	}
	
	@RequestMapping(path="{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getRoom(@PathVariable String id) {
		Room room = RoomManagerFactory.getRoomManager().getRoom(id);
		return new ResponseEntity<String>(
				new RoomView(room).toString(), HttpStatus.OK);
	}
}
