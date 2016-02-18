package com.accenture.bepresence.bepresence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by cedric.mendes on 26-Dec-15.
 */
public class AttendanceDataAccess implements Serializable {
    SQLiteDatabase myDetails;

    AttendanceDataAccess() {
    }

    AttendanceDataAccess(SQLiteDatabase myDetails) {
        this.myDetails = myDetails;
    }

    public boolean insertDetails(String inlid, String name, String shift) {

        myDetails.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(inl_id VARCHAR, username VARCHAR, shift VARCHAR)");
        myDetails.execSQL("INSERT INTO UserDetails VALUES('" + inlid + "','" + name + "','" + shift + "')");

        return true;
    }

    public AttendanceModel getDetails(String inlid) {
        try {
            Log.e("IN get details", "abcd");
            Cursor resultSet = myDetails.rawQuery("SELECT * FROM UserDetails where inl_id = '" + inlid + "'", null);
            resultSet.moveToFirst();
            return new AttendanceModel(inlid, resultSet.getString(1), resultSet.getString(2));
        } catch (Exception e) {
            return new AttendanceModel();
        }
    }

}
