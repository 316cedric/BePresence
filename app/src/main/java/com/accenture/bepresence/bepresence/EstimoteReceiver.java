package com.accenture.bepresence.bepresence;

/**
 * Created by cedric.mendes on 28-Nov-15.
 */

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.connection.internal.EstimoteService;

public class EstimoteReceiver extends BroadcastReceiver{
    private Intent estimoteServiceIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i("xyz123", "bluetooth is off");
                    if(estimoteServiceIntent != null) {
                        context.stopService(estimoteServiceIntent);
                        estimoteServiceIntent = null;
                    }
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.i("xyz123", "bluetooth is on");
                    if(estimoteServiceIntent == null) {
                        estimoteServiceIntent = new Intent(context, EstimoteService.class);
                        context.startService(estimoteServiceIntent);
                    }
                    break;
            }
        }
    }
}
