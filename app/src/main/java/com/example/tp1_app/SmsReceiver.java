package com.example.tp1_app;

import android.content.*;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import java.util.List;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SmsMessage[] messages =
                Telephony.Sms.Intents.getMessagesFromIntent(intent);

        if (messages == null || messages.length == 0) return;

        String sender = messages[0].getDisplayOriginatingAddress();

        List<String> blacklist = FileHelper.readFile(context, "blacklist.txt");
        List<String> whitelist = FileHelper.readFile(context, "whitelist.txt");

        boolean isBlack = containsNumber(blacklist, sender);
        boolean isWhite = containsNumber(whitelist, sender);

        if (isBlack || (!whitelist.isEmpty() && !isWhite)) {

            // Alert
            context.startService(new Intent(context, MyService.class));

            abortBroadcast();
        }
    }

    private boolean containsNumber(List<String> list, String number) {
        for (String n : list) {
            if (number.equals(n) || number.endsWith(n)) return true;
        }
        return false;
    }
}