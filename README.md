# download
一.添加看门狗，程序被退出后，自动重启
1、首先，我们创建一个服务，我给起了一个通俗易懂的名字KeepLiveService，字面就能理解，可以吧 然后呢，在里面进行判断应用存活状态，等等之类的，直接看代码吧，我也不多说了 ，


 
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
        //设置通知可能报错，
        Notification notification = new Notification(R.mipmap.ic_launcher, "前台服务！应用包活！"
                , System.currentTimeMillis());
        //设置通知默认效果
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        startForeground(1, notification);
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
                boolean isRunning = isServiceStarted(getApplicationContext(), "包名");
                //没有运行就开启他
                if (!isRunning) {
                    Log.e("MyTestService", "程序未开启============");
//下面getLaunchIntentforPackage()里面的参数是你程序的报名，也就是，build.gradle里面的applicationId，所以，这里你要记得更改一下 
                    Intent intent = getPackageManager().getLaunchIntentForPackage("包名");
                    startActivity(intent);
                }
//                else {
//                    Log.e("MyTestService", "========程序已经运行了==========");
//                }
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
 

2、在AndroidManifest.xml的Application标签下，注册服务

  <service
            android:name=".service.KeepLiveService"
            android:enabled="true"
            android:exported="true"></service>
3、就是启动服务啦 ，在你需要开启服务的地方，也就是，程序主入口那里，进行开启，这样保证程序启动就开启服务，也就能一直检测这程序是否开启了 ，这里，有个地方说明一下，就是，程序自检的时候，我在里面备注了，要将自己的applicationId写上，上面是我自己的包名，你不更改是不能用的，所以要注意一下

    Intent seviceIntent = new Intent(this, KeepLiveService.class);
    startService(seviceIntent );
3、其次呢，其次呢，额，其次，其实就完成了，没了，就这些了 ，当你应用崩溃后，经过一定时间就能重启应用了，当然，你不能在你程序不稳定的情况下添加这个，在你测试结束后，如果没有出现问题，就添加上这个，如果出现突然的bug，应用不至于被杀死的状态，这样，不会造成不必要的麻烦了，应用的自启动时间，也就是，检测崩溃的时间，你可以设定，在程序里面，参数咋onCreat里面的  delay还有intevalPeriod，这两个参数，意思是delay是程序开启多长时间后，启动这个服务，intevalPeriod这个参数是，程序崩溃后，多长时间再进行自检，如果检测到程序崩溃了，就立即启动程序，








二。监听进入后台后，直接切换到前台


1. 判断APP是否在前台运行
 public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }
2. 将APP切换到前台
//如果APP是在后台运行
            if (!AllUtils.isRunningForeground(this)) {
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

3. 必须添加的权限

<!--前后台的切换-->
    <uses-permission android:name="android.permission.GET_TASKS"/>
    <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL"/>
    <uses-permission android:name="android.permission.GET_TOP_ACTIVITY_INFO"/>
    <uses-permission android:name="android.permission.REORDER_TASKS"/>

三。点击下载app，并且安装，安装成功后，删除安装包。
