package com.accenture.bepresence.bepresence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btLogin;
    EditText etInl, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etInl = (EditText) findViewById(R.id.etInl);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);

        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Log.e("in", "click");
        ParseUser.logInInBackground(etInl.getText().toString().trim().toUpperCase(), etPassword.getText().toString().trim(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent i = new Intent(getBaseContext(),BeaconFinder.class);
                    editor.putString("inl",etInl.getText().toString().trim().toUpperCase());
                    editor.apply();
                    editor.commit();
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid INL ID or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
