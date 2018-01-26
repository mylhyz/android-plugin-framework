package com.viper.android.apf.binder_hook;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.IInterface;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mylhyz on 2018/1/25.
 * <p>
 * binder proxy handler
 */

public class BinderProxyHandler implements InvocationHandler {

    private IBinder mBase;
    private Class<?> mStub;
    private Class<?> mInrerface;

    @SuppressLint("PrivateApi")
    public BinderProxyHandler(IBinder base) {
        mBase = base;
        try {
            this.mStub = Class.forName("android.content.IClipboard$Stub");
            this.mInrerface = Class.forName("android.content.IClipboard");
        } catch (Exception e) {
            Logger.e("hook binder failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Logger.i("hook [queryLocalInterface]");
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[]{IBinder.class, IInterface.class, this.mInrerface},
                    new BinderHandler(mBase, mStub));
        }
        Logger.i("invoke method [" + method.getName() + "]");
        return null;
    }
}
