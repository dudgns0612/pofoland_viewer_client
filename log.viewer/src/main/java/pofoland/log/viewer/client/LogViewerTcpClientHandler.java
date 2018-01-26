package pofoland.log.viewer.client;

import java.util.StringTokenizer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pofoland.log.viewer.constant.NetworkProtocolConstant;
import pofoland.log.viewer.utils.LoggerManager;
import pofoland.log.viewer.utils.StringUtils;
import pofoland.log.viewer.view.ClientLoggingWindow;

public class LogViewerTcpClientHandler extends SimpleChannelInboundHandler<String>{
	
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
			} else if (NetworkProtocolConstant.CLIENT_LOG_SIZE.equals(protocol)) {
				clientLoggingWindow.writeLogger(">>LOG LINESIZE - " + value);
			}
		} catch (Exception e) {
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
