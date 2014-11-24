package com.taobao.nior;

import android.content.Context;
import android.media.AudioManager;
import android.widget.ImageView;
import com.taobao.athrun.ri.R;
import com.taobao.athrun.ri.MainActivity;

public class MonitorAudioChanger extends Thread {
	
	private AudioManager am;

	private Context context;
	
	private final ImageView floatView;
	
	private int[] initialAudioVolumes;
	
	private boolean bStop = false;
	
	public MonitorAudioChanger(Context context, ImageView floatView, AudioManager am) {
		this.context = context;
		this.floatView = floatView;
		this.am = am;
	}

	public void stopMonitor() {
		bStop = true;
	}
	
	public void run() {
		initialAudioVolumes = readAudioVolumes();
		
		int[] currentAudioVolumes = null;
		while(!bStop) {
			currentAudioVolumes = readAudioVolumes();
			
			if (isAudioVolumesChange(initialAudioVolumes, currentAudioVolumes)) {
				break;
			}
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		NiorReplayer.stop();
		
		floatView.post(new Runnable() {  
	        public void run() {
	        	// 为了尽可能让程序状态与图标变化保持同步，就直接放到了UI线程中去执行
	        	MainActivity.setState(0);
	    		MainActivity.setReplayTimes(1);
	    		MainActivity.setMonitorThread(null);
	    		
	    		floatView.setImageDrawable(context.getResources().getDrawable(R.drawable.touch));
	        }
		});
		
	}
	
	private boolean isAudioVolumesChange(int[] initialAudioVolumes, int[] currentAudioVolumes) {
		for(int i=0; i<5; i++) {
			if (initialAudioVolumes[i] != currentAudioVolumes[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	private int[] readAudioVolumes() {
		int[] currentAudioVolumes = new int[5];
		
		//通话音量

	      //int max = am.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );

	      int current_1 = am.getStreamVolume( AudioManager.STREAM_VOICE_CALL );

	      currentAudioVolumes[0] = current_1;
	      

	//系统音量

	      //max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM ); 

	      int current_2 = am.getStreamVolume( AudioManager.STREAM_SYSTEM ); 

	      currentAudioVolumes[1] = current_2;

	//铃声音量

	      //max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_RING );

	      int current_3 = am.getStreamVolume( AudioManager.STREAM_RING );

	      currentAudioVolumes[2] = current_3;

	//音乐音量

	      //max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );

	      int current_4 = am.getStreamVolume( AudioManager.STREAM_MUSIC );

	      currentAudioVolumes[3] = current_4;

	//提示声音音量

	      //max = am.getStreamMaxVolume( AudioManager.STREAM_ALARM );

	      int current_5 = am.getStreamVolume( AudioManager.STREAM_ALARM );

	      currentAudioVolumes[4] = current_5;
	      
	      return currentAudioVolumes;
	}
}
