package com.taobao.athrun.ri;

import com.taobao.athrun.ri.R;
import com.taobao.nior.MonitorAudioChanger;
import com.taobao.nior.NiorRecorder;
import com.taobao.nior.NiorReplayer;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private WindowManager wm = null;
	private WindowManager.LayoutParams params1 = null;
	private WindowManager.LayoutParams params2 = null;
	
	private Button stopReplayBtn;
	private MyFloatView floatView = null;
	private View linearView = null;
	private EditText replayTimesText = null;
	private static int replayTimes = 1;
	
	public static void setReplayTimes(int replayTimes) {
		MainActivity.replayTimes = replayTimes;
	}
	
	private static MonitorAudioChanger monitorThread = null;
	
    public static void setMonitorThread(MonitorAudioChanger monitorThread) {
		MainActivity.monitorThread = monitorThread;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       //Ĭ�ϻ���״̬
        PowerManager.WakeLock wakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE	, "MainActivity");
		wakeLock.acquire();
		
		//���û���
		if(getScreenMode() == 1){
			setScreenMode(0);
		}
		
		// ������Ļ�������û����
		//setWindowScreen();
	 	       
        init();
        createView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 0, 0, "�˳�");
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case 0:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		}
		
		return true;
	}

	/**
     * ��ʼ���ؼ���Ӧ���б�������ڴ�
     */
	private void init(){
		stopReplayBtn = (Button) findViewById(R.id.stop_replay);
    	
    	stopReplayBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ֹͣ�ط�
				if (state == STATE_REPLAY_OVER) {
					stopReplay(getApplicationContext(), floatView);
				}
			}
			
		});
    	
    }
	
	private static void startRecord(Context context, ImageView floatView) {
		NiorRecorder.record();
		// ͼ�����Ϊֹͣ¼��ͼ��
		floatView.setImageDrawable(context.getResources().getDrawable(R.drawable.stop));
		state = STATE_RECORD_OVER;
	}
	
	private static void stopRecord(Context context, ImageView floatView) {
		NiorRecorder.stop();
		floatView.setImageDrawable(context.getResources().getDrawable(R.drawable.touch));
		state = STATE_INITIAL;
	}
	
	private static void startReplay(Context context, AudioManager am, ImageView floatView) {
		if (monitorThread == null) {
			monitorThread = new MonitorAudioChanger(context.getApplicationContext(), floatView, am);
			monitorThread.start();
		}
		
		NiorReplayer.replay("nior.rcd", replayTimes);
		
		// ͼ�����Ϊֹͣ�ط�ͼ��
		floatView.setImageDrawable(context.getResources().getDrawable(R.drawable.stop));
		state = STATE_REPLAY_OVER;
	}
	
	private static void stopReplay(Context context, ImageView floatView) {
		NiorReplayer.stop();
		
		floatView.setImageDrawable(context.getResources().getDrawable(R.drawable.touch));
		state = STATE_INITIAL;
		replayTimes = 1;
		
		if (monitorThread == null) {
			monitorThread.stopMonitor();
			monitorThread = null;
		}
	}
	
	 // ����״̬��0Ϊ��ʼ̬��1Ϊ¼��̬��2Ϊ¼�ƽ�����3Ϊ�ط�̬��4Ϊ�طŽ���
	 private static int state = 0;
	 private static final int STATE_INITIAL = 0;
	 private static final int STATE_RECORDING = 1;
	 private static final int STATE_RECORD_OVER = 2;
	 private static final int STATE_REPLAYING = 3;
	 private static final int STATE_REPLAY_OVER = 4;
	 
	 public static void setState(int state) {
		MainActivity.state = state;
	}
	 
	 private static boolean linearViewVisible = false;

	private void modifyReplayTimes(int times) {
		 if (replayTimesText != null) {
			 String replayTimesStr = replayTimesText.getText().toString();
			if (!replayTimesStr.trim().equals("")) {
				int replayTimes = 1; 
				try {
					replayTimes = Integer.parseInt(replayTimesStr);
					replayTimes = replayTimes * times;
				 } catch(Exception ex) {
					 ex.printStackTrace();
				 }
				 replayTimesText.setText("" + replayTimes);	
			} else {
				replayTimesText.setText("1");
			}
			
		 }
	 }
	 
	
	 
	/**
	 * ����������
	 */
	 private void createView(){
		 //��ȡWindowManager
	     wm=(WindowManager)getApplicationContext().getSystemService("window");
	     //android.view.WindowManagerImpl wm2 = (android.view.WindowManagerImpl)wm;
	     
	     
	     //����LayoutParams(ȫ�ֱ�������ز���
	     int widthSize = wm.getDefaultDisplay().getWidth();
	     params1 = ParamUtil.getLayoutParam(0, 0, widthSize/7, widthSize/7);
	     params2 = ParamUtil.getLayoutParam(widthSize/6, widthSize/6, widthSize*2/3, widthSize*3/3 + 20);

	     floatView=new MyFloatView(getApplicationContext(), params1);
	     floatView.setImageResource(R.drawable.touch);
	     
	     linearView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_float, null);
	     
	     //ImageView appView = (ImageView) linearView.findViewById(R.id.app_float);
	     //ImageView clearView = (ImageView) linearView.findViewById(R.id.clear);
	     //final EditText playbackText = (EditText) linearView.findViewById(R.id.playback_host);
	     Button exitBtn = (Button)linearView.findViewById(R.id.exit);
	     Button recordBtn = (Button)linearView.findViewById(R.id.record);
	     Button replayBtn = (Button)linearView.findViewById(R.id.replay);
	     replayTimesText = (EditText)linearView.findViewById(R.id.replay_times);
	     SeekBar seekBar = (SeekBar)linearView.findViewById(R.id.seekBar_times);
	     Button twoTimesBtn = (Button)linearView.findViewById(R.id.two_times);
	     Button tenTimesBtn = (Button)linearView.findViewById(R.id.ten_times);
	     
	     exitBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					wm.removeView(linearView);
					linearViewVisible = false;
					linearView.setVisibility(View.GONE);
					wm.addView(floatView, params1);
				}
			});
	     
		recordBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wm.removeView(linearView);
				linearViewVisible = false;
				linearView.setVisibility(View.GONE);
				wm.addView(floatView, params1);

				state = STATE_RECORDING;
				// ͼ���Ϊrecord
				floatView.setImageDrawable(getResources().getDrawable(R.drawable.record));
			}
		});
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (replayTimesText != null) {
					replayTimesText.setText("" + progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}
		});
		
		twoTimesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modifyReplayTimes(2);
			}
			
		});
		
		tenTimesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modifyReplayTimes(10);
			}
			
		});
	     
		replayBtn.setOnClickListener(new OnClickListener() {
	    	 
			@Override
			public void onClick(View v) {
				
				wm.removeView(linearView);
				linearViewVisible = false;
				wm.addView(floatView, params1);
				
				state = STATE_REPLAYING;
				// ͼ���Ϊreplay
				floatView.setImageDrawable(getResources().getDrawable(R.drawable.play));
				
			}
		});
		
		// ��Ӧ�����������ý�ȥ���Ա��ȡ��Դ�ļ�
		NiorRecorder.setContext(getApplicationContext());
	    NiorReplayer.setContext(getApplicationContext());
	    
	    
	    
	     floatView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//showMessage("��һ�δ���");
				
				if (state == STATE_RECORDING) {
					startRecord(getApplicationContext(), floatView);
				} else if (state == STATE_RECORD_OVER) {
					stopRecord(getApplicationContext(), floatView);
				} else if (state == STATE_REPLAYING) {
					String replayTimesStr = replayTimesText.getText().toString();
					if (!replayTimesStr.trim().equals("")) {
						int tmpTimes = 1;
						try {
							tmpTimes = Integer.parseInt(replayTimesStr);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
						
						// �����ȷǷ�ֵ����
						if (tmpTimes >= 1) {
							replayTimes = tmpTimes;
						}
					}
					
					AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					
					startReplay(getApplicationContext(), am, floatView);
				} else if (state == STATE_REPLAY_OVER) {
					replayTimes--;
					if (replayTimes == 0) {
						stopReplay(getApplicationContext(), floatView);
					}
				} else {					
					if (linearViewVisible) {
						return;
					}
					
					linearViewVisible = true;
					
					state = STATE_INITIAL;
					replayTimes = 1;
					
					// �������֣���ʱ����Ӧ���Σ�����addView����
					wm.addView(linearView, params2);
					linearView.setVisibility(View.VISIBLE);
					wm.removeView(floatView);
				}
			}
		 });
	     
	     /*
	     // ����ǿ��ֹͣ�طţ�ʵ��ʹ��ʱ���֣����Ѱ�ס���¼��������ˣ�
	     floatView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if ((state == STATE_REPLAY_OVER)||(state == STATE_INITIAL)) {
					NiorReplayer.stop();
					
					floatView.setImageDrawable(getResources().getDrawable(R.drawable.touch));
					state = STATE_INITIAL;
					replayTimes = 1;
				}
				return false;
			}
	    	 
	     });
	     */
	     
	     /*
	     floatView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				showMessage("press key: " + arg1 + ", arg2: " + arg2 + ", view: " + arg0);
				
				return false;
			}
	    	 
	     });
	     */
	    
	     if (linearView != null) {
	    	 linearView.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {
	 				wm.removeView(linearView);
	 				linearViewVisible = false;
	 				wm.addView(floatView, params1);
	 			}
	 		});
		}
		
	     //���������
	     wm.addView(floatView, params1);
	 }
	  
	 @Override
	 public void onDestroy() {
		// �ڳ����˳�(Activity���٣�ʱ������������
//		if(linearView != null){
//			wm.removeView(linearView);
//		}
//	    wm.removeView(floatView);
		super.onDestroy();
		
	}
	 
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return true;
		}
		return false;
	}
   /** 
	 * ��õ�ǰ��Ļ���ȵ�ģʽ     
	 * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 Ϊ�Զ�������Ļ���� 
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0  Ϊ�ֶ�������Ļ���� 
	 */  
	private int getScreenMode(){
		int screenMode = 0;
		try{
			screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		}catch(Exception e){
			e.printStackTrace();
		}
		return screenMode;
	}
	
  /** 
    * ���õ�ǰ��Ļ���ȵ�ģʽ     
    * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 Ϊ�Զ�������Ļ���� 
    * SCREEN_BRIGHTNESS_MODE_MANUAL=0  Ϊ�ֶ�������Ļ���� 
    */  
    private void setScreenMode(int paramInt){  
       try{  
          Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);  
        }catch (Exception localException){  
          localException.printStackTrace();  
        }  
    }  
    /**
     * ������Ļ����
     */
    private void setWindowScreen(){
    	Window localWindow = getWindow();  
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();  
        float f = 5 / 255.0F;  
        localLayoutParams.screenBrightness = f;  
        localWindow.setAttributes(localLayoutParams); 
    }
    /**
     * �����ʾ����Ϣ  
     * @param content
     */
	private void showMessage(String content){
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}
}