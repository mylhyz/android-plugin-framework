package com.viper.android.apf.binder_hook;

import android.content.ClipData;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/25.
 * <p>
 * 实现 IClipboard 接口的代理对象
 */

public class ClipboardManagerProxyHandler implements InvocationHandler {

    private Object mBase;

    public ClipboardManagerProxyHandler(IBinder base, Class<?> subClazz) {
        try {
            Method as_interface_method = subClazz.getDeclaredMethod("asInterface", IBinder.class);
            this.mBase = as_interface_method.invoke(null, base);//先获取原始对象
        } catch (Exception e) {
            Logger.e("binder hook failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())) {//hook IClipboard.getPrimaryClip
            Logger.i("hook [getPrimaryClip]");
            return ClipData.newPlainText(null, "hooked text");
        }

        if ("hasPrimaryClip".equals(method.getName())) {//hook IClipboard.hasPrimaryClip
            Logger.i("hook [hasPrimaryClip]");
            return true;
        }
        return method.invoke(mBase, args);//对其他方法按照传递给原始对象处理
    }
}
