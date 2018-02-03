package com.viper.android.apf.activity_lifecycle_hook;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by mylhyz on 2018/2/2.
 * <p>
 * hook to proxy activity lifecycle
 */

public final class IActivityLifecycleHook {


    public static void inject() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hookActivityManagerForOreo();
        } else {
            hookActivityManager();
        }
    }

    @SuppressLint("PrivateApi")
    private static void hookActivityManager() throws Exception {
        Class<?> activity_manager_native_class = Class.forName("android.app.ActivityManagerNative");
        Field g_default_field = activity_manager_native_class.getDeclaredField("gDefault");
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

    @SuppressLint("PrivateApi")
    private static void hookActivityManagerForOreo() throws Exception {
        Class<?> activity_manager_class = ActivityManager.class;
        Field i_activity_manager_singleton_field = activity_manager_class.getDeclaredField("IActivityManagerSingleton");
        i_activity_manager_singleton_field.setAccessible(true);
        Object IActivityManagerSingleton = i_activity_manager_singleton_field.get(null);

        Class<?> singleton = Class.forName("android.util.Singleton");
        Field m_instance_field = singleton.getDeclaredField("mInstance");
        m_instance_field.setAccessible(true);

        Object rawActivityManager = m_instance_field.get(IActivityManagerSingleton);

        Class<?> IActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxyActivityManager = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IActivityManagerInterface},
                new IActivityManagerHandler(rawActivityManager));
        m_instance_field.set(IActivityManagerInterface, proxyActivityManager);
    }
}
