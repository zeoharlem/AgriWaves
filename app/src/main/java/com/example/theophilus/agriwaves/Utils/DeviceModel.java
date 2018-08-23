package com.example.theophilus.agriwaves.Utils;

import android.os.Build;

/**
 * Created by Theophilus on 8/2/2017.
 */

public class DeviceModel {
    static String model         = Build.MODEL;
    static String manufacturer  = Build.MANUFACTURER;
    static String brand         = Build.BRAND;
    static String device        = Build.DEVICE;
    static String serial        = Build.SERIAL;
    static String user          = Build.USER;

    public static String getModel() {
        return model;
    }

    public static String getManufacturer() {
        return manufacturer;
    }

    public static String getBrand() {
        return brand;
    }

    public static String getDevice() {
        return device;
    }

    public static String getSerial() {
        return serial;
    }

    public static String getUser(){
        return user;
    }
}
