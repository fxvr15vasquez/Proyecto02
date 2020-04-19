package com.example.proyecto02.modeloDB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyecto02.modelo.Tarea;

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
                //ta.setTar_foto(cr.getBlob(4));
                ta.setMat_id(cr.getInt(5));
                lista.add(ta);
            }while (cr.moveToNext());
        }

        return lista;
    }

    public boolean insertTars(Tarea tar,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
            String SQLi="";
            SQLi+="insert into Tarea (tar_id,tar_fech_entrega,tar_nomb,tar_descrip,tar_foto,mat_id)";
            SQLi+=" values (";
            SQLi+=" "+tar.getTar_id()+" ";
            SQLi+=",'"+tar.getTar_fech_entrega()+"'";
            SQLi+=",'"+tar.getTar_nombre()+"'";
            SQLi+=", '"+tar.getTar_descrip()+"'";
            SQLi+=", "+tar.getTar_foto()+" ";
            SQLi+=", "+tar.getMat_id()+" ";
            SQLi+=")";
            try {
                conn.getWritableDatabase().execSQL(SQLi);
                return true;
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
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
