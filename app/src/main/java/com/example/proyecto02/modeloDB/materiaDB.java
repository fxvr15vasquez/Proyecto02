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
        SQLi+="insert into materia(mat_id,mat_nombre,mat_nivel,mat_descrip,mat_profesor)";
        SQLi+=" values (1"+",'"+materia.getMat_nombre()+"'"+",'"+materia.getMat_nivel()+"'"+",'"+materia.getMat_descrip()+"'";
        SQLi+=",'"+materia.getMat_profesor()+"')";
        Log.d("materia",String.valueOf(materia.getMat_nombre()));
        Log.d("nivel",String.valueOf(materia.getMat_nivel()));
        Log.d("desc",String.valueOf(materia.getMat_descrip()));
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
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;
        String SQLC="select ROWID as _id,* from Materia mt JOIN Estudiante et ON et.est_id = mat.est_id JOIN Usuario us ON"+
                "us.user_id = et.user_id where user_id = "+id;
        cursor= db.rawQuery(SQLC,null);
        return cursor;
    }
}
