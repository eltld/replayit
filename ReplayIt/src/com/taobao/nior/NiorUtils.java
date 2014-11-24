package com.taobao.nior;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class NiorUtils {
	
	public static boolean executeShellCommand(String command, IShellOutputReceiver receiver) {
		String TAG = "";
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			
			StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), receiver);  
            // kick off stdout  
            outGobbler.start();   
			
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.d(TAG,
					"the device is not rooted， error message： "
							+ e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (process != null) {
					process.destroy();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private static String getCmdPrint(String cmd) {
		OutputStream os = new ByteArrayOutputStream();

		try {
			executeShellCommand(cmd,
					new OutputStreamShellOutputReceiver(os));
			os.flush();
			return os.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	private static String getPsPrint() {
		return getCmdPrint("ps");
	}
	
	public static String parsePid(String exeName) {

		String content = getPsPrint();
		String[] pidInfos = content.split("\n");
		for (String info : pidInfos) {
			if (info != null) {
				// shell 18867 18856 852 300 c022c0bc afe0cdec S ./gsnap
				if (info.contains(exeName)) {
					String[] fragments = info.split(" ");
					int idx = 0;
					for (String str : fragments) {
						if ((str != null) && (!str.trim().equals(""))) {
							idx++;
						}

						if (idx == 2) {
							return str;
						}
					}
				}
			}
		}

		return "";
	}
	
	public static void killProcess(String pid) {
		// 鏃х殑鎵嬫満锛岀己涔廹rep鍜宎wk绋嬪簭锛�
		//String killCmd = "kill -9 `ps | grep \"gsnap\" | grep -v \"grep\" | awk '{print $2}'`";
		String killCmd = "kill -9 " + pid;
				
		try {
			executeShellCommand(killCmd,
					new OutputStreamShellOutputReceiver(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拷贝文件到android本机
	 * @param is
	 * @param localFilePath
	 */
	public static void copyFileToLocal(InputStream is, String localFilePath) {
		FileOutputStream fs = null;

		try {
			int readLen = 0;
			fs = new FileOutputStream(localFilePath);

			byte[] buffer = new byte[1024];

			while ((readLen = is.read(buffer)) != -1) {
				fs.write(buffer, 0, readLen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void copyFileToLocal(InputStream is, FileOutputStream outStream) {
		
		try {
			int readLen = 0;
			

			byte[] buffer = new byte[1024];

			while ((readLen = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, readLen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
