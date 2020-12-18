package com.salesbox.utils;

import java.util.ArrayList;

public class Constants {
    public static String POLL_CODE = "";
    /**心跳时间 300s*/
    public static final  int   HEAT_TIME = 5*60*1000;
    public static boolean isOsConnect = true;
    public static boolean isSrceenIng = false;
    public static boolean isBarcodeYuyue = false;
    public static ArrayList<String> AdverImagesAll = new ArrayList<>();

    public static final String CURRENT_APP_PACKAGE = "com.wondware.courier";
    public static final String CURRENT_APP_CLASS = "com.wondware.courier.pro.main.view.WondWareActivity";
    public static final String APP_UPDATE_ROOT =  "/mnt/sdcard/wondware/download/";
  //  public static final String APP_UPDATE_NAME = "CourierCabinet.apk";

    public static String Device_Id = "";
    public static String Device_secretKey = "cb99e866074924a59fe8ba00a5c20b77";
    public static final String WONDAWARE_MESSAGE_ACTION = "android.wondware.message.action";
    public static final String WONDAWARE_JSON_MESSAGE_ACTION = "android.wondware.json.message.action";
    public static final String WONDAWARE_JSON_S_SEND_ACTION = "android.wondware.json.s.send.action";

    public static final String SERVICE_CLASS = "com.wondware.sdk.mqtt.service.WondwareService";
    public static final String SERVICE_ACTION = "com.wondware.sdk.server.WondwareService";
    public static final String SERVICE_PACKAGE = "com.wondware.sdk";
    public static final String WONDWARE_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String AD_SYNC_PICTURE_ACTION = "android.wwwl.action.AD_SYNC_PICTURE_ACTION";
    public static final String SHOW_CHANGE_BATTERY_ACTION = "android.wondware.action.CHANGE_BATTERY_ACTION";

    public static final String GETWEATHER = "http://wthrcdn.etouch.cn/weather_mini?";
    public static final int NET_ACK_WONDARE_NOT_EXIST = 0;
    public static final int NET_ACK_DEVICE_NOT_EXIST = -1;
    public static final int NET_ACK_SUCCESS = 200;

    public static final String PREFERENCE_NAME = "courier_Pref_Common"; //sp
    public static final String WONDWARE_MSG_CMD_OPEN_BOX = "2L";//开箱

    public static final  byte  WONDWARE_MSG_LOCK_STATUS = 0x01;
    public static final  byte  WONDWARE_MSG_COMMON = 0x02;
    public static final  byte  WONDWARE_MSG_OPENBOX = 0x04;
    public static final  byte  WONDWARE_MSG_SET_SECRETKEY = 0x05;
    public static final  byte  WONDWARE_MSG_SET_ADV = 0x06;
    public static final  byte  WONDWARE_MSG_BOX_STATUS = 0x07;
    public static final  byte  WONDWARE_MSG_DEVICE_INFO = 0x08;
    public static final  byte  WONDWARE_MSG_DEVICE_STATUS = 0x09;
    public static final  byte  WONDWARE_MSG_UPDATE_INFO = 0x0A;
    public static final  byte  WONDWARE_MSG_DEVICE_CONNECT_STATUS = 0x0B;
    public static final  byte  WONDWARE_MSG_DEVICE_ID_STATUS = 0x0C;


    public static final  byte  FRAGMENT_MSG_HOME = 0x00;
    public static final  byte  FRAGMENT_MSG_YUYUE = 0x01;
    public static final  byte  FRAGMENT_MSG_PULL_HOME = 0x02;
    public static final  byte  FRAGMENT_MSG_PULL_OPEN = 0x03;
    public static final  byte  FRAGMENT_MSG_YUYUE_OPEN = 0x04;
    public static final  byte  FRAGMENT_MSG_YUYUE_PAY = 0x05;
    public static final  byte  FRAGMENT_MSG_PUSH_QUICK = 0x06;
    public static final  byte  FRAGMENT_MSG_PUSH_PHONE_OPEN = 0x07;
    public static final  byte  FRAGMENT_MSG_PUSH_QUICK_OPEN = 0x08;
    public static final  byte  FRAGMENT_MSG_PUSH_SUCCESS = 0x09;

    public static final String WONDWARE_DEFAULT_QRCODE
            = "http://weixin.qq.com/q/02vtUpEej5fm310000M07Q";

    /**通信*/
    public static final String MSG_BROADCAST_DATA_TYPE = "s_data";
    public static final String MSG_BROADCAST_JSON_TYPE = "json_type";
    public static final String MSG_BROADCAST_JSON_DATA_TYPE = "json_data";

    /**后台修改设备秘钥推送至屏幕*/
    public static final String MSG_SET_SECRETKEY = "setSecretKey";
    /**后台修改广告图片推送至屏幕*/
    public static final String MSG_SET_ADV       = "setAdv";
    /**后台禁用箱子推送至屏幕*/
    public static final String MSG_SET_BOX_STATUS = "setBoxStatus";
    /**后台柜子信息重置推送至屏幕*/
    public static final String MSG_SET_DEVICE_INFO = "setDeviceInfo";
    /**柜子维修禁用状态推送至屏幕*/
    public static final String MSG_SET_DEVICE_STATUS = "setDeviceStatus";
    /**应用升级*/
    public static final String MSG_SET_DEVICE_UPDATE = "setUpdate";

    /**锁状态改变*/
    public static final String MSG_DEVICE_LOCK_STATUS_CHANGE = "lock_status_change";
    /**服务的网络连接状态*/
    public static final String WONDWARE_MSG_CONNECT_STATUS = "connect_status";
    /**服务的功能类型*/
    public static final String WONDWARE_MSG_FUNC_TYPE = "func_type";
    /**设备ID*/
    public static final String MSG_SET_DEVICE_ID_STATUS = "device_id";
    /**设备状态 禁用启用*/
    public static final String MSG_SET_DEVICE_USE_STATUS = "device_status";
    /**设备升级*/
    public static final String MSG_SET_DEVICE_UPDATE_INFO = "update_info";
    /**设备唯一码*/
    public static final String MSG_SET_DEVICE_UNICODE = "activationCode";
    /**修改秘钥*/
    public static final String MSG_SET_DEVICE_SECRETKEY = "Device_secretKey";

    /**快递柜消息结果返回处理*/
    public static final String MSG_DEVICE_KUAI_DEL = "del";
  //  扫描码投递：{opty:lco, data:123456}
    public static final String MSG_DEVICE_KUAI_LCO = "lco";
    public static final String MSG_DEVICE_KUAI_PRE = "pre";
    public static final String MSG_DEVICE_KUAI_GET = "get";
    public static final String MSG_DEVICE_KUAI_CAL = "cal";
    public static final String MSG_DEVICE_KUAI_SUC = "suc";

    public static final String MSG_DEVICE_KUAI_QRC = "qrc";
    public static final String MSG_DEVICE_KUAI_ADD = "add";
    public static final String MSG_DEVICE_KUAI_PHO = "pho";
    public static final String MSG_DEVICE_KUAI_INFO = "info";
    public static final String MSG_DEVICE_KUAI_BOXINFO = "boxInfo";

}
