package com.viper.android.apf.ams_pms_hook;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/1/31.
 * <p>
 * IPackageManager interface
 */

public class IPackageManagerHandler implements InvocationHandler {

    Object mBase;

    public IPackageManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.i("hook IPackageManager [" + method.getName() + "]");
        return method.invoke(mBase,args);
    }
}
