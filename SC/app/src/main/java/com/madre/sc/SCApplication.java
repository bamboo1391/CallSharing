package com.madre.sc;

import android.app.Application;
import android.content.Context;

/**
 * Created by bambo on 08/11/2015.
 */
public class SCApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
