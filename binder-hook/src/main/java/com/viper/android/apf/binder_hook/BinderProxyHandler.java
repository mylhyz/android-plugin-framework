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
 * <p>
 * hook IBinder 对象的 queryLocalInterface 方法，让其返回我们创建的代理IInterface对象
 */

public class BinderProxyHandler implements InvocationHandler {

    private IBinder mBase;
    private Class<?> mStub;
    private Class<?> mInterface;

    @SuppressLint("PrivateApi")
    public BinderProxyHandler(IBinder base) {
        mBase = base;
        try {
            this.mStub = Class.forName("android.content.IClipboard$Stub");
            this.mInterface = Class.forName("android.content.IClipboard");
        } catch (Exception e) {
            Logger.e("hook binder failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {//为查询接口添加动态代理
            Logger.i("hook [queryLocalInterface]");
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),//代理的class loader
                    new Class[]{IBinder.class, IInterface.class, this.mInterface},//代理实现的接口数组（Binder/IInterface/IClipboard）
                    new BinderHandler(mBase, mStub));//返回一个代理对象代理实现上述接口
        }
        Logger.i("invoke method [" + method.getName() + "]");
        return null;
    }
}
