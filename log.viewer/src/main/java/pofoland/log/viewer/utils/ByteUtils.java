package pofoland.log.viewer.utils;

/**
 * 2018.01 .03
 * 바이트계산을 위한 Utils
 * @author Hoons
 */
public class ByteUtils {
	
	/**
	 * stx - 시작 세그먼트 1byte 
	 * note - 비고 1byte => 0x00 String / 0x01 Object
	 * value size - 패킷 사이즈 4byte
	 * value - 데이터 가변  =>  protocol&data
	 * etx = 종료 세그먼트 1byte 
	 * @return
	 */
	public static byte[] makeSendPacket(byte[] sendValue, byte type) {
		int valueLength = sendValue.length;
		
		byte[] sendPacket = new byte[valueLength+7];
		
		//2 = 0x02 STX : 시작 세그먼트
		sendPacket[0] = 0x02;

		//0 = 0x00 NUL: note
		sendPacket[1] = type;
		
		// Big Endian 패킷 size 4byte
		byte[] valueLengthBytes = intToByteBigEndian(sendPacket.length);
		System.arraycopy(valueLengthBytes, 0, sendPacket, 2, valueLengthBytes.length);
		
		//value 가변
		System.arraycopy(sendValue, 0, sendPacket, 6, valueLength);
		
		//3 = 0x03 ETX : 종료 세그먼트
		sendPacket[sendPacket.length-1] = 0x03;
		
		return sendPacket;
	}
	
	// 바이트 -> 헥사코드
	public static String byteToHexString(byte buf[]) {
		String format = "0x%02X, ";
		StringBuffer packet = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			if (i == buf.length - 1) {
				format = format.replace(", ", "");
			}
			packet.append(String.format(format, buf[i]));
		}
		return packet.toString();
	}
	
	// 1바이트 -> 헥사코드
	public static String singleByteToHexString(byte buf) {
		String format = "0x%02X";
		StringBuffer packet = new StringBuffer();

		packet.append(String.format(format, buf));

		return packet.toString();
	}
	
	// 리틀인디안 바이트 -> 인트
	public static int byteToIntLittleEndian(byte[] byteArray) {
		return (byteArray[0] & 0xff) | (byteArray[1] & 0xff) << 8 | (byteArray[2] & 0xff) << 16 | (byteArray[3] & 0xff) << 24;
	}
	
	// 빅인디안 바이트 -> 인트
	public static int byteToIntBigEndian(byte[] byteArray) {
		return (byteArray[0] & 0xff) << 24 | (byteArray[1] & 0xff) << 16 | (byteArray[2] & 0xff) << 8 | (byteArray[3] & 0xff);
	}
	
	// 리틀인디안 인트 -> 바이트
	public static byte[] intToByteLittleEndian(int num) {
		byte[] byteArray = new byte[4];
		
		byteArray[3] = (byte) (num);
		byteArray[2] = (byte) (num >> 8);
		byteArray[1] = (byte) (num >> 16);
		byteArray[0] = (byte) (num >> 24);

		return byteArray;
	}
	
	// 빅인디안 인트 -> 바이트
	public static byte[] intToByteBigEndian(int num) {
		byte[] byteArray = new byte[4];
		
		byteArray[3] = (byte) (num);
		byteArray[2] = (byte) (num >> 8);
		byteArray[1] = (byte) (num >> 16);
		byteArray[0] = (byte) (num >> 24);
		
		return byteArray;
	}
}
