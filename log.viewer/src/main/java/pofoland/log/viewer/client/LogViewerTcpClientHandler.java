package pofoland.log.viewer.client;

import java.util.StringTokenizer;

import org.json.simple.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pofoland.log.viewer.constant.NetworkProtocolConstant;
import pofoland.log.viewer.utils.JsonUtils;
import pofoland.log.viewer.view.ClientLoggingWindow;

public class LogViewerTcpClientHandler extends SimpleChannelInboundHandler<String>{
	
	ClientLoggingWindow clientLoggingWindow = null;
	public static ChannelHandlerContext ctx;
	
	@SuppressWarnings("static-access")
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		clientLoggingWindow = new ClientLoggingWindow();
		System.out.println("서버접속");
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		StringTokenizer stringTokenizer = new StringTokenizer(msg, "$");
		String protocol = stringTokenizer.nextToken();
		String value = stringTokenizer.nextToken();
		try {
			if (NetworkProtocolConstant.SERVER_SEND_LOG_MESSAGE.equals(protocol)) {
				clientLoggingWindow.writeLogger(value);
			} else if (NetworkProtocolConstant.CLINET_SEND_START.equals(protocol)) {
				clientLoggingWindow.writeLogger(value);
			} else if (NetworkProtocolConstant.CLINET_SEND_STOP.equals(protocol)) {
				clientLoggingWindow.writeLogger(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(cause.getCause()+"::"+cause.getMessage());
		cause.printStackTrace();
	}
	
	public static void viewerStateChange(String state) {
		if (state.equals("Y")) {
			JSONObject sendJsonObject = JsonUtils.setJsonValue(NetworkProtocolConstant.CLINET_SEND_START);
			ctx.writeAndFlush(sendJsonObject);
		} else {
			JSONObject sendJsonObject = JsonUtils.setJsonValue(NetworkProtocolConstant.CLINET_SEND_STOP);
			ctx.writeAndFlush(sendJsonObject);
		}
	}
}
