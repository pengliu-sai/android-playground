package com.example.liupeng.broadcastbestpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by liupeng on 16/3/5.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
