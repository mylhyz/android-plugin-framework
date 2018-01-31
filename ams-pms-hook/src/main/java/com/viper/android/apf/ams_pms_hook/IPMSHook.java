package com.viper.android.apf.ams_pms_hook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mylhyz on 2018/1/31.
 * <p>
 * hook package manager service
 */

public final class IPMSHook {

    @SuppressLint("PrivateApi")
    public static void inject(Context context) throws Exception {
        //get currentActivityThread
        Class<?> activity_thread_class = Class.forName("android.app.ActivityThread");
        Method current_activity_thread_method = activity_thread_class.getDeclaredMethod("currentActivityThread");
        current_activity_thread_method.setAccessible(true);
        Object currentActivityThread = current_activity_thread_method.invoke(null);
        //get sPackageManager instance
        Field s_package_manager_field = activity_thread_class.getDeclaredField("sPackageManager");
        s_package_manager_field.setAccessible(true);
        Object sPackageManager = s_package_manager_field.get(currentActivityThread);
        //create proxy package manager
        Class<?> i_package_manager_interface = Class.forName("android.content.pm.IPackageManager");
        Object proxy = Proxy.newProxyInstance(i_package_manager_interface.getClassLoader(),
                new Class<?>[]{i_package_manager_interface},
                new IPackageManagerHandler(sPackageManager));
        //set proxy to host
        s_package_manager_field.set(currentActivityThread, proxy);

        //proxy another caller
        PackageManager pm = context.getPackageManager();
        Field m_pm_field = pm.getClass().getDeclaredField("mPM");
        m_pm_field.setAccessible(true);
        m_pm_field.set(pm, proxy);
    }
}
