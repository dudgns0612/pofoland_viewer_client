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
import pofoland.log.viewer.queue.CircularQueue;

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
	
	public void consoleEventAction(String eventText) {
		consoleTextField.setText("# ");
		if (KeyCodeRuleConstant.CLEAR_TXET.equals(eventText)) {
			logArea.setText("");
		} else if (KeyCodeRuleConstant.SHUTDOWN_TEXT.equals(eventText)) {
			System.exit(0);
		} else if (KeyCodeRuleConstant.LOG_START.equals(eventText)) {
			LogViewerTcpClientHandler.viewerStateChange("Y");
			startBtn.setEnabled(false);
			stopBtn.setEnabled(true);
		} else if (KeyCodeRuleConstant.LOG_STOP.equals(eventText)) {
			LogViewerTcpClientHandler.viewerStateChange("N");
			stopBtn.setEnabled(false);
			startBtn.setEnabled(true);
		} else if (KeyCodeRuleConstant.IPCONFIG.equals(eventText)) {
			try {
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
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} else {
			logArea.append(">>존재하지 않는 명령어입니다. \n");
		}
	}
}
