package com.viper.android.apf.host;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.viper.android.apf.binder_hook.BinderHook;
import com.viper.android.apf.dynamic_proxy_hook.DynamicProxyHook;


/**
 * Created by mylhyz on 2018/1/21.
 * <p>
 * application for apf
 */

public class APFApplication extends Application {

//    private static final String TAG = APFApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            BinderHook.inject();
            DynamicProxyHook.inject();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }

        Thread.setDefaultUncaughtExceptionHandler(new UEH());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
