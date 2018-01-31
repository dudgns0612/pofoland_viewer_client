package pofoland.log.viewer.convertion;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import org.json.simple.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import pofoland.log.viewer.utils.ByteUtils;
import pofoland.log.viewer.utils.LoggerManager;

public class LogViewerClientDecoder extends ByteToMessageDecoder {
	
	/**
	 * <pre>
	 * packetAllSize = 총 패킷 사이즈
	 * tempDataSize = 패킷 오버 사이즈 누적 사이즈
	 * packetDataSize = 들어온 패킷 사이즈
	 * packetData = 들어온 패킷 총 데이터 STX~ETX
	 * packetMessageData = 들어온 패킷의 메시지
	 * </pre>
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int packetAllSize = in.readableBytes();
		
		//최소사이즈 판별
		if (packetAllSize < 4) {
			return;
		}
		
		try {
			byte[] read = new byte[packetAllSize];
			
			for (int i = 0 ; i < packetAllSize ; i++) {
				read[i] = in.readByte();
			}
			String type = ByteUtils.singleByteToHexString(read[1]);
			System.out.println("encoding Type : "+type);
			
			//중간에 끊길 시 기존 인덱스 저장
			if (!ByteUtils.byteToHexString(read).contains("0x02") || !ByteUtils.byteToHexString(read).contains("0x03")) {
				in.resetReaderIndex();
				return;
			} 
			
			
			if (type.equals("0x00")) {
				String reciveMsg = "";
				//사이즈 오버 시 남은 인덱스 사이즈 저장
				int tempDataSize = 0;
				
				while (true) {
					byte[] packetDataSize = new byte[4];
					System.arraycopy(read, tempDataSize+2, packetDataSize, 0, 4);
					int dataSize = ByteUtils.byteToIntBigEndian(packetDataSize);
		
					
					byte[] packetData = new byte[dataSize];
					System.arraycopy(read, tempDataSize, packetData, 0, dataSize);
					
					byte[] packetMessageData = new byte[packetData.length-6];
					System.arraycopy(packetData,6, packetMessageData, 0, packetData.length-6);
					System.arraycopy(packetMessageData, 0, packetMessageData, 0, packetMessageData.length-1);
					
					reciveMsg = new String(packetMessageData,"UTF-8");
					
					//인덱스 누적
					tempDataSize += dataSize;
					if (tempDataSize == packetAllSize) {
						break;
					} 
				}
				String msg = reciveMsg.split("[&]")[1];
				
				if (msg.trim().length() > 0) {
					out.add(reciveMsg.trim());
				} else {
					out.add(reciveMsg);
				}
			} else {
				byte[] packetDataSize = new byte[4];
				System.arraycopy(read, 2, packetDataSize, 0, 4);
				int dataSize = ByteUtils.byteToIntBigEndian(packetDataSize);
				
				
				byte[] packetMessageData = new byte[dataSize];
				System.arraycopy(read,6, packetMessageData, 0, read.length-6);
				System.arraycopy(packetMessageData, 0, packetMessageData, 0, packetMessageData.length-1);
				
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packetMessageData);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				
				JSONObject returnObject = (JSONObject) objectInputStream.readObject();
				out.add(returnObject);
			}
		} catch (Exception e) {
			LoggerManager.debug(getClass(), "LogViewerClientDecoder : " + e.getMessage());
		} finally {
		}
	}

}
