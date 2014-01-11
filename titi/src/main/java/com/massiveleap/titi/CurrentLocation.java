package com.massiveleap.titi;

import android.location.Location;

/**
 * Created by kouiskas on 11/01/2014.
 */
public class CurrentLocation {
    private static Location myLocation;

    public static void setLocation(Location location) {
        myLocation = location;
    }

    public static Location getLocation() {
        return myLocation;
    }
}
