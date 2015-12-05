package com.accenture.bepresence.bepresence;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.internal.utils.L;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EstimoteService extends Service {

    private BeaconManager beaconManager;
    private String user;

    private static final Region[] BEACONS = new Region[] {
            new Region("Purple ", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 64235, 19344),
            new Region("Green ", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 6459, 51635)
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Beacons monitoring service starting", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        user = sharedPreferences.getString("inl", "");

        BluetoothAdapter.getDefaultAdapter().enable();

        startMonitoring();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Beacons monitoring service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        //this.onCreate();
    }

    private void startMonitoring() {
        if (beaconManager == null) {
            beaconManager = new BeaconManager(this);

            // Configure verbose debug logging.
            L.enableDebugLogging(true);

            /**
             * Scanning
             */

            Toast.makeText(this, "in Start Monitoring", Toast.LENGTH_SHORT).show();
            beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 1);

            beaconManager.setRangingListener(new BeaconManager.RangingListener() {

                @Override
                public void onBeaconsDiscovered(Region paramRegion, List<Beacon> paramList) {
                    if (paramList != null && !paramList.isEmpty()) {
                        Beacon beacon = paramList.get(0);
                        Utils.Proximity proximity = Utils.computeProximity(beacon);
                        if (proximity == Utils.Proximity.IMMEDIATE) {
                            Log.d("TAG", "entered in region " + paramRegion.getProximityUUID());
                            Toast.makeText(getBaseContext(),"entered in region " + paramRegion.getIdentifier(),Toast.LENGTH_LONG).show();
                            //postNotification(paramRegion);
                        } else if (proximity == Utils.Proximity.FAR) {
                            Log.d("TAG", "exiting in region " + paramRegion.getProximityUUID());
                            Toast.makeText(getBaseContext(),"exiting in region " + paramRegion.getIdentifier(),Toast.LENGTH_LONG).show();
                            //removeNotification(paramRegion);
                        }
                        //Toast.makeText(getBaseContext(), "In Region : "+beacon.getName(),Toast.LENGTH_LONG);
                    }
                }

            });

            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    try {
                        Log.d("TAG", "connected");
                        for (Region region : BEACONS) {
                            beaconManager.startRanging(region);
                        }
                    } catch (Exception e) {
                        Log.d("TAG", "Error while starting monitoring");
                    }
                }
            });
        }
    }

}
