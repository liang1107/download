package com.salesbox.utils;

/**
 * Created by zhouyp on 2019-02-14
 */
public enum FuncType {
    BATTERY_INFO(0),
    UPGRADE(1),
    AD_URL(2),
    COMMON_INFO(3),
    COURIER_INFO(4);


    private final int type;

    FuncType(int type)
    {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static FuncType getType(int type)
    {
        for (FuncType enums: FuncType.values())
        {
            if(enums.getType() == type)
            {
                return enums;
            }
        }
        return null;
    }
}
