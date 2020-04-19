package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.proyecto02.adaptador.AdaptadorTarea;
import com.example.proyecto02.modeloDB.tareaDB;

public class Materia extends AppCompatActivity {

    ListView listatareas;
    tareaDB tarDB;
    final int id_user = MainActivity.id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        tarDB = new tareaDB();

        listatareas = (ListView) findViewById(R.id.listTareas);
        listatareas.setAdapter(new AdaptadorTarea(this,tarDB.selecTars(id_user,this)));
    }
}
