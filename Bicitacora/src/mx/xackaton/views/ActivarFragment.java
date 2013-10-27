package mx.xackaton.views;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.xackaton.bicitacora.DBPista;
import mx.xackaton.bicitacora.DBPunto;
import mx.xackaton.bicitacora.GetGPS;
import mx.xackaton.bicitacora.SERVER;
import mx.xackaton.bicitacora.global;

import com.example.android.effectivenavigation.R;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;


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
		int pista = 0;
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
						Cursor cursor = db.getLastRoad();
						if (cursor != null)
							pista = db.getLastRoad().getInt(0)+1;
						locationListener = new GetGPS(pista, db_punto);
			        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
					}else{
						start_stop.setImageResource(R.drawable.botonactivara);
						started = false;
						hora_fin = new Date();
						locationManager.removeUpdates(locationListener);
						db.save_road(pista, hora_inicio, hora_fin,db.longitud(pista, db_punto) , db.distance(pista, db_punto));
						Cursor cursor = db.getLastRoad();
						send_data(cursor);
						db.close();
						db_punto.close();
					}
				}
			});
        }
        
        public void send_data(Cursor cursor){
        	try{
        		final JSONObject json_pista = new JSONObject();
        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		String ini = format.format(new Date(cursor.getString(1)));
        		String fin = format.format(new Date(cursor.getString(2)));
        		System.out.println("Inicio: "+ini+" Fin: "+fin);
        		json_pista.put("hora_inicio", ini);
        		json_pista.put("hora_fin", fin);
        		JSONArray puntos = new JSONArray();
        		Cursor aux = db_punto.getPointsByRoad(cursor.getInt(0));
        		System.out.println(aux.getCount());
        		if (aux.getCount() > 0){
	        		aux.moveToFirst();
	        		JSONObject punto = new JSONObject();
	        		for (int i = 0; i < aux.getCount(); i++){
	        			if(!aux.isNull(i)){
	        				punto.put("latitud", aux.getFloat(2));
	        				punto.put("longitud", aux.getFloat(3));
	        				punto.put("altitud", aux.getFloat(4));
	        				puntos.put(punto);
	        				punto = new JSONObject();
	        				aux.moveToNext();
	        			}else{
	        				break;
	        			}
	        		}
        		}
        		json_pista.put("puntos", puntos);
        		json_pista.put("longitud", cursor.getFloat(4));
        		json_pista.put("desplazamiento", cursor.getFloat(3));
        		Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						System.out.println("Entro a la enviada al servidor");
						SERVER server = new SERVER();
						server.road(json_pista);
					}
				});
        		t.start();
        		try{
        			t.join();
        		}catch(Exception e){}
        		Toast.makeText(global.contexto, "Ya se envio al servidor", Toast.LENGTH_SHORT).show();
        	}catch(Exception e){
        		Toast.makeText(global.contexto, e.getCause().toString(), Toast.LENGTH_LONG).show();
        	}
        }
        
        
}
