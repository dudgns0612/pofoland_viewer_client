package pofoland.log.viewer.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JTextField;

import io.netty.channel.ChannelHandlerContext;

public class ClientWindowEventListener extends WindowAdapter{
	
	private ChannelHandlerContext ctx = null;
	private JTextField consoleTextField = null;
	
	public ClientWindowEventListener(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		Map<String,Object> guiInstanceMap = ClientLoggingWindow.guiInstanceMap;
		consoleTextField = (JTextField) guiInstanceMap.get("consoleTextField");
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		consoleTextField.requestFocus();
		consoleTextField.setText("# ");
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		ctx.close();
		System.out.println("종료");
	}
}
