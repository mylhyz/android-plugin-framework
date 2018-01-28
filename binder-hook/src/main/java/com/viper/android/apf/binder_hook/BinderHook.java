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
 * <p>
 * hook binder的机制
 */

public class BinderHook {

    @SuppressLint("PrivateApi")
    @SuppressWarnings("unchecked")
    public static void inject() throws Exception {
        //创建代理剪贴板binder对象，用以代理剪贴板服务
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method get_service_method = serviceManager.getDeclaredMethod("getService", String.class);
        IBinder rawBinder = (IBinder) get_service_method.invoke(null, Context.CLIPBOARD_SERVICE);//获取原始service-manager对象
        IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                new Class[]{IBinder.class},
                new BinderProxyHandler(rawBinder));
        //将代理对象放到缓存中，替换掉系统原始对象
        Field s_cache_field = serviceManager.getDeclaredField("sCache");
        s_cache_field.setAccessible(true);
        Map<String, IBinder> cache = (Map<String, IBinder>) s_cache_field.get(null);
        cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
    }
}
