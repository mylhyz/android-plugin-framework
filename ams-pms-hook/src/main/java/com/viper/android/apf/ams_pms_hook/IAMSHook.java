package com.viper.android.apf.ams_pms_hook;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by mylhyz on 2018/1/28.
 * <p>
 * hook activity manager service
 */

public class IAMSHook {

    @SuppressLint("PrivateApi")
    public static void inject() throws Exception {
        Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
        Field g_default_field = activityManagerNative.getDeclaredField("gDefault");
        g_default_field.setAccessible(true);
        Object gDefault = g_default_field.get(null);

        Class<?> singleton = Class.forName("android.util.Singleton");
        Field m_instance_field = singleton.getDeclaredField("mInstance");
        m_instance_field.setAccessible(true);
        Object rawIActivityManager = m_instance_field.get(gDefault);

        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxyIActivityManager = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerInterface},
                new IActivityManagerHandler(rawIActivityManager));
        m_instance_field.set(gDefault, proxyIActivityManager);
    }
}
