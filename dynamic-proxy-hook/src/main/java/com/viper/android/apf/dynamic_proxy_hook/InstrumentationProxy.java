package com.viper.android.apf.dynamic_proxy_hook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/21.
 * <p>
 * proxy for instrumentation
 * <p>
 * 代理接口，这个使用的是继承的方法然后替换其实现
 */

public class InstrumentationProxy extends Instrumentation {
//    private final static String TAG = InstrumentationProxy.class.getSimpleName();

    private Instrumentation mBase;

    public InstrumentationProxy(Instrumentation base) {
        mBase = base;
    }

    @SuppressWarnings("unused")
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle options) {
        Logger.i("InstrumentationProxy$execStartActivity");
        Logger.i(""
                + who + '\n'
                + contextThread + '\n'
                + token + '\n'
                + target + '\n'
                + intent + '\n'
                + requestCode + '\n'
                + options + '\n'
        );
        try {
            //由于execStartActivity是隐藏API，所以无法被重载/直接调用，因此使用反射调用
            @SuppressLint("PrivateApi")
            Method exec_start_activity_method = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            exec_start_activity_method.setAccessible(true);
            return (ActivityResult) exec_start_activity_method.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            Logger.e(e.getMessage());
            throw new RuntimeException("can not execute execStartActivity");
        }
    }
}
