package com.example.proyecto02.modeloDB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proyecto02.modelo.Materia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class materiaDB{
    private static final String DATABASE="proyecto.db";
    ArrayList<Materia> listMat;

    public String insertaMateria(Materia materia, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String SQLi="";
        SQLi+="insert into materia(mat_id,mat_nombre,mat_nivel,mat_descrip,mat_profesor,est_id)";
        SQLi+=" values ("+materia.getMat_id()+",'"+materia.getMat_nombre()+"'"+",'"+materia.getMat_nivel()+"'"+",'"+materia.getMat_descrip()+"'";
        SQLi+=",'"+materia.getMat_profesor()+"'"+materia.getEst_id()+" )";
        try {
            db.execSQL(SQLi);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return null;
    }

    public Cursor listaMaterias(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        Cursor cursor;
        String SQLC="select ROWID as _id,* from Materia mt JOIN Estudiante et ON et.est_id = mt.est_id JOIN Usuario us ON "+
                "us.usu_id = et.usu_id where us.usu_id = "+id;
        try{
            cursor= conn.getReadableDatabase().rawQuery(SQLC,null);
            conn.close();
            return cursor;
        }catch (Exception e){
            System.out.println("Error al cargar Materia: "+e.getMessage());
            return null;
        }
    }
    public int maxUser(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        int max=0;
        Cursor cr;
        String SQLC="select MAX(mat_id) from Materia";
        cr= db.rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            max = cr.getInt(0);
        }
        db.close();
        return max + 1;
    }
}
