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
	public static JSONObject setJsonValue(String protocol ,String key, String value) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("PROTOCOL", protocol);
        jsonObject.put(key , value);
        
        return jsonObject;
	}
	
    @SuppressWarnings("unchecked")
	public static JSONObject jsonSetValue(String protocol , String[] keys , String[] values) {
        JSONObject json = new JSONObject();
        json.put("PROTOCOL", protocol);
        try {
            for(int i = 0 ; i < keys.length ; i++) {
                json.put(keys[i], values[i]);
            }
        } catch (Exception e) {
            System.out.println("keys and values not the same size!");
        }
        return json;
    }
}
