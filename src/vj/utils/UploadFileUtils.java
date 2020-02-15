package vj.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

/**
 * 文件上传操作工具类
 * @author vjames
 *
 */
public class UploadFileUtils {
	//将上传的临时文件保存到指定目录并返回路径
	public static String copy(File resource) {
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("/yyyy/MM/dd/");
		String dataStr = sdf.format(date);
		
		File dataDir = new File("D:\\0-java\\avtivti_0204\\upladFiles" + dataStr);
		if(!dataDir.exists()){
			dataDir.mkdirs();
		}
		
		//构建目标文件
		File tager = new File(dataDir.getPath() +"\\"+ UUID.randomUUID().toString());
		
		try {
			FileUtils.copyFile(resource, tager);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tager.getPath();
	}
	



}
