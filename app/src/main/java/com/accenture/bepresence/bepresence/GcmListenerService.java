package com.accenture.bepresence.bepresence;

import android.os.Bundle;

/**
 * Created by u.parthiban on 12/12/2015.
 */
public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    private static final  String TAG = "MyGCMListener";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        if(from.startsWith("/blah"))
        {}
        else
        {
            // start our service

        }


    }


}
