/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.xackaton.bicitacora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

/**
 *
 * @author xianur0
 */
public class DBPunto extends SQLiteOpenHelper{
    String create = "CREATE TABLE Punto (id INTEGER PRIMARY KEY AUTOINCREMENT, id_pista INTEGER, num INTEGER,latitud REAL, longitud REAL, fecha TEXT)";
    
    SQLiteDatabase db;
   

    public DBPunto(Context contexto, String tabla, SQLiteDatabase.CursorFactory cursor, int version){
        super(contexto, tabla, cursor, version);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqld) {
        sqld.execSQL(create);
    }

    public void init(){
        this.db= this.getWritableDatabase();
    }
    
    public void savePoint(int id_pista, int num_point, double latitude, double longitude, Date date){
        if(db != null){
        	try{
	            db.execSQL("INSERT INTO Punto(id_pista, num, latitud, longitud, fecha) VALUES ("+id_pista+","+num_point+","+latitude+","+longitude+",'"+date+"');");
	            Toast.makeText(global.contexto, "Se guardo la coordenada: Lat: "+latitude+" Lon: "+longitude, Toast.LENGTH_SHORT).show();
        	}catch(Exception e){
        		Toast.makeText(global.contexto, e.getCause().toString(), Toast.LENGTH_SHORT).show();
        	}
        }
        
    }
    
    public void close(){
        if(db != null)
            db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqld, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Punto");
        db.execSQL(create);
    }
    
}
