package com.viper.android.apf.binder_hook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by mylhyz on 2018/1/25.
 * <p>
 * binder hook
 *
 * hook binder的机制
 */

public class BinderHook {

    @SuppressLint("PrivateApi")
    @SuppressWarnings("unchecked")
    public static void inject() throws Exception {
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method get_service_method = serviceManager.getDeclaredMethod("getService", String.class);
        IBinder rawBinder = (IBinder) get_service_method.invoke(null, Context.CLIPBOARD_SERVICE);
        IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                new Class[]{IBinder.class},
                new BinderProxyHandler(rawBinder));
        Field s_cache_field = serviceManager.getDeclaredField("sCache");
        s_cache_field.setAccessible(true);
        Map<String, IBinder> cache = (Map<String, IBinder>) s_cache_field.get(null);
        cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
    }
}
