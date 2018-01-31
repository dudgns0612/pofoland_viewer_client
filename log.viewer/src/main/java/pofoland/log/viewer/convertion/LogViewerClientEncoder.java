package pofoland.log.viewer.convertion;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pofoland.log.viewer.utils.ByteUtils;

public class LogViewerClientEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String returnType = msg.getClass().getName();
		
		if (returnType.contains("String")) {
			// String - >> byte[] 변환
			out = Unpooled.directBuffer();
			byte[] sendByteEncoding = ByteUtils.makeSendPacket(String.valueOf(msg).getBytes("UTF-8"), (byte)0x00);
			out.writeBytes(sendByteEncoding);
			
			ctx.writeAndFlush(out);
		}
	}
}
