package com.salesbox;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KeepLiveService extends Service {

    private TimerTask task;
    private Timer timer;
    private Intent sevice;

    @Override
    public void onCreate() {
        super.onCreate();
        if (task != null) {
            task.cancel();
            task = null;
        }
        task = createVideoSchedulerTimer();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        long delay = 15000;
        long intevalPeriod = 15 * 1000;
        timer.schedule(task, delay, intevalPeriod);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent == null) {
            startServices();
        }
//        Notification notification = new Notification(R.mipmap.ic_launcher, "前台服务！应用包活！"
//                , System.currentTimeMillis());
//        //设置通知默认效果
//        notification.flags = Notification.FLAG_SHOW_LIGHTS;
//        startForeground(1, notification);
        return Service.START_STICKY;
    }


    private void startServices() {
        stopSelf();
        if (sevice == null)
            sevice = new Intent(this, KeepLiveService.class);
        startService(sevice);
    }

    private TimerTask createVideoSchedulerTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //判断程序是否在运行
                boolean isRunning = isServiceStarted(getApplicationContext(), "com.salesbox");
                //没有运行就开启他
                if (!isRunning) {
                    Log.e("MyTestService", "程序未开启============");
//下面getLaunchIntentforPackage()里面的参数是你程序的报名，也就是，build.gradle里面的applicationId，所以，这里你要记得更改一下
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.salesbox");
                    startActivity(intent);
                }
                else {
                    Log.e("MyTestService", "========程序已经运行了==========");
                }
            }
        };
        return timerTask;
    }

    /**
     * 检测一个android程序是否在运行
     *
     * @param context
     * @param PackageName
     * @return
     */
    public static boolean isServiceStarted(Context context, String PackageName) {
        boolean isStarted = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(PackageName) && info.baseActivity.getPackageName().equals(PackageName)) {
                isStarted = true;
                break;
            }
        }
        return isStarted;
    }

    @Override
    public void onLowMemory() {
        startServices();
        super.onLowMemory();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }
}
