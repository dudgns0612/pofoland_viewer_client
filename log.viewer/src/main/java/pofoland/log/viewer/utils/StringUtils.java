package pofoland.log.viewer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	/**
	 * 대소문자 구분없이 포함 유무
	 * @param complete
	 * @param lack
	 * @return
	 */
	public static boolean toUpperCaseConstains (String complete, String lack) {
		String toUpComplete = complete.toUpperCase();
		String toLowerComplete = complete.toLowerCase();
		if (toUpComplete.contains(lack) || toLowerComplete.contains(lack)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 숫자만 포함 유무 검사
	 * @param str
	 * @return
	 */
	public static boolean numberMatcherInspect(String str) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	public static String makeSendStr (String protocol) {
		return protocol+"&";
	}
	
	public static String makeSendStr (String protocol,String msg) {
		return protocol+"&"+msg;
	}
	
	public static String makeSendStr (String protocol,Object msg) {
		return protocol+"&"+msg;
	}
}
