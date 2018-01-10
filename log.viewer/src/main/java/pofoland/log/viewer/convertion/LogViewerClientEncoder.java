package pofoland.log.viewer.convertion;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LogViewerClientEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(msg);
        oos.flush();
        
        byte[] bs = bos.toByteArray();
        
        out = Unpooled.directBuffer();
        out.writeBytes(bs);
        ctx.writeAndFlush(out);
        bos.close();
        oos.close();
	}
	
}
