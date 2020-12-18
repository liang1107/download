package com.salesbox.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
	public static int getDisplayWidth(Activity activity){
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
	}
	
	/** 
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *  
     * @param pxValue 
     * @param:scale
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 将dip或dp值转换为px值，保证尺寸大小不变 
     *  
     * @param dipValue 
     * @param: scale
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
  
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param: fontScale
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param: fontScale
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }

    public static float px2px(Context context, float pixValue) {
        // final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        final float   fontSize = context.getResources().getDisplayMetrics().widthPixels *pixValue /1080;
        return  fontSize + 0.5f;
    }
}
