/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xakaton.bicitacora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;

/**
 *
 * @author xianur0
 */
public class DBPunto extends SQLiteOpenHelper{
    String create = "CREATE TABLE Punto (num INTEGER,latitud REAL, longitud REAL, fecha TEXT)";
    
    SQLiteDatabase db;
    
    public static void save_point(String lat,String lon){
        // do... anything
    }

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
    
    public void savePoint(int num_point, double latitude, double longitude, Date date){
        if(db != null){
            db.execSQL("INSERT INTO Punto(num, latitud, longitud, fecha) VALUES ("+num_point+","+latitude+","+longitude+",'"+date+"');");
            System.out.println("Se guardo la coordenada: "+latitude+", "+longitude);
        }
        
    }
    
    public void close(){
        if(db != null)
            db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqld, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
