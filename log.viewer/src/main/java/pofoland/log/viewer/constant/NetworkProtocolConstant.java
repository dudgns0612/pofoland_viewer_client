package pofoland.log.viewer.constant;

/**
 * SERVER_SEND_LOG_MESSAGE = 로그 메세지 전송 프로토콜
 * CLINET_SEND_START = 클라이언트 로그 전송 시작 프로토콜
 * CLINET_SEND_STOP = 클라이언트 로그 전송 정지 프로토콜
 * @author KIM YOUNG HOON
 */
public class NetworkProtocolConstant {
	
	public static final String SERVER_SEND_LOG_MESSAGE = "SERVER_SEND_LOG_MESSAGE";
	public static final String CLINET_SEND_START = "CLINET_SEND_START";
	public static final String CLINET_SEND_STOP = "CLINET_SEND_STOP";
	public static final String CLIENT_LOG_SIZE_CHANGE = "CLIENT_LOG_SIZE_CHANGE";
	public static final String CLIENT_LOG_SIZE_DEFUALT = "CLIENT_LOG_SIZE_DEFUALT";
	public static final String CLIENT_LOG_SIZE = "CLIENT_LOG_SIZE";
	public static final String CLIENT_LOG_DATE = "CLINET_LOG_DATE";
	public static final String CLIENT_LOG_DIR = "CLIENT_LOG_DIR";
	public static final String CLIENT_LOG_FILE_DOWN = "CLINET_LOG_FILE_DOWN";
	
	public static final String SERVER_ERROR_MESSAGE = "SERVER_ERROR_MESSAGE";
}
