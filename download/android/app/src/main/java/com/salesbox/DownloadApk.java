package com.salesbox;

//package com.XXX;  XXX为包名，与MainApplication.java或MainActivity.java的保持一致即可

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

public class DownloadApk extends ReactContextBaseJavaModule {

    public static String description;

    DownloadManager downManager;
    Activity myActivity;
    private static ReactContext myContext;

    public DownloadApk(ReactApplicationContext reactContext) {

        super(reactContext);
        myContext=reactContext;
    }

    @Override
    public String getName() {
        return "DownloadApk";
    }
    @ReactMethod
    public void fileDelete(){
//        deleteSingleFile("zujian.apk");
//        deleteSingleFile("bigapp.apk");
//        File dirFile = new File(Environment.DIRECTORY_DOWNLOADS);
        String[] a = myContext.fileList();
        for (String it :a){
            Toast.makeText(myContext, it, Toast.LENGTH_SHORT).show();
        }
//        String b = String.valueOf(a);
//        Toast.makeText(getReactApplicationContext(), (CharSequence) myContext.getFilesDir(), Toast.LENGTH_SHORT).show();
//        deleteAllFiles(getReactApplicationContext().getFilesDir());
//        getReactApplicationContext().deleteFile(getReactApplicationContext().fileList()[0]);
        File file = new File( myContext.getFilesDir(),"Download/zujian.apk");
        String file2 = myContext.getFilesDir().getAbsolutePath();
        File fir= myContext.getExternalFilesDir("Download");
        File file3 = new File(Environment.getRootDirectory().toString());
        File path =new File(file2);
//        myContext.deleteFile(path);
        deleteDirectory(fir);
        fir.delete();
    }


    private boolean deleteDirectory(File aaa) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
//        if (!filePath.endsWith(File.separator))
//            filePath = filePath + File.separator;
        File dirFile = aaa;
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Toast.makeText(getReactApplicationContext(), "删除目录失败： 不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
//            else if (file.isDirectory()) {
//                flag = deleteDirectory(file.getAbsolutePath());
//                if (!flag)
//                    break;
//            }
        }
        if (!flag) {
            Toast.makeText(getReactApplicationContext(), "删除目录失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录成功！");
            return true;
        } else {
            Toast.makeText(getReactApplicationContext(), "删除目录：失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Toast.makeText(getReactApplicationContext(), "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getReactApplicationContext(), "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @ReactMethod
    public void downloading(String url, String description) {
        DownloadApk.description = description;

        myActivity = getCurrentActivity();
        downManager = (DownloadManager)myActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new Request(uri);

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        //设置通知栏标题
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle("app");

        if (description == null || "".equals(description)) {
            description = "目标apk正在下载";
        }

        request.setDescription(description);
        request.setAllowedOverRoaming(false);

        // 设置文件存放目录
        request.setDestinationInExternalFilesDir(myActivity, Environment.DIRECTORY_DOWNLOADS, description);

        long downloadid = downManager.enqueue(request);
        SharedPreferences sPreferences = myActivity.getSharedPreferences("ggfw_download", 0);
        sPreferences.edit().putLong("ggfw_download_apk", downloadid).commit();
    }
}

