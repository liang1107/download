package com.salesbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.salesbox.utils.ArrayUtils;
import com.salesbox.utils.Constants;
import com.salesbox.utils.LogsUtil;

import java.io.File;


public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        Toast.makeText(context,intent.getAction()+"122123" , Toast.LENGTH_SHORT).show();
        LogsUtil.d("---onReceive---action=" + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
//            Toast.makeText(context,"自动打开程序前前前" , Toast.LENGTH_SHORT).show();
//            try {
//                Thread.sleep(10000L);//休眠3秒

                Intent sendIntent = new Intent(context, MainActivity.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
//            Toast.makeText(context,"自动打开程序后后后" , Toast.LENGTH_SHORT).show();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED))
        {
            String packageName = intent.getData().getSchemeSpecificPart();
            LogsUtil.i("-------------------1111-----------------");
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED))
        {
            String packageName = intent.getData().getSchemeSpecificPart();
            LogsUtil.i("-------------------2222----------------");
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED))
        {
            String packageName = intent.getData().getSchemeSpecificPart();
            LogsUtil.i("-------------------333-----------------+:" + packageName);
            if ("com.salesbox".equals(packageName) && ! ArrayUtils.isServiceRuned(context, Constants.SERVICE_CLASS) )
            {
                Intent sendIntent = new Intent(context, MainActivity.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
            if ("com.wondware.sdk".equals(packageName)) {
                Intent sendIntent = new Intent(context, MainActivity.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
                Toast.makeText(context,"通讯组件安装成功" , Toast.LENGTH_SHORT).show();
                File fir= context.getExternalFilesDir("Download");
                deleteDirectory( context, fir);
                fir.delete();

            }

            if ("com.bigapp".equals(packageName)  )
            {
                Intent sendIntent = new Intent(context, MainActivity.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
                Toast.makeText(context,"大屏app========success" , Toast.LENGTH_SHORT).show();
                File fir= context.getExternalFilesDir("Download");
                deleteDirectory( context, fir);
                fir.delete();


            }
        }
    }


    public boolean deleteSingleFile(Context context,String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Toast.makeText(context, "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean deleteDirectory(Context context,File aaa) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
//        if (!filePath.endsWith(File.separator))
//            filePath = filePath + File.separator;
        File dirFile = aaa;
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Toast.makeText(context, "删除目录失败： 不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(context,file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
//            else if (file.isDirectory()) {
//                flag = deleteDirectory(context,file.getAbsolutePath());
//                if (!flag)
//                    break;
//            }
        }
        if (!flag) {
            Toast.makeText(context, "删除目录失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录成功！");
            return true;
        } else {
            Toast.makeText(context, "删除目录：失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
