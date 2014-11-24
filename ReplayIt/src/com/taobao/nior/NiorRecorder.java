package com.taobao.nior;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;

public class NiorRecorder {
	
	private static Context context;
	
	public static void setContext(Context context) {
		NiorRecorder.context = context;
	}

	public static void record() {
		if (!isReady()) {
			init();
		}
		
		doRecord();
	}
	
	private static void doRecord() {
		stop();
		new RunRecordThread().start();
	}
	
	private static boolean isReady() {
		//check if the recorder file is already there
		File recordExe = new File(NiorConstants.RECORDER_PATH);
		return recordExe.exists();
	}
	
	private static void init() {
		
		//工程根目录下的assets文件夹中存放，比如assets/test.xml 这样我们使用下面的代码  
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		try {
			is = am.open("event_record");
			
			FileOutputStream outStream = context.openFileOutput("recorder", Context.MODE_PRIVATE);
			NiorUtils.copyFileToLocal(is, outStream);
			outStream.close();
			
			//chmod 755 /data/local
			NiorUtils.executeShellCommand("su -c ' chmod 755 /data/local | mkdir /data/local/nior | mkdir /data/local/nior/rr '",  NullOutputReceiver.getReceiver());
			
			//push the record file to /data/local/nior/rr
			NiorUtils.executeShellCommand("su -c ' cat " + NiorConstants.LOCAL_RECORDER_PATH + " > " + NiorConstants.RECORDER_PATH + " '",  NullOutputReceiver.getReceiver());
			
			/*
			// 复制前创建目录结构，注意：因为权限问题，实际执行会没有效果！
			File niorDir = new File(NiorConstants.NIOR_EXE_DIR);
			if (!niorDir.exists()) {
				niorDir.mkdirs();
			}
			*/

			//NiorUtils.copyFileToLocal(is, NiorConstants.RECORDER_PATH);

			//chmod for permission reason
			NiorUtils.executeShellCommand("su -c ' chmod 555 "+NiorConstants.RECORDER_PATH+" '",  NullOutputReceiver.getReceiver());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void stop() {
		doStop();
	}
	
	private static String parseNiorRecordPid() {
		return NiorUtils.parsePid("recorder");
	}
	
	private static boolean doStop() {
		String niorRecordPid = parseNiorRecordPid();		
		NiorUtils.killProcess(niorRecordPid);
		return true;
	}
	
	private static boolean checkProcessExists() {
		return !NiorUtils.parsePid("recorder").equals("");
	}
	
	public static boolean isRecording() {
		return checkProcessExists();
	}
}

class RunRecordThread extends Thread {
	public void run() {
		String recordCmd = NiorConstants.RECORDER_PATH+" "+NiorConstants.REMOTE_BASE_DIR+"nior.rcd";
		NiorUtils.executeShellCommand(recordCmd, NullOutputReceiver.getReceiver());
	}
}
