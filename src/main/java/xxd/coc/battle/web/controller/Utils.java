package xxd.coc.battle.web.controller;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class);
	
	public static String getErrorMessage(String message) {
		JSONObject jsonError = new JSONObject();
		try {
			jsonError.put("error", message);
		} catch (Exception e) {
			log.error("generate error message failed:", e);
		}
		return jsonError.toString();
	}
}
