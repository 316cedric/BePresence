package com.accenture.bepresence.bepresence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by cedric.mendes on 26-Dec-15.
 */
public class AttendanceDataAccess {

    SQLiteDatabase myDetails = openOrCreateDatabase("my details", null, null);

    public boolean insertDetails(String inlid, String name, String shift){

        myDetails.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(inl_id VARCHAR, username VARCHAR, shift VARCHAR)");
        myDetails.execSQL("INSERT INTO UserDetails VALUES('"+inlid+"','"+name+"','"+shift+"'");

        return true;
    }

    public AttendanceModel getDetails (String inlid) {
        
    }

}
