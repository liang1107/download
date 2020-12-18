package com.salesbox;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.salesbox.utils.Constants;
import com.salesbox.utils.NetWorkUtil;
import com.salesbox.utils.ZXingUtils;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ys.rkapi.MyManager;

import org.json.JSONException;
import org.json.JSONObject;


public class OpenNativeModule extends ReactContextBaseJavaModule {
    public static final String TAG = "TAG";
    private ReactContext mReactContext;
    private static ReactContext myContext;
    public static final String EVENT_NAME = "nativeCallRn";
    SoundPool soundPool=null ;
    ToneGenerator tone=null;
    MyManager manager;
    public OpenNativeModule(ReactApplicationContext context) {
        super(context);
        this.mReactContext = context;
        myContext=context;
        manager = MyManager.getInstance(myContext);
    }

    @Override
    public String getName() {
        return "OpenNativeModule";
    }
    //获取设备信息
    @ReactMethod
    public void getSeverMsg(Callback successBack) {
//        android.os.Build.VERSION.RELEASE：获取系统版本字符串。如4.1.2 或7.1.2等
//        android.os.Build.VERSION.SDK_INT：系统的API级别 数字表示
//        android.os.Build.BOARD：获取设备基板名称
//        android.os.Build.BOOTLOADER:获取设备引导程序版本号
//        android.os.Build.BRAND：获取设备品牌
//        android.os.Build.CPU_ABI：获取设备指令集名称（CPU的类型）
//        android.os.Build.CPU_ABI2：获取第二个指令集名称
//        android.os.Build.DEVICE：获取设备驱动名称
//        android.os.Build.DISPLAY：获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
//        android.os.Build.FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
//        android.os.Build.HARDWARE：设备硬件名称,一般和基板名称一样（BOARD）
//        android.os.Build.HOST：设备主机地址
//        android.os.Build.ID:设备版本号。
//        android.os.Build.MODEL ：获取手机的型号 设备名称。如：SM-N9100（三星Note4）
//        android.os.Build.MANUFACTURER:获取设备制造商。如：samsung
//        android:os.Build.PRODUCT：整个产品的名称
//        android:os.Build.RADIO：无线电固件版本号，通常是不可用的 显示
//        unknownandroid.os.Build.TAGS：设备标签。如release-keys 或测试的 test-keys
//        android.os.Build.TIME：时间
//        android.os.Build.TYPE:设备版本类型 主要为"user" 或"eng".
//                android.os.Build.USER:设备用户名 基本上都为android-build
//        android.os.Build.VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
//        android.os.Build.VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
       String RELEASE =  android.os.Build.VERSION.RELEASE;
        String SDK_INT = String.valueOf(Build.VERSION.SDK_INT);
       String BOARD =android.os.Build.BOARD;
        String BOOTLOADER =android.os.Build.BOOTLOADER;
        String BRAND =android.os.Build.BRAND;
        String CPU_ABI =android.os.Build.CPU_ABI;
        String CPU_ABI2 =android.os.Build.CPU_ABI2;
        String DEVICE =android.os.Build.DEVICE;
        String DISPLAY =android.os.Build.DISPLAY;
        String FINGERPRINT =android.os.Build.FINGERPRINT;
        String HARDWARE =android.os.Build.HARDWARE;
        String HOST =android.os.Build.HOST;
        String ID =android.os.Build.ID;
        String MODEL =android.os.Build.MODEL;
        String MANUFACTURER =android.os.Build.MANUFACTURER;
        String PRODUCT =android.os.Build.PRODUCT;
        String TIME = String.valueOf(android.os.Build.TIME);
        String USER =android.os.Build.USER;

    String msg = "RELEASE="+RELEASE+",SDK_INT="+SDK_INT+",BOARD="+BOARD+",BOOTLOADER="+BOOTLOADER+",BRAND="+BRAND+",CPU_ABI="+CPU_ABI+",CPU_ABI2="+CPU_ABI2+",DEVICE="+DEVICE+
            ",DISPLAY="+DISPLAY+",FINGERPRINT="+FINGERPRINT+",HARDWARE="+HARDWARE+",HOST="+HOST+",ID="+ID+",MODEL="+MODEL+",MANUFACTURER="+MANUFACTURER+",PRODUCT="+PRODUCT+",USER="+USER+
            ",TIME="+TIME;
        successBack.invoke(msg);
    }
    //重启
    @ReactMethod
    public void reboot() {
        Toast.makeText(getReactApplicationContext(), "重新启动", Toast.LENGTH_SHORT).show();
        manager.reboot();
    }
    //Toast
    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }
    //    隐藏键盘
    @ReactMethod
    public void hideKeyboard() {
        final Activity activity = getCurrentActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        manager.setSoftKeyboardHidden(false);
    }
    @ReactMethod
    public void openKeyboard() {
        final Activity activity = getCurrentActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1); // hide
        manager.setSoftKeyboardHidden(true);
    }
    @ReactMethod
    public void isNetworkConnected(Callback successBack){
        boolean a =  NetWorkUtil.isNetworkConnected(getReactApplicationContext());
        boolean b = NetWorkUtil.isNetUsable(getReactApplicationContext());
        boolean c = false;
        if( a && b ){
            c=true;
        }
        successBack.invoke(String.valueOf(c));

    }
    @ReactMethod
    public void gitVersion(Callback successBack, Callback errorBack){
        try{
            Log.d(TAG,"调取版本好");
            String result;
            result =  manager.getApiVersion();
            Log.d(TAG,"API Version = " + result);
            successBack.invoke(result);
        }catch (Exception e){
            errorBack.invoke(e.getMessage());
        }

    }
    @ReactMethod
    public void changebar(Callback successBack, Callback errorBack){
        try{
            Log.d(TAG,"更改状态栏");
            String result;
            Log.d(TAG, String.valueOf(manager.getNavBarHideState())+"状态栏的状态查看");
            if (manager.getNavBarHideState())
            {
                manager.hideNavBar(false);
            }else{
                manager.hideNavBar(true);
            }
            successBack.invoke("ok");
        }catch (Exception e){
            errorBack.invoke(e.getMessage());
        }

    }
    @ReactMethod
    public boolean console() {
        Log.d(TAG,"跳转123跳转");
        Toast.makeText(mReactContext,"原生toast", Toast.LENGTH_SHORT).show();
//        Settings.System.putInt(myContext.getContentResolver(), Settings.System.SYSTEMBAR_HIDE,1);
//            Intent i = new Intent("com.tchip.changeBarHideStatus");
//        myContext.sendBroadcast(i);

        return false;


    }



    @ReactMethod
    public void dataToJS(Callback successBack, Callback errorBack){
        try{
            Activity currentActivity = getCurrentActivity();

            String result = currentActivity.getIntent().getStringExtra("number");
            if (TextUtils.isEmpty(result)){
                result = "没有1111数据";
            }
            System.out.print(result+"123");
            Log.d(TAG,result);
            Toast.makeText(mReactContext,result, Toast.LENGTH_SHORT).show();
            successBack.invoke(result);
        }catch (Exception e){
            errorBack.invoke(e.getMessage());
        }
    }
    @ReactMethod
    public void openBox(String code, String typeEIR){
        try{

            int type = 0x01;
            JSONObject ee = new JSONObject();
            ee.put("opty",typeEIR);
            ee.put("data",code);
//            Toast.makeText(this.mReactContext,ee.toString(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();

            intent.putExtra(Constants.MSG_BROADCAST_JSON_TYPE,type);
            intent.putExtra(Constants.MSG_BROADCAST_DATA_TYPE,ee.toString());

            intent.setAction(Constants.WONDAWARE_JSON_MESSAGE_ACTION);
            getReactApplicationContext().sendBroadcast(intent);
            Log.i("guangbo", intent.getAction());
//            Toast.makeText(mReactContext,intent.getAction(),Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }
    }

    @ReactMethod
    public void stringtoJson(String code){
        try {
            JSONObject json =  new JSONObject(code);
            String a = (String) json.get("qr");
            Toast.makeText(this.mReactContext,a, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @ReactMethod
    public void stringtopic(String code, Callback successBack){

        Bitmap qrImage = ZXingUtils.createQRImage(code,224,224,true);
        String IMG = ZXingUtils.bitmapToBase64(qrImage);
        successBack.invoke(IMG);


    }
    /**
     * 获取数据
     * @param key 需要获取的key
     * @param defaultString
     * @return
     */
    @ReactMethod
    public String getData(String key, String defaultString)
    {
        //使用提供者的内部方法
        Uri uri = Uri.parse("content://com.wondware.sdk.provider.WondwareProvider/wondware");
        Cursor cursor = myContext.getContentResolver().query(uri, new String[]{
                Constants.MSG_SET_DEVICE_ID_STATUS,
                Constants.MSG_SET_DEVICE_UNICODE,
                Constants.WONDWARE_MSG_CONNECT_STATUS,
                Constants.MSG_SET_DEVICE_SECRETKEY}, null, null, null);
        if(cursor == null || cursor.getCount() == 0)
        {
            Log.i(TAG,"wondware data cursor is null");
            return defaultString;
        }
        cursor.moveToFirst();

//        String  pasy =cursor.getString(0)+"ww"+cursor.getString(1)+"ww"+cursor.getString(2)+"ww"+cursor.getString(3);
//        Toast.makeText(this.mReactContext,pasy,Toast.LENGTH_SHORT).show();

        if( Constants.MSG_SET_DEVICE_ID_STATUS.equals(key))
        {
            Toast.makeText(this.mReactContext,cursor.getString(0), Toast.LENGTH_SHORT).show();
            cursor.close();

        }else  if( Constants.MSG_SET_DEVICE_UNICODE.equals(key)){
            cursor.close();

        }else  if( Constants.WONDWARE_MSG_CONNECT_STATUS.equals(key)){
            cursor.close();

        }else  if( Constants.MSG_SET_DEVICE_SECRETKEY.equals(key)){
            cursor.close();

        }
        cursor.close();
        return defaultString;
    }
    /**
     * 获取数据 device_id
     * @param
     * @param
     */
    @ReactMethod
    public void getDeviceId(Callback successBack)
    {
        //使用提供者的内部方法
        Uri uri = Uri.parse("content://com.wondware.sdk.provider.WondwareProvider/wondware");
        Cursor cursor = myContext.getContentResolver().query(uri, new String[]{
                Constants.MSG_SET_DEVICE_ID_STATUS}, null, null, null);
        if(cursor == null || cursor.getCount() == 0)
        {
            successBack.invoke(false);
        }else{
            cursor.moveToFirst();
            String device_id = cursor.getString(0);
            successBack.invoke(device_id);
        }

        cursor.close();

    }

    //建立监听通过原生传递参数给react-native
    public static void sendEventToRn(String eventName, String paramss) {
        myContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, paramss);


    }
    //主动释放音频
    @ReactMethod
    public void reSoundPool(){
        if(soundPool==null){

        }else{
            soundPool.release();
        }
    }
    //播放文件声音
    @ReactMethod
    public void renusic(String soundName) {
//        Toast.makeText(getReactApplicationContext(), "播放音乐", Toast.LENGTH_SHORT).show();
        //实例化SoundPool
        if(soundPool==null){

        }else{
            soundPool.release();
        }

        //sdk版本21是SoundPool 的一个分水岭
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入最多播放音频数量,
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            /**
             * 第一个参数：int maxStreams：SoundPool对象的最大并发流数
             * 第二个参数：int streamType：AudioManager中描述的音频流类型
             *第三个参数：int srcQuality：采样率转换器的质量。 目前没有效果。 使用0作为默认值。
             */
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
         int voiceId = 0;
        //可以通过四种途径来记载一个音频资源：
        //1.通过一个AssetFileDescriptor对象
        //int load(AssetFileDescriptor afd, int priority)
        //2.通过一个资源ID
        //int load(Context context, int resId, int priority)
        //3.通过指定的路径加载
        //int load(String path, int priority)
        //4.通过FileDescriptor加载
        //int load(FileDescriptor fd, long offset, long length, int priority)
        //声音ID 加载音频资源,这里用的是第二种，第三个参数为priority，声音的优先级*API中指出，priority参数目前没有效果，建议设置为1。
        switch (soundName){
//            case "againqueren":
//                  voiceId = soundPool.load(getReactApplicationContext(), R.raw.againqueren, 1);
//                break;
//            case "codeerr":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.codeerr, 1);
//                break;
//            case "danhaoerr":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.danhaoerr, 1);
//                break;
//            case "di":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.di, 1);
//                break;
//            case "jijian":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.jijian, 1);
//                break;
//            case "lanjiancode":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.lanjiancode, 1);
//                break;
//            case "lanjiancodeerr":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.lanjiancodeerr, 1);
//                break;
//            case "openbox":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.openbox, 1);
//                break;
//            case "openboxs":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.openboxs, 1);
//                break;
//            case "ordercode":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.ordercode, 1);
//                break;
//            case "ordercodeerr":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.ordercodeerr, 1);
//                break;
//            case "orderfull":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.orderfull, 1);
//                break;
//            case "paymoney":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.paymoney, 1);
//                break;
//            case "pickupcode":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.pickupcode, 1);
//                break;
//            case "queren":
//                voiceId = soundPool.load(getReactApplicationContext(), R.raw.queren, 1);
//                break;
        }
//        final int voiceId = soundPool.load(getReactApplicationContext(), R.raw.pickupcode, 1);
//        final int voiceId = soundPool.load(getReactApplicationContext(), R.raw.di, 1);
        //异步需要等待加载完成，音频才能播放成功np
        int finalVoiceId = voiceId;
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    //第一个参数soundID
                    //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
                    //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
                    //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
                    //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
                    //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
                    soundPool.play(finalVoiceId, 1, 1, 1, 0, 1);
                }
            }
        });
    }
    //播放系统声音
    @ReactMethod
    public void renusicTwo() {

        if (tone==null){

        }else{
            tone.release();
        }
//        tone = new ToneGenerator(AudioManager.STREAM_SYSTEM,ToneGenerator.MAX_VOLUME);
        tone = new ToneGenerator(AudioManager.STREAM_VOICE_CALL,80);
        Log.e("0000000000000","00000000000000000000");
//      ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC,80);
        tone.startTone(ToneGenerator.TONE_PROP_BEEP);
    }


//    @ReactMethod
//    public void openqi() {
//        try {
//
//            // 获取UsbManager
//            myUsbManager = (UsbManager) myContext.getSystemService(myContext.USB_SERVICE);
//            initInfo();//初始化模拟数据
//
//            //枚举设备
//            enumerateDevice();
//            //找到设备接口数据
//            findInterface();
//            //打开设备
//            openDevice();
//            //分配端点
//            //assignEndpoint();
//
//        }catch (Exception e){
//
//        }
//    }
////扫码器
//
//    private void initInfo(){
//        //打开灯
//        mybuffer = new byte[]{(byte)0x57, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x47, (byte)0x05, (byte)0x50, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
//        //关闭灯
//       // mybuffer = new byte[]{(byte)0x57, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x02, (byte)0x07, (byte)0x04, (byte)0x50, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
//
//        Log.i("mybuffer", bytesToHexString(mybuffer));
//    }
//
//
//    /**
//     * 分配端点，IN | OUT，即输入输出；此处我直接用1为OUT端点，0为IN，当然你也可以通过判断
//     */
//    private void assignEndpoint() {
//        if (myInterface.getEndpoint(1) != null) {
//
//            epOut = myInterface.getEndpoint(1);
//        }
//        if (myInterface.getEndpoint(0) != null) {
//            epIn = myInterface.getEndpoint(0);
//        }
//
////       Log.d(TAG, "分配 112"+getString(R.string.text));
//        Log.d(TAG, "分配 epOut   "+epOut);
//        Log.d(TAG, "分配 epIn   "+epIn);
//        int re = myDeviceConnection.bulkTransfer(epOut, mybuffer, mybuffer.length, 3000);
//        byte[] reByte = new byte[64];
//        int re2 = myDeviceConnection.bulkTransfer(epIn, reByte, reByte.length, 3000);
//        Log.i("reByte", "re="+re+",re2="+ re2 + "\n" + bytesToHexString(reByte));
//        Toast.makeText(mReactContext, bytesToHexString(reByte), Toast.LENGTH_LONG).show(); //
//
//    }
//
//    //获取权限，打开设备
//    private void openDevice() {
//        if (myInterface != null) {
//            UsbDeviceConnection conn = null;
//            // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限，可以查阅相关资料
//            PendingIntent pi = PendingIntent.getBroadcast(mReactContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
//            if (!myUsbManager.hasPermission(myUsbDevice)) {
//                myUsbManager.requestPermission(myUsbDevice, pi);
//            }
//            if (myUsbManager.hasPermission(myUsbDevice)) {
//                conn = myUsbManager.openDevice(myUsbDevice);
//            } else {
//                Toast.makeText(mReactContext, "未获得权限", Toast.LENGTH_SHORT).show();
//            }
//
//            if (conn == null) {
//                return;
//            }
//
//            if (conn.claimInterface(myInterface, true)) {
//                myDeviceConnection = conn; // 到此你的android设备已经连上HID设备
//                Log.d(TAG, "打开设备成功");
//                Toast.makeText(mReactContext, "打开设备成功", Toast.LENGTH_SHORT).show();
//                assignEndpoint();
//            } else {
//                conn.close();
//            }
//        }
//    }
//
//
//    /**
//     * 找设备接口
//     */
//    private void findInterface() {
//        if (myUsbDevice != null) {
//            Log.d(TAG, "interfaceCounts : " + myUsbDevice.getInterfaceCount());
//            Log.d(TAG, "interfaceType : " + myUsbDevice.getInterface(0).getEndpoint(0).getType());
//            for (int i = 0; i < myUsbDevice.getInterfaceCount(); i++) {
//                UsbInterface intf = myUsbDevice.getInterface(i);
//                // 根据手上的设备做一些判断，其实这些信息都可以在枚举到设备时打印出来
////                Log.d(TAG, i+"  interfaceCounts2222 : " + intf.getInterfaceClass());3
////                UsbEndpoint[mAddress=129,mAttributes=3,mMaxPacketSize=8,mInterval=4]
//                Log.d(TAG, i+"  getInterfaceClass : " + intf.getInterfaceClass());
//                Log.d(TAG, i+"  getInterfaceSubclass : " + intf.getInterfaceSubclass());//得到该接口的子类 0
//                Log.d(TAG, i+"  getInterfaceProtocol : " + intf.getInterfaceProtocol()); //协议类型 1
//                if (intf.getInterfaceClass() == 3
//                        && intf.getInterfaceSubclass() == 0
//                        && intf.getInterfaceProtocol() == 0) {
//                    myInterface = intf;
//                    Log.d(TAG, "找到我的设备接口1");
//                    break;
//                }
//            }
//        }
//    }
//
//    /**
//     * 枚举设备
//     */
//    private void enumerateDevice() {
//        if (myUsbManager == null)
//            return;
//
//        HashMap<String, UsbDevice> deviceList = myUsbManager.getDeviceList();
//        if (!deviceList.isEmpty()) { // deviceList不为空
//            StringBuffer sb = new StringBuffer();
//            for (UsbDevice device : deviceList.values()) {
//                sb.append(device.toString());
//                sb.append("\n");
////                info.setText(sb);
//                Toast.makeText(mReactContext, sb, Toast.LENGTH_SHORT).show();
//                // 输出设备信息
//                Log.d(TAG, "DeviceInfo: " + device.getVendorId() + " , "
//                        + device.getProductId());
//
//                // 枚举到设备
//                if (device.getVendorId() == VendorID
//                        && device.getProductId() == ProductID) {
//                    myUsbDevice = device;
//                    Log.d(TAG, "枚举设备成功");
//                }
//            }
//        }
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.main, menu);
////        return true;
////    }
//
//    public static String bytesToHexString(byte[] src){
//        StringBuilder stringBuilder = new StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }












}

