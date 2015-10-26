package com.madre.sc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.madre.sc.service.PopService;

/**
 * Created by bambo on 26/10/2015.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

    private String TAG = IncomingCallReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        PhoneStateListener phoneListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(TAG, "IDLE");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.d(TAG, "OFFHOOK");
//                        Toast.makeText(context, "Incoming Call: " + incomingNumber, Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(TAG, "RINGING");
//                        Toast.makeText(context, "Incoming Call: " + incomingNumber, Toast.LENGTH_SHORT).show();
                        context.startService(new Intent(context, PopService.class));
                        break;
                }
            }
        };
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }
}
