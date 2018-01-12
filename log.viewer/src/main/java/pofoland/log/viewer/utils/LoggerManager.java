package pofoland.log.viewer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerManager {
	
	private static Logger logger = LoggerFactory.getLogger(LoggerManager.class);
	
	public LoggerManager() {}
	
	public static void info(Class<?> clazz, String msg) {
		logger.info("["+clazz.getName()+"] " + msg);
	}
	
	public static void info(Class<?> clazz, String format ,Object arg) {
		logger.info("["+clazz.getName()+"] " + format, arg);
	}
	
	public static void info(Class<?> clazz, String format ,Object... arguments) {
		logger.info("["+clazz.getName()+"] " + format, arguments);
	}
	
	public static void debug(Class<?> clazz, String msg) {
		logger.debug("["+clazz.getName()+"] " + msg);
	}
	
	public static void debug(Class<?> clazz, String format, Object arg) {
		logger.debug("["+clazz.getName()+"] " + format, arg);
	}
	
	public static void debug(Class<?> clazz, String format, Object... arguments) {
		logger.debug("["+clazz.getName()+"] " + format, arguments);
	}
	
	public static void error(Class<?> clazz, String msg) {
		logger.error("["+clazz.getName()+"] " + msg);
	}
	
	public static void error(Class<?> clazz, String format, Object arg) {
		logger.error("["+clazz.getName()+"] " + format, arg);
	}
	
	public static void error(Class<?> clazz, String format, Object... arguments) {
		logger.error("["+clazz.getName()+"] " + format, arguments);
	}
}
