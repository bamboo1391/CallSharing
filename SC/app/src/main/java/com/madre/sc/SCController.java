package com.madre.sc;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;

import org.json.JSONObject;

/**
 * Created by bambo on 08/11/2015.
 */
public class SCController {

    private String TAG = SCController.class.getSimpleName();

    private static SCController mInstance;

    public static SCController getInstance() {
        if (mInstance == null)
            mInstance = new SCController();
        return mInstance;
    }

    public void makeRequestFacebook() {
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

        AccessToken token= AccessToken.getCurrentAccessToken();
        Log.e(TAG, "Token: " + token.toString() + "\n" + token.getToken());

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();
                Log.e(TAG, "RESULT: " + object.toString());
            }
        });
        GraphRequest request1 = new GraphRequest(token, "/me/posts?limit=1&fields=id,message,full_picture,type,source,created_time,comments.summary(true).limit(0),likes.summary(true).limit(0)",
                null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Log.e(TAG, "RESULT: " + graphResponse.toString());
            }
        });
        Log.e(TAG, request.toString());
        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,message,full_picture,type,source,created_time,comments.summary(true).limit(0),likes.summary(true).limit(0)");
//        parameters.putString("limit","50");
        parameters.putString("fields", "id,name,comments.summary(true).limit(0),likes.summary(true).limit(0)");
//        request.setParameters(parameters);
//        request.executeAsync();
        request1.setParameters(parameters);
        request1.executeAsync();

    }
}
