package mx.xackaton.views;
import java.util.Date;

import mx.xackaton.bicitacora.DBPista;
import mx.xackaton.bicitacora.DBPunto;
import mx.xackaton.bicitacora.GetGPS;
import mx.xackaton.bicitacora.global;

import com.example.android.effectivenavigation.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 *
 * @author master
 */
public class ActivarFragment extends Fragment{
	
		ImageButton start_stop, pause;
		boolean started = false;
		Date hora_inicio;
		Date hora_fin;
		LocationManager locationManager;
		LocationListener locationListener;
		DBPunto db_punto;
		DBPista db;
		int pista;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.bike_fragment, container, false);

            init(rootView);
            return rootView;
        }
        
        private void init(View v){
        	locationManager = (LocationManager)global.contexto.getSystemService(global.contexto.LOCATION_SERVICE);
        	
        	start_stop = (ImageButton)v.findViewById(R.id.start_stop);
        	pause = (ImageButton)v.findViewById(R.id.pause);
        	start_stop.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(!started){
						start_stop.setImageResource(R.drawable.botondetener);
						started = true;
						hora_inicio = new Date();
						db = new DBPista(global.contexto, "Pista", null, 1);
						db.init();
						db_punto = new DBPunto(global.contexto, "Punto", null, 1);
						db_punto.init();
						pista = db.getLastRoad()+1;
						locationListener = new GetGPS(pista, db_punto);
			        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
					}else{
						start_stop.setImageResource(R.drawable.botonactivara);
						started = false;
						hora_fin = new Date();
						locationManager.removeUpdates(locationListener);
						db.save_road(pista, hora_inicio, hora_fin, 0, 0);
						db.close();
						db_punto.close();
					}
				}
			});
        }
}
