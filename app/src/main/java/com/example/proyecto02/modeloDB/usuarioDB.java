package com.example.proyecto02.modeloDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto02.modelo.Usuario;

import java.util.ArrayList;

public class usuarioDB{

    private static final String DATABASE="proyecto.db";
    ArrayList<Usuario> listaUsu;



    public ArrayList<Usuario> selecUsers(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista.clear();
        Cursor cr;
        String SQLC="select ROWID as _id,* from Usuario";
        cr= db.rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Usuario us = new Usuario();
                us.setUsu_id(cr.getInt(0));
                us.setUsu_nomb(cr.getString(1));
                us.setUsu_pass(cr.getString(2));
                us.setPer_id(cr.getInt(3));
                lista.add(us);
            }while (cr.moveToNext());
        }
    db.close();
        return lista;
    }
    public ArrayList<String> selecUsersbyID(int id,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        lista.clear();
        Cursor cr;
        String SQLC="select u.usu_nomb, p.per_nomb, p.per_corr_elec  from Usuario u JOIN Persona p ON p.per_id = u.per_id where u.usu_id ="+id;
        cr= db.rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            do{
                lista.add(cr.getString(0));
                lista.add(cr.getString(1));
                lista.add(cr.getString(2));
            }while (cr.moveToNext());
        }
        db.close();
        return lista;
    }

    public int buscar(String pr,Context miContext){
        int x = 0;
        listaUsu = selecUsers(miContext);
        for (Usuario u:listaUsu) {
            if (u.getUsu_nomb().equals(pr)){
                x++;
            }
        }
        return x;
    }

    public boolean insertUser(Usuario usu,Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        if (buscar(usu.getUsu_nomb(),miContext) == 0){
            ContentValues cv = new ContentValues();
            cv.put("usu_id",usu.getUsu_id());
            cv.put("usu_nomb",usu.getUsu_nomb());
            cv.put("usu_cont",usu.getUsu_pass());
            cv.put("per_id",usu.getPer_id());
            try {
                int regis = (int) db.insert("Usuario",null,cv);
                db.close();
                return (regis>0);
            }catch (Exception ex){
                System.out.println("ERROR usuario: "+ex.getMessage());
                db.close();
                return false;
            }

        }else{
            db.close();
            return false;
        }

    }

    public int maxUser(Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        int max=0;
        Cursor cr;
        String SQLC="select MAX(usu_id) from Usuario";
        cr= db.rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            max = cr.getInt(0);
        }
        db.close();
        return max + 1;
    }

    //login
    public int login(String u, String p, Context miContext){
        Conexion conn = new Conexion(miContext,DATABASE,null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        int acep=0;
        Cursor cr;
        String SQLC="select usu_id from Usuario where usu_nomb = '" + u + "' AND usu_cont = '"+ p +"'" ;
        cr= db.rawQuery(SQLC,null);
        if(cr != null && cr.moveToFirst()){
            acep = cr.getInt(0);
        }else{
            acep = 0;
        }
        db.close();
        return acep;
    }
}
