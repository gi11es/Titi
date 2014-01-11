package com.massiveleap.titi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by kouiskas on 11/01/2014.
 */
public class CallReceiver extends BroadcastReceiver {
    private String incomingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        incomingNumber = b.getString("incoming_number");

        Log.v("Titi", "Incoming call..." + incomingNumber);

        if (incomingNumber != null && incomingNumber.equals("+33630924903")) {
            Log.v("Titi", "Send SMS");

            Location location = CurrentLocation.getLocation();

            SmsManager sms = SmsManager.getDefault();

            if (location != null) {
                sms.sendTextMessage("+33630924903", null, "Current location. Vitesse: " + location.getSpeed() + " Latitude: "  + location.getLatitude() + " longitude: " + location.getLongitude(), null, null);
            } else {
                sms.sendTextMessage("+33630924903", null, "Failed to get location", null, null);
            }
        }
    }
}
