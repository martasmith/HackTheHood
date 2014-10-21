package com.codepath.hackthehood.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.codepath.hackthehood.activities.PitchDeckActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by ravi on 10/20/14.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.i("INFO", intent.toString());
        Intent intentForPitchDeckActivity = new Intent(context, PitchDeckActivity.class);
        intentForPitchDeckActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentForPitchDeckActivity);
    }
}
