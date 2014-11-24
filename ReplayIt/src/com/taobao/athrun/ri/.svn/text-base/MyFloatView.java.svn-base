package com.taobao.athrun.ri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * 获得浮动的图片位置
 * @author renxia.sd
 *
 */
@SuppressLint("ViewConstructor")
public class MyFloatView extends ImageView {
	
	private final static String TAG = "athrun";
	
	private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    
    private boolean isMove = false;
    
    private OnClickListener clickListener;
    
    private WindowManager wm = (WindowManager)getContext().getApplicationContext().getSystemService("window");
    
    //此wmParams为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams wmParams;

	public MyFloatView(Context context, WindowManager.LayoutParams wmParams) {
		super(context);		
		this.wmParams = wmParams;
		Log.i(TAG, "LayoutParams：" + wmParams);
	}
	
	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 //获取相对屏幕的坐标，即以屏幕左上角为原点		 
	     x = event.getRawX();   
	     y = event.getRawY()-25;   //25是系统状态栏的高度
	     
	     float screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
	    
	     switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	        	//获取相对View的坐标，即以此View左上角为原点
	        	mTouchStartX =  event.getX();  
                mTouchStartY =  event.getY();
	            break;
	        case MotionEvent.ACTION_MOVE:
	        	if(x > 45 && (screenWidth - x) > 45) {
	        		isMove = true;
		            updateViewPosition();
				}
		         break;
	        case MotionEvent.ACTION_UP:
	        	if (isMove) {
	        		isMove = false;
		        	float height = wm.getDefaultDisplay().getHeight();
		        	float width = wm.getDefaultDisplay().getWidth();
		        	//设置X
		        	if (x<(width/2)) {x = mTouchStartX;}
		        	else {x = width; mTouchStartX = 0;}
		        	//设置Y
		    		if (y < (height/6)) y = mTouchStartY;
					else if (y >= (height/6) && y < (height/2))	y = height/3;
					else if (y >= (height/2) && y < (height/6)*5) y =  height/3*2;
					else y = height;
					
		        	updateViewPosition();
		        	mTouchStartX = mTouchStartY = 0;
		        	break;
				}else {
					if (clickListener != null) {
						clickListener.onClick(this);
					}
				}
	        }
	        return true;
		}
	 
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.clickListener = l;
	}
	 
	 /**
	  * 更新图片的坐标
	  */
	 private void updateViewPosition(){
		//更新浮动窗口位置参数
		wmParams.x=(int)( x-mTouchStartX);
		wmParams.y=(int) (y-mTouchStartY);
	    wm.updateViewLayout(this, wmParams);
	 }
}
