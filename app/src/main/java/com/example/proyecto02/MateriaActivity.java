package com.example.proyecto02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto02.adaptador.AdaptadorTarea;
import com.example.proyecto02.modelo.Tarea;
import com.example.proyecto02.modeloDB.tareaDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MateriaActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listatareas;
    TextView mater;
    tareaDB tarDB;
    final int id_user = MainActivity.id_usuario;
    int id_mat;
    ArrayList<Tarea> listTar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        tarDB = new tareaDB();

        listatareas = (ListView) findViewById(R.id.listTareas);
        mater = (TextView) findViewById(R.id.txtMatnomb);
        listTar = new ArrayList<Tarea>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mater.setText(extras.getString("mat_nomb"));
            id_mat = extras.getInt("mat_id");
        }
        if(tarDB.selecTars(id_user,this) != null){
            listTar= tarDB.selecTarMat(id_mat,this);
            listatareas.setAdapter(new AdaptadorTarea(this,listTar));
            listatareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    return false;
                }
            });
        }
        FloatingActionButton fab = findViewById(R.id.fabtTarea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Accion del boton", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent inlmateria = new Intent(MateriaActivity.this, IngresoTarea.class);
                inlmateria.putExtra("idM", id_mat);
                startActivity(inlmateria);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btningre:

                break;
            case R.id.btnregis:
                Intent ingretar  = new Intent(MateriaActivity.this, IngresoTarea.class);
                startActivity(ingretar);
                break;
            case R.id.btnSca:

                break;
        }
    }
}
