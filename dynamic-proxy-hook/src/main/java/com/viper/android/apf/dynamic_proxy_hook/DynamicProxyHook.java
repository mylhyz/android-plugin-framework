package com.viper.android.apf.dynamic_proxy_hook;

import android.annotation.SuppressLint;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/21.
 * <p>
 * hook for instrumentation
 */

public class DynamicProxyHook {


    public static void inject() throws Exception {
        @SuppressLint("PrivateApi")
        Class<?> activity_thread_class = Class.forName("android.app.ActivityThread");
        Method current_activity_thread_method = activity_thread_class.getDeclaredMethod("currentActivityThread");
        current_activity_thread_method.setAccessible(true);
        Object currentActivityThread = current_activity_thread_method.invoke(null);

        Field m_instrumentation_field = activity_thread_class.getDeclaredField("mInstrumentation");
        m_instrumentation_field.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) m_instrumentation_field.get(currentActivityThread);
        Instrumentation mInstrumentationProxy = new InstrumentationProxy(mInstrumentation);
        m_instrumentation_field.set(currentActivityThread, mInstrumentationProxy);
    }
}
