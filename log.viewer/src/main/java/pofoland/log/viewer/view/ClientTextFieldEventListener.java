package pofoland.log.viewer.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.netty.channel.ChannelHandlerContext;
import pofoland.log.viewer.client.LogViewerTcpClientHandler;
import pofoland.log.viewer.constant.KeyCodeRuleConstant;
import pofoland.log.viewer.constant.NetworkProtocolConstant;
import pofoland.log.viewer.queue.CircularQueue;
import pofoland.log.viewer.utils.LoggerManager;
import pofoland.log.viewer.utils.StringUtils;

public class ClientTextFieldEventListener extends KeyAdapter{

	private ChannelHandlerContext ctx  = null;
	private CircularQueue consoleQueue = null;
	private JTextField consoleTextField  = null;
	private JTextArea logArea = null;
	private String keyEventText = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	
	public ClientTextFieldEventListener(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		consoleQueue = new CircularQueue();
		Map<String, Object> guiInstanceMap = ClientLoggingWindow.guiInstanceMap;
		consoleTextField = (JTextField) guiInstanceMap.get("consoleTextField");
		logArea = (JTextArea) guiInstanceMap.get("logArea");
		startBtn = (JButton) guiInstanceMap.get("startBtn");
		stopBtn = (JButton) guiInstanceMap.get("stopBtn");
	}
	
	/**
	 * 10 : Enter
	 * 38 : 위방향키
	 * 40 : 아래방향키
	 * 8 : BackSpace
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyEventText = consoleTextField.getText();
		if (keyCode == 10) {
			keyEventText = keyEventText.split("#")[1].trim();
			consoleEventAction(keyEventText);
			consoleQueue.enqueue(keyEventText);
		} else if (keyCode == 38) {
			keyEventText = consoleQueue.getCountData(keyCode);
			consoleTextField.setText("# "+keyEventText);
		} else if (keyCode == 40) {
			keyEventText = consoleQueue.getCountData(keyCode);
			consoleTextField.setText("# "+keyEventText);
		} else if (keyCode == 8) {
			if (keyEventText.length() <= 2) {
				consoleTextField.setText("#  ");
			}
		}
	}
	
	/**
	 * 콘솔 이벤트 관리
	 * @param eventText
	 */
	public void consoleEventAction(String eventText) {
		consoleTextField.setText("# ");
		try {
			if (KeyCodeRuleConstant.CLEAR.equalsIgnoreCase(eventText)) {
				logArea.setText("");
			} else if (KeyCodeRuleConstant.SHUTDOWN.equalsIgnoreCase(eventText)) {
				System.exit(0);
			} else if (KeyCodeRuleConstant.LOG_START.equalsIgnoreCase(eventText)) {
				LogViewerTcpClientHandler.viewerStateChange("Y");
				startBtn.setEnabled(false);
				stopBtn.setEnabled(true);
			} else if (KeyCodeRuleConstant.LOG_STOP.equalsIgnoreCase(eventText)) {
				LogViewerTcpClientHandler.viewerStateChange("N");
				stopBtn.setEnabled(false);
				startBtn.setEnabled(true);
			} else if (KeyCodeRuleConstant.IPCONFIG.equalsIgnoreCase(eventText)) {
				InetAddress inetAddress = InetAddress.getLocalHost();
				StringBuffer sb = new StringBuffer();
				sb.append("........................................................................").append("\n");
				sb.append("\n");
				sb.append("이더넷 어댑터 로컬 영역 연결 : ").append("\n");
				sb.append("\n");
				sb.append("  USER-PC . . . . . . . . . . : ").append(inetAddress.getHostName()).append("\n");
				sb.append("  운영체제  . . . . . . . . . . : ").append(System.getProperty("os.name")).append("\n");
				sb.append("  IPv4 주소 . . . . . . . . . . : ").append(inetAddress.getHostAddress()).append("\n");
				sb.append("\n");
				sb.append("........................................................................").append("\n");
				
				logArea.append(sb.toString());
			} else if (StringUtils.toUpperCaseConstains(eventText, KeyCodeRuleConstant.LOG_LINE_SIZE)) {
				if (KeyCodeRuleConstant.LOG_LINE_SIZE.equalsIgnoreCase(eventText)) {
					LogViewerTcpClientHandler.sendMessage(NetworkProtocolConstant.CLIENT_LOG_SIZE);
				} else {
					try {
						if (eventText.contains(KeyCodeRuleConstant.LINE_SIZE_CHANGE)) {
							String logLineSize = eventText.split(" ")[2];
							LogViewerTcpClientHandler.sendMessage(NetworkProtocolConstant.CLIENT_LOG_SIZE_CHANGE, logLineSize);
						} else {
							LogViewerTcpClientHandler.sendMessage(NetworkProtocolConstant.CLIENT_LOG_SIZE_DEFUALT, "170");
						}
					} catch(Exception e) {
						logArea.append(">>존재하지 않는 명령어입니다. \n");
					}
				}
			} else if (StringUtils.toUpperCaseConstains(eventText, KeyCodeRuleConstant.LOG_DATE)) {
				String logDate = eventText.split(" ")[1];
				LogViewerTcpClientHandler.sendMessage(NetworkProtocolConstant.CLIENT_LOG_DATE, logDate);
				
				logArea.setText("");
				logArea.append(">>로그데이터 갱신중.... \n");
			} else if (KeyCodeRuleConstant.LOG_OPTION.equalsIgnoreCase(eventText)){
				StringBuffer sb = new StringBuffer();
				sb.append("......................................LOG OPTION....................................").append("\n");
				sb.append("\n");
				sb.append("  CLEAR . . . . . . . . . . . : ").append("로그 전부 삭제.").append("\n");
				sb.append("  SHUTDOWN . . . . . . . . .: ").append("로그뷰어 종료.").append("\n");
				sb.append("  IPCONFIG  . . . . . . . . . : ").append("OS정보 및 사용자 네트워크 정보 표출").append("\n");
				sb.append("  LOGLINE . . . . . . : ").append("현재 로그라인길이 사이즈 표출").append("\n");
				sb.append("  LOGLINE -OPTION   . . . : ").append("LOG_LINE_SIZE OPTION").append("\n");
				sb.append("  -CHANGE 값(숫자) . . . . . . : ").append("로그라인길이 변경").append("\n");
				sb.append("  -DEFAULT . . . . . . . . . . . .: ").append("로그라인길이 기본사이즈로 변경").append("\n");
				sb.append("  EX) : ").append("linesize -change").append("\n");
				sb.append("\n");
				sb.append("..........................................................................................").append("\n");
				
				logArea.append(sb.toString());
			} else {
				logArea.append(">>존재하지 않는 명령어입니다. \n");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LoggerManager.error(getClass(),"CONSOLE EVNET ERROR MESSAGE : {}", "ArrayIndexOutOfBoundsException : 인덱스 갯수 오류");
			logArea.append(">>올바르지 않은 명령어입니다. \n");
		} catch (NullPointerException e) {
			LoggerManager.error(getClass(),"CONSOLE EVNET ERROR MESSAGE : {}", "NullPointerException : 데이터 NULL");
			logArea.append(">>올바르지 않은 명령어입니다. \n");
		} catch (UnknownHostException e) {
			logArea.append(">>사용이 불가능한 명령어입니다. \n");
			LoggerManager.error(getClass(),"CONSOLE EVNET ERROR MESSAGE : {}", "UnknownHostException : 알 수 없는 호스트");
		}
	}
}
