package com.viper.android.apf.ams_pms_hook;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/28.
 * <p>
 * proxy for IActivityManager
 */

public class IActivityManagerHandler implements InvocationHandler {

    private Object mBase;

    public IActivityManagerHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.i("hook IActivityManager [" + method.getName() + "]");
        return method.invoke(mBase, args);
    }
}
