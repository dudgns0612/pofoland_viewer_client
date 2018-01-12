package pofoland.log.viewer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.netty.channel.ChannelHandlerContext;
import pofoland.log.viewer.client.LogViewerTcpClientHandler;

public class ClientLoggingWindow {
	private ChannelHandlerContext ctx  = null;
	private JTextArea logArea = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	public static Map<String, Object> guiInstanceMap;
	
	public ClientLoggingWindow() {
		this.ctx = LogViewerTcpClientHandler.ctx;
		guiInstanceMap = new HashMap<String, Object>();
		showWindow();
	}
	
	public void showWindow() {
		JFrame windowFrame = new JFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		JPanel headerPanel = new JPanel(new GridLayout(1, 1));
		startBtn= new JButton("시작");
		stopBtn = new JButton("중지");
		startBtn.setEnabled(false);
		
		headerPanel.add(startBtn);
		headerPanel.add(stopBtn);
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		logArea = new JTextArea();
		Font areaFont = new Font("돋움", Font.PLAIN, 15);
		logArea.setBackground(Color.BLACK);
		logArea.setForeground(Color.WHITE);
		logArea.setFont(areaFont);
		logArea.setBorder(BorderFactory.createEmptyBorder());
		logArea.setEditable(false);
		logArea.append("================================================================LOG VIEWER(VER_0.1)========================================================= \n");
		logArea.append("================================================================="+dateFormat.format(date)+"========================================================== \n");
		JScrollPane contentPane = new JScrollPane(logArea);
		contentPane.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField consoleTextField = new JTextField();
		Font textFont = new Font("돋움", Font.BOLD, 16);
		consoleTextField.setBackground(Color.BLACK);
		consoleTextField.setForeground(Color.WHITE);
		consoleTextField.setFont(textFont);
		Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
		consoleTextField.setCursor(cursor);
		consoleTextField.setDragEnabled(false);
		consoleTextField.setBorder(BorderFactory.createEmptyBorder());
		
		mainPanel.add(headerPanel,"North");
		mainPanel.add(contentPane, "Center");
		mainPanel.add(consoleTextField, "South");
		
		windowFrame.add(mainPanel);
		windowFrame.setTitle("Pofoland LogView(Ver 0.1)");
		windowFrame.setSize(1400,800);
		windowFrame.setVisible(true);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//GUI인스턴스 추가
		guiInstanceMap.put("logArea", logArea);
		guiInstanceMap.put("startBtn", startBtn);
		guiInstanceMap.put("stopBtn", stopBtn);
		guiInstanceMap.put("consoleTextField", consoleTextField);
		guiInstanceMap.put("windowFrame", windowFrame);
		
		//이벤트 리스너 등록
		windowFrame.addWindowListener(new ClientWindowEventListener(ctx));
		startBtn.addActionListener(new ClientWindwActionListener());
		stopBtn.addActionListener(new ClientWindwActionListener());
		consoleTextField.addKeyListener((new ClientTextFieldEventListener(ctx)));
	}
	
	public void writeLogger(String msg) {
		logArea.append(msg+"\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public static Map<String,Object> getGuiInstance() {
		return guiInstanceMap;
	}
}
