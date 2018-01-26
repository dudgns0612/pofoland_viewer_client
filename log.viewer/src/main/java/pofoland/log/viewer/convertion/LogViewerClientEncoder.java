package pofoland.log.viewer.convertion;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pofoland.log.viewer.utils.ByteUtils;

public class LogViewerClientEncoder extends MessageToByteEncoder<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String sendStr, ByteBuf out) throws Exception {
		// String - >> byte[] 변환
		out = Unpooled.directBuffer();
		byte[] sendByteEncoding = ByteUtils.makeSendPacket(sendStr.getBytes("UTF-8"));
		out.writeBytes(sendByteEncoding);
		
		ctx.writeAndFlush(out);
		
	}
}
