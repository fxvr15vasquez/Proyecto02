package com.example.proyecto02.modeloDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.proyecto02.modelo.Tarea;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class tareaDB{

    private static final String DATABASE="proyecto.db";


    public ArrayList<Tarea> selecTars(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        ArrayList<Tarea> lista = new ArrayList<Tarea>();
        lista.clear();
        Cursor cr;
        String SQLC="select * from Tarea ta JOIN Materia ma ON ma.mat_id = ta.mat_id "+
                "JOIN Estudiante et ON et.est_id = ma.est_id JOIN Usuario us ON " +
                " us.usu_id = et.usu_id where us.usu_id = "+id;
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Tarea ta= new Tarea();
                ta.setTar_id(cr.getInt(0));
                ta.setTar_fech_entrega(cr.getString(1));
                ta.setTar_nombre(cr.getString(2));
                ta.setTar_descrip(cr.getString(3));
                //ta.setTar_foto(cr.getBlob(4));
                ta.setMat_id(cr.getInt(5));
                lista.add(ta);
            }while (cr.moveToNext());
        }

        return lista;
    }
    public ArrayList<Tarea> selecTarMat(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        ArrayList<Tarea> lista = new ArrayList<Tarea>();
        lista.clear();
        Cursor cr;
        String SQLC="select * from Tarea ta JOIN Materia ma ON ma.mat_id = ta.mat_id "+
                " where ma.mat_id = "+id;
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Tarea ta= new Tarea();
                ta.setTar_id(cr.getInt(0));
                ta.setTar_fech_entrega(cr.getString(1));
                ta.setTar_nombre(cr.getString(2));
                ta.setTar_descrip(cr.getString(3));
                ta.setTar_foto(cr.getBlob(4));
                ta.setMat_id(cr.getInt(5));
                lista.add(ta);
            }while (cr.moveToNext());
        }

        return lista;
    }
    public Tarea selecTarMatBYID(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        Tarea ta = new Tarea();
        Cursor cr;
        String SQLC="select * from Tarea  where tar_id = "+id;
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
                ta.setTar_id(cr.getInt(0));
                ta.setTar_fech_entrega(cr.getString(1));
                ta.setTar_nombre(cr.getString(2));
                ta.setTar_descrip(cr.getString(3));
                ta.setTar_foto(cr.getBlob(4));
                ta.setMat_id(cr.getInt(5));
        }
        return ta;
    }

    public ArrayList<Tarea> selecTarFech(int id, String fech,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        ArrayList<Tarea> lista = new ArrayList<Tarea>();
        lista.clear();
        Cursor cr;
        String SQLC="select * from Tarea ta JOIN Materia ma ON ma.mat_id = ta.mat_id "+
                "JOIN Estudiante et ON et.est_id = ma.est_id JOIN Usuario us ON " +
                " us.usu_id = et.usu_id where ta.tar_fech_entrega = '"+fech+"' AND us.usu_id = "+id;
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Tarea ta= new Tarea();
                ta.setTar_id(cr.getInt(0));
                ta.setTar_fech_entrega(cr.getString(1));
                ta.setTar_nombre(cr.getString(2));
                ta.setTar_descrip(cr.getString(3));
                ta.setTar_foto(cr.getBlob(4));
                ta.setMat_id(cr.getInt(5));
                lista.add(ta);
            }while (cr.moveToNext());
        }

        return lista;
    }

    public boolean insertTars(Tarea tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tar_id",tar.getTar_id());
        cv.put("tar_fech_entrega",tar.getTar_fech_entrega());
        cv.put("tar_nomb",tar.getTar_nombre());
        cv.put("tar_descrip",tar.getTar_descrip());
        cv.put("tar_foto",tar.getTar_foto());
        cv.put("mat_id",tar.getMat_id());
        try {
            int ingrs = (int) db.insert("Tarea","mat_id",cv);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al ingreso de tarea"+ex.getMessage());
            return false;
        }
    }
    public boolean editTars(Tarea tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tar_fech_entrega",tar.getTar_fech_entrega());
        cv.put("tar_nomb",tar.getTar_nombre());
        cv.put("tar_descrip",tar.getTar_descrip());
        cv.put("tar_foto",tar.getTar_foto());
        try {
            int ingrs = (int) db.update("Tarea",cv,"tar_id ="+tar.getTar_id(),null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al editar de tarea"+ex.getMessage());
            return false;
        }
    }
    public boolean elimTars(int tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            int ingrs = (int) db.delete("Tarea","tar_id ="+tar,null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al eliminar la tarea"+ex.getMessage());
            return false;
        }
    }
    public boolean elimTarsBYMAT(int tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            int ingrs = (int) db.delete("Tarea","mat_id ="+tar,null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al eliminar la tarea"+ex.getMessage());
            return false;
        }
    }

    public int maxTars(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        int max=0;
        Cursor cr;
        String SQLC="select MAX(tar_id) from Tarea";
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            max = cr.getInt(0);
        }
        return max + 1;
    }



}
