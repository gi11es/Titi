package com.massiveleap.titi;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by kouiskas on 11/01/2014.
 */
public class MyService extends Service {
    @Override
    public void onCreate() {
        Log.v("Titi", "Start service");

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new LocationListener() {
            private long moving = 0;

            public void onLocationChanged(Location location) {
                if (location == null) {
                    return;
                }

                // Called when a new location is found by the network location provider.
                Log.v("Titi", "Speed: " + location.getSpeed());
                CurrentLocation.setLocation(location);
                SmsManager sms = SmsManager.getDefault();

                long unixTime = System.currentTimeMillis() / 1000L;

                if (location.getSpeed() > 5.0) {
                    if (moving == 0) {
                        sms.sendTextMessage("+33630924903", null, "Titi bouge. Vitesse: " + location.getSpeed() + " Latitude: "  + location.getLatitude() + " longitude: " + location.getLongitude(), null, null);
                    }
                    moving = unixTime;
                } else if (moving > 0 && (unixTime - moving > 5 * 60)) {
                    moving = 0;
                    sms.sendTextMessage("+33630924903", null, "Titi stoppe. Latitude: "  + location.getLatitude() + " longitude: " + location.getLongitude(), null, null);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        new Thread(new Runnable(){
            public void run() {
                while (true) {
                    locationListener.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.e("Titi", "Exception: " + e);
                    }
                }

            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

}
