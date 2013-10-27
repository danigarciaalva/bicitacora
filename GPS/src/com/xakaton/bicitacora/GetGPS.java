/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xakaton.bicitacora;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Date;

/**
 *
 * @author xianur0
 */
public class GetGPS implements LocationListener {

    DBPunto db;
    private static int num_point = 0;
    private static int pista = 0;
    public GetGPS(Context context){
        db = new DBPunto(context, "Punto", null , 1);
        db.init();
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
        num_point++;
        db.savePoint(pista, num_point, loc.getLatitude(), loc.getLongitude(), new Date());   
    }
    
    public void toFinish(){
        db.close();
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