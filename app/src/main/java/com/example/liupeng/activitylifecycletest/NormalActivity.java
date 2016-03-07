package com.example.liupeng.activitylifecycletest;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by liupeng on 16/3/3.
 */
public class NormalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
    }
}
