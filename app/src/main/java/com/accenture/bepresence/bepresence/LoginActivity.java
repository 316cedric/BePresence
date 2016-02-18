package com.accenture.bepresence.bepresence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.iid.InstanceID;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btSave;
    EditText etInl;
    TextView tvShift, tvName;
    AttendanceDataAccess attendanceDataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etInl = (EditText) findViewById(R.id.etInl);
        tvShift = (TextView) findViewById(R.id.tvShift);
        tvName = (TextView) findViewById(R.id.tvName);

        btSave = (Button) findViewById(R.id.btSave);

        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

//        ParseUser.logInInBackground(etInl.getText().toString().trim().toUpperCase(), etPassword.getText().toString().trim(), new LogInCallback() {
//            public void done(ParseUser user, ParseException e) {
//                if (user != null) {
//                    Intent i = new Intent(getBaseContext(),BeaconFinder.class);
//                    editor.putString("inl",etInl.getText().toString().trim().toUpperCase());
//                    editor.apply();
//                    editor.commit();
//                    startActivity(i);
//                    finish();
//                } else {
//                    Toast.makeText(getBaseContext(), "Invalid INL ID or Password", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        SQLiteDatabase myDetails = openOrCreateDatabase("MyDetails", MODE_PRIVATE, null);

        attendanceDataAccess = new AttendanceDataAccess(myDetails);
        attendanceDataAccess.insertDetails(etInl.getText().toString(), tvName.getText().toString(), tvShift.getText().toString());

        Intent i = new Intent(this,DetailsActivity.class);
        startActivity(i);

    }


    private void TacallGcmFirstTime() throws IOException {
      String iid=  InstanceID.getInstance(this.getApplicationContext()).getId();
        String scope="GCM";
        String authorizedEntity="bepresence-438b7";
        String token=InstanceID.getInstance(this.getApplicationContext()).getToken(authorizedEntity,scope);

        String server="http://localhost:64152/";

        String q = "values/";
        try
        {
            URL url = new URL(server+q+token);
            HttpURLConnection conn = ( HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            Log.e("HTTPREQ",token+url);
        }
        finally {

        }
    }
}
