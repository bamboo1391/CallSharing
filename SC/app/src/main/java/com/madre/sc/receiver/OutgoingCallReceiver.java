package com.madre.sc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.madre.sc.service.PopService;

/**
 * Created by bambo on 26/10/2015.
 */
public class OutgoingCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//        Toast.makeText(context, "Outgoing Call: " + phoneNumber, Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, PopService.class));

    }
}
