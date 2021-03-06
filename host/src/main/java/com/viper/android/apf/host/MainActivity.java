package com.viper.android.apf.host;

import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.viper.android.apf.activity_lifecycle_hook.StubActivity;
import com.viper.android.apf.ams_pms_hook.b.BActivity;
import com.viper.android.apf.dynamic_proxy_hook.a.AActivity;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.dynamic_proxy_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hookStartActivity(AActivity.class);
            }
        });

        findViewById(R.id.binder_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hookClipboardManager();
            }
        });

        findViewById(R.id.ams_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hookActivityManager();
            }
        });

        findViewById(R.id.pms_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hookPackageManager();
            }
        });

        findViewById(R.id.activity_lifecycle_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hookActivityLifecycle();
            }
        });
    }


    private void hookStartActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    private void hookClipboardManager() {
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (manager != null && manager.hasPrimaryClip()) {
            Logger.i(manager.getPrimaryClip().toString());
        }
    }

    private void hookActivityManager() {
        startActivity(new Intent(this, BActivity.class));
    }

    private void hookPackageManager() {
        getBaseContext().getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
    }

    private void hookActivityLifecycle() {
        Intent intent = new Intent(this, StubActivity.class);
        startActivity(intent);
    }
}
