package com.viper.android.apf.dynamic_proxy_hook.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by mylhyz on 2018/1/21.
 * <p>
 * simple activity
 */

public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Button(this));
    }
}
