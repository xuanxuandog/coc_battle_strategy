package xxd.coc.battle.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xxd.coc.battle.core.model.Attacker;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> room(HttpServletRequest req) {
		
		return new ResponseEntity<String>("hello12213", HttpStatus.OK);
    }
}
