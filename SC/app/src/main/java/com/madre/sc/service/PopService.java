package com.madre.sc.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.madre.sc.R;
import com.madre.sc.common.Constants;
import com.madre.sc.view.activity.PopupAvatarActivity;
import com.madre.sc.view.fragment.MapsFragment;

import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

public class PopService extends Service implements FloatingViewListener {
    public PopService() {
    }

    private static final int NOTIFICATION_ID = 9083150;

    private IBinder mPopServiceBinder;

    private FloatingViewManager mFloatingViewManager;
    private boolean isShowPop = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mPopServiceBinder = new PopServiceBinder(this);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final CircleImageView iconView = (CircleImageView) inflater.inflate(R.layout.widget_chathead, null, false);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPop) {
                    sendBroadcast(new Intent(Constants.INTENT_ACTION_CLOSE_ACTIVITY));
                } else {
                    Intent dialogIntent = new Intent(PopService.this, PopupAvatarActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
            }
        });

        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        options.shape = FloatingViewManager.SHAPE_CIRCLE;
        options.overMargin = (int) (10 * metrics.density);
        mFloatingViewManager.addViewToWindow(iconView, options);

        startForeground(NOTIFICATION_ID, createNotification());

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();
        sendBroadcast(new Intent(Constants.INTENT_ACTION_CLOSE_ACTIVITY));
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mPopServiceBinder;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
    }

    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }

    private Notification createNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Title");
        builder.setContentText("Message");
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);

        // PendingIntent作成
        final Intent notifyIntent = new Intent(this, MapsFragment.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);

        return builder.build();
    }

    public static class PopServiceBinder extends Binder {

        private final WeakReference<PopService> mService;

        PopServiceBinder(PopService service) {
            mService = new WeakReference<>(service);
        }

        public PopService getService() {
            return mService.get();
        }
    }

}
