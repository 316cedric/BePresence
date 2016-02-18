package com.accenture.bepresence.bepresence;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    TextView tvInlid, tvName, tvShift, tvStatus;
    AttendanceDataAccess attendanceDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvInlid = (TextView) findViewById(R.id.tvInlid);
        tvName = (TextView) findViewById(R.id.tvName);
        tvShift = (TextView) findViewById(R.id.tvShift);
        tvStatus = (TextView) findViewById(R.id.tvStatus);


        SQLiteDatabase myDetails = openOrCreateDatabase("MyDetails", MODE_PRIVATE, null);
//        SQLiteDatabase myDetails = SQLiteDatabase.openDatabase("MyDetails", null, 0);

        attendanceDataAccess = new AttendanceDataAccess(myDetails);
       // attendanceDataAccess = (AttendanceDataAccess)getIntent().getSerializableExtra("MyTable");
       // Log.e("after", attendanceDataAccess.toString());

        AttendanceModel attendanceModel = attendanceDataAccess.getDetails("INL111");

        tvInlid.setText(attendanceModel.inlid);
        tvName.setText(attendanceModel.name);
        tvShift.setText(attendanceModel.shift);
        tvStatus.setText("Present");

        Calendar calendar = Calendar.getInstance();
        // 8 AM
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                new Intent(this, EstimoteService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        //startService(new Intent(getBaseContext(), EstimoteService.class));
    }
}
