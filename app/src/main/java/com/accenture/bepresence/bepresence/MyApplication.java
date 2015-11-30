package com.accenture.bepresence.bepresence;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import java.util.List;
import java.util.UUID;

/**
 * Created by u.parthiban on 11/28/2015.
 */
public class MyApplication extends Application {

    private  MyApplication singleton;
    private BeaconManager beaconManager;

    public   MyApplication getSingleton()
    {

        return  singleton;
    }


    @Override
    public  void  onCreate(){

        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
singleton = this;
          beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
              @Override
          public  void  onServiceReady() {
                  beaconManager.startMonitoring(new Region("mybeacon", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 6459, 51635));

              }
          });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener(){
            @Override
        public  void onEnteredRegion(Region region,List<Beacon> list)
            {
                Log.e("BeaconRegion",region.getIdentifier() + "hello");
                String title="" ;
                String message="";
                for (Beacon beacon: list ) {
                    title =title + region.getIdentifier();
                    message = beacon.getProximityUUID() + "major" + beacon.getMajor() + "minor" + beacon.getMinor()+message;
                }
                Log.e("BeaconMessage",message+"");
                sendMessage(title,message);
            }

            @Override
            public void onExitedRegion(Region region)
            {
                Log.e("exit",region.getIdentifier() + "exited)");

                sendExitMessage(region.getIdentifier() , "region exited");
            }
        });
    }


    public  void sendMessage(String title,String message) {

        Log.e("message", message + "");

        Intent notifyIntent = new Intent(getApplicationContext(),BeaconFinder.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP  );
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra("intentid","onInsideRange");
        notifyIntent.putExtra("title", title);
        notifyIntent.putExtra("message", message);
        notifyIntent.setAction(Intent.ACTION_SEND);
        startActivity(notifyIntent);
    }

    public  void sendExitMessage(String title,String message) {
        Log.e("message", message + "");
        Intent notifyIntent = new Intent(getApplicationContext(),BeaconFinder.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP  );
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra("intentid", "onExitRange");
        notifyIntent.putExtra("title", title);
        notifyIntent.putExtra("message", message);
        notifyIntent.setAction(Intent.ACTION_SEND);
        startActivity(notifyIntent);
        PendingIntent intent = PendingIntent.getActivities(this,0,new Intent[]{notifyIntent},PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
