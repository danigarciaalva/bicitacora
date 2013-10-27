/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.xackaton.bicitacora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

/**
 *
 * @author xianur0
 */
public class DBPista extends SQLiteOpenHelper{
    String create = "CREATE TABLE Pista (id INTEGER PRIMARY KEY AUTOINCREMENT, hora_inicio TEXT, hora_fin TEXT, desplazamiento REAL, longitud REAL)";
    
    SQLiteDatabase db;
   

    public DBPista(Context contexto, String tabla, SQLiteDatabase.CursorFactory cursor, int version){
        super(contexto, tabla, cursor, version);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqld) {
        sqld.execSQL(create);
    }

    public void init(){
        this.db= this.getWritableDatabase();
    }
    
    public void save_road(int pista, Date hora_inicio, Date hora_fin, double desplazamiento, double longitud){
        if(db != null){
        	try{
	            db.execSQL("INSERT INTO Pista(hora_inicio, hora_fin, desplazamiento, longitud) VALUES ('"+hora_inicio.toString()+
	            	"','"+hora_fin.toString()+"',"+desplazamiento+","+longitud+");");
	            Toast.makeText(global.contexto, "Se guardo la pista: "+pista, Toast.LENGTH_SHORT).show();
        	}catch(Exception e){
        		Toast.makeText(global.contexto, e.getCause().toString(), Toast.LENGTH_SHORT).show();
        	}
        }
        
    }
    
    public Cursor getLastRoad(){
    	Cursor cursor =  db.rawQuery("SELECT * FROM Pista WHERE id = (SELECT MAX(id) FROM Pista)", null);
    	if (cursor.getCount() > 0){
    		cursor.moveToLast();
    		return cursor;
    	}
    	return null;
    }
    
    public double distance(int pista, DBPunto db_punto){
    	Cursor cursor = db_punto.getPointsByRoad(pista);
    	double distance = 0.0;
    	if ( cursor.getCount() > 0){
    		cursor.moveToFirst();
    		double lat = cursor.getDouble(2);
    		double lon = cursor.getDouble(3);
    		for(int i = 1; i < cursor.getCount(); i++){
    			if(!cursor.isNull(i)){
    				cursor.moveToPosition(i);
    				double lat_aux = cursor.getDouble(2);
    				double lon_aux = cursor.getDouble(3);
    				double R = 6371;
    				distance += Math.acos(Math.sin(lat) * Math.sin(lat_aux) +
    									Math.cos(lat) * Math.cos(lat_aux) * 
    									Math.cos(lon_aux - lon)) * R;
    				lat = lat_aux;
    				lon = lon_aux;
    			}
    		}
    	}
    	return distance;
    }
    
    public double longitud(int pista, DBPunto db_punto){
    	Cursor cursor = db_punto.getPointsByRoad(pista);
    	double longitud = 0.0;
    	Toast.makeText(global.contexto, "Cantidad de puntos: "+cursor.getCount(), Toast.LENGTH_SHORT).show();
    	if ( cursor.getCount() > 1){
    		cursor.moveToFirst();
    		double lat = cursor.getDouble(2);
    		double lon = cursor.getDouble(3);
    		cursor.moveToLast();
    		double lat_aux = cursor.getDouble(2);
    		double lon_aux = cursor.getDouble(3);
    		double R = 6371;
    		longitud = Math.acos(Math.sin(lat) * Math.sin(lat_aux) +
    							Math.cos(lat) * Math.cos(lat_aux) * 
    							Math.cos(lon_aux - lon)) * R;
    	}
    	return longitud;
    }
    public void close(){
        if(db != null)
            db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqld, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Pista");
        db.execSQL(create);
    }
    
}
