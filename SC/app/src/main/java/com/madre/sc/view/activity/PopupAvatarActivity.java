package com.madre.sc.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.madre.sc.R;
import com.madre.sc.common.Constants;
import com.madre.sc.view.fragment.AvatarFragment;
import com.madre.sc.view.fragment.MapsFragment;

/**
 * Created by TamND on 27/10/2015.
 */
public class PopupAvatarActivity extends FragmentActivity {

    private Button btnShare;
    private ImageView btnClose;
    private RelativeLayout layoutContainer;
    private BroadcastReceiver mReceiver;

    AvatarFragment avatarFragment;
    Fragment mapFragment;

    private boolean isShowMap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_avatar);

        initUI();
        initData();
        initControl();
        initReceiver();
    }

    private void initUI() {
        btnShare = (Button) findViewById(R.id.btnShareLocation);
        btnClose = (ImageView) findViewById(R.id.btnClose);
    }

    private void initData() {
        btnShare.setText("Share location");
        avatarFragment = new AvatarFragment();
        mapFragment = new MapsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutContainer, avatarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initControl() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowMap) {
                    isShowMap = false;
                    btnShare.setText("Close map");

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.layoutContainer, avatarFragment);
                    transaction.hide(mapFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    isShowMap = true;
                    btnShare.setText("Share location");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.layoutContainer, mapFragment);
                    transaction.hide(avatarFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//                homeIntent.addCategory(Intent.CATEGORY_HOME);
//                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(homeIntent);
                System.exit(0);
            }
        });
    }

    private void initReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.INTENT_ACTION_CLOSE_ACTIVITY)) {
                    finish();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.INTENT_ACTION_CLOSE_ACTIVITY);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
