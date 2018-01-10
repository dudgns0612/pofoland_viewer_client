package pofoland.log.viewer.utils;

/**
 * 2018.01 .03
 * 바이트계산을 위한 Utils
 * @author Hoons
 */
public class ByteUtils {
	
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
