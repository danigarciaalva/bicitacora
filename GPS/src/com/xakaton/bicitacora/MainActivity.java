package com.xakaton.bicitacora;

import android.app.Activity;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        global.contexto = this.getApplicationContext();
        setContentView(R.layout.main);
        LocationManager locationManager = (LocationManager) getSystemService(global.contexto.LOCATION_SERVICE);
        LocationListener locationListener = new getgps(this);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}
