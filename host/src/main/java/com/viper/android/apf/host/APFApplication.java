package com.viper.android.apf.host;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.viper.android.apf.activity_lifecycle_hook.IActivityLifecycleHook;


/**
 * Created by mylhyz on 2018/1/21.
 * <p>
 * application for apf
 */

public final class APFApplication extends Application {

    //    private static final String TAG = APFApplication.class.getSimpleName();
    private static final String TAG_APF = "APF_DEBUG";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initLogger();

        try {
//            BinderHook.inject();
//            DynamicProxyHook.inject();
//            IAMSHook.inject();
//            IPMSHook.inject(base);
            IActivityLifecycleHook.inject();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }

        Thread.setDefaultUncaughtExceptionHandler(new UEH());
    }

    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy
                .newBuilder()
                .tag(TAG_APF)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("Application OnCreate");
    }
}
