package com.salesbox;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.facebook.react.ReactActivity;
import com.salesbox.utils.ArrayUtils;
import com.salesbox.utils.Constants;
import com.salesbox.utils.LogsUtil;

public class MainActivity extends ReactActivity {
  private WondwareServerReceiver mWondwareReceiver ;
  protected static Context instance;
  private Timer timer;
  private TimerTask task;
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "salesBox";
  }
  //接受广播的的的的
  public class WondwareServerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      LogsUtil.i("action:"+intent.getAction());
//      Toast.makeText(context,intent.getAction()+"接收到广播了啊" , Toast.LENGTH_SHORT).show();

      if(intent.getAction().equals(Constants.WONDAWARE_MESSAGE_ACTION))
      {
        String Sdata = intent.getStringExtra(Constants.MSG_BROADCAST_DATA_TYPE );
        OpenNativeModule.sendEventToRn("Sdata",Sdata);
//          Toast.makeText(context,intent.getStringExtra(Constants.MSG_BROADCAST_DATA_TYPE )+"接收的Sdata参数" , Toast.LENGTH_SHORT).show();

      }else if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){

      }else if(intent.getAction().equals(Constants.WONDAWARE_JSON_S_SEND_ACTION)){
        String data = intent.getStringExtra(Constants.MSG_BROADCAST_JSON_DATA_TYPE);
//        try {
//          JSONObject json =  new JSONObject(data);
//        } catch (JSONException e) {
//          e.printStackTrace();
//        }
        OpenNativeModule.sendEventToRn("JsonData",data);
//          Toast.makeText(context,intent.getStringExtra(Constants.MSG_BROADCAST_JSON_DATA_TYPE )+"接收的JsonData参数" , Toast.LENGTH_SHORT).show();
//        Toast.makeText(context,intent.getIntExtra(Constants.MSG_BROADCAST_JSON_TYPE,0)+"接收的JsonData==0000000参数" , Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mWondwareReceiver = new WondwareServerReceiver();
    IntentFilter mIntentFilter = new IntentFilter();
    mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
    mIntentFilter.addAction(Constants.WONDAWARE_MESSAGE_ACTION);
    mIntentFilter.addAction(Constants.WONDAWARE_JSON_S_SEND_ACTION);
    this.registerReceiver(mWondwareReceiver,mIntentFilter);
    instance = this;
//    Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常

    Intent seviceIntent = new Intent(this, KeepLiveService.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      //android8.0以上通过startForegroundService启动service
      startForegroundService(seviceIntent);
    } else {
      startService(seviceIntent );
    }

    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    timer = new Timer();
    task=createVideoSchedulerTimer();
    long delay = 15000;
    long intevalPeriod = 15 * 1000;
    timer.schedule(task, delay, intevalPeriod);


  }
  // 创建服务用于捕获崩溃异常
//  private UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
//    public void uncaughtException(Thread thread, Throwable ex) {
//      Toast.makeText(instance,"发生崩溃议程了" , Toast.LENGTH_SHORT).show();
//      restartApp();//发生崩溃异常时,重启应用
//    }
//  };
//  public void restartApp(){
//    Intent intent = new Intent(instance,MainActivity.class);
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    instance.startActivity(intent);
//    android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
//  }

  private TimerTask createVideoSchedulerTimer() {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run(){
        if (!ArrayUtils.isRunningForeground(instance)) {
          Log.e("MyTestService", "进入后台了开始启动============");
          //获取ActivityManager
          ActivityManager mAm = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
          //获得当前运行的task
          List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
          for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if (rti.topActivity.getPackageName().equals(getPackageName())) {
              mAm.moveTaskToFront(rti.id, 0);
              return;
            }
          }
          //若没有找到运行的task，用户结束了task或被系统释放，则重新启动mainactivity
          Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);
          resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          startActivity(resultIntent);
        }
      }

    };
    return timerTask;
  }
  @Override
  public void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(mWondwareReceiver);

  }
}
