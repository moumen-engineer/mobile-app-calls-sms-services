package com.example.tp1_app;

import android.Manifest;
import android.content.*;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import java.util.List;
import androidx.annotation.RequiresPermission;

public class CallReceiver extends BroadcastReceiver {

    @RequiresPermission(Manifest.permission.ANSWER_PHONE_CALLS)

    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (!TelephonyManager.EXTRA_STATE_RINGING.equals(state)) return;

        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (number == null) return;

        List<String> blacklist = FileHelper.readFile(context, "blacklist.txt");
        List<String> whitelist = FileHelper.readFile(context, "whitelist.txt");

        boolean isBlack = containsNumber(blacklist, number);
        boolean isWhite = containsNumber(whitelist, number);

        // RULES
        if (isBlack || (!whitelist.isEmpty() && !isWhite)) {

            //// Reject Call using TelecomManager API
            if (Build.VERSION.SDK_INT >= 28) {
                TelecomManager telecomManager = (TelecomManager)
                        context.getSystemService(Context.TELECOM_SERVICE);
                telecomManager.endCall();
            }
        }
    }

    private boolean containsNumber(List<String> list, String number) {
        for (String n : list) {
            if (number.equals(n) || number.endsWith(n)) return true;
        }
        return false;
    }
}