package com.viper.android.apf.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.viper.android.apf.dynamic_proxy_hook.a.AActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.dynamic_proxy_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AActivity.class);
            }
        });
    }


    private void start(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
