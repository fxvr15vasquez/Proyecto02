package com.example.proyecto02.modeloDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto02.modelo.Notas;
import com.example.proyecto02.modelo.Persona;
import com.example.proyecto02.modelo.Tarea;

import java.util.ArrayList;

public class notasDB  {
    private static final String DATABASE="proyecto.db";
    ArrayList<Notas> listaNotas;

    public boolean insertNota(Notas not, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("not_id",not.getNot_id());
            cv.put("not_fecha",not.getNot_fech());
            cv.put("not_descrip",not.getNot_descrip());
            cv.put("not_foto",not.getNot_foto());
            cv.put("mat_id",not.getMat_id());
            int ingrs = (int) db.insert("Notas","not_id",cv);
            db.close();
            return (ingrs>0);
    }
    public ArrayList<Notas> selecNotas(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        ArrayList<Notas> lista = new ArrayList<Notas>();
        lista.clear();
        Cursor cr;
        String SQLC="select * from Notas";
        cr = conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Notas not = new Notas();
                not.setNot_id(cr.getInt(0));
                not.setNot_foto(cr.getBlob(1));
                not.setNot_descrip(cr.getString(2));
                not.setNot_fech(cr.getString(3));
                not.setMat_id(cr.getInt(4));
                lista.add(not);
            }while (cr.moveToNext());
        }
        return lista;
    }

    public ArrayList<Notas> selecTarNotbyMat(int id, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        ArrayList<Notas> lista = new ArrayList<Notas>();
        lista.clear();
        Cursor cr;
        String SQLC="select * from Notas nt JOIN Materia ma ON ma.mat_id = nt.mat_id "+
                " where nt.mat_id = "+id;
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Notas nt = new Notas();
                nt.setNot_id(cr.getInt(0));
                nt.setNot_foto(cr.getBlob(1));
                nt.setNot_descrip(cr.getString(2));
                nt.setNot_fech(cr.getString(3));
                nt.setMat_id(cr.getInt(4));
                lista.add(nt);
            }while (cr.moveToNext());
        }

        return lista;
    }

    public boolean editNot(Notas not, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("not_fecha",not.getNot_fech());
        cv.put("not_descrip",not.getNot_descrip());
        cv.put("not_foto",not.getNot_foto());
        try {
            int ingrs = (int) db.update("Notas",cv,"not_id ="+not.getNot_id(),null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al editar de Nota"+ex.getMessage());
            return false;
        }
    }
    public boolean elimNot(int tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            int ingrs = (int) db.delete("Notas","not_id ="+tar,null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al eliminar la nota"+ex.getMessage());
            return false;
        }
    }
    public boolean elimNotBYMAT(int tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            int ingrs = (int) db.delete("Notas","mat_id ="+tar,null);
            db.close();
            return (ingrs>0);
        }catch (SQLException ex){
            System.out.println("Error al eliminar la nota"+ex.getMessage());
            return false;
        }
    }
    public int maxNot(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        int max=0;
        Cursor cr;
        String SQLC="select MAX(not_id) from Notas";
        cr= conn.getReadableDatabase().rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            max = cr.getInt(0);
        }
        return max + 1;
    }
}
