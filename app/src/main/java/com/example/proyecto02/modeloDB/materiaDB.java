package com.example.proyecto02.modeloDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proyecto02.modelo.Materia;
import com.example.proyecto02.modelo.Persona;
import com.example.proyecto02.modelo.Tarea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class materiaDB{
    private static final String DATABASE="proyecto.db";
    ArrayList<Materia> listMat;

    public boolean insertaMateria(Materia mat, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mat_id",mat.getMat_id());
        cv.put("mat_nombre",mat.getMat_nombre());
        cv.put("mat_nivel",mat.getMat_nivel());
        cv.put("mat_descrip",mat.getMat_descrip());
        cv.put("mat_profesor",mat.getMat_profesor());
        cv.put("est_id",mat.getEst_id());
        try {
            int ingrs = (int) db.insert("Materia","mat_id",cv);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al ingreso de materia"+ex.getMessage());
            return false;
        }
    }
    public boolean editMateria(Materia mat, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mat_id",mat.getMat_id());
        cv.put("mat_nombre",mat.getMat_nombre());
        cv.put("mat_nivel",mat.getMat_nivel());
        cv.put("mat_descrip",mat.getMat_descrip());
        cv.put("mat_profesor",mat.getMat_profesor());
        cv.put("est_id",mat.getEst_id());
        try {
            int ingrs = (int) db.update("Materia",cv,"mat_id="+mat.getMat_id(),null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al editar de materia"+ex.getMessage());
            return false;
        }
    }

    public boolean elimMatr(int tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            int ingrs = (int) db.delete("Materia","mat_id ="+tar,null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al elminar tarea"+ex.getMessage());
            return false;
        }
    }

    public ArrayList<Materia> listaMaterias(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        Cursor cursor;
        ArrayList<Materia> lista = new ArrayList<Materia>();
        lista.clear();
        String SQLC="select * from Materia mt JOIN Estudiante et ON et.est_id = mt.est_id JOIN Usuario us ON "+
                "us.usu_id = et.usu_id where us.usu_id = "+id;
        try{
            cursor = conn.getReadableDatabase().rawQuery(SQLC,null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    Materia mat = new Materia();
                    mat.setMat_id(cursor.getInt(0));
                    mat.setMat_nombre(cursor.getString(1));
                    mat.setMat_nivel(cursor.getString(2));
                    mat.setMat_descrip(cursor.getString(3));
                    lista.add(mat);
                }while (cursor.moveToNext());
            }
            conn.close();
            System.out.println("MOSTRANDO LISTA DE MATERIAS:");
            return lista;
        }catch (Exception e){
            System.out.println("Error al cargar MateriaActivity: "+e.getMessage());
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
