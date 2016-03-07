package com.example.liupeng.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liupeng on 16/3/5.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootCompleteReceiver", "BootCompleteReceiver1111111");
        Toast.makeText(context, "Boot Completeddddddddd", Toast.LENGTH_LONG).show();
    }
}
