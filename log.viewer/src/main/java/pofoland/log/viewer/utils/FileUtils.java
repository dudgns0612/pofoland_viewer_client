package pofoland.log.viewer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
	
	public FileUtils() {
		
	}
	
	public static void FileCopy(File orgFile, String filePath) {
		File storeLogFile = null;
		
		if (filePath.contains(".log")) {
			storeLogFile = new File(filePath);
		} else {
			storeLogFile = new File(filePath+".log");
		}
		try {
			storeLogFile.createNewFile();
			
			FileInputStream inputStream = new FileInputStream(orgFile);        
			FileOutputStream outputStream = new FileOutputStream(storeLogFile);
			
			FileChannel fcin =  inputStream.getChannel();
			FileChannel fcout = outputStream.getChannel();
			
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
			
			fcout.close();
			fcin.close();
			
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			LoggerManager.error(FileUtils.class, "FILE IO ERROR : IO EXCEPTION 파일 입출력 ERROR");
		}
	}
	
}
