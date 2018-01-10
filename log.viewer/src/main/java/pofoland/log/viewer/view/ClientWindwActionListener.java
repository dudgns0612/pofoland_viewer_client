package pofoland.log.viewer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

import pofoland.log.viewer.client.LogViewerTcpClientHandler;

public class ClientWindwActionListener implements ActionListener{
	
	private JButton startBtn = null;
	private JButton stopBtn = null;
	
	public ClientWindwActionListener() {
		Map<String,Object> guiInstanceMap = ClientLoggingWindow.getGuiInstance();
		startBtn = (JButton) guiInstanceMap.get("startBtn");
		stopBtn = (JButton) guiInstanceMap.get("stopBtn");
	}

	public void actionPerformed(ActionEvent e) {
		JButton jButton = (JButton)e.getSource();
		if (jButton.getText().equals("시작")) {
			LogViewerTcpClientHandler.viewerStateChange("Y");
			startBtn.setEnabled(false);
			stopBtn.setEnabled(true);
		} else if (jButton.getText().equals("중지")) {
			LogViewerTcpClientHandler.viewerStateChange("N");
			stopBtn.setEnabled(false);
			startBtn.setEnabled(true);
		}
	}

}
