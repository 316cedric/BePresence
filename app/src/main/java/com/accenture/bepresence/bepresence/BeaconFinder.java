package com.accenture.bepresence.bepresence;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.parse.Parse;
import com.parse.ParseObject;

public class BeaconFinder extends Activity {

    TextView hello;
    String userid;
    private BeaconManager beaconManager;
    ParseObject testObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(!isBluetoothAvailable()) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            BluetoothAdapter.getDefaultAdapter().enable();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enable Bluetooth?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        userid = sharedPreferences.getString("inl","");

        Toast.makeText(this,userid,Toast.LENGTH_LONG).show();

        testObject = new ParseObject("Presence");

        beaconManager = new BeaconManager(getApplicationContext());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("helloAdd", "insode");
        String id = intent.getStringExtra("intentid");
        String title = "", message = "";
        switch (id) {
            case "onInsideRange":
                title = intent.getStringExtra("title");
                message = intent.getStringExtra("message");
                hello = (TextView) findViewById(R.id.helloWorld);
                hello.setTextColor(Color.RED);
                hello.setTextSize(22);
                hello.setText(title + "\n" + message);
                testObject.put("User", userid);
                testObject.put("Region", title);
                testObject.put("UUID", message);
                testObject.saveInBackground();
                break;

            case "onExitRange":
                title = intent.getStringExtra("title");
                message = intent.getStringExtra("message");
                hello = (TextView) findViewById(R.id.helloWorld);
                hello.setTextColor(Color.BLUE);
                hello.setTextSize(22);
                hello.setText(title + "\n" + message);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent notifyintent) {
        String title = notifyintent.getStringExtra("title");
        String message = notifyintent.getStringExtra("message");
        hello.setTextColor(Color.RED);
        hello.setTextSize(22);
        hello.setText(title + "\n" + message);
    }

    public static boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
