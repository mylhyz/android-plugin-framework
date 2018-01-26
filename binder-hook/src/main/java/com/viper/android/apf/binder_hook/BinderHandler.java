package com.viper.android.apf.binder_hook;

import android.content.ClipData;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/25.
 * <p>
 * proxy handler for binder service
 */

public class BinderHandler implements InvocationHandler {

    private Object mBase;

    public BinderHandler(IBinder base, Class<?> subClazz) {
        try {
            Method as_interface_method = subClazz.getDeclaredMethod("asInterface", IBinder.class);
            this.mBase = as_interface_method.invoke(null, base);
        } catch (Exception e) {
            Logger.e("binder hook failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())) {
            Logger.i("hook [getPrimaryClip]");
            return ClipData.newPlainText(null, "hooked text");
        }

        if ("hasPrimaryClip".equals(method.getName())) {
            Logger.i("hook [hasPrimaryClip]");
            return true;
        }
        return method.invoke(mBase, args);
    }
}
