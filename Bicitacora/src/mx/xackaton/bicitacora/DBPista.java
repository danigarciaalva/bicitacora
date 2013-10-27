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
	            	"','"+hora_fin+"',"+desplazamiento+","+longitud+");");
	            Toast.makeText(global.contexto, "Se guardo la pista: "+pista, Toast.LENGTH_SHORT).show();
        	}catch(Exception e){
        		Toast.makeText(global.contexto, e.getCause().toString(), Toast.LENGTH_SHORT).show();
        	}
        }
        
    }
    
    public int getLastRoad(){
    	Cursor cursor =  db.rawQuery("SELECT * FROM Pista WHERE id = (SELECT MAX(id) FROM Pista)", null);
    	if (cursor.getCount() > 0){
    		cursor.moveToLast();
    		return cursor.getInt(0);
    	}
    	return -1;
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
