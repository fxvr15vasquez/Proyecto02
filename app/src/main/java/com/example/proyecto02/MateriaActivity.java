package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto02.adaptador.AdaptadorNotas;
import com.example.proyecto02.adaptador.AdaptadorTarea;
import com.example.proyecto02.modelo.Notas;
import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.notasDB;
import com.example.proyecto02.modeloDB.tareaDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MateriaActivity extends AppCompatActivity {

    ListView listatareas;
    ListView listanotas;
    TextView mater;
    tareaDB tarDB;
    notasDB noDB;
    final int id_user = MainActivity.id_usuario;
    int id_mat;
    ArrayList<Tarea> listTar;
    ArrayList<Notas> listNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        tarDB = new tareaDB();
        noDB = new notasDB();

        listatareas = (ListView) findViewById(R.id.listTareas);
        listanotas = (ListView) findViewById(R.id.listvNotas);
        mater = (TextView) findViewById(R.id.txtMatnomb);
        listTar = new ArrayList<Tarea>();
        listNot = new ArrayList<Notas>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mater.setText(extras.getString("mat_nomb"));
            id_mat = extras.getInt("mat_id");
        }
        if(tarDB.selecTars(id_user,this) != null){
            listTar= tarDB.selecTarMat(id_mat,this);
            listatareas.setAdapter(new AdaptadorTarea(this,listTar));
            listatareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent tareaedit = new Intent(MateriaActivity.this, EditarTarea.class);
                    tareaedit.putExtra("idTar", listTar.get(position).getTar_id());
                    startActivity(tareaedit);
                }
            });
        }
        if(noDB.selecNotasBymatID(id_mat,this) != null){
            listNot= noDB.selecNotasBymatID(id_mat,this);
            listanotas.setAdapter(new AdaptadorNotas(this,listNot));
            listanotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent notasedit = new Intent(MateriaActivity.this, EditarNotas.class);
                    notasedit.putExtra("idNot", listNot.get(position).getNot_id());
                    startActivity(notasedit);
                }
            });
        }
        FloatingActionButton fab = findViewById(R.id.fabtTarea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ingreso de tareas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent inlmateria = new Intent(MateriaActivity.this, IngresoTarea.class);
                inlmateria.putExtra("idM", id_mat);
                startActivity(inlmateria);
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fbaaddNotas);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ingreso de notas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent inlnotas = new Intent(MateriaActivity.this, Ingreso_Notas.class);
                inlnotas.putExtra("idN", id_mat);
                startActivity(inlnotas);
            }
        });
    }
    @Override
    public void onResume() {
        if(tarDB.selecTars(id_user,this) != null){
            listTar= tarDB.selecTarMat(id_mat,this);
            listatareas.setAdapter(new AdaptadorTarea(this,listTar));
        }
        if(noDB.selecNotasBymatID(id_mat,this) != null){
            listNot= noDB.selecNotasBymatID(id_mat,this);
            listanotas.setAdapter(new AdaptadorNotas(this,listNot));
        }
        super.onResume();
    }
}
