package com.xakaton.bicitacora;

import android.app.Activity;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.xakaton.bicitacora.R;
public class MainActivity extends Activity
{
    LocationManager locationManager;
    LocationListener locationListener;
    Button start_stop, pause;
    boolean started = false;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
/*        
        start_stop = (Button)findViewById(R.id.start_stop);
        locationManager = (LocationManager) getSystemService(global.contexto.LOCATION_SERVICE);
        locationListener = new GetGPS(this);
        start_stop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(!started){
                    started = true;
                    start_stop.setText("Fin");
                    locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                }else{
                    started = false;
                    start_stop.setText("Inicio");
                    locationManager.removeUpdates(locationListener);
                }
            }
        });
        global.contexto = this.getApplicationContext();*/
        
    }
}
