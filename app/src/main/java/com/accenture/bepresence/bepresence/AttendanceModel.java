package com.accenture.bepresence.bepresence;

import java.io.Serializable;

/**
 * Created by cedric.mendes on 26-Dec-15.
 */
public class AttendanceModel implements Serializable{

    String inlid, name, shift;

    AttendanceModel(){
        inlid = "";
        name = "";
        shift = "";
    }

    AttendanceModel (String inlid, String name, String shift){
        this.inlid = inlid;
        this.name = name;
        this.shift = shift;
    }
}
