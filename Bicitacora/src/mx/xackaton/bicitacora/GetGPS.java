/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.xackaton.bicitacora;

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
    private int pista = 0;
    public GetGPS(int pista, DBPunto db_punto){
    	this.pista = pista;
        this.db = db_punto;
    }
    
    @Override
    public void onLocationChanged(Location loc) {
    	double lat = loc.getLatitude();
    	double lon = loc.getLongitude();
        String longitude = "Longitude: " + lon;
        System.out.println(longitude);
        String latitude = "Latitude: " + lat;
        System.out.println(latitude);
        num_point++;
        //db.savePoint(pista, num_point, loc.getLatitude(), loc.getLongitude(), new Date());
        System.out.println("Pista: "+pista+" Num punto: "+num_point+" Latitud: "+lat+" Longitud: "+lon);
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