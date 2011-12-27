package com.talkweb.ei.util.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class FileLog {

	static Logger log = Logger.getLogger(FileLog.class.getName());
	
	/**
	 * @param args
	 */
	private static String getDate() {
		String nowTime = null;
		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		nowTime = simpleDateFormat.format(nowDate);

		return nowTime;
	}

	// 取得时间
	private static String getTime() {
		String nowTime = null;
		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		nowTime = simpleDateFormat.format(nowDate);

		return nowTime;

	}

	// 写日志
	public static void writeLog(String logType, String modelStr, String logStr) {
		File establishFile = null;
		FileWriter fw = null;

		String filePath = LogConstant.FILE_ADDR;
		String filePathfull = filePath + "/" + FileLog.getDate()
				+ logType + ".txt";

		try {
			establishFile = new File(filePath);
			establishFile.mkdirs();
			
			//System.out.println("path:" + establishFile.getAbsolutePath());

			fw = new FileWriter(filePathfull, true);
			fw.write(FileLog.getTime() + "    " + modelStr + "    "
					+ logStr + "\n");

			fw.flush();
		} catch (IOException e) {
			log.error("FileLog error:" + e.getMessage());
		} finally {
			if (establishFile != null)
				establishFile = null;
			if (fw != null) {
				try {
					fw.close();
				} catch (Exception e) {
					log.error("FileLog error:" + e.getMessage());
				} finally {
					fw = null;
				}
			}
		}

	}

	/*public static void main(String[] args) {
		String userdir = FileLog.class.getResource("/").getPath();
		System.out.println("path3:" + userdir);
		
		int index = userdir.indexOf("class");
		if (index >= 0) {
			userdir = userdir.substring(0, index-1);
		}
		System.out.println("path4:" + userdir);
		//FileLog.writeLog(LogConstant.LogFile_Info, LogConstant.LOGFILE_LOADCONFIGFILE, "mytest log");
	}*/
}
