/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xakaton.bicitacora;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author xianur0
 */
public class getgps implements LocationListener {

    public getgps(){
        
    }
    
    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(
                global.contexto,
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        System.out.println(longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        System.out.println(latitude);
        /*-------to get City-Name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(global.contexto, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
            }
            cityName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
        System.out.println(s);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String string, int i, Bundle bundle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}