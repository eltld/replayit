package com.taobao.athrun.ri;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
/**
 * 设置悬浮窗口的标签参数
 * @author renxia.sd
 *
 */
public class ParamUtil extends WindowManager.LayoutParams{

	public static LayoutParams getLayoutParam(int x, int y, int width, int height){
		
		LayoutParams params = new WindowManager.LayoutParams();
		 /**
	       *以下都是WindowManager.LayoutParams的相关属性
	       */
		params.type=LayoutParams.TYPE_PHONE;   //设置window type
	     //wmParams.format=PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明
		params.format=PixelFormat.TRANSPARENT;
		params.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                 | LayoutParams.FLAG_NOT_FOCUSABLE;
	     
	     /**  设置Window flag
	       *  下面的flags属性的效果形同“锁定”。
	       *  悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
	       *  wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL 
	       *               | LayoutParams.FLAG_NOT_FOCUSABLE
	       *               
	       *               | LayoutParams.FLAG_NOT_TOUCHABLE;
	       *  http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html
	       */
		params.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
	                              | LayoutParams.FLAG_NOT_FOCUSABLE;
	       
		params.gravity=Gravity.LEFT|Gravity.TOP;   //调整悬浮窗口至左上角
	     //以屏幕左上角为原点，设置x、y初始值
		params.x = x;
		params.y = y;
	    //设置悬浮窗口长宽数据
		params.width = width;
		params.height = height;
	    
		return params;
	}
}
