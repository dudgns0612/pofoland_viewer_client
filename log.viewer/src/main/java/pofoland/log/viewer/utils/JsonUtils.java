package pofoland.log.viewer.utils;

import org.json.simple.JSONObject;

public class JsonUtils {
	
	@SuppressWarnings("unchecked")
	public static JSONObject setJsonValue(String protocol) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PROTOCOL", protocol);
        
        return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject setJsonValue(String protocol, Object value) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("PROTOCOL", protocol);
        jsonObject.put("VALUE" , value);
        
        return jsonObject;
	}
}
