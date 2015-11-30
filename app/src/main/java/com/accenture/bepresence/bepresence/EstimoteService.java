package com.accenture.bepresence.bepresence;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EstimoteService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            EstimoteManager.Create((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE), this, intent);
        } catch (Exception e) {
            Log.i("xyz", "No intent found>> " + e.getMessage());
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EstimoteManager.stop();
    }
}
