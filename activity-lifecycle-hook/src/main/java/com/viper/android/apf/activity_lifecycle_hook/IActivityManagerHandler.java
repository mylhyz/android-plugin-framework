package com.viper.android.apf.activity_lifecycle_hook;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mylhyz on 2018/2/3.
 * <p>
 * proxy object handler
 */

public class IActivityManagerHandler implements InvocationHandler {

    private Object mBase;

    public IActivityManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            return hookStartActivity(method, args);
        }
        return method.invoke(mBase, args);
    }

    private Object hookStartActivity(Method method, Object[] args) throws Exception {
        Logger.i("hookStartActivity args size is: %d", args.length);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) {
                builder.append(i).append("->").append("null").append('\n');
            } else {
                builder.append(i).append("->").append(args[i].toString()).append('\n');
            }
        }
        Logger.i("args=:\n%s", builder.toString());
        return method.invoke(mBase, args);
    }
}
