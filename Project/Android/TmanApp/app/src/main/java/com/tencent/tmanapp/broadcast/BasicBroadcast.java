package com.tencent.tmanapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tmanapp.util.Config;

public class BasicBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Log.d(Config.tag, "get msg = " + msg);
        Toast.makeText(context, "get msg = " + msg, Toast.LENGTH_SHORT).show();
    }
}
