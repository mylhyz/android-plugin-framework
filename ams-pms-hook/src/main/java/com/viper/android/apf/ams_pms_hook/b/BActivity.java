package com.viper.android.apf.ams_pms_hook.b;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mylhyz on 2018/1/30.
 * <p>
 * simple activity
 */

public class BActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setText(getTitle());
        setContentView(view);
    }
}
