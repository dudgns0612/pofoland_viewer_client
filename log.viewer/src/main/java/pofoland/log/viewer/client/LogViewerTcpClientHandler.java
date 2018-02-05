package pofoland.log.viewer.client;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pofoland.log.viewer.constant.NetworkProtocolConstant;
import pofoland.log.viewer.utils.LoggerManager;
import pofoland.log.viewer.utils.StringUtils;
import pofoland.log.viewer.view.ClientLoggingWindow;

public class LogViewerTcpClientHandler extends SimpleChannelInboundHandler<Object>{
	
	ClientLoggingWindow clientLoggingWindow = null;
	public static ChannelHandlerContext ctx;
	
	@SuppressWarnings("static-access")
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		clientLoggingWindow = new ClientLoggingWindow();
		LoggerManager.info(getClass(), "Server in success");
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		String objType = msg.getClass().getName();
		try {
			if (objType.contains("String")) {
				StringTokenizer stringTokenizer = new StringTokenizer(String.valueOf(msg), "&");
				String protocol = stringTokenizer.nextToken();
				String value = stringTokenizer.nextToken();
				
				if (NetworkProtocolConstant.SERVER_SEND_LOG_MESSAGE.equals(protocol)) {
					clientLoggingWindow.writeLogger(value);
				} else if (NetworkProtocolConstant.CLINET_SEND_START.equals(protocol)) {
					clientLoggingWindow.writeLogger(value);
				} else if (NetworkProtocolConstant.CLINET_SEND_STOP.equals(protocol)) {
					clientLoggingWindow.writeLogger(value);
				} else if (NetworkProtocolConstant.CLIENT_LOG_SIZE.equals(protocol)) {
					clientLoggingWindow.writeLogger(">>LOG LINESIZE - " + value);
				} else if (NetworkProtocolConstant.CLIENT_LOG_DATE.equals(protocol)) {
					clientLoggingWindow.writeLogger(value);
				}
			} else {
				JSONObject resObject = (JSONObject)msg;
				String protocol = (String) resObject.get("PROTOCOL");
				if (NetworkProtocolConstant.CLIENT_LOG_DIR.equals(protocol)) {
					List<Map<String,String>> resList = (List<Map<String, String>>) resObject.get("VALUE");
					StringBuffer sb = new StringBuffer();
					sb.append("=============================== LOG DIRECTORY ==============================").append("\n");
					for (int i=0 ; i < resList.size() ; i++) {
						Map<String, String> resMap = resList.get(i);
						sb.append(resMap.get("modfiyDate")).append("\t").append(resMap.get("type")).append("\t").append(resMap.get("name")).append("\n");
					}
					sb.append("============================================================================").append("\n");
					clientLoggingWindow.writeLogger(sb.toString());
				}
			}
		} catch (Exception e) {
			//TODO 오류정리
			e.printStackTrace();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LoggerManager.info(getClass(), (cause.getCause()+"::"+cause.getMessage()));
		cause.printStackTrace();
	}
	
	public static void sendMessage(String protocol) {
		ctx.writeAndFlush(StringUtils.makeSendStr(protocol));
	}
	
	public static void sendMessage(String protocol, String msg) {
		ctx.writeAndFlush(StringUtils.makeSendStr(protocol, msg));
	}
	
	public static void viewerStateChange(String state) {
		if (state.equals("Y")) {
			ctx.writeAndFlush(StringUtils.makeSendStr(NetworkProtocolConstant.CLINET_SEND_START));
		} else {
			ctx.writeAndFlush(StringUtils.makeSendStr(NetworkProtocolConstant.CLINET_SEND_STOP));
		}
	}
}
