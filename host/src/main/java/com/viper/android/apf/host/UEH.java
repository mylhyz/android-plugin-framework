package com.viper.android.apf.host;

import com.orhanobut.logger.Logger;

/**
 * Created by mylhyz on 2018/1/28.
 * <p>
 * custom ueh
 */

public final class UEH implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    UEH() {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.e(e, "tid=" + t.getId());
        mUncaughtExceptionHandler.uncaughtException(t, e);
    }
}
