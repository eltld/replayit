package com.taobao.nior;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class NiorReplayer {
	
private static Context context;

	private static RunReplayThread replayThread = null;

	public static void setContext(Context context) {
		NiorReplayer.context = context;
	}
	
	public static void replay(String fileName) {
		stop();
		replay(fileName, 1);
	}
	
	public static void replay(String fileName, int times) {
		stop();
		if (!isReady()) {
			init();
		}
		
		doReplay(fileName, times);
	}
	
	public static void stop() {
		/*
		if (isReplaying()) {
			throw new RRException("recorder is already stopped");
		}
		*/
		if (replayThread != null) {
			replayThread.stopReplay();
		}
		
		doStop();
		
		replayThread = null;
		
	}
	
	private static String parseNiorReplayPid() {
		return NiorUtils.parsePid("replayer");
	}
	
	private static void doStop() {
		String niorReplayPid = parseNiorReplayPid();		
		NiorUtils.killProcess(niorReplayPid);
	}

	private static boolean checkProcessExists() {
		return false;
	}
	
	public boolean isReplaying() {
		return checkProcessExists();
	}

	private static void doReplay(String fileName,int times) {
		replayThread = new RunReplayThread(fileName, times);
		replayThread.start();
	}

	private static boolean isReady() {
		File replayExe = new File(NiorConstants.REPLAYER_PATH);
		return replayExe.exists();
	}

	private static void init() {
		
		//工程根目录下的assets文件夹中存放，比如assets/test.xml 这样我们使用下面的代码  
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		try {
			is = am.open("event_replay");
			
			FileOutputStream outStream = context.openFileOutput("replayer", Context.MODE_PRIVATE);
			NiorUtils.copyFileToLocal(is, outStream);
			outStream.close();
			
			//chmod 755 /data/local
			NiorUtils.executeShellCommand("su -c ' chmod 755 /data/local | mkdir /data/local/nior | mkdir /data/local/nior/rr '",  NullOutputReceiver.getReceiver());
			
			// push the replay file to /data/local/nior/rr
			NiorUtils.executeShellCommand("su -c ' cat " + NiorConstants.LOCAL_REPLAYER_PATH + " > " + NiorConstants.REPLAYER_PATH + " '",  NullOutputReceiver.getReceiver());
			
			
			//NiorUtils.copyFileToLocal(is, NiorConstants.REPLAYER_PATH);
			
			//chmod for permission reason
			NiorUtils.executeShellCommand("su -c ' chmod 555 "+NiorConstants.REPLAYER_PATH+" '",  NullOutputReceiver.getReceiver());
			
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

}

class RunReplayThread extends Thread {
	String fileName;
	int times;
	boolean bStop = false;
	
	public RunReplayThread(String fileName, int times) {
		this.fileName = fileName;
		this.times = times;
	}

	public void stopReplay() {
		bStop = true;
	}

	public void run() {
		String replayCmd = NiorConstants.REPLAYER_PATH+" "+NiorConstants.REMOTE_BASE_DIR+fileName;
		for (int i = 0; i < times; i++) {
			if (bStop) {
				break;
			}
			
			NiorUtils.executeShellCommand(replayCmd, NullOutputReceiver.getReceiver());
		}
	}
}