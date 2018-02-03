package com.viper.android.apf.activity_lifecycle_hook;

import android.content.ComponentName;
import android.content.Intent;

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
        Intent raw;
        int index = 0;
        for (int i = 0; i < args.length; ++i) {
            if (args[i] instanceof Intent) {
                index = i;
                break;
            }
        }
        raw = (Intent) args[index];

        Intent newIntent = new Intent();
        final String pkg = "com.viper.android.apf.activity_lifecycle_hook";
        newIntent.setComponent(new ComponentName(pkg, StubActivity.class.getCanonicalName()));
        newIntent.putExtra("target_intent", raw);

        args[index] = newIntent;
        Logger.i("args[%d] %s", index, args[index]);

        return method.invoke(mBase, args);
    }
}
